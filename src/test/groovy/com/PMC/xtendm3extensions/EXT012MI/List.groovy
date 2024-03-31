/**
 * README
 * Name: EXT012MI.List
 * Extend M3 Table EXT001 List data
 * Description: List Existing EXT001 Data
 * Date	    Changed By      	               Description
 * 20240330 Mohamed Adel - Hatem Abdellatif  List EXT001 Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class List extends ExtendM3Transaction {
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
  private String iDIVI;
  
  public List(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
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
    iDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    
    if (!validate()) {
		  return;
		}
    
    // Fetch Data from Table EXT001
    fetchRecords();
  }
  
  /**
	 * fetchRecords - To List existing records in Table EXT001
	 *
	 * @param  null
	 * @return boolean
	 */
  private void fetchRecords() {
    DBAction query = database.table("EXT001")
                             .index("10")
                             .selectAllFields()
                             .build();
                                      
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
    container.set("EXCUNO", iCUNO);
    
    if(!query.readAll(container, 2, releasedItemProcessor)) {
		  // do not return NOK
		}
  }
    Closure<?> releasedItemProcessor = { DBContainer readResult  ->
      mi.outData.put("CONO", readResult.get("EXCONO").toString());
      mi.outData.put("DIVI", readResult.get("EXDIVI").toString());
      mi.outData.put("CUNO", readResult.get("EXCUNO").toString());
	    mi.outData.put("FVDT", readResult.get("EXFVDT").toString());
      mi.outData.put("PRGM", readResult.get("EXPRGM").toString());
      mi.outData.put("DUNS", readResult.get("EXDUNS").toString());
      mi.outData.put("CITY", readResult.get("EXCITY").toString());
      mi.outData.put("STAT", readResult.get("EXSTAT").toString());
      mi.outData.put("CZIP", readResult.get("EXCZIP").toString());
      mi.outData.put("BYGR", readResult.get("EXBYGR").toString());
      mi.outData.put("CUGR", readResult.get("EXCUGR").toString());
      mi.outData.put("CTCH", readResult.get("EXCTCH").toString());
	    mi.outData.put("BRKR", readResult.get("EXBRKR").toString());
      mi.outData.put("A120", readResult.get("EXA120").toString());
      mi.outData.put("A121", readResult.get("EXA121").toString());
      mi.outData.put("A122", readResult.get("EXA122").toString());
      mi.outData.put("A123", readResult.get("EXA123").toString());
      mi.outData.put("A124", readResult.get("EXA124").toString());
      mi.outData.put("STS2", readResult.get("EXSTS2").toString());
      mi.outData.put("A256", readResult.get("EXA256").toString());
      mi.outData.put("MENA", readResult.get("EXMENA").toString());
	    mi.outData.put("USNM", readResult.get("EXUSNM").toString());
      mi.outData.put("N030", readResult.get("EXN030").toString());
      mi.outData.put("N130", readResult.get("EXN130").toString());
      mi.outData.put("DAT1", readResult.get("EXDAT1").toString());
      mi.outData.put("TVDT", readResult.get("EXTVDT").toString());
      mi.outData.put("CHID", readResult.get("EXCHID").toString());
      mi.outData.put("LMDT", readResult.get("EXLMDT").toString());
      mi.outData.put("CHNO", readResult.get("EXCHNO").toString());
      mi.outData.put("RGDT", readResult.get("EXRGDT").toString());
      mi.outData.put("LMTS", readResult.get("EXLMTS").toString());
      mi.outData.put("RGTM", readResult.get("EXRGTM").toString());
      mi.write();
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
		
		return true;
	}
  
}