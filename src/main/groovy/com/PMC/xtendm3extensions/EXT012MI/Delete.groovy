/**
 * README
 * Name: EXT012MI.Delete
 * Extend M3 Table EXT001 Delete
 * Description: Delete EXT001 Data
 * Date	    Changed By      	               Description
 * 20240211 Mohamed Adel - Hatem Abdellatif  Delete EXT001 Data
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
   * Input Fields of EXTWCH Table
   */  
  private String iCONO;
  private String iA050;
  
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
    iA050 = mi.inData.get("A050") == null ? "" : mi.inData.get("A050").trim();
    
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
    DBAction query = database.table("EXT001")
                     .index("00")
                     .build();
		
  	DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
    container.set("EXA050", iA050);
  	
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

		return true;
	}
  
}