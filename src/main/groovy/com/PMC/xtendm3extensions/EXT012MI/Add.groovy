/**
 * README
 * Name: EXT012MI.Add
 * Extend M3 Table EXT001 AddBalIdentity
 * Description: Add processed custom data to EXT001 table
 * Date	    Changed By      	               Description
 * 20240102 Hatem Abdellatif - Mohamed Adel  Add EXT3PL Data
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
   * Input Fields of EXT3PL Table
   */
  private String iCONO;
  private String iDIVI;
  private String iA050;
  private String iA150;
  private String iA151;
  private	String iA152;
  private String iA153;
  private String iA154;
  private String iA155;
  private String iA156;
  private String iA157;
  private String iA158;
  private String iA159;
  private String iA120;
  private String iA121;
  private String iA122;
  private String iA123;
  private String iA124;
  private String iA125;
  private String iA126;
  private String iA127;
  private	String iA256;
  private String iA257;
  private String iN030;
  private String iN130;
  private String iN230;
  private String iN330;
  private String iN430;
  private String iN530;
  private String iN630;
  private String iN730;
  private String iDAT1;
  private	String iDAT2;
  private String iDAT3;
  private String iDAT4;
  private String iDAT5;

  /**
	 * Global variables
	 */
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
	iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
	iDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
	iA050 = mi.inData.get("A050") == null ? "" : mi.inData.get("A050").trim();
	iA150 = mi.inData.get("A150") == null ? "" : mi.inData.get("A150").trim();
	iA151 = mi.inData.get("A151") == null ? "" : mi.inData.get("A151").trim();
	iA152 = mi.inData.get("A152") == null ? "" : mi.inData.get("A152").trim();
	iA153 = mi.inData.get("A153") == null ? "" : mi.inData.get("A153").trim();
	iA154 = mi.inData.get("A154") == null ? "" : mi.inData.get("A154").trim();
	iA155 = mi.inData.get("A155") == null ? "" : mi.inData.get("A155").trim();
	iA156 = mi.inData.get("A156") == null ? "" : mi.inData.get("A156").trim();
	iA157 = mi.inData.get("A157") == null ? "" : mi.inData.get("A157").trim();
	iA158 = mi.inData.get("A158") == null ? "" : mi.inData.get("A158").trim();
	iA159 = mi.inData.get("A159") == null ? "" : mi.inData.get("A159").trim();
	iA120 = mi.inData.get("A120") == null ? "" : mi.inData.get("A120").trim();
	iA121 = mi.inData.get("A121") == null ? "" : mi.inData.get("A121").trim();
	iA122 = mi.inData.get("A122") == null ? "" : mi.inData.get("A122").trim();
	iA123 = mi.inData.get("A123") == null ? "" : mi.inData.get("A123").trim();
	iA124 = mi.inData.get("A124") == null ? "" : mi.inData.get("A124").trim();
	iA125 = mi.inData.get("A125") == null ? "" : mi.inData.get("A125").trim();
	iA126 = mi.inData.get("A126") == null ? "" : mi.inData.get("A126").trim();
	iA127 = mi.inData.get("A127") == null ? "" : mi.inData.get("A127").trim();
	iA256 = mi.inData.get("A256") == null ? "" : mi.inData.get("A256").trim();
	iA257 = mi.inData.get("A257") == null ? "" : mi.inData.get("A257").trim();
	iN030 = mi.inData.get("N030") == null ? "" : mi.inData.get("N030").trim();
	iN130 = mi.inData.get("N130") == null ? "" : mi.inData.get("N130").trim();
	iN230 = mi.inData.get("N230") == null ? "" : mi.inData.get("N230").trim();
	iN330 = mi.inData.get("N330") == null ? "" : mi.inData.get("N330").trim();
	iN430 = mi.inData.get("N430") == null ? "" : mi.inData.get("N430").trim();
	iN530 = mi.inData.get("N530") == null ? "" : mi.inData.get("N530").trim();
	iN630 = mi.inData.get("N630") == null ? "" : mi.inData.get("N630").trim();
	iN730 = mi.inData.get("N730") == null ? "" : mi.inData.get("N730").trim();
	iDAT1 = mi.inData.get("DAT1") == null ? "" : mi.inData.get("DAT1").trim();
	iDAT2 = mi.inData.get("DAT2") == null ? "" : mi.inData.get("DAT2").trim();
	iDAT3 = mi.inData.get("DAT3") == null ? "" : mi.inData.get("DAT3").trim();
	iDAT4 = mi.inData.get("DAT4") == null ? "" : mi.inData.get("DAT4").trim();
	iDAT5 = mi.inData.get("DAT5") == null ? "" : mi.inData.get("DAT5").trim();

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
	  
	// Add a new record in EXT3PL table.
	addRecord();
  }
  
  /**
	 * addRecord - To add a new record in Table EXT001
	 *
	 * @param  null
	 * @return boolean
	 */
  private void addRecord() {
	DBAction query = database.table("EXT001")
							 .index("00")
							 .build();
	  
	DBContainer container = query.getContainer();
	container.setInt("EXCONO", iCONO.toInteger());
	container.set("EXDIVI", iDIVI);
	container.set("EXA050", iA050);

	if(iA150 != "" && iA150 != "?"){
	  container.set("EXA150", iA150);
	}
	
	if(iA151 != "" && iA151 != "?"){
	  container.set("EXA151", iA151);
	}
	
	if(iA152 != "" && iA152 != "?"){
	  container.set("EXA152", iA152);
	}
	
	if(iA153 != "" && iA153 != "?"){
	  container.set("EXA153", iA153);
	}
	
	if(iA154 != "" && iA154 != "?"){
	  container.set("EXA154", iA154);
	}
	
	if(iA155 != "" && iA155 != "?"){
	  container.set("EXA155", iA155);
	}
	
	if(iA156 != "" && iA156 != "?"){
	  container.set("EXA156", iA156);
	}
	
	if(iA157 != "" && iA157 != "?"){
	  container.set("EXA157", iA157);
	}
	
	if(iA158 != "" && iA158 != "?"){
	  container.set("EXA158", iA158);
	}
	
	if(iA159 != "" && iA159 != "?"){
	  container.set("EXA159", iA159);
	}
	
	if(iA120 != "" && iA120 != "?"){
	  container.set("EXA120", iA120);
	}
	
	if(iA121 != "" && iA121 != "?"){
	  container.set("EXA121", iA121);
	}
	
	if(iA122 != "" && iA122 != "?"){
	  container.set("EXA122", iA122);
	}
	
	if(iA123 != "" && iA123 != "?"){
	  container.set("EXA123", iA123);
	}
	
	if(iA124 != "" && iA124 != "?"){
	  container.set("EXA124", iA124);
	}
	
	if(iA125 != "" && iA125 != "?"){
	  container.set("EXA125", iA125);
	}
	
	if(iA126 != "" && iA126 != "?"){
	  container.set("EXA126", iA126);
	}
	
	if(iA127 != "" && iA127 != "?"){
	  container.set("EXA127", iA127);
	}
	
	if(iA256 != "" && iA256 != "?"){
	  container.set("EXA256", iA256);
	}
	
	if(iA257 != "" && iA257 != "?"){
	  container.set("EXA257", iA257);
	}
	
	if(iN030 != "" && iN030 != "?"){
	  container.setDouble("EXN030", iN030.toDouble());
	}
	
	if(iN030 != "" && iN030 != "?"){
	  container.setDouble("EXN030", iN030.toDouble());
	}
		
	if(iN030 != "" && iN030 != "?"){
	  container.setDouble("EXN030", iN030.toDouble());
	}
	
	if(iN130 != "" && iN130 != "?"){
	  container.setDouble("EXN130", iN130.toDouble());
	}
	
	if(iN230 != "" && iN230 != "?"){
	  container.setDouble("EXN230", iN230.toDouble());
	}
	
	if(iN330 != "" && iN330 != "?"){
	  container.setDouble("EXN330", iN330.toDouble());
	}
	
	if(iN430 != "" && iN430 != "?"){
	  container.setDouble("EXN430", iN430.toDouble());
	}
	
	if(iN530 != "" && iN530 != "?"){
	  container.setDouble("EXN530", iN530.toDouble());
	}
	
	if(iN630 != "" && iN630 != "?"){
	  container.setDouble("EXN630", iN630.toDouble());
	}
	
	if(iN730 != "" && iN730 != "?"){
	  container.setDouble("EXN730", iN730.toDouble());
	}
	
	if(iDAT1 != "" && iDAT1 != "?"){
	  container.setInt("EXDAT1", iDAT1.toInteger());
	}
	
	if(iDAT2 != "" && iDAT2 != "?"){
	  container.setInt("EXDAT2", iDAT2.toInteger());
	}
	
	if(iDAT3 != "" && iDAT3 != "?"){
	  container.setInt("EXDAT3", iDAT3.toInteger());
	}
	
	if(iDAT4 != "" && iDAT4 != "?"){
	  container.setInt("EXDAT4", iDAT4.toInteger());
	}
	
	if(iDAT5 != "" && iDAT5 != "?"){
	  container.setInt("EXDAT5", iDAT5.toInteger());
	}

	container.set("EXLMDT", currentDate);
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
		
		if(!(iDAT1 == null || iDAT1 == "")){
	  if (!validateDate("YYYYMMdd", iDAT1)) {
		mi.error("Invalid date: " + iDAT1);
		return false;
	  }
	}
	
	if(!(iDAT2 == null || iDAT2 == "")){
	  if (!validateDate("YYYYMMdd", iDAT2)) {
		mi.error("Invalid date: " + iDAT2);
		return false;
	  }
	}
	
	if(!(iDAT3 == null || iDAT3 == "")){
	  if (!validateDate("YYYYMMdd", iDAT3)) {
		mi.error("Invalid date: " + iDAT3);
		return false;
	  }
	}
	
	if(!(iDAT4 == null || iDAT4 == "")){
	  if (!validateDate("YYYYMMdd", iDAT4)) {
		mi.error("Invalid date: " + iDAT4);
		return false;
	  }
	}
	
	if(!(iDAT5 == null || iDAT5 == "")){
	  if (!validateDate("YYYYMMdd", iDAT5)) {
		mi.error("Invalid date: " + iDAT5);
		return false;
	  }
	}

		return true;
	}
}