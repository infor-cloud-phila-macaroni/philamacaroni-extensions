/**
 * README
 * Name: EXT007MI.Add
 * Extend M3 Table EXTD89 Add
 * Description: Add EXTD89 Data
 * Date	    Changed By      	               Description
 * 20230929 Mohamed Adel - Hatem Abdellatif  Update EXTD89 Data
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;
import java.time.format.*;

public class Add extends ExtendM3Transaction {
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
  private String iDOLN;
  private String iWHLO;
  private	String iTWLO;
  private String iFACI;
  private String iTRNR;
  private String iTYPE;
  
  /**
	 * Global variables
	 */
	int currentCompany = (Integer)program.getLDAZD().CONO;
	String currentDivision = program.LDAZD.DIVI.toString();
  int changeNumber = 0;
	int sequence = 0;
	private String currentFormattedDate;
	private int currentDate = 0;
	private int currentTime = 0;
	private String currentTimeString;
  
  
  public Add(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
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
    iTIME = mi.inData.get("TIME") == null ? "" : mi.inData.get("TIME").trim();
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
    iWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    iTWLO = mi.inData.get("TWLO") == null ? "" : mi.inData.get("TWLO").trim();
    iFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim();
    iTRNR = mi.inData.get("TRNR") == null ? "" : mi.inData.get("TRNR").trim();
    iTYPE = mi.inData.get("TYPE") == null ? "" : mi.inData.get("TYPE").trim();
    
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
  	
    // Add a new record in EXTD89 table.
    addRecord();
    
  }
  
  /**
	 * addRecord - To add a new record in Table EXTD89
	 *
	 * @param  null
	 * @return boolean
	 */
  private void addRecord() {
    DBAction query = database.table("EXTD89")
                             .index("10")
                             .build();
      
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", currentCompany);
    container.set("EXDIVI", currentDivision);
    container.set("EXTRNR", iTRNR);
    container.set("EXFACI", iFACI);
    container.set("EXWHLO", iWHLO);
    container.set("EXTYPE", iTYPE);
    container.setInt("EXDOLN", iDOLN.toInteger());
    container.set("EXTIME", iTIME);
    
    if(iD030 != "" && iD030 != "?"){
      container.setInt("EXD030", iD030.toInteger());
    }
    
    if(iD130 != "" && iD130 != "?"){
      container.setInt("EXD130", iD130.toInteger());
    }
    
    if(iD230 != "" && iD230 != "?"){
      container.setInt("EXD230", iD230.toInteger());
    }
    
    if(iD330 != "" && iD330 != "?"){
      container.setInt("EXD330", iD330.toInteger());
    }
    
    if(iD430 != "" && iD430 != "?"){
      container.setInt("EXD430", iD430.toInteger());
    }
    
    if(iN030 != "" && iN030 != "?"){
      container.setLong("EXN030", iN030.toLong());
    }
    
    if(iN130 != "" && iN130 != "?"){
      container.setLong("EXN130", iN130.toLong());
    }
    
    if(iN230 != "" && iN230 != "?"){
      container.setLong("EXN230", iN230.toLong());
    }
    
    if(iN330 != "" && iN330 != "?"){
      container.setLong("EXN330", iN330.toLong());
    }
    
    if(iN430 != "" && iN430 != "?"){
      container.setLong("EXN430", iN430.toLong());
    }
    
    if(iN530 != "" && iN530 != "?"){
      container.setLong("EXN530", iN530.toLong());
    }
    
    if(iA030 != "" && iA030 != "?"){
      container.set("EXA030", iA030);
    }
    
    if(iA130 != "" && iA130 != "?"){
      container.set("EXA130", iA130);
    }
    
    if(iA230 != "" && iA230 != "?"){
      container.set("EXA230", iA230);
    }
    
    if(iA330 != "" && iA330 != "?"){
      container.set("EXA330", iA330);
    }
    
    if(iA430 != "" && iA430 != "?"){
      container.set("EXA430", iA430);
    }
    
    if(iA530 != "" && iA530 != "?"){
      container.set("EXA530", iA530);
    }
    
    if(iA630 != "" && iA630 != "?"){
      container.set("EXA630", iA630);
    }
    
    if(iA730 != "" && iA730 != "?"){
      container.set("EXA730", iA730);
    }
    
    if(iA830 != "" && iA830 != "?"){
      container.set("EXA830", iA830);
    }
    
    if(iA930 != "" && iA930 != "?"){
      container.set("EXA930", iA930);
    }
    
    if(iB030 != "" && iB030 != "?"){
      container.set("EXB030", iB030);
    }
    
    if(iB130 != "" && iB130 != "?"){
      container.set("EXB130", iB130);
    }
    
    if(iB230 != "" && iB230 != "?"){
      container.set("EXB230", iB230);
    }
    
    if(iB330 != "" && iB330 != "?"){
      container.set("EXB330", iB330);
    }
    
    if(iB430 != "" && iB430 != "?"){
      container.set("EXB430", iB430);
    }
    
    if(iB530 != "" && iB530 != "?"){
      container.set("EXB530", iB530);
    }
    
    if(iTWLO != "" && iTWLO != "?"){
      container.set("EXTWLO", iTWLO);
    }

    container.set("EXRGDT", currentDate);
    container.set("EXRGTM", currentTime);
    container.set("EXCHID", program.getUser());
    container.setInt("EXCHNO", 1);

    Closure<?> insertCallBack = {}

    query.insert(container);
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
  	* getLatestLine - Get the latest number per DO and Type.
  	*
  	* @param  null
  	* @return int
  	*/
  def int getLatestLine(){
    int validRecord = 0;
    int _DOLN = 0;
    DBAction query = database.table("EXTD89")
                             .index("00")
                             .selection("EXCONO", "EXTRNR", "EXTYPE","EXDOLN")
                             .reverse()
                             .build();
                             
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", currentCompany);
    container.set("EXTRNR", iTRNR);
    container.set("EXTYPE", iTYPE);
    
    Closure<?> readData = { DBContainer dataContainer  ->
      _DOLN = dataContainer.get("EXDOLN");
    }
    
    if (!query.readAll(container, 3, 1, readData)) {
      validRecord = 0;
    }
    else{
      validRecord = _DOLN + 1;
    }
    
    return validRecord;
  }
  
  /**
   * validateDate - Validate input
   *
   * @param  String - formatStr
   * @param  String - dateStr
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
   * validateTimeStamp - Validate input
   *
   * @param  String - strDate
   * @return boolean
   */
  def boolean validateTimeStamp(String strDateTime){
    logger.debug("Validate date");
    try {
  	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
      ZonedDateTime.parse(strDateTime, formatter);
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
		
		if(!(iTIME == null || iTIME == "")){
      if (!validateTimeStamp(iTIME)) {
        mi.error("Invalid timestamp: " + iTIME);
        return false;
      }
    } else {
      iTIME = currentFormattedDate + "T" + currentTimeString + "Z";
    }
    
    if (mi.in.get("TRNR") == null) {
      mi.error("DO Number is Missing")
      return false;
    } else if (mi.in.get("TYPE") == null) {
      mi.error("Uber BOD Type is Missing")
      return false;
    }
    
    if (!validOrderNumber()) {
      mi.error("Order number: " + iTRNR + " is invalid");
		  return false;
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
      if (!validateDate("YYYYMMdd", iD030)) {
        mi.error("Invalid date: " + iD030);
        return false;
      }
    }
    
    if(!(iD130 == null || iD130 == "")){
      if (!validateDate("YYYYMMdd", iD130)) {
        mi.error("Invalid date: " + iD130);
        return false;
      }
    }
    
    if(!(iD230 == null || iD230 == "")){
      if (!validateDate("YYYYMMdd", iD230)) {
        mi.error("Invalid date: " + iD230);
        return false;
      }
    }
    
    if(!(iD330 == null || iD330 == "")){
      if (!validateDate("YYYYMMdd", iD330)) {
        mi.error("Invalid date: " + iD330);
        return false;
      }
    }
    
    if(!(iD430 == null || iD430 == "")){
      if (!validateDate("YYYYMMdd", iD430)) {
        mi.error("Invalid date: " + iD430);
        return false;
      }
    }
    
    iDOLN = getLatestLine();

		return true;
	}
 
}