/**
 * README
 * Name: EXT012MI.Get
 * Extend M3 Table EXT001 Get data
 * Description: Get EXT001 Existing Data
 * Date	    Changed By      	               Description
 * 202403030 Mohamed Adel - Hatem Abdellatif  Get EXT001 Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class Get extends ExtendM3Transaction {
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
  private String iCUNO;
  private String iDUNS;
  private String iFVDT;
  private String iDIVI;
  
  public Get(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
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
    iDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    
    if (!validate()) {
		  return;
		}
    
    // Fetch Data from Table EXT001
    fetchRecord();
  }
  
    /**
	 * fetchRecord - To get an existing record in Table EXT001
	 *
	 * @param  null
	 * @return boolean
	 */
  private void fetchRecord() {
    DBAction query = database.table("EXT001")
                             .index("00")
                             .selectAllFields()
                             .build();
                                      
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
    container.set("EXDIVI", iDIVI);
    container.set("EXCUNO", iCUNO);
    container.set("EXDUNS", iDUNS);
    container.set("EXFVDT", iFVDT.toInteger());
      
    if (query.read(container)) {
      mi.outData.put("CONO", container.get("EXCONO").toString());
      mi.outData.put("DIVI", container.get("EXDIVI").toString());
      mi.outData.put("CUNO", container.get("EXCUNO").toString());
	    mi.outData.put("FVDT", container.get("EXFVDT").toString());
      mi.outData.put("PRGM", container.get("EXPRGM").toString());
      mi.outData.put("DUNS", container.get("EXDUNS").toString());
      mi.outData.put("CITY", container.get("EXCITY").toString());
      mi.outData.put("STAT", container.get("EXSTAT").toString());
      mi.outData.put("CZIP", container.get("EXCZIP").toString());
      mi.outData.put("BYGR", container.get("EXBYGR").toString());
      mi.outData.put("CUGR", container.get("EXCUGR").toString());
      mi.outData.put("CTCH", container.get("EXCTCH").toString());
	    mi.outData.put("BRKR", container.get("EXBRKR").toString());
      mi.outData.put("USNM", container.get("EXUSNM").toString());
      mi.outData.put("A120", container.get("EXA120").toString());
      mi.outData.put("A121", container.get("EXA121").toString());
      mi.outData.put("A122", container.get("EXA122").toString());
      mi.outData.put("A123", container.get("EXA123").toString());
      mi.outData.put("A124", container.get("EXA124").toString());
      mi.outData.put("STS2", container.get("EXSTS2").toString());
      mi.outData.put("A256", container.get("EXA256").toString());
      mi.outData.put("MENA", container.get("EXMENA").toString());
      mi.outData.put("N030", container.get("EXN030").toString());
      mi.outData.put("N130", container.get("EXN130").toString());
      mi.outData.put("DAT1", container.get("EXDAT1").toString());
      mi.outData.put("TVDT", container.get("EXTVDT").toString());
      mi.outData.put("CHID", container.get("EXCHID").toString());
      mi.outData.put("LMDT", container.get("EXLMDT").toString());
      mi.outData.put("CHNO", container.get("EXCHNO").toString());
      mi.outData.put("RGDT", container.get("EXRGDT").toString());
      mi.outData.put("LMTS", container.get("EXLMTS").toString());
      mi.outData.put("RGTM", container.get("EXRGTM").toString());
    } else {
      mi.error("Record does not exist in EXT001 database table!");
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
	  * validate - Validate input
	  *
	  * @param  null
	  * @return boolean
	  */
	private boolean validate() {

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
		
		return true;
	}
  
}