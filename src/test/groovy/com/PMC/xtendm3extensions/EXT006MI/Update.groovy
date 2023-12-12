/**
 * README
 * Name: EXT006MI.Update
 * Standard Table MHDISH Update
 * Description: Update MHDISH Data
 * Date	    Changed By      	               Description
 * 20230828 Hatem Abdellatif - Mohamed Adel  Update MHDISH Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;


public class Update extends ExtendM3Transaction {
  private final MIAPI mi;
  private final DatabaseAPI database;
	private final LoggerAPI logger;
	private final ProgramAPI program;
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
  
  /*** Input Fields of EXTUBH Table */
  private String iCONO;
  private String iINOU;
  private String iDLIX;
  private String iDPOL;
  private String iDSDT;
  private String iDSHM;
  private String iSROT;
  private String iSROD;
  private String iROUT;
  private String iRODN;
  private String iMODL;
  private String iMODF;
  private String iTEDL;
  private String iTEDF;
  private String iDTDT;
  private String iDTHM;
  private String iHAFE;
  private String iLODO;
  private String iETRN;
  private String iFWNS;
  private String iFWNO;
  private String iIRST;
  private String iTSID;
  private String iRASN;
  private String iDFDT;
  private String iDFHM;
  private String iPWDT;
  private String iPWHM;
  private String iAWDT;
  private String iAWHM;
  private String iRSCD;
  private String iSDES;
  int currentCompany = (Integer)program.getLDAZD().CONO;
	int changeNumber = 0;
	int sequence = 0;
	String deliveryStatus;


  public Update(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
    this.logger = logger;
  }
  
  public void main() {
    
    //Get All input Fields Values:
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
    iINOU = mi.inData.get("INOU") == null ? "" : mi.inData.get("INOU").trim();
    iDLIX = mi.inData.get("DLIX") == null ? "" : mi.inData.get("DLIX").trim();
    iDPOL = mi.inData.get("DPOL") == null ? "" : mi.inData.get("DPOL").trim();
    iDSDT = mi.inData.get("DSDT") == null ? "" : mi.inData.get("DSDT").trim();
    iDSHM = mi.inData.get("DSHM") == null ? "" : mi.inData.get("DSHM").trim();
    iSROT = mi.inData.get("SROT") == null ? "" : mi.inData.get("SROT").trim();
    iSROD = mi.inData.get("SROD") == null ? "" : mi.inData.get("SROD").trim();
    iROUT = mi.inData.get("ROUT") == null ? "" : mi.inData.get("ROUT").trim();
    iRODN = mi.inData.get("RODN") == null ? "" : mi.inData.get("RODN").trim();
    iMODL = mi.inData.get("MODL") == null ? "" : mi.inData.get("MODL").trim();
    iMODF = mi.inData.get("MODF") == null ? "" : mi.inData.get("MODF").trim();
    iTEDL = mi.inData.get("TEDL") == null ? "" : mi.inData.get("TEDL").trim();
    iTEDF = mi.inData.get("TEDF") == null ? "" : mi.inData.get("TEDF").trim();
    iDTDT = mi.inData.get("DTDT") == null ? "" : mi.inData.get("DTDT").trim();
    iDTHM = mi.inData.get("DTHM") == null ? "" : mi.inData.get("DTHM").trim();
    iHAFE = mi.inData.get("HAFE") == null ? "" : mi.inData.get("HAFE").trim();
    iLODO = mi.inData.get("LODO") == null ? "" : mi.inData.get("LODO").trim();
    iETRN = mi.inData.get("ETRN") == null ? "" : mi.inData.get("ETRN").trim();
    iFWNS = mi.inData.get("FWNS") == null ? "" : mi.inData.get("FWNS").trim();
    iFWNO = mi.inData.get("FWNO") == null ? "" : mi.inData.get("FWNO").trim();
    iIRST = mi.inData.get("IRST") == null ? "" : mi.inData.get("IRST").trim();
    iTSID = mi.inData.get("TSID") == null ? "" : mi.inData.get("TSID").trim();
    iRASN = mi.inData.get("RASN") == null ? "" : mi.inData.get("RASN").trim();
    iDFDT = mi.inData.get("DFDT") == null ? "" : mi.inData.get("DFDT").trim();
    iDFHM = mi.inData.get("DFHM") == null ? "" : mi.inData.get("DFHM").trim();
    iPWDT = mi.inData.get("PWDT") == null ? "" : mi.inData.get("PWDT").trim();
    iPWHM = mi.inData.get("PWHM") == null ? "" : mi.inData.get("PWHM").trim();
    iAWDT = mi.inData.get("AWDT") == null ? "" : mi.inData.get("AWDT").trim();
    iAWHM = mi.inData.get("AWHM") == null ? "" : mi.inData.get("AWHM").trim();
    iRSCD = mi.inData.get("RSCD") == null ? "" : mi.inData.get("RSCD").trim();
    
    if (!validate()) {
			 return;
		}
		
		readStatus();
    if (deliveryStatus < "50") {
      updateRecord();
    } else {
      mi.error("Not allowed to update as this record as the delivery status is equal or more than (50).");
      return;
    }
		
  }
  
  /**
	 * readStatus - Read of the current record status
	 *
	 * @param  null
	 * @return boolean
	 */
  private boolean readStatus() {
    DBAction query = database.table("MHDISH").index("00").selection("OQCONO", "OQINOU", "OQDLIX", "OQPGRS").build();
    DBContainer container = query.getContainer();
    container.setInt("OQCONO", iCONO.toInteger());
    container.setInt("OQINOU", iINOU.toInteger());
    container.setLong("OQDLIX", iDLIX.toLong());
    if (query.read(container)) {
      deliveryStatus = container.get("OQPGRS");
    }
  }
  
  /**
   * updates record in the MHDISH table
   *
   */
  private void updateRecord() {
    logger.debug("Before database read");
    

    DBAction dbaMHDISH = database.table("MHDISH")
                                 .index("00")
                                 .build();
          
    DBContainer conMHDISH = dbaMHDISH.getContainer();
    conMHDISH.setInt("OQCONO", iCONO.toInteger());
    conMHDISH.setInt("OQINOU", iINOU.toInteger());
    conMHDISH.setLong("OQDLIX", iDLIX.toLong());
    
    
    // Update changed information
    if(!dbaMHDISH.readLock(conMHDISH, updateCallBack)){
      mi.error("Record does not exist");
      return;
    }

  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
  
    if (iDPOL != "" && iDPOL != "?") {
      lockedResult.set("OQDPOL", iDPOL);
    }
    
    if (iDSDT != "" && iDSDT != "?") {
      lockedResult.setInt("OQDSDT", iDSDT.toInteger());
    }
    
    if (iDSHM != "" && iDSHM != "?") {
      lockedResult.setInt("OQDSHM", iDSHM.toInteger());
    } else {
      lockedResult.setInt("OQDSHM", 0);
    }

    if (iSROT != "" && iSROT != "?") {
      lockedResult.set("OQSROT", iSROT);
    }
    
    if (iSROD != "" && iSROD != "?") {
      lockedResult.setInt("OQSROD", iSROD.toInteger());
    }
    
    if (iROUT != "" && iROUT != "?") {
      lockedResult.set("OQROUT", iROUT);
      lockedResult.set("OQSDES", iSDES);
    }
    
    if (iRODN != "" && iRODN != "?") {
      lockedResult.setInt("OQRODN", iRODN.toInteger());
    }
    
    if (iMODL != "" && iMODL != "?") {
      lockedResult.set("OQMODL", iMODL);
    }
    
    if (iMODF != "" && iMODF != "?") {
      lockedResult.set("OQMODF", iMODF);
    }
    
    if (iTEDL != "" && iTEDL != "?") {
      lockedResult.set("OQTEDL", iTEDL);
    }
    
    if (iTEDF != "" && iTEDF != "?") {
      lockedResult.set("OQTEDF", iTEDF);
    }
    
    if (iDTDT != "" && iDTDT != "?") {
      lockedResult.setInt("OQDTDT", iDTDT.toInteger());
    }

    if (iDTHM != "" && iDTHM != "?") {
      lockedResult.setInt("OQDTHM", iDTHM.toInteger());
    } else {
      lockedResult.setInt("OQDTHM", 0);
    }

    if (iHAFE != "" && iHAFE != "?") {
      lockedResult.set("OQHAFE", iHAFE);
    }
    
    if (iLODO != "" && iLODO != "?") {
      lockedResult.set("OQLODO", iLODO);
    }
    
    if (iETRN != "" && iETRN != "?") {
      lockedResult.set("OQETRN", iETRN);
    }
    
    if (iFWNS != "" && iFWNS != "?") {
      lockedResult.set("OQFWNS", iFWNS);
    }
    
    if (iFWNO != "" && iFWNO != "?") {
      lockedResult.set("OQFWNO", iFWNO);
    }
    
    if (iIRST != "" && iIRST != "?") {
      lockedResult.set("OQIRST", iIRST);
    }
    
    if (iTSID != "" && iTSID != "?") {
      lockedResult.set("OQTSID", iTSID);
    }
    
    if (iRASN != "" && iRASN != "?") {
      lockedResult.set("OQRASN", iRASN);
    }
    
    if (iDFDT != "" && iDFDT != "?") {
      lockedResult.setInt("OQDFDT", iDFDT.toInteger());
    }

    if (iDFHM != "" && iDFHM != "?") {
      lockedResult.setInt("OQDFHM", iDFHM.toInteger());
    } else {
      lockedResult.setInt("OQDFHM", 0);
    }
    
    if (iPWDT != "" && iPWDT != "?") {
      lockedResult.setInt("OQPWDT", iPWDT.toInteger());
    }
    
    if (iPWHM != "" && iPWHM != "?") {
      lockedResult.setInt("OQPWHM", iPWHM.toInteger());
    } else {
      lockedResult.setInt("OQPWHM", 0);
    }

    if (iAWDT != "" && iAWDT != "?") {
      lockedResult.setInt("OQAWDT", iAWDT.toInteger());
    }

    if (iAWHM != "" && iAWHM != "?") {
      lockedResult.setInt("OQAWHM", iAWHM.toInteger());
    } else {
      lockedResult.setInt("OQAWHM", 0);
    }

    if (iRSCD != "" && iRSCD != "?") {
      lockedResult.set("OQRSCD", iRSCD);
    }
    
    lockedResult.set("OQLMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
		lockedResult.set("OQCHNO", lockedResult.getInt("OQCHNO")+1);
		lockedResult.set("OQCHID", program.getUser());
    lockedResult.update();
    
    logger.debug("After database read");
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
	  * validDeliveryTerms - Validate the delivery terms
	  *
	  * @param  null
	  * @return boolean
	  */
  def boolean validDeliveryTerms(){
    boolean validRecord = false;
    def parameters = ["TEDL" : iTEDL, "LNCD" : "GB"];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    
    miCaller.call("CRS065MI", "GetDelyTerm", parameters, handler);
    return validRecord;
  }
  
  /**
  	* validDeliveryMethod - Validate the delivery method
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validDeliveryMethod(){
    boolean validRecord = false;
    def parameters = ["MODL" : iMODL, "LNCD" : "GB"];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    
    miCaller.call("CRS070MI", "GetDelyMethod", parameters, handler);
    return validRecord;
  }
  
  /**
  	* validDeliveryMethod - Validate the delivery method
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validRout(){
    boolean validRecord = false;
    def parameters = ["ROUT" : iROUT];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        iSDES = response.SDES;
        validRecord = true;
      }
    };
    
    miCaller.call("DRS005MI", "GetRoute", parameters, handler);
    return validRecord;
  }
  
  /**
  	* validRailStation - Validate the rail station
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validRailStation(){
    boolean validRecord = false;
    def parameters = ["RASN" : iRASN];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    
    miCaller.call("CRS061MI", "GetRailStation", parameters, handler);
    return validRecord;
  }
  
  /**
	  * validateDate - Validate input
	  *
	  * @param  String - strDate
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
	  * validateTime - Validate input
	  *
	  * @param  String - strDate
	  * @return boolean
	  */
  def boolean validateTime(String timeStr, String formatStr){
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr, Locale.ENGLISH);
      LocalTime.parse(timeStr, formatter);
    } catch (DateTimeParseException e) {
      logger.debug("Exception: " + e.toString());
      return false;
    }
    return true;
  }
  
  /**
  	* validHarbour - Validate harbour input
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validHarbour(){
    boolean validRecord = false;
    
    DBAction query = database.table("CSYTAB").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("CTCONO", iCONO.toInteger());
    container.set("CTDIVI", "");
    container.set("CTSTCO", "HAFE");
    container.set("CTSTKY", iHAFE);
    container.set("CTLNCD", "");
    
    // Read information
    if(!query.read(container)){
      return false;
    } else {
      return true;
    }
  }
  
  /**
  	* validTransactionReason - Validate the transaction reason input
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validTransactionReason(){
    boolean validRecord = false;
    
    DBAction query = database.table("CSYTAB").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("CTCONO", iCONO.toInteger());
    container.set("CTDIVI", "");
    container.set("CTSTCO", "RSCD");
    container.set("CTSTKY", iRSCD);
    container.set("CTLNCD", "");
    
    // Read information
    if(!query.read(container)){
      return false;
    } else {
      return true;
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
		} else if (!validateCompany()) {
			mi.error("Company number " + iCONO + " is invalid");
			return false;
		}
		
		/**
     * Calling method validDeliveryMethod to read and to validate the delivery method input
     */
   
    if (!(iMODL == null || iMODL == "")) {
      if (!validDeliveryMethod()){
        mi.error("Invalid delivery method: " + iMODL);
  		  return false;
      }
    }
    
    if (!(iMODF == null || iMODF == "")) {
      if (!validDeliveryMethod()){
        mi.error("Invalid final delivery method: " + iMODF);
  		  return false;
      }
    }
	
	  /**
     * Calling method validDeliveryMethod to read and to validate the delivery method input
     */
    if (!(iTEDL == null || iTEDL == "")) {
      if (!validDeliveryTerms()) {
        mi.error("Invalid delivery terms: " + iTEDL);
  		  return false;
      }
    }
    
    if (!(iTEDF == null || iTEDF == "")) {
      if (!validDeliveryTerms()) {
        mi.error("Invalid final delivery terms: " + iTEDF);
  		  return false;
      }
    }
    
    /**
     * Calling method validRout to read and to validate the rout input
     */
    if (!(iROUT == null || iROUT == "")) {
      if (!validRout()) {
        mi.error("Invalid rout: " + iROUT);
  		  return false;
      }
    }
    
    /**
     * Calling method validRout to read and to validate the rout input
     */
    if (!(iRASN == null || iRASN == "")) {
      if (!validRailStation()) {
        mi.error("Invalid rail station: " + iRASN);
  		  return false;
      }
    }
    
    /**
     * Calling method validHarbour to read and to validate the harbour input
     */
    if (!(iHAFE == null || iHAFE == "")) {
      if (!validHarbour()) {
        mi.error("Invalid harbour: " + iHAFE);
  		  return false;
      }
    }
    
    /**
     * Calling method validHarbour to read and to validate the harbour input
     */
    if (!(iRSCD == null || iRSCD == "")) {
      if (!validTransactionReason()) {
        mi.error("Invalid transaction reason: " + iRSCD);
  		  return false;
      }
    }
		
		if(!(iDSDT == null || iDSDT == "")){
      if (!validateDate("YYYYMMdd", iDSDT)) {
        mi.error("Invalid departure date: " + iDSDT);
        return false;
      }
    }
    
    if(!(iDTDT == null || iDTDT == "")){
      if (!validateDate("YYYYMMdd", iDTDT)) {
        mi.error("Invalid requested departure date: " + iDTDT);
        return false;
      }
    }
    
    if(!(iDFDT == null || iDFDT == "")){
      if (!validateDate("YYYYMMdd", iDFDT)) {
        mi.error("Invalid actual departure date: " + iDFDT);
        return false;
      }
    }
    
    if(!(iPWDT == null || iPWDT == "")){
      if (!validateDate("YYYYMMdd", iPWDT)) {
        mi.error("Invalid forwarder planned arrive date: " + iPWDT);
        return false;
      }
    }
    
    if(!(iAWDT == null || iAWDT == "")){
      if (!validateDate("YYYYMMdd", iAWDT)) {
        mi.error("Invalid forwarder actual arrive date: " + iAWDT);
        return false;
      }
    }
    
    if(!(iDSHM == null || iDSHM == "")){
      if (!validateTime(iDSHM, "HHmm")) {
        mi.error("Invalid departure time: " + iDSHM);
        return false;
      }
    }
    
    if(!(iDTHM == null || iDTHM == "")){
      if (!validateTime(iDTHM, "HHmm")) {
        mi.error("Invalid requested departure time: " + iDTHM);
        return false;
      }
    }
    
    if(!(iDFHM == null || iDFHM == "")){
      if (!validateTime(iDTHM, "HHmm")) {
        mi.error("Invalid actual departure time: " + iDFHM);
        return false;
      }
    }

		return true;
	}
}