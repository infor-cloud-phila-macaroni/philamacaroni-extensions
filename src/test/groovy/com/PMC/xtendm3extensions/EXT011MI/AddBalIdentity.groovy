/**
 * README
 * Name: EXT011MI.AddBalIdentity
 * Extend M3 Table EXT3PL AddBalIdentity
 * Description: Add processed balance identity data to EXT3PL
 * Date	    Changed By      	               Description
 * 20240102 Mohamed Adel - Hatem Abdellatif  Add EXT3PL Data
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


public class AddBalIdentity extends ExtendM3Transaction {
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
  private String iITNO;
  private String iWHLO;
  private String iWHSL;
  private	String iZBAN;
  private String iZCAM;
  private String iSTQT;
  private String iSTQI;
  private String iSTAT;
  private String iRTRY;
  private String iRUPD;
  private String iEXIS;
  private String iSTQF;
  private String iRTRX;
  private String iRTRZ;

  /**
	 * Global variables
	 */
  int changeNumber = 0;
	int sequence = 0;
	private String currentFormattedDate;
	private int currentDate = 0;
	private int currentTime = 0;
	private String currentTimeString;
  
  public AddBalIdentity(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
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
    iITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim();
    iWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    iWHSL = mi.inData.get("WHSL") == null ? "" : mi.inData.get("WHSL").trim();
    iZBAN = mi.inData.get("ZBAN") == null ? "" : mi.inData.get("ZBAN").trim();
    iZCAM = mi.inData.get("CAMU") == null ? "" : mi.inData.get("CAMU").trim();
    iSTQT = mi.inData.get("STQT") == null ? "" : mi.inData.get("STQT").trim();
    iSTQI = mi.inData.get("STQI") == null ? "" : mi.inData.get("STQI").trim();
    iSTAT = mi.inData.get("STAT") == null ? "" : mi.inData.get("STAT").trim();
    iRTRY = mi.inData.get("RTRY") == null ? "" : mi.inData.get("RTRY").trim();
    iRUPD = mi.inData.get("RUPD") == null ? "" : mi.inData.get("RUPD").trim();
    iEXIS = mi.inData.get("EXIS") == null ? "" : mi.inData.get("EXIS").trim();
    iSTQF = mi.inData.get("STQF") == null ? "" : mi.inData.get("STQF").trim();
    iRTRX = mi.inData.get("RTRX") == null ? "" : mi.inData.get("RTRX").trim();
    iRTRZ = mi.inData.get("RTRZ") == null ? "" : mi.inData.get("RTRZ").trim();
    
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
	 * addRecord - To add a new record in Table EXT3PL
	 *
	 * @param  null
	 * @return boolean
	 */
  private void addRecord() {
    DBAction query = database.table("EXT3PL")
                             .index("00")
                             .build();
      
    DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
    container.set("EXITNO", iITNO);
    container.set("EXWHLO", iWHLO);
    container.set("EXWHSL", iWHSL);
    container.set("EXZBAN", iZBAN);
    container.set("EXZCAM", iZCAM);

    if(iSTQT != "" && iSTQT != "?"){
      container.setDouble("EXSTQT", iSTQT.toDouble());
    }
    
    if(iSTQI != "" && iSTQI != "?"){
      container.setDouble("EXSTQI", iSTQI.toDouble());
    }
    
    if(iSTAT != "" && iSTAT != "?"){
      container.set("EXSTAT", iSTAT);
    }
    
    if(iRUPD != "" && iRUPD != "?"){
      container.set("EXRUPD", iRUPD);
    }
    
    if(iRTRY != "" && iRTRY != "?"){
      container.set("EXRTRY", iRTRY);
    }
    
    if(iEXIS != "" && iEXIS != "?"){
      container.set("EXEXIS", iEXIS);
    }
    if(iDIVI != "" && iDIVI != "?"){
      container.set("EXDIVI", iDIVI);
    }
    
    if(iSTQF != "" && iSTQF != "?"){
      container.setDouble("EXSTQF", iSTQF.toDouble());
    }
    
    if(iRTRX != "" && iRTRX != "?"){
      container.set("EXRTRX", iRTRX);
    }
    if(iRTRZ != "" && iRTRZ != "?"){
      container.set("EXRTRZ", iRTRZ);
    }

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
  	* validWarehouse - Validate transaction warehouse
  	*
  	* @param  null
  	* @return boolean
  	*/
  private boolean validWarehouse(){
    boolean validRecord = false;
    Map<String, String> parameters = ["WHLO" : iWHLO];
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
  	* validLocation - Validate Location
  	*
  	* @param  null
  	* @return boolean
  	*/
  private boolean validLocation(){
    boolean validRecord = false;
    Map<String, String> parameters = ["WHLO" : iWHLO, "WHSL": iWHSL];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    miCaller.call("MMS010MI", "GetLocation", parameters, handler);
    return validRecord;
  }

  /**
  	* validItemNumber - Validate Item Number
  	*
  	* @param  null
  	* @return boolean
  	*/
  private boolean validItemNumber(){
    boolean validRecord = false;
    Map<String, String> parameters = ["ITNO" : iITNO];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    miCaller.call("MMS200MI", "GetItmBasic", parameters, handler);
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
		
		if(!(iITNO == null || iITNO == "")){
      if (!validItemNumber()) {
        mi.error("Item Number data " + iITNO + " is invalid");
  		  return false;
      }
		}
		
		if(!(iWHLO == null || iWHLO == "")){
      if (!validWarehouse()) {
        mi.error("Warehouse data " + iWHLO + " is invalid");
  		  return false;
      }
		}

    if(!(iWHSL == null || iWHSL == "")){
      if (!validLocation()) {
        mi.error("Location data " + iWHSL + " is invalid");
  		  return false;
      }
		}

		return true;
	}
}