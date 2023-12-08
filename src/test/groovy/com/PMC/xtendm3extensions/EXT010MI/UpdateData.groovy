/**
 * README
 * Name: EXT010MI.UpdateData
 * Extend M3 Table EXTWCH UpdateData
 * Description: Update EXTWCH Data
 * Date	    Changed By      	               Description
 * 20231127 Mohamed Adel - Hatem Abdellatif  Update EXTWCH Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class UpdateData extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final LoggerAPI logger
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
	/**
   * Input Fields of EXTWCH Table
  */
  private String iCONO;
  private String iDIVI;
  private String iFZIP;
  private String iTZIP;
  private String iWHLO;
  private String iWHNM;
  private String iCOST;
  private String iDEML;
  private String iFDTE;
  private String iTDTE;
  private String iA030;
  private String iA130;
  private String iA230;
  private String iN030;
  private String iN130;
  /**
	 * Global variables
	 */
  int changeNumber = 0;
	int sequence = 0;
	private String currentFormattedDate;
	private int currentDate = 0;
	private int currentTime = 0;
	private String currentTimeString;
  
  public UpdateData(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
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
	iWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
	iCOST = mi.inData.get("COST") == null ? "" : mi.inData.get("COST").trim();
	iDEML = mi.inData.get("DEML") == null ? "" : mi.inData.get("DEML").trim();
	iFDTE = mi.inData.get("FDTE") == null ? "" : mi.inData.get("FDTE").trim();
	iTDTE = mi.inData.get("TDTE") == null ? "" : mi.inData.get("TDTE").trim();
	iA030 = mi.inData.get("A030") == null ? "" : mi.inData.get("A030").trim();
	iA130 = mi.inData.get("A130") == null ? "" : mi.inData.get("A130").trim();
	iA230 = mi.inData.get("A230") == null ? "" : mi.inData.get("A230").trim();
	iN030 = mi.inData.get("N030") == null ? "" : mi.inData.get("N030").trim();
	iN130 = mi.inData.get("N130") == null ? "" : mi.inData.get("N130").trim();
	
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
	  
	// Update an existing record in EXTWCH table.
	updateRecord();
  }
  
  /**
   * updates record in the EXTWCH table
   *
   */
  private void updateRecord() {
	logger.debug("Start database update");
	DBAction query = database.table("EXTWCH").index("00").build();
	DBContainer container = query.getContainer();

	container.setInt("EXCONO", iCONO.toInteger());
	container.set("EXFZIP", iFZIP);
	container.set("EXTZIP", iTZIP);
	container.setInt("EXDEML", iDEML.toInteger());
	container.setInt("EXFDTE", iFDTE.toInteger());
	
	// Update changed information
	if(!query.readLock(container, updateCallBack)){
	  mi.error("Record does not exist");
	  return;
	}

  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
	if(iTDTE != "" && iTDTE != "?"){
	  lockedResult.setInt("EXTDTE", iTDTE.toInteger());
	}
	
	if(iCOST != "" && iCOST != "?"){
	  lockedResult.setDouble("EXCOST", iCOST.toDouble());
	}
	
	if(iWHLO != "" && iWHLO != "?"){
	  lockedResult.set("EXWHLO", iWHLO);
	}
	
	if(iWHNM != "" && iWHNM != "?"){
	  lockedResult.set("EXWHNM", iWHNM);
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
	
	if(iN030 != "" && iN030 != "?"){
	  lockedResult.setDouble("EXN030", iN030.toDouble());
	}
	
	if(iN130 != "" && iN130 != "?"){
	  lockedResult.setDouble("EXN130", iN130.toDouble());
	}
  
	lockedResult.set("EXLMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
		lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
		lockedResult.set("EXCHID", program.getUser());
	lockedResult.update();
  }
  
  /**
	  * validateCompany - Validate comapny via MNS095MI API.
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
		iWHNM = response.WHNM;
		validRecord = true;
	  }
	};
	miCaller.call("MMS005MI", "GetWarehouse", parameters, handler);
	return validRecord;
  }
  
  /**
   * validateDate - Validate input Date
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
		
		if(!(iWHLO == null || iWHLO == "")){
	  if (!validWarehouse()) {
		mi.error("Warehouse data " + iWHLO + " is invalid");
			return false;
	  }
		} else {
		  iWHNM = "";
		}
	
	if(!(iFDTE == null || iFDTE == "")){
	  if (!validateDate("YYYYMMdd", iFDTE)) {
		mi.error("Invalid date: " + iFDTE);
		return false;
	  }
	}
	
	if(!(iTDTE == null || iTDTE == "")){
	  if (!validateDate("YYYYMMdd", iTDTE)) {
		mi.error("Invalid date: " + iTDTE);
		return false;
	  }
	}

		return true;
	}
}