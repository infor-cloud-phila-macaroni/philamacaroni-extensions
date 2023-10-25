/**
 * README
 * Name: EXT007MI.Get
 * Extend M3 Table EXTD89 Get
 * Description: Get EXTD89 Data
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

public class Get extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final LoggerAPI logger
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
  
  /**
   * input Fields of EXTD89 Table
  */
  private String iCONO;
  private String iDIVI;
  private String iWHLO;
  private String iFACI;
  private String iTRNR;
  private String iTYPE;
  private String iDOLN;
  
  
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
    iTRNR = mi.inData.get("TRNR") == null ? "" : mi.inData.get("TRNR").trim();
    iTYPE = mi.inData.get("TYPE") == null ? "" : mi.inData.get("TYPE").trim();
    iDOLN = mi.inData.get("DOLN") == null ? "" : mi.inData.get("DOLN").trim();
    
    if (!validate()) {
		  return;
		}
    
    // Get an existing record in EXTD89 table.
    fetchRecord();
  }
  
  /**
	 * fetchRecord - To get an existing record in Table EXTD89
	 *
	 * @param  null
	 * @return boolean
	 */
  private void fetchRecord() {
    DBAction query = database.table("EXTD89")
                             .index("00")
                             .selection("EXCONO", "EXDIVI", "EXTRNR", "EXTYPE", "EXWHLO", "EXFACI", "EXA030", "EXA130", "EXA230", "EXA330", "EXA430", "EXA530", "EXA630", "EXA730", 
                                        "EXA830", "EXA930", "EXB030", "EXB130", "EXB230", "EXB330", "EXB430", "EXB530", "EXN030", "EXN130", "EXN230", "EXN330", "EXN430", "EXN530",
                                        "EXD030", "EXD130", "EXD230" ,"EXD330", "EXD430", "EXCHID", "EXLMDT", "EXCHNO", "EXRGDT", "EXLMTS", "EXRHLM", "EXRGTM", "EXTIME", "EXTWLO", 
                                        "EXDOLN")
                             .build();
                                      
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
  	container.set("EXTRNR", iTRNR);
  	container.set("EXTYPE", iTYPE);
  	container.setInt("EXDOLN", iDOLN.toInteger());
     
    Closure<?> readData = { DBContainer dataContainer ->
      mi.outData.put("CONO", dataContainer.get("EXCONO").toString());
      mi.outData.put("DIVI", dataContainer.get("EXDIVI").toString());
      mi.outData.put("TRNR", dataContainer.get("EXTRNR").toString());
      mi.outData.put("TYPE", dataContainer.get("EXTYPE").toString());
      mi.outData.put("WHLO", dataContainer.get("EXWHLO").toString());
      mi.outData.put("FACI", dataContainer.get("EXFACI").toString());
      mi.outData.put("A030", dataContainer.get("EXA030").toString());
      mi.outData.put("A130", dataContainer.get("EXA130").toString());
      mi.outData.put("A230", dataContainer.get("EXA230").toString());
      mi.outData.put("A330", dataContainer.get("EXA330").toString());
      mi.outData.put("A430", dataContainer.get("EXA430").toString());
      mi.outData.put("A530", dataContainer.get("EXA530").toString());
      mi.outData.put("A630", dataContainer.get("EXA630").toString());
      mi.outData.put("A730", dataContainer.get("EXA730").toString());
      mi.outData.put("A830", dataContainer.get("EXA830").toString());
      mi.outData.put("A930", dataContainer.get("EXA930").toString());
      mi.outData.put("B030", dataContainer.get("EXB030").toString());
      mi.outData.put("B130", dataContainer.get("EXB130").toString());
      mi.outData.put("B230", dataContainer.get("EXB230").toString());
      mi.outData.put("B330", dataContainer.get("EXB330").toString());
      mi.outData.put("B430", dataContainer.get("EXB430").toString());
      mi.outData.put("B530", dataContainer.get("EXB530").toString());
      mi.outData.put("N030", dataContainer.get("EXN030").toString());
      mi.outData.put("N130", dataContainer.get("EXN130").toString());
      mi.outData.put("N230", dataContainer.get("EXN230").toString());
      mi.outData.put("N330", dataContainer.get("EXN330").toString());
      mi.outData.put("N430", dataContainer.get("EXN430").toString());
      mi.outData.put("N530", dataContainer.get("EXN530").toString());
      mi.outData.put("D030", dataContainer.get("EXD030").toString());
      mi.outData.put("D130", dataContainer.get("EXD130").toString());
      mi.outData.put("D230", dataContainer.get("EXD230").toString());
      mi.outData.put("D330", dataContainer.get("EXD330").toString());
      mi.outData.put("D430", dataContainer.get("EXD430").toString());
      mi.outData.put("CHID", dataContainer.get("EXCHID").toString());
      mi.outData.put("LMDT", dataContainer.get("EXLMDT").toString());
      mi.outData.put("CHNO", dataContainer.get("EXCHNO").toString());
      mi.outData.put("RGDT", dataContainer.get("EXRGDT").toString());
      mi.outData.put("LMTS", dataContainer.get("EXLMTS").toString());
      mi.outData.put("RHLM", dataContainer.get("EXRHLM").toString());
      mi.outData.put("RGTM", dataContainer.get("EXRGTM").toString());
      mi.outData.put("TIME", dataContainer.get("EXTIME").toString());
      mi.outData.put("TWLO", dataContainer.get("EXTWLO").toString());
      mi.outData.put("DOLN", dataContainer.get("EXDOLN").toString());
      mi.write();
      return;
    }
      
    if (!query.readAll(container, 4, 200, readData)) {
      mi.error("Record does not exist in EXTD89 database table!");
    }
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
		} else if (!iCONO.isInteger()) {
			mi.error("Company number " + iCONO + " is invalid");
			return false;
		}

		return true;
	}
}