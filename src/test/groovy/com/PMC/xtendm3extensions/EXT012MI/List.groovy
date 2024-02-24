/**
 * README
 * Name: EXT012MI.List
 * Extend M3 Table EXT001 List data
 * Description: List EXT001 Data
 * Date	    Changed By      	               Description
 * 20240224 Mohamed Adel - Hatem Abdellatif  List EXT001 Data
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
  private String iA050;
  private String iA150;
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
	iA050 = mi.inData.get("A050") == null ? "" : mi.inData.get("A050").trim();
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
	container.set("EXA050", iA050);
	
	if(!query.readAll(container, 2, releasedItemProcessor)) {
		  // do not return NOK
		}
  }
	Closure<?> releasedItemProcessor = { DBContainer readResult  ->
	  mi.outData.put("CONO", readResult.get("EXCONO").toString());
	  mi.outData.put("DIVI", readResult.get("EXDIVI").toString());
	  mi.outData.put("A050", readResult.get("EXA050").toString());
	  mi.outData.put("A150", readResult.get("EXA150").toString());
	  mi.outData.put("A151", readResult.get("EXA151").toString());
	  mi.outData.put("A152", readResult.get("EXA152").toString());
	  mi.outData.put("A153", readResult.get("EXA153").toString());
	  mi.outData.put("A154", readResult.get("EXA154").toString());
	  mi.outData.put("A155", readResult.get("EXA155").toString());
	  mi.outData.put("A156", readResult.get("EXA156").toString());
	  mi.outData.put("A157", readResult.get("EXA157").toString());
	  mi.outData.put("A158", readResult.get("EXA158").toString());
	  mi.outData.put("A159", readResult.get("EXA159").toString());
	  mi.outData.put("A120", readResult.get("EXA120").toString());
	  mi.outData.put("A121", readResult.get("EXA121").toString());
	  mi.outData.put("A122", readResult.get("EXA122").toString());
	  mi.outData.put("A123", readResult.get("EXA123").toString());
	  mi.outData.put("A124", readResult.get("EXA124").toString());
	  mi.outData.put("A125", readResult.get("EXA125").toString());
	  mi.outData.put("A126", readResult.get("EXA126").toString());
	  mi.outData.put("A127", readResult.get("EXA127").toString());
	  mi.outData.put("A256", readResult.get("EXA256").toString());
	  mi.outData.put("A257", readResult.get("EXA257").toString());
	  mi.outData.put("N030", readResult.get("EXN030").toString());
	  mi.outData.put("N130", readResult.get("EXN130").toString());
	  mi.outData.put("N230", readResult.get("EXN230").toString());
	  mi.outData.put("N330", readResult.get("EXN330").toString());
	  mi.outData.put("N430", readResult.get("EXN430").toString());
	  mi.outData.put("N530", readResult.get("EXN530").toString());
	  mi.outData.put("N630", readResult.get("EXN630").toString());
	  mi.outData.put("N730", readResult.get("EXN730").toString());
	  mi.outData.put("DAT1", readResult.get("EXDAT1").toString());
	  mi.outData.put("DAT2", readResult.get("EXDAT2").toString());
	  mi.outData.put("DAT3", readResult.get("EXDAT3").toString());
	  mi.outData.put("DAT4", readResult.get("EXDAT4").toString());
	  mi.outData.put("DAT5", readResult.get("EXDAT5").toString());
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
		
		return true;
	}
  
}