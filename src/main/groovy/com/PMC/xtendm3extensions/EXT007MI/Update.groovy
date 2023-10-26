/**
 * README
 * Name: EXT007MI.Update
 * Extend M3 Table EXTD89 Update
 * Description: Add EXTD89 Data
 * Date	    Changed By      	               Description
 * 20230929 Hatem Abdellatif - Mohamed Adel  Update EXTD89 Data
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class Update extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final LoggerAPI logger
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
  
  /**
   * Input Fields of EXTD89 Table
   */
  private	String iA030;
  private	String iA130;
  private	String iA230;
  private	String iA330;
  private	String iA430;
  private	String iA530;
  private	String iA630;
  private	String iA730;
  private	String iA830;
  private	String iA930;
  private	String iB030;
  private	String iB130;
  private	String iB230;
  private	String iB330;
  private	String iB430;
  private	String iB530;
  private	String iTIME;
  private String iN030;
  private String iN130;
  private String iN230;
  private String iN330;
  private String iN430;
  private String iN530;
  private String iD030;
  private String iD130;
  private String iD230;
  private String iD330;
  private String iD430;
  private String iCONO;
  private String iDOLN;
  private String iDIVI;
  private String iWHLO;
  private	String iTWLO;
  private String iFACI;
  private String iTRNR;
  private String iTYPE;
  
  /**
	 * Global variables
	 */
  String currentFacility = program.LDAZD.FACI.toString();
  int changeNumber = 0;
	int sequence = 0;
	private String currentFormattedDate;
	private int currentDate = 0;
	private int currentTime = 0;
	private String currentTimeString;
  
  
  public Update(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
    this.logger = logger;
    
  }
  
  public void main() {
    iA030 = mi.inData.get("A030") == null ? "" : mi.inData.get("A030").trim();
    iA130 = mi.inData.get("A130") == null ? "" : mi.inData.get("A130").trim();
    iA230 = mi.inData.get("A230") == null ? "" : mi.inData.get("A230").trim();
    iA330 = mi.inData.get("A330") == null ? "" : mi.inData.get("A330").trim();
    iA430 = mi.inData.get("A430") == null ? "" : mi.inData.get("A430").trim();
    iA530 = mi.inData.get("A530") == null ? "" : mi.inData.get("A530").trim();
    iA630 = mi.inData.get("A630") == null ? "" : mi.inData.get("A630").trim();
    iA730 = mi.inData.get("A730") == null ? "" : mi.inData.get("A730").trim();
    iA830 = mi.inData.get("A830") == null ? "" : mi.inData.get("A830").trim();
    iA930 = mi.inData.get("A930") == null ? "" : mi.inData.get("A930").trim();
    iB030 = mi.inData.get("B030") == null ? "" : mi.inData.get("B030").trim();
    iB130 = mi.inData.get("B130") == null ? "" : mi.inData.get("B130").trim();
    iB230 = mi.inData.get("B230") == null ? "" : mi.inData.get("B230").trim();
    iB330 = mi.inData.get("B330") == null ? "" : mi.inData.get("B330").trim();
    iB430 = mi.inData.get("B430") == null ? "" : mi.inData.get("B430").trim();
    iB530 = mi.inData.get("B530") == null ? "" : mi.inData.get("B530").trim();
    iN030 = mi.inData.get("N030") == null ? "" : mi.inData.get("N030").trim();
    iN130 = mi.inData.get("N130") == null ? "" : mi.inData.get("N130").trim();
    iN230 = mi.inData.get("N230") == null ? "" : mi.inData.get("N230").trim();
    iN330 = mi.inData.get("N330") == null ? "" : mi.inData.get("N330").trim();
    iN430 = mi.inData.get("N430") == null ? "" : mi.inData.get("N430").trim();
    iN530 = mi.inData.get("N530") == null ? "" : mi.inData.get("N530").trim();
    iD030 = mi.inData.get("D030") == null ? "" : mi.inData.get("D030").trim();
    iD130 = mi.inData.get("D130") == null ? "" : mi.inData.get("D130").trim();
    iD230 = mi.inData.get("D230") == null ? "" : mi.inData.get("D230").trim();
    iD330 = mi.inData.get("D330") == null ? "" : mi.inData.get("D330").trim();
    iD430 = mi.inData.get("D430") == null ? "" : mi.inData.get("D430").trim();
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
    iDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    iWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    iTWLO = mi.inData.get("TWLO") == null ? "" : mi.inData.get("TWLO").trim();
    iFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim();
    iTRNR = mi.inData.get("TRNR") == null ? "" : mi.inData.get("TRNR").trim();
    iTYPE = mi.inData.get("TYPE") == null ? "" : mi.inData.get("TYPE").trim();
    iDOLN = mi.inData.get("DOLN") == null ? "" : mi.inData.get("DOLN").trim();
    
    currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
    DateTimeFormatter dtfString = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    LocalTime localTime = LocalTime.now();
    currentTime = dtf.format(localTime).toInteger();
    currentFormattedDate = dateFormatter.format(LocalDate.now());
    currentTimeString = dtfString.format(localTime);
    
    if (!validate()) {
		  return;
		}
  	
    // Update an existing record in EXTD89 table.
    updateRecord();
    
  }
  
  /**
	 * addRecord - To add a new record in Table EXTD89
	 *
	 * @param  null
	 * @return boolean
	 */
  private void updateRecord() {
    
    DBAction query = database.table("EXTD89")
                             .index("00")
                             .build();
      
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
  	container.set("EXTRNR", iTRNR);
  	container.set("EXTYPE", iTYPE);
  	container.setInt("EXDOLN", iDOLN.toInteger());
  	container.set("EXDIVI", iDIVI);
  	
  	// Update changed information
    if(!query.readLock(container, updateCallBack)){
      mi.error("Record does not exist");
      return;
    }
  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
  
    if(iFACI != "" && iFACI != "?"){
      lockedResult.set("EXFACI", iFACI);
    } else {
      lockedResult.set("EXFACI", currentFacility);
    }

    if(iD030 != "" && iD030 != "?"){
      lockedResult.setInt("EXD030", iD030.toInteger());
    }

    if(iD130 != "" && iD130 != "?"){
      lockedResult.setInt("EXD130", iD130.toInteger());
    }

    if(iD230 != "" && iD230 != "?"){
      lockedResult.setInt("EXD230", iD230.toInteger());
    }

    if(iD330 != "" && iD330 != "?"){
      lockedResult.setInt("EXD330", iD330.toInteger());
    }

    if(iD430 != "" && iD430 != "?"){
      lockedResult.setInt("EXD430", iD430.toInteger());
    }

    if(iN030 != "" && iN030 != "?"){
      lockedResult.setLong("EXN030", iN030.toLong());
    }

    if(iN130 != "" && iN130 != "?"){
      lockedResult.setLong("EXN130", iN130.toLong());
    }

    if(iN230 != "" && iN230 != "?"){
      lockedResult.setLong("EXN230", iN230.toLong());
    }

    if(iN330 != "" && iN330 != "?"){
      lockedResult.setLong("EXN330", iN330.toLong());
    }

    if(iN430 != "" && iN430 != "?"){
      lockedResult.setLong("EXN430", iN430.toLong());
    }

    if(iN530 != "" && iN530 != "?"){
      lockedResult.setLong("EXN530", iN530.toLong());
    }

    if(iA030 != "" && iA030 != "?"){
      lockedResult.set("EXA030", iA030);
    }

    if(iA130 != "" && iA130 != "?"){
      lockedResult.set("EXA130", iA130);
    }

    if(iA230 != "" && iA230 != "?"){
      lockedResult.set("EXA230", iA230);
    }

    if(iA330 != "" && iA330 != "?"){
      lockedResult.set("EXA330", iA330);
    }
  
    if(iA430 != "" && iA430 != "?"){
      lockedResult.set("EXA430", iA430);
    }

    if(iA530 != "" && iA530 != "?"){
      lockedResult.set("EXA530", iA530);
    }

    if(iA630 != "" && iA630 != "?"){
      lockedResult.set("EXA630", iA630);
    }

    if(iA730 != "" && iA730 != "?"){
      lockedResult.set("EXA730", iA730);
    }

    if(iA830 != "" && iA830 != "?"){
      lockedResult.set("EXA830", iA830);
    }

    if(iA930 != "" && iA930 != "?"){
      lockedResult.set("EXA930", iA930);
    }

    if(iB030 != "" && iB030 != "?"){
      lockedResult.set("EXB030", iB030);
    }

    if(iB130 != "" && iB130 != "?"){
      lockedResult.set("EXB130", iB130);
    }

    if(iB230 != "" && iB230 != "?"){
      lockedResult.set("EXB230", iB230);
    }

    if(iB330 != "" && iB330 != "?"){
      lockedResult.set("EXB330", iB330);
    }

    if(iB430 != "" && iB430 != "?"){
      lockedResult.set("EXB430", iB430);
    }

    if(iB530 != "" && iB530 != "?"){
      lockedResult.set("EXB530", iB530);
    }

    if(iTWLO != "" && iTWLO != "?"){
      lockedResult.set("EXTWLO", iTWLO);
    }

    if(iWHLO != "" && iWHLO != "?"){
      lockedResult.set("EXWHLO", iWHLO);
    }

    lockedResult.set("EXRGDT", currentDate);
    lockedResult.set("EXRGTM", currentTime);
		lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO")+1);
		lockedResult.set("EXCHID", program.getUser());
    lockedResult.update();
  }
  
  /**
  	* validWarehouse - Validate transaction warehouse
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validWarehouse(){
    boolean validRecord = false;
    def parameters = ["WHLO" : iWHLO];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    miCaller.call("MMS005MI", "GetWarehouse", parameters, handler);
    return validRecord;
  }
  
  /**
  	* validToWarehouse - Validate transaction warehouse
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validToWarehouse(){
    boolean validRecord = false;
    def parameters = ["WHLO" : iTWLO];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    miCaller.call("MMS005MI", "GetWarehouse", parameters, handler);
    return validRecord;
  }
  
  /**
  	* validFacility - Validate transaction facility
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validFacility(){
    boolean validRecord = false;
    def parameters = ["FACI" : iFACI];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    miCaller.call("CRS008MI", "Get", parameters, handler);
    return validRecord;
  }
  
  /**
  	* validOrderNumber - Validate order number
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validOrderNumber(){
    boolean validRecord = false;
    def parameters = ["TRNR" : iTRNR];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    miCaller.call("MMS100MI", "GetHead", parameters, handler);
    return validRecord;
  }
  
  /**
   * validateDate - Validate input
   *
   * @param  String - strDate
   * @return boolean
   */
  private boolean validateDate(String strDate){
    String strDateRegEx =  "\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|[3][01])";
      
    if(strDate.matches(strDateRegEx)){
      return true;
    } else {
      return false;
    }
  }
   
  /**
   * validateTime - Validate input
   *
   * @param  String - strDate
   * @return boolean
   */
  private boolean validateTime(String strDate){
    String strDateRegEx =  "(([0-1]?[0-9])|(2[0-3]))[0-5][0-9][0-5][0-9]";
      
    if(strDate.matches(strDateRegEx)){
      return true;
    } else {
      return false;
    }
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
		
		if (iDIVI == "") {
			iDIVI = program.LDAZD.DIVI.toString();
		}
    
    if (!validWarehouse()) {
      mi.error("Warehouse data " + iWHLO + " is invalid");
		  return false;
    }
    
    if (!validToWarehouse()) {
      mi.error("To Warehouse data " + iTWLO + " is invalid");
		  return false;
    }
    
    if (!validFacility()) {
      mi.error("Facility data " + iFACI + " is invalid");
		  return false;
    }
    
    if(!(iD030 == null || iD030 == "")){
      if (!validateDate(iD030)) {
        mi.error("Invalid date: " + iD030);
        return false;
      }
    }
    
    if(!(iD130 == null || iD130 == "")){
      if (!validateDate(iD130)) {
        mi.error("Invalid date: " + iD130);
        return false;
      }
    }
    
    if(!(iD230 == null || iD230 == "")){
      if (!validateDate(iD230)) {
        mi.error("Invalid date: " + iD230);
        return false;
      }
    }
    
    if(!(iD330 == null || iD330 == "")){
      if (!validateDate(iD330)) {
        mi.error("Invalid date: " + iD330);
        return false;
      }
    }
    
    if(!(iD430 == null || iD430 == "")){
      if (!validateDate(iD430)) {
        mi.error("Invalid date: " + iD430);
        return false;
      }
    }

		return true;
	}
 
}