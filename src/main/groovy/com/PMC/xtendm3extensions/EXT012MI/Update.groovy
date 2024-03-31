/**
 * README
 * Name: EXT012MI.Update
 * Extend M3 Table EXT001 Update
 * Description: Update Existing custom data to EXT001 table
 * Date	    Changed By      	               Description
 * 20240330 Hatem Abdellatif - Mohamed Adel  Add EXT001 Data
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
 
public class Update extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final LoggerAPI logger
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
  
  /**
   * Input Fields of EXT001 Table
   */
  private String iCONO;
  private String iDIVI;
  private String iCUNO;
  private String iFVDT;
  private String iPRGM;
  private String iDUNS;
  private String iCITY;
  private String iSTAT;
  private String iCZIP;
  private String iBYGR;
  private String iCUGR;
  private String iCTCH;
  private String iBRKR;
  private String iA120;
  private String iA121;
  private String iA122;
  private String iA123;
  private String iA124;
  private String iSTS2;
  private String iA256;
  private String iMENA;
  private String iN030;
  private String iN130;
  private String iDAT1;
  private String iTVDT;

  /**
	 * Global variables
	 */
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
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
    iDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    iCUNO = mi.inData.get("CUNO") == null ? "" : mi.inData.get("CUNO").trim();
    iFVDT = mi.inData.get("FVDT") == null ? "" : mi.inData.get("FVDT").trim();
    iPRGM = mi.inData.get("PRGM") == null ? "" : mi.inData.get("PRGM").trim();
    iDUNS = mi.inData.get("DUNS") == null ? "" : mi.inData.get("DUNS").trim();
    iCITY = mi.inData.get("CITY") == null ? "" : mi.inData.get("CITY").trim();
    iSTAT = mi.inData.get("STAT") == null ? "" : mi.inData.get("STAT").trim();
    iCZIP = mi.inData.get("CZIP") == null ? "" : mi.inData.get("CZIP").trim();
    iBYGR = mi.inData.get("BYGR") == null ? "" : mi.inData.get("BYGR").trim();
    iCUGR = mi.inData.get("CUGR") == null ? "" : mi.inData.get("CUGR").trim();
    iCTCH = mi.inData.get("CTCH") == null ? "" : mi.inData.get("CTCH").trim();
    iBRKR = mi.inData.get("BRKR") == null ? "" : mi.inData.get("BRKR").trim();
    iA120 = mi.inData.get("A120") == null ? "" : mi.inData.get("A120").trim();
    iA121 = mi.inData.get("A121") == null ? "" : mi.inData.get("A121").trim();
    iA122 = mi.inData.get("A122") == null ? "" : mi.inData.get("A122").trim();
    iA123 = mi.inData.get("A123") == null ? "" : mi.inData.get("A123").trim();
    iA124 = mi.inData.get("A124") == null ? "" : mi.inData.get("A124").trim();
    iSTS2 = mi.inData.get("STS2") == null ? "" : mi.inData.get("STS2").trim();
    iA256 = mi.inData.get("A256") == null ? "" : mi.inData.get("A256").trim();
    iMENA = mi.inData.get("MENA") == null ? "" : mi.inData.get("MENA").trim();
    iN030 = mi.inData.get("N030") == null ? "" : mi.inData.get("N030").trim();
    iN130 = mi.inData.get("N130") == null ? "" : mi.inData.get("N130").trim();
    iDAT1 = mi.inData.get("DAT1") == null ? "" : mi.inData.get("DAT1").trim();
    iTVDT = mi.inData.get("TVDT") == null ? "" : mi.inData.get("TVDT").trim();

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
  	
    // Update an existing record in EXT001 table.
    updateRecord();
  }
  
  /**
   * updates record in the EXT001 table
   *
   */
  private void updateRecord() {
    DBAction query = database.table("EXT001").index("00").build();
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
    container.set("EXDIVI", iDIVI);
    container.set("EXCUNO", iCUNO);
    container.set("EXDUNS", iDUNS);
    container.set("EXFVDT", iFVDT.toInteger());
    
    // Update changed information
    if(!query.readLock(container, updateCallBack)){
      mi.error("Record does not exist");
      return;
    }

  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
    
    lockedResult.setInt("EXCONO", iCONO.toInteger());
    lockedResult.set("EXDIVI", iDIVI);
    lockedResult.set("EXCUNO", iCUNO);
    lockedResult.set("EXDUNS", iDUNS);
    lockedResult.set("EXFVDT", iFVDT.toInteger());
      
    if(iPRGM != "" && iPRGM != "?"){
      lockedResult.set("EXPRGM", iPRGM);
    }
    
    if(iCITY != "" && iCITY != "?"){
      lockedResult.set("EXCITY", iCITY);
    }
    
    if(iSTAT != "" && iSTAT != "?"){
      lockedResult.set("EXSTAT", iSTAT);
    }
    
    if(iCZIP != "" && iCZIP != "?"){
      lockedResult.set("EXCZIP", iCZIP);
    }
    
    if(iBYGR != "" && iBYGR != "?"){
      lockedResult.set("EXBYGR", iBYGR);
    }
    
    if(iCUGR != "" && iCUGR != "?"){
      lockedResult.set("EXCUGR", iCUGR);
    }
    
    if(iCTCH != "" && iCTCH != "?"){
      lockedResult.set("EXCTCH", iCTCH);
    }
    
    if(iBRKR != "" && iBRKR != "?"){
      lockedResult.set("EXBRKR", iBRKR);
    }
    
    if(iA120 != "" && iA120 != "?"){
      lockedResult.set("EXA120", iA120);
    }
    
    if(iA121 != "" && iA121 != "?"){
      lockedResult.set("EXA121", iA121);
    }
    
    if(iA122 != "" && iA122 != "?"){
      lockedResult.set("EXA122", iA122);
    }
    
    if(iA123 != "" && iA123 != "?"){
      lockedResult.set("EXA123", iA123);
    }
    
    if(iA124 != "" && iA124 != "?"){
      lockedResult.set("EXA124", iA124);
    }
    
    if(iSTS2 != "" && iSTS2 != "?"){
      lockedResult.set("EXSTS2", iSTS2);
    }
    
    if(iA256 != "" && iA256 != "?"){
      lockedResult.set("EXA256", iA256);
    }
    
    if(iMENA != "" && iMENA != "?"){
      lockedResult.set("EXMENA", iMENA);
    }
        
    if(iN030 != "" && iN030 != "?"){
      lockedResult.setDouble("EXN030", iN030.toDouble());
    }
    
    if(iN130 != "" && iN130 != "?"){
      lockedResult.setDouble("EXN130", iN130.toDouble());
    }
  
    if(iDAT1 != "" && iDAT1 != "?"){
      lockedResult.setInt("EXDAT1", iDAT1.toInteger());
    }
    
    if(iTVDT != "" && iTVDT != "?"){
      lockedResult.setInt("EXTVDT", iTVDT.toInteger());
    }

    lockedResult.set("EXLMDT", currentDate);
    lockedResult.set("EXRGDT", currentDate);
    lockedResult.set("EXRGTM", currentTime);
    lockedResult.set("EXCHID", program.getUser());
    lockedResult.setInt("EXCHNO", 1);
    lockedResult.update();
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
	  * validateDivision - Validate division
	  *
	  * @param  null
	  * @return boolean
	  */
  private boolean validateDivision(){
    boolean validRecord = false;
    Map<String, String> parameters = ["CONO" : iCONO, "DIVI" : iDIVI];
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
	  
	  if ((iPRGM == null || iPRGM == "") && (iCITY == null || iCITY == "") && (iBRKR == null || iBRKR == "") &&
	      (iSTAT == null || iSTAT == "") && (iCZIP == null || iCZIP == "") && (iBYGR == null || iBYGR == "") &&
	      (iCUGR == null || iCUGR == "") && (iCTCH == null || iCTCH == "") && (iA120 == null || iA120 == "") &&
	      (iA121 == null || iA121 == "") && (iA122 == null || iA122 == "") && (iA123 == null || iA123 == "") &&
	      (iA124 == null || iA124 == "") && (iSTS2 == null || iSTS2 == "") && (iA256 == null || iA256 == "") && 
		    (iMENA == null || iMENA == "") && (iN030 == null || iN030 == "") && (iN130 == null || iN130 == "") && 
		    (iDAT1 == null || iDAT1 == "") && (iTVDT == null || iTVDT == "")) {
	    mi.error("Please enter data into the input fields.");
			return false; 
	    
	  }
	  
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
		
		if(!(iDAT1 == null || iDAT1 == "")){
      if (!validateDate("YYYYMMdd", iDAT1)) {
        mi.error("Invalid date: " + iDAT1);
        return false;
      }
    }
    
    if(!(iTVDT == null || iTVDT == "")){
      if (!validateDate("YYYYMMdd", iTVDT)) {
        mi.error("Invalid date: " + iTVDT);
        return false;
      }
    }
		return true;
	}
}