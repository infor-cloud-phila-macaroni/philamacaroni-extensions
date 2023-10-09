/**
 * README
 * Name: EXT989MI.Add
 * Extend M3 Table EXTD89 Add
 * Description: Add EXTD89 Data
 * Date	    Changed By      	               Description
 * 20230929 Mohamed Adel - Hatem Abdellatif  Update EXTD89 Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

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
  private	String inA030;
  private	String inA130;
  private	String inA230;
  private	String inA330;
  private	String inA430;
  private	String inA530;
  private	String inA630;
  private	String inA730;
  private	String inA830;
  private	String inA930;
  private	String inB030;
  private	String inB130;
  private	String inB230;
  private	String inB330;
  private	String inB430;
  private	String inB530;
  private	String intime;
  private long inN030=0;
  private long inN130=0;
  private long inN230=0;
  private long inN330=0;
  private long inN430=0;
  private long inN530=0;
  private int inD030=0;
  private int inD130=0;
  private int inD230=0;
  private int inD330=0;
  private int inD430=0;
  private int inCONO = 0;
  private int inDONR = 0;
  private String inDIVI;
  private String inWHLO;
  private	String inTWLO;
  private String inFACI;
  private String inTRNR;
  private String inTYPE;
  
  
  public Add(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
    this.logger = logger;
    
  }
  
  public void main() {
    if (mi.inData.get("CONO") == null || mi.inData.get("CONO").trim() =="") {
        inCONO = Integer.parseInt(program.LDAZD.CONO.toString())
    } 
    else {
        inCONO = mi.inData.get("CONO") as int
    }    
    
    if (mi.inData.get("DIVI") == null || mi.inData.get("DIVI").trim() == "") {
      inDIVI = program.LDAZD.DIVI.toString()
    } else {
      inDIVI = mi.inData.get("DIVI").trim()
    }
    int currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
    DateTimeFormatter dtfString = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalTime localTime = LocalTime.now();
    int currentTime = dtf.format(localTime).toInteger();
    String currentTimeString = dtfString.format(localTime);
    inTRNR = (mi.in.get("TRNR")==null) ? "" : mi.in.get("TRNR") //Get DO Number
	  inFACI = (mi.in.get("FACI")==null) ? "" : mi.in.get("FACI") //Get FACI
	  inWHLO = (mi.in.get("WHLO")==null) ? "" : mi.in.get("WHLO") //Get WHLO
	  intime = (mi.in.get("TIME")==null) ? "" : mi.in.get("TIME")
	  inTWLO = (mi.in.get("TWLO")==null) ? "" : mi.in.get("TWLO")
    //Get All Alpha Field Values:
    inTYPE = (mi.in.get("TYPE")==null) ? "" : mi.in.get("TYPE") //Get Alpha Field 0
	  inA030 = (mi.in.get("A030")==null) ? "" : mi.in.get("A030") //Get Alpha Field 0
    inA130 = (mi.in.get("A130")==null) ? "" : mi.in.get("A130") //Get Alpha Field 1
    inA230 = (mi.in.get("A230")==null) ? "" : mi.in.get("A230") //Get Alpha Field 2
    inA330 = (mi.in.get("A330")==null) ? "" : mi.in.get("A330") //Get Alpha Field 3
    inA430 = (mi.in.get("A430")==null) ? "" : mi.in.get("A430") //Get Alpha Field 4
    inA530 = (mi.in.get("A530")==null) ? "" : mi.in.get("A530") //Get Alpha Field 5
    inA630 = (mi.in.get("A630")==null) ? "" : mi.in.get("A630") //Get Alpha Field 6
    inA730 = (mi.in.get("A730")==null) ? "" : mi.in.get("A730") //Get Alpha Field 7
    inA830 = (mi.in.get("A830")==null) ? "" : mi.in.get("A830") //Get Alpha Field 8
    inA930 = (mi.in.get("A930")==null) ? "" : mi.in.get("A930") //Get Alpha Field 9
    inB030 = (mi.in.get("B030")==null) ? "" : mi.in.get("B030") //Get Alpha Field 0 - long
    inB130 = (mi.in.get("B130")==null) ? "" : mi.in.get("B130") //Get Alpha Field 1 - long
    inB230 = (mi.in.get("B230")==null) ? "" : mi.in.get("B230") //Get Alpha Field 2 - long
    inB330 = (mi.in.get("B330")==null) ? "" : mi.in.get("B330") //Get Alpha Field 3 - long
    inB430 = (mi.in.get("B430")==null) ? "" : mi.in.get("B430") //Get Alpha Field 4 - long
    inB530 = (mi.in.get("B530")==null) ? "" : mi.in.get("B530") //Get Alpha Field 5 - long
    //Get Date Values:
    inD030 = (mi.in.get("D030")==null) ? 0 : mi.in.get("D030") as int //Date Field 0
    inD130 = (mi.in.get("D130")==null) ? 0 : mi.in.get("D130") as int //Date Field 1
    inD230 = (mi.in.get("D230")==null) ? 0 : mi.in.get("D230") as int //Date Field 2
    inD330 = (mi.in.get("D330")==null) ? 0 : mi.in.get("D330") as int //Date Field 3
    inD430 = (mi.in.get("D430")==null) ? 0 : mi.in.get("D430") as int //Date Field 4
    //Get Numeric Integer Field Values:
  	inN030 = (mi.in.get("N030") == null) ? 0 : mi.in.get("N030") as long  // Numeric Field 0
  	inN130 = (mi.in.get("N130") == null) ? 0 : mi.in.get("N130") as long // Numeric Field 1
  	inN230 = (mi.in.get("N230") == null) ? 0 : mi.in.get("N230") as long // Numeric Field 2
  	inN330 = (mi.in.get("N330") == null) ? 0 : mi.in.get("N330") as long // Numeric Field 3
  	inN430 = (mi.in.get("N430") == null) ? 0 : mi.in.get("N430") as long // Numeric Field 4
  	inN530 = (mi.in.get("N530") == null) ? 0 : mi.in.get("N530") as long // Numeric Field 5
  	
    if(!(intime != "" && intime != "?")){
      intime = currentDate + "T" + currentTimeString;
    }
    if (mi.in.get("TRNR") == null) {
      mi.error("DO Number is Missing")
      return
    }
    else if (mi.in.get("TYPE") == null) {
      mi.error("Uber BOD Type is Missing")
      return
    }
    if (!validWarehouse()) {
      mi.error("Warehouse data " + inWHLO + " is invalid");
		  return
    }
    if (!validToWarehouse()) {
      mi.error("To Warehouse data " + inTWLO + " is invalid");
		  return
    }
    if (!validFacility()) {
      mi.error("Facility data " + inFACI + " is invalid");
		  return
    }
    inDONR = GetLatestDONR();
        DBAction dbaEXTD89 = database.table("EXTD89")
          .index("10")
          .build()
    DBContainer conEXTD89 = dbaEXTD89.getContainer()
    conEXTD89.setInt("EXCONO", inCONO)
    conEXTD89.set("EXDIVI", inDIVI)
    conEXTD89.set("EXTRNR", inTRNR)
    conEXTD89.set("EXFACI", inFACI)
    conEXTD89.set("EXWHLO", inWHLO)
    conEXTD89.set("EXTYPE", inTYPE)
    conEXTD89.set("EXDONR", inDONR)
    conEXTD89.set("EXTIME", intime)
    if(inD030 != 0 && inD030 != "?"){
      conEXTD89.setInt("EXD030", inD030)
    }
    if(inD130 != 0 && inD130 != "?"){
      conEXTD89.setInt("EXD130", inD130)
    }
    if(inD230 != 0 && inD230 != "?"){
      conEXTD89.setInt("EXD230", inD230)
    }
    if(inD330 != 0 && inD330 != "?"){
      conEXTD89.setInt("EXD330", inD330)
    }
    if(inD430 != 0 && inD430 != "?"){
     conEXTD89.setInt("EXD430", inD430)
    }
    if(inN030 != 0 && inN030 != "?"){
      conEXTD89.setLong("EXN030", inN030)
    }
    if(inN130 != 0 && inN130 != "?"){
      conEXTD89.setLong("EXN130", inN130)
    }
    if(inN230 != 0 && inN230 != "?"){
      conEXTD89.setLong("EXN230", inN230)
    }
    if(inN330 != 0 && inN330 != "?"){
      conEXTD89.setLong("EXN330", inN330)
    }
    if(inN430 != 0 && inN430 != "?"){
      conEXTD89.setLong("EXN430", inN430)
    }
    if(inN530 != 0 && inN530 != "?"){
      conEXTD89.setLong("EXN530", inN530)
    }
    if(inA030 != "" && inA030 != "?"){
      conEXTD89.set("EXA030", inA030)
    }
    if(inA130 != "" && inA130 != "?"){
      conEXTD89.set("EXA130", inA130)
    }
    if(inA230 != "" && inA230 != "?"){
      conEXTD89.set("EXA230", inA230)
    }
    if(inA330 != "" && inA330 != "?"){
      conEXTD89.set("EXA330", inA330)
    }
    if(inA430 != "" && inA430 != "?"){
      conEXTD89.set("EXA430", inA430)
    }
    if(inA530 != "" && inA530 != "?"){
      conEXTD89.set("EXA530", inA530)
    }
    if(inA630 != "" && inA630 != "?"){
      conEXTD89.set("EXA630", inA630)
    }
    if(inA730 != "" && inA730 != "?"){
      conEXTD89.set("EXA730", inA730)
    }
    if(inA830 != "" && inA830 != "?"){
      conEXTD89.set("EXA830", inA830)
    }
    if(inA930 != "" && inA930 != "?"){
      conEXTD89.set("EXA930", inA930)
    }
    if(inB030 != "" && inB030 != "?"){
      conEXTD89.set("EXB030", inB030)
    }
    if(inB130 != "" && inB130 != "?"){
      conEXTD89.set("EXB130", inB130)
    }
    if(inB230 != "" && inB230 != "?"){
      conEXTD89.set("EXB230", inB230)
    }
    if(inB330 != "" && inB330 != "?"){
      conEXTD89.set("EXB330", inB330)
    }
    if(inB430 != "" && inB430 != "?"){
      conEXTD89.set("EXB430", inB430)
    }
    if(inB530 != "" && inB530 != "?"){
      conEXTD89.set("EXB530", inB530)
    }
    if(inTWLO != "" && inTWLO != "?"){
      conEXTD89.set("EXTWLO", inTWLO)
    }

    conEXTD89.set("EXRGDT", currentDate)
    conEXTD89.set("EXRGTM", currentTime)
    conEXTD89.set("EXCHID", program.getUser())
    conEXTD89.setInt("EXCHNO", 1)

    Closure<?> insertCallBack = {}

    dbaEXTD89.insert(conEXTD89, insertCallBack)
  }
    /**
  	* validWarehouse - Validate transaction warehouse
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validWarehouse(){
    boolean validRecord = false;
    def parameters = ["WHLO" : inWHLO];
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
  def boolean validToWarehouse(){
    boolean validRecord = false;
    def parameters = ["WHLO" : inTWLO];
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
    def parameters = ["FACI" : inFACI];
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
  	* GetDONRData - Get the latest number per DO and Type.
  	*
  	* @param  null
  	* @return int
  	*/
  def int GetLatestDONR(){
    int validRecord = 0;
    int _DONR = 0;
    DBAction dbaEXTD89 = database.table("EXTD89").index("00").selection("EXCONO", "EXTRNR", "EXTYPE","EXDONR").build();
    DBContainer conEXTD89 = dbaEXTD89.getContainer();
    conEXTD89.set("EXCONO", inCONO);
    conEXTD89.set("EXTRNR", inTRNR);
    conEXTD89.set("EXTYPE", inTYPE);
         Closure<?> readEXTD89Data = { DBContainer DataEXTD89 ->
          _DONR = DataEXTD89.get("EXDONR");
     }
    if (!dbaEXTD89.readAll(conEXTD89, 2, readEXTD89Data)) {
        validRecord =0;
    }
    else{
      validRecord = _DONR+1;
    }
    
    
    return validRecord;
  }
 
}