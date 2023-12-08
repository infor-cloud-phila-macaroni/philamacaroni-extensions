/**
 * README
 * Name: EXT009MI.Add
 * Extend M3 Table EXTEDI Add
 * Description: Add EXTEDI Data
 * Date	    Changed By      	               Description
 * 20231208 Mohamed Adel - Hatem Abdellatif  Update EXTEDI Data
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
   * Input Fields of EXTEDI Table
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
  private String iDIVI;
  private String iWHLO;
  private	String iCUNO;
  private String iFACI;
  private String iORNO;
  private String iTYPE;
  private String iCUNM;
  private String iBOID;
  private	String iRES1;
  private String iOTYP;
  private String iSTAT;
  private String iREAS;
  private String iORDT;
  private String iRLDZ;
  private String iOCNO;
  
  /**
	 * Global variables
	 */
  int changeNumber = 0;
	int sequence = 0;
	private String currentFormattedDate;
	private int currentDate = 0;
	private int currentTime = 0;
	private String currentTimeString;
	private String API_ORDT;
	private String API_RLDZ;
  
  
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
	iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
	iDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
	iWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
	iFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim();
	iORNO = mi.inData.get("ORNO") == null ? "" : mi.inData.get("ORNO").trim();
	iTYPE = mi.inData.get("TYPE") == null ? "" : mi.inData.get("TYPE").trim();
	iCUNO = mi.inData.get("CUNO") == null ? "" : mi.inData.get("CUNO").trim();
	iBOID = mi.inData.get("BOID") == null ? "" : mi.inData.get("BOID").trim();
	iRES1 = mi.inData.get("RES1") == null ? "" : mi.inData.get("RES1").trim();
	iREAS = mi.inData.get("REAS") == null ? "" : mi.inData.get("REAS").trim();
	iOTYP = mi.inData.get("OTYP") == null ? "" : mi.inData.get("OTYP").trim();
	iSTAT = mi.inData.get("STAT") == null ? "" : mi.inData.get("STAT").trim();
	iORDT = mi.inData.get("ORDT") == null ? "" : mi.inData.get("ORDT").trim();
	iRLDZ = mi.inData.get("RLDZ") == null ? "" : mi.inData.get("RLDZ").trim();
	
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
	  
	// Add a new record in EXTEDI table.
	addRecord();
	
  }
  
  /**
	 * addRecord - To add a new record in Table EXTD89
	 *
	 * @param  null
	 * @return boolean
	 */
  private void addRecord() {
	DBAction query = database.table("EXTEDI")
							 .index("10")
							 .build();
	  
	DBContainer container = query.getContainer();
	container.setInt("EXCONO", iCONO.toInteger());
	container.set("EXORNO", iORNO);
	container.set("EXCUNO", iCUNO);
	container.set("EXCUNM", iCUNM);
	container.setInt("EXOCNO", iOCNO.toInteger());
	
	if(iRES1 != "" && iRES1 != "?"){
	  container.set("EXRES1", iRES1);
	}
	
	if(iREAS != "" && iREAS != "?"){
	  container.set("EXREAS", iREAS);
	}
	
	if(iOTYP != "" && iOTYP != "?"){
	  container.set("EXOTYP", iOTYP);
	}
	
	if(iSTAT != "" && iSTAT != "?"){
	  container.set("EXSTAT", iSTAT);
	}
	
	if(iORDT != "" && iORDT != "?"){
	  container.setInt("EXORDT", iORDT.toInteger());
	}
	
	if(iRLDZ != "" && iRLDZ != "?"){
	  container.setInt("EXRLDZ", iRLDZ.toInteger());
	}
	
	if(iBOID != "" && iBOID != "?"){
	  container.set("EXBOID", iBOID);
	}
	
	if(iTIME != "" && iTIME != "?"){
	  container.set("EXTIME", iTIME);
	}
	
	if(iDIVI != "" && iDIVI != "?"){
	  container.set("EXDIVI", iDIVI);
	}
	
	if(iFACI != "" && iFACI != "?"){
	  container.set("EXFACI", iFACI);
	}
	
	if(iWHLO != "" && iWHLO != "?"){
	  container.set("EXWHLO", iWHLO);
	}
	
	if(iTYPE != "" && iTYPE != "?"){
	  container.set("EXTYPE", iTYPE);
	}
	
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
	
	container.set("EXRGDT", currentDate);
	container.set("EXRGTM", currentTime);
	container.set("EXCHID", program.getUser());
	container.setInt("EXCHNO", 1);

	query.insert(container);
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
	  * validateDivision - Validate division
	  *
	  * @param  null
	  * @return boolean
	  */
  def boolean validateDivision(){
	boolean validRecord = false;
	def parameters = ["CONO" : iCONO, "DIVI" : iDIVI];
	Closure<?> handler = { Map<String, String> response ->
	  if (response.containsKey('errorMsid')){
		validRecord = false;
	  } else {
		validRecord = true;
	  }
	};
	
	miCaller.call("MNS100MI", "GetBasicData", parameters, handler);
	return validRecord;
  }
  
  /**
	  * validateOrderNumber - Validate Order Number
	  *
	  * @param  null
	  * @return boolean
	  */
  def boolean validateOrderNumber(){
	boolean validRecord = false;
	def parameters = ["CONO" : iCONO, "ORNO" : iORNO];
	Closure<?> handler = { Map<String, String> response ->
	  if (!response.containsKey('errorMsid')){
		validRecord = true;
		if(iORDT == "" || iORDT == "?" || iORDT == null){
		  API_ORDT = response.ORDT;
		}
		if(iRLDZ == "" || iRLDZ == "?" || iRLDZ == null){
		  API_RLDZ = response.RLDZ;
		}
	  } else {
		validRecord = false;
	  }
	};
	
	miCaller.call("OIS100MI", "GetHead", parameters, handler);
	return validRecord;
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
	try {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	  ZonedDateTime.parse(strDateTime, formatter);
	} catch (DateTimeParseException e) {
	  return false;
	}
	return true;
 }
 
  /**
	  * validCustomer - Validate transaction Customer
	  *
	  * @param  null
	  * @return boolean
	  */
  def boolean validCustomer(){
	boolean validRecord = false;
	def parameters = ["CONO" : iCONO, "CUNO" : iCUNO];
	Closure<?> handler = { Map<String, String> response ->
	  if (!response.containsKey('errorMsid')){
		iCUNM = response.CUNM;
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
	  * validCustomer - Validate transaction Customer
	  *
	  * @param  null
	  * @return boolean
	  */
  def int getLatestLine(){
	int validRecord = 0;
	int _OCNO = 0;
	DBAction query = database.table("EXTEDI")
							 .index("00")
							 .selection("EXCONO", "EXCUNO", "EXORNO","EXOCNO")
							 .reverse()
							 .build();
							 
	DBContainer container = query.getContainer();
	container.setInt("EXCONO", iCONO.toInteger());
	container.set("EXCUNO", iCUNO);
	container.set("EXORNO", iORNO);
	
	Closure<?> readData = { DBContainer dataContainer  ->
	  _OCNO = dataContainer.get("EXOCNO");
	}
	
	if (!query.readAll(container, 3, 1, readData)) {
	  validRecord = 0;
	}
	else{
	  validRecord = _OCNO + 1;
	}
	
	return validRecord;
  }
  
  /**
	  * validate - Validate input
	  *
	  * @param  null
	  * @return boolean
	  */
	def boolean validate() {
	  
	  if (iCONO == "") {
			iCONO = (Integer)program.getLDAZD().CONO;
		} else if (!validateCompany()) {
			mi.error("Company number " + iCONO + " is invalid");
			return false;
		}
		
		if (iDIVI == "") {
			iDIVI = program.LDAZD.DIVI.toString();
		} else if (!validateDivision()) {
			mi.error("Division " + iDIVI + " is invalid");
			return false;
		}

	if(iCUNO == ""){
	  mi.error("Customer Number is Missing")
	  return false;
	} else if (!validCustomer()) {
	  mi.error("Customer " + iCUNO + " is invalid");
		  return false;
	}
	
	if (mi.in.get("ORNO") == null) {
	  mi.error("Order Number is Missing")
	  return false;
	} else if (!validateOrderNumber()) {
	  mi.error("Invalid Order number "+iORNO);
	  return false;
	}
		
		if (iDIVI == "") {
			iDIVI = program.LDAZD.DIVI.toString();
		} else if (!validateDivision()) {
			mi.error("Division " + iDIVI + " is invalid");
			return false;
		}
		
		if(!(iFACI == null || iFACI == "")){
	  if (!validFacility()) {
		mi.error("Facility data " + iFACI + " is invalid");
		return false;
	  }
	}
		
		if(!(iTIME == null || iTIME == "")){
	  if (!validateTimeStamp(iTIME)) {
		mi.error("Invalid timestamp: " + iTIME);
		return false;
	  }
	} else {
	  iTIME = currentFormattedDate + "T" + currentTimeString + "Z";
	}

	if(!(iWHLO == null || iWHLO == "")){
	  if (!validWarehouse()) {
		mi.error("Warehouse data " + iWHLO + " is invalid");
		return false;
	  }
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
	
	if(!(iORDT == null || iORDT == "")){
	  if (!validateDate("YYYYMMdd", iORDT)) {
		mi.error("Invalid Order date: " + iORDT);
		return false;
	  }
	}
	else{
	  iORDT = API_ORDT;
	}
	
	if(!(iRLDZ == null || iRLDZ == "")){
	  if (!validateDate("YYYYMMdd", iRLDZ)) {
		mi.error("Invalid Requested Delivery date: " + iRLDZ);
		return false;
	  }
	}
	else{
	  iRLDZ = API_RLDZ;
	}
	
	iOCNO = getLatestLine();
		return true;
	}
 
}