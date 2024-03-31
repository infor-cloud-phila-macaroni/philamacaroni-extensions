/**
 * README
 * Name: EXT012MI.Delete
 * Extend M3 Table EXT001 Delete
 * Description: Delete EXT001 Existing Data
 * Date	    Changed By      	               Description
 * 20240330 Mohamed Adel - Hatem Abdellatif  Delete EXT001 Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class Delete extends ExtendM3Transaction {
  private final MIAPI mi;
  private final DatabaseAPI database;
  private final ProgramAPI program;
  private final LoggerAPI logger;
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
	
  /**
   * Input Fields of EXT001 Table
   */  
  private String iCONO;
  private String iCUNO;
  private String iFVDT;
  private String iDUNS;
  
  public Delete(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
    this.logger = logger;
  }
  
  
  public void main() {
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
    iCUNO = mi.inData.get("CUNO") == null ? "" : mi.inData.get("CUNO").trim();
    iFVDT = mi.inData.get("FVDT") == null ? "" : mi.inData.get("FVDT").trim();
    iDUNS = mi.inData.get("DUNS") == null ? "" : mi.inData.get("DUNS").trim();
    
    if (!validate()) {
		  return;
		}
    
    // Delete an existing record in EXT001 table.
    deleteRecord();
  }
  
    /**
	 * deleteRecord - To delete an existing record in Table EXT001
	 *
	 * @param  null
	 * @return boolean
	 */
  private void deleteRecord() {
    DBAction query = database.table("EXT001")
                     .index("00")
                     .build();
		
  	DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
    container.set("EXCUNO", iCUNO);
    container.set("EXDUNS", iDUNS);
    container.set("EXFVDT", iFVDT.toInteger());
  	
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
  private boolean validateCompany(){
    boolean validRecord = false;
    Map<String, String> parameters = ["CONO" : iCONO];
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
  
  /** validCustomer - Validate transaction Customer
  	*
  	* @param  null
  	* @return boolean
  	*/
  private boolean validCustomer(){
    boolean validRecord = false;
    Map<String, String> parameters = ["CONO" : iCONO, "CUNO" : iCUNO];
    Closure<?> handler = { Map<String, String> response ->
      if (!response.containsKey('errorMsid')){
        validRecord = true;
      }
      else{
        validRecord = false;
      }
    };
    miCaller.call("CRS610MI", "GetBasicData", parameters, handler);
    return validRecord;
  }
  
  /**
   * validateDate - Validate input
   *
   * @param  String - formatStr
   * @param  String - dateStr
   * @return boolean
   */
  private boolean validateDate(String formatStr, String dateStr){
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
	private boolean validate() {

		if (iCONO == "") {
			iCONO = (Integer)program.getLDAZD().CONO;
		} else if (!iCONO.isInteger()) {
			mi.error("Company number " + iCONO + " is invalid");
			return false;
		}
		
		if(iCUNO == ""){
      if (!validCustomer()) {
        mi.error("Invalid Customer: " + iCUNO);
        return false;
      }
    }
    
    if(iFVDT == ""){
      if (!validateDate("YYYYMMdd", iFVDT)) {
        mi.error("Invalid Valid From date: " + iFVDT);
        return false;
      }
    }

		return true;
	}
  
}