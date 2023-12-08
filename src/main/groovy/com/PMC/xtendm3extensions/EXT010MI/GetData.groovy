/**
 * README
 * Name: EXT010MI.GetData
 * Extend M3 Table EXTWCH GetData
 * Description: Get EXTWCH Data
 * Date	    Changed By      	               Description
 * 20231127 Mohamed Adel - Hatem Abdellatif  Get EXTWCH Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class GetData extends ExtendM3Transaction {
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
  private String iFZIP;
  private String iTZIP;
  private String iDEML;
  private String iFDTE;
  
  public GetData(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
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
    iDEML = mi.inData.get("DEML") == null ? "" : mi.inData.get("DEML").trim();
    iFDTE = mi.inData.get("FDTE") == null ? "" : mi.inData.get("FDTE").trim();
 
    if (!validate()) {
		  return;
		}
    
    // Fetch Data from Table EXTWCH
    fetchRecord();
  }
  
  /**
	 * fetchRecord - To get an existing record in Table EXTD89
	 *
	 * @param  null
	 * @return boolean
	 */
  private void fetchRecord() {
    DBAction query = database.table("EXTWCH")
                             .index("00")
                             .selection("EXCONO", "EXFZIP", "EXTZIP", "EXDEML", "EXFDTE", "EXTDTE", "EXA030", "EXA130", "EXA230", "EXCOST", "EXWHLO", 
                                        "EXWHNM", "EXUSID", "EXENUS", "EXN030", "EXN130", "EXCHID", "EXLMDT", "EXCHNO", "EXRGDT", "EXLMTS", "EXRGTM")
                             .build();
                                      
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
    container.set("EXFZIP", iFZIP);
    container.set("EXTZIP", iTZIP);
    container.setInt("EXDEML", iDEML.toInteger());
    container.setInt("EXFDTE", iFDTE.toInteger());
      
    if (query.read(container)) {
      mi.outData.put("CONO", container.get("EXCONO").toString());
      mi.outData.put("FZIP", container.get("EXFZIP").toString());
      mi.outData.put("TZIP", container.get("EXTZIP").toString());
      mi.outData.put("DEML", container.get("EXDEML").toString());
      mi.outData.put("FDTE", container.get("EXFDTE").toString());
      mi.outData.put("TDTE", container.get("EXTDTE").toString());
      mi.outData.put("COST", container.get("EXCOST").toString());
      mi.outData.put("WHLO", container.get("EXWHLO").toString());
      mi.outData.put("WHNM", container.get("EXWHNM").toString());
      mi.outData.put("USID", container.get("EXUSID").toString());
      mi.outData.put("ENUS", container.get("EXENUS").toString());
      mi.outData.put("A030", container.get("EXA030").toString());
      mi.outData.put("A130", container.get("EXA130").toString());
      mi.outData.put("A230", container.get("EXA230").toString());
      mi.outData.put("N030", container.get("EXN030").toString());
      mi.outData.put("N130", container.get("EXN130").toString());
      mi.outData.put("CHID", container.get("EXCHID").toString());
      mi.outData.put("LMDT", container.get("EXLMDT").toString());
      mi.outData.put("CHNO", container.get("EXCHNO").toString());
      mi.outData.put("RGDT", container.get("EXRGDT").toString());
      mi.outData.put("LMTS", container.get("EXLMTS").toString());
      mi.outData.put("RGTM", container.get("EXRGTM").toString());
    } else {
      mi.error("Record does not exist in EXTWCH database table!");
    }
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
	  * validateCompany - Validate Date
	  *
	  * @param  null
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
	boolean validate() {

		if (iCONO == "") {
			iCONO = (Integer)program.getLDAZD().CONO;
		} else if (!validateCompany()) {
			mi.error("Company number " + iCONO + " is invalid");
			return false;
		}
    
    if(!(iFDTE == null || iFDTE == "")){
      if (!validateDate("YYYYMMdd", iFDTE)) {
        mi.error("Invalid date: " + iFDTE);
        return false;
      }
    }

		return true;
	}
}