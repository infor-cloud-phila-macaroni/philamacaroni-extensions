/**
 * README
 * Name: EXT010MI.DeleteDate
 * Extend M3 Table EXTWCH DelteData
 * Description: Delete EXTWCH Data
 * Date	    Changed By      	               Description
 * 20231127 Mohamed Adel - Hatem Abdellatif  Delete EXTWCH Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class DeleteData extends ExtendM3Transaction {
  private final MIAPI mi;
  private final DatabaseAPI database;
  private final ProgramAPI program;
  private final LoggerAPI logger;
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
	
  /**
   * Input Fields of EXTWCH Table
   */  
  private String iCONO;
  private String iFZIP;
  private String iTZIP;
  private String iDEML;
  private String iFDTE;
  
  public DeleteData(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
    this.logger = logger;
  }
  
  public void main() {
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
    iFZIP = mi.inData.get("FZIP") == null ? "" : mi.inData.get("FZIP").trim();
    iTZIP = mi.inData.get("TZIP") == null ? "" : mi.inData.get("TZIP").trim();
    iDEML = mi.inData.get("DEML") == null ? "" : mi.inData.get("DEML").trim();
    iFDTE = mi.inData.get("FDTE") == null ? "" : mi.inData.get("FDTE").trim();
    
    if (!validate()) {
		  return;
		}
    
    // Delete an existing record in EXTWCH table.
    deleteRecord();
  }
  
  /**
	 * deleteRecord - To delete an existing record in Table EXTD89
	 *
	 * @param  null
	 * @return boolean
	 */
  private void deleteRecord() {
    DBAction query = database.table("EXTWCH")
                     .index("00")
                     .build();
		
  	DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
    container.set("EXFZIP", iFZIP);
    container.set("EXTZIP", iTZIP);
    container.setInt("EXDEML", iDEML.toInteger());
    container.setInt("EXFDTE", iFDTE.toInteger());
  	
  	Closure<?> deleteCallback = { LockedResult lockedResult ->
      lockedResult.delete();
    }
    
    if(!query.readLock(container, deleteCallback)) {
      mi.error("Record does not exist");
      return;
		}
  }
  
 /**
	 * validateCompany - Validate comapny
	 *
	 * @param  null
	 * @return boolean
	 */
  def boolean validateCompany(){
    boolean validRecord = false;
    def parameters = ["CONO" : iCONO];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    
    miCaller.call("MNS095MI", "Get", parameters, handler);
    return validRecord;
  }
  
 /**
	 * validateCompany - Validate Date
	 *
	 * @param  null
	 * @return boolean
	 */
  def boolean validateDate(String formatStr, String dateStr){
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr).BASIC_ISO_DATE;
      LocalDate.parse(dateStr, formatter);
    } catch (DateTimeParseException e) {
      logger.debug("Exception: " + e.toString());
      return false;
    }
    return true;
  }
  
  /**
	  * validate - Validate input
	  *
	  * @param  null
	  * @return boolean
	  */
	boolean validate() {

		if (iCONO == "") {
			iCONO = (Integer)program.getLDAZD().CONO;
		} else if (!iCONO.isInteger()) {
			mi.error("Company number " + iCONO + " is invalid");
			return false;
		}
    
    if(!(iFDTE == null || iFDTE == "")){
      if (!validateDate("YYYYMMdd", iFDTE)) {
        mi.error("Invalid date: " + iFDTE);
        return false;
      }
    }

		return true;
	}
  
}