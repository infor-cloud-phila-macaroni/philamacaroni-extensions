/**
 * README
 * Name: EXT012MI.Get
 * Extend M3 Table EXT001 Get data
 * Description: Get EXT001 Data
 * Date	    Changed By      	               Description
 * 20240211 Mohamed Adel - Hatem Abdellatif  Get EXT001 Data
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
  private String iA050;
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
	iA050 = mi.inData.get("A050") == null ? "" : mi.inData.get("A050").trim();
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
							 .selection("EXCONO", "EXDIVI", "EXA050", "EXA150", "EXA151", "EXA152", "EXA153", "EXA154", "EXA155", "EXA156", "EXA157",
										"EXA158", "EXA159", "EXA120", "EXA121", "EXA122", "EXA123", "EXA124", "EXA125", "EXA126", "EXA127", "EXA256",
										"EXA257", "EXN030", "EXN130", "EXN230", "EXN330", "EXN430", "EXN530", "EXN630", "EXN730", "EXDAT1", "EXDAT2",
										"EXDAT3", "EXDAT4", "EXDAT5", "EXCHID", "EXLMDT", "EXCHNO", "EXRGDT", "EXLMTS", "EXRGTM")
							 .build();
									  
	DBContainer container = query.getContainer();
	container.setInt("EXCONO", iCONO.toInteger());
	container.set("EXDIVI", iDIVI);
	container.set("EXA050", iA050);
	  
	if (query.read(container)) {
	  mi.outData.put("CONO", container.get("EXCONO").toString());
	  mi.outData.put("DIVI", container.get("EXDIVI").toString());
	  mi.outData.put("A050", container.get("EXA050").toString());
	  mi.outData.put("A150", container.get("EXA150").toString());
	  mi.outData.put("A151", container.get("EXA151").toString());
	  mi.outData.put("A152", container.get("EXA152").toString());
	  mi.outData.put("A153", container.get("EXA153").toString());
	  mi.outData.put("A154", container.get("EXA154").toString());
	  mi.outData.put("A155", container.get("EXA155").toString());
	  mi.outData.put("A156", container.get("EXA156").toString());
	  mi.outData.put("A157", container.get("EXA157").toString());
	  mi.outData.put("A158", container.get("EXA158").toString());
	  mi.outData.put("A159", container.get("EXA159").toString());
	  mi.outData.put("A120", container.get("EXA120").toString());
	  mi.outData.put("A121", container.get("EXA121").toString());
	  mi.outData.put("A122", container.get("EXA122").toString());
	  mi.outData.put("A123", container.get("EXA123").toString());
	  mi.outData.put("A124", container.get("EXA124").toString());
	  mi.outData.put("A125", container.get("EXA125").toString());
	  mi.outData.put("A126", container.get("EXA126").toString());
	  mi.outData.put("A127", container.get("EXA127").toString());
	  mi.outData.put("A256", container.get("EXA256").toString());
	  mi.outData.put("A257", container.get("EXA257").toString());
	  mi.outData.put("N030", container.get("EXN030").toString());
	  mi.outData.put("N130", container.get("EXN130").toString());
	  mi.outData.put("N230", container.get("EXN230").toString());
	  mi.outData.put("N330", container.get("EXN330").toString());
	  mi.outData.put("N430", container.get("EXN430").toString());
	  mi.outData.put("N530", container.get("EXN530").toString());
	  mi.outData.put("N630", container.get("EXN630").toString());
	  mi.outData.put("N730", container.get("EXN730").toString());
	  mi.outData.put("DAT1", container.get("EXDAT1").toString());
	  mi.outData.put("DAT2", container.get("EXDAT2").toString());
	  mi.outData.put("DAT3", container.get("EXDAT3").toString());
	  mi.outData.put("DAT4", container.get("EXDAT4").toString());
	  mi.outData.put("DAT5", container.get("EXDAT5").toString());
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