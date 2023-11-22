/**
 * README
 * Name: EXT005MI.Update
 * Standard Table OOHEAD Update
 * Description: Update OOHEAD Data
 * Date	    Changed By      	               Description
 * 20230828 Hatem Abdellatif - Mohamed Adel  Update OOHEAD Data
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
	
		
	/**
	 * Input fields ExtendM3Transaction
	 */
	private String iCONO;
  private String iORNO;
  private String iFACI;
  private String iWHLO;
  private String iORDT;
  private String iRLDT;
  private String iRLHM;
  private String iRLDZ;
  private String iRLHZ;
  private String iFDDT;
  private String iOPRI;
  private String iOBLC;
  private String iTEPY;
  private String iPYCD;
  private String iTECD;
  private String iMODL;
  private String iTEDL;
  private String iTEL2;
  private String iTEPA;
  private String iADID;
  private String iSMCD;
  private String iOFNO;
  private String iOREF;
  private String iYREF;
  private String iCUOR;
  private String iEXCD;
  private String iHAFE;
  private String iROUT;
  private String iRODN;
  private String iRASN;
  private String iUCA1;
  private String iUCA2;
  private String iUCA3;
  private String iUCA4;
  private String iUCA5;
  private String iUCA6;
  private String iUCA7;
  private String iUCA8;
  private String iUCA9;
  private String iUCA0;
  private String iUDN1;
  private String iUDN2;
  private String iUDN3;
  private String iUDN4;
  private String iUDN5;
  private String iUDN6;
  private String iUID1;
  private String iUID2;
  private String iUID3;
  private String iUCT1;
	String currentFacility = program.LDAZD.FACI.toString();
	int changeNumber = 0;
	int sequence = 0;
	String highStatus;
	String lowStatus;
	
	
	/**
	 * Output Fields of OIS300MI
	 */
	private String oORNO = "";
	private String oFACI = "";
	
  
	public Update(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
    this.logger = logger;
  }
  
  public void main() {
    
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
    iORNO = mi.inData.get("ORNO") == null ? "" : mi.inData.get("ORNO").trim();
    iFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim();
    iWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    iORDT = mi.inData.get("ORDT") == null ? "" : mi.inData.get("ORDT").trim();
    iRLDT = mi.inData.get("RLDT") == null ? "" : mi.inData.get("RLDT").trim();
    iFDDT = mi.inData.get("FDDT") == null ? "" : mi.inData.get("FDDT").trim();
    iOPRI = mi.inData.get("OPRI") == null ? "" : mi.inData.get("OPRI").trim();
    iOBLC = mi.inData.get("OBLC") == null ? "" : mi.inData.get("OBLC").trim();
    iTEPY = mi.inData.get("TEPY") == null ? "" : mi.inData.get("TEPY").trim();
    iPYCD = mi.inData.get("PYCD") == null ? "" : mi.inData.get("PYCD").trim();
    iTECD = mi.inData.get("TECD") == null ? "" : mi.inData.get("TECD").trim();
    iMODL = mi.inData.get("MODL") == null ? "" : mi.inData.get("MODL").trim();
    iTEDL = mi.inData.get("TEDL") == null ? "" : mi.inData.get("TEDL").trim();
    iTEL2 = mi.inData.get("TEL2") == null ? "" : mi.inData.get("TEL2").trim();
    iTEPA = mi.inData.get("TEPA") == null ? "" : mi.inData.get("TEPA").trim();
    iADID = mi.inData.get("ADID") == null ? "" : mi.inData.get("ADID").trim();
    iSMCD = mi.inData.get("SMCD") == null ? "" : mi.inData.get("SMCD").trim();
    iOFNO = mi.inData.get("OFNO") == null ? "" : mi.inData.get("OFNO").trim();
    iOREF = mi.inData.get("OREF") == null ? "" : mi.inData.get("OREF").trim();
    iYREF = mi.inData.get("YREF") == null ? "" : mi.inData.get("YREF").trim();
    iCUOR = mi.inData.get("CUOR") == null ? "" : mi.inData.get("CUOR").trim();
    iEXCD = mi.inData.get("EXCD") == null ? "" : mi.inData.get("EXCD").trim();
    iHAFE = mi.inData.get("HAFE") == null ? "" : mi.inData.get("HAFE").trim();
    iROUT = mi.inData.get("ROUT") == null ? "" : mi.inData.get("ROUT").trim();
    iRODN = mi.inData.get("RODN") == null ? "" : mi.inData.get("RODN").trim();
    iRASN = mi.inData.get("RASN") == null ? "" : mi.inData.get("RASN").trim();
    iUCA1 = mi.inData.get("UCA1") == null ? "" : mi.inData.get("UCA1").trim();
    iUCA2 = mi.inData.get("UCA2") == null ? "" : mi.inData.get("UCA2").trim();
    iUCA3 = mi.inData.get("UCA3") == null ? "" : mi.inData.get("UCA3").trim();
    iUCA4 = mi.inData.get("UCA4") == null ? "" : mi.inData.get("UCA4").trim();
    iUCA5 = mi.inData.get("UCA5") == null ? "" : mi.inData.get("UCA5").trim();
    iUCA6 = mi.inData.get("UCA6") == null ? "" : mi.inData.get("UCA6").trim();
    iUCA7 = mi.inData.get("UCA7") == null ? "" : mi.inData.get("UCA7").trim();
    iUCA8 = mi.inData.get("UCA8") == null ? "" : mi.inData.get("UCA8").trim();
    iUCA9 = mi.inData.get("UCA9") == null ? "" : mi.inData.get("UCA9").trim();
    iUCA0 = mi.inData.get("UCA0") == null ? "" : mi.inData.get("UCA0").trim();
    iUDN1 = mi.inData.get("UDN1") == null ? "" : mi.inData.get("UDN1").trim();
    iUDN2 = mi.inData.get("UDN2") == null ? "" : mi.inData.get("UDN2").trim();
    iUDN3 = mi.inData.get("UDN3") == null ? "" : mi.inData.get("UDN3").trim();
    iUDN4 = mi.inData.get("UDN4") == null ? "" : mi.inData.get("UDN4").trim();
    iUDN5 = mi.inData.get("UDN5") == null ? "" : mi.inData.get("UDN5").trim();
    iUDN6 = mi.inData.get("UDN6") == null ? "" : mi.inData.get("UDN6").trim();
    iUID1 = mi.inData.get("UID1") == null ? "" : mi.inData.get("UID1").trim();
    iUID2 = mi.inData.get("UID2") == null ? "" : mi.inData.get("UID2").trim();
    iUID3 = mi.inData.get("UID3") == null ? "" : mi.inData.get("UID3").trim();
    iUCT1 = mi.inData.get("UCT1") == null ? "" : mi.inData.get("UCT1").trim();
    
    if (!validate()) {
			 return;
		}
		
    // If no errors then update the record
    if (!validateUpdate()) {
      mi.error("The record you are seeking is currently locked.");
      return;
    } else {
      readStatus();
      if (lowStatus >= "22" && lowStatus <= "33") {
        lockUnlockRecord(1);
        updateRecord();
        lockUnlockRecord(0);
      } else {
        mi.error("Not allowed to update as this order low status not between 22 and 33");
        return;
      }
    }
  }
  
  /**
	 * readStatus - Read of the current record status
	 *
	 * @param  null
	 * @return boolean
	 */
  private boolean readStatus() {
    DBAction query = database.table("OOHEAD").index("00").selection("OACONO", "OAORNO", "OAORST", "OAORSL").build();
    DBContainer container = query.getContainer();
    container.setInt("OACONO", iCONO.toInteger());
    container.set("OAORNO", iORNO);
    if (query.read(container)) {
      highStatus = container.get("OAORST");
      lowStatus = container.get("OAORSL");
    }
  }
  
  /**
   * updates record in the OOHEAD table
   *
   */
  private void updateRecord() {
    logger.debug("Start database update");
    DBAction query = database.table("OOHEAD").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("OACONO", iCONO.toInteger());
    container.set("OAORNO", iORNO);
    
    if (query.read(container)) {
      highStatus = container.get("OAORST");
      lowStatus = container.get("OAORSL");
    }
    
    // Update changed information
    if(!query.readLock(container, updateCallBack)){
      mi.error("Record does not exist");
      return;
    }

  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
  
    if(iFACI != "" && iFACI != "?"){
      lockedResult.set("OAFACI", iFACI);
    } else {
      lockedResult.set("OAFACI", currentFacility);
    }
  
    if(iWHLO != "" && iWHLO != "?"){
      lockedResult.set("OAWHLO", iWHLO);
    }
    
    if(iORDT != "" && iORDT != "?"){
      lockedResult.setInt("OAORDT", iORDT.toInteger());
    }
    
    if(iRLDT != "" && iRLDT != "?"){
      lockedResult.setInt("OARLDT", iRLDT.toInteger());
    }
    
    if(iFDDT != "" && iFDDT != "?"){
      lockedResult.setInt("OAFDDT", iFDDT.toInteger());
    }
    
    if(iOPRI != "" && iOPRI != "?"){
      lockedResult.setInt("OAOPRI", iOPRI.toInteger());
    }
    
    if(iOBLC != "" && iOBLC != "?"){
      lockedResult.setInt("OAOBLC", iOBLC.toInteger());
    }
    
    if(iTEPY != "" && iTEPY != "?"){
      lockedResult.set("OATEPY", iTEPY);
    }
    
    if(iPYCD != "" && iPYCD != "?"){
      lockedResult.set("OAPYCD", iPYCD);
    }
    
    if(iTECD != "" && iTECD != "?"){
      lockedResult.set("OATECD", iTECD);
    }
    
    if(iMODL != "" && iMODL != "?"){
      lockedResult.set("OAMODL", iMODL);
    }
    
    if(iTEDL != "" && iTEDL != "?"){
      lockedResult.set("OATEDL", iTEDL);
    }
    
    if(iTEL2 != "" && iTEL2 != "?"){
      lockedResult.set("OATEL2", iTEL2);
    }
    
    if(iTEPA != "" && iTEPA != "?"){
      lockedResult.set("OATEPA", iTEPA);
    }
    
    if(iADID != "" && iADID != "?"){
      lockedResult.set("OAADID", iADID);
    }
    
    if(iSMCD != "" && iSMCD != "?"){
      lockedResult.set("OASMCD", iSMCD);
    }
    
    if(iOFNO != "" && iOFNO != "?"){
      lockedResult.set("OAOFNO", iOFNO);
    }
    
    if(iOREF != "" && iOREF != "?"){
      lockedResult.set("OAOREF", iOREF);
    }
    
    if(iYREF != "" && iYREF != "?"){
      lockedResult.set("OAYREF", iYREF);
    }
    
    if(iCUOR != "" && iCUOR != "?"){
      lockedResult.set("OACUOR", iCUOR);
    }
    
    if(iEXCD != "" && iEXCD != "?"){
      lockedResult.set("OAEXCD", iEXCD);
    }
    
    if(iHAFE != "" && iHAFE != "?"){
      lockedResult.set("OAHAFE", iHAFE);
    }
    
    if(iROUT != "" && iROUT != "?"){
      lockedResult.set("OAROUT", iROUT);
    }
    
    if(iRODN != "" && iRODN != "?"){
      lockedResult.setInt("OARODN", iRODN.toInteger());
    } else {
      lockedResult.setInt("OARODN", 0);
    }
    
    if(iRASN != "" && iRASN != "?"){
      lockedResult.set("OARASN", iRASN);
    }
    
    if(iUCA1 != "" && iUCA1 != "?"){
      lockedResult.set("OAUCA1", iUCA1);
    }
    
    if(iUCA2 != "" && iUCA2 != "?"){
      lockedResult.set("OAUCA2", iUCA2);
    }
    
    if(iUCA3 != "" && iUCA3 != "?"){
      lockedResult.set("OAUCA3", iUCA3);
    }
    
    if(iUCA4 != "" && iUCA4 != "?"){
      lockedResult.set("OAUCA4", iUCA4);
    }
    
    if(iUCA5 != "" && iUCA5 != "?"){
      lockedResult.set("OAUCA5", iUCA5);
    }
    
    if(iUCA6 != "" && iUCA6 != "?"){
      lockedResult.set("OAUCA6", iUCA6);
    }
    
    if(iUCA7 != "" && iUCA7 != "?"){
      lockedResult.set("OAUCA7", iUCA7);
    }
    
    if(iUCA8 != "" && iUCA8 != "?"){
      lockedResult.set("OAUCA8", iUCA8);
    }
    
    if(iUCA9 != "" && iUCA9 != "?"){
      lockedResult.set("OAUCA9", iUCA9);
    }
    
    if(iUCA0 != "" && iUCA0 != "?"){
      lockedResult.set("OAUCA0", iUCA0);
    }
    
    if(iUDN1 != "" && iUDN1 != "?"){
      lockedResult.setDouble("OAUDN1", iUDN1.toDouble());
    }
    
    if(iUDN2 != "" && iUDN2 != "?"){
      lockedResult.setDouble("OAUDN2", iUDN2.toDouble());
    }
    
    if(iUDN3 != "" && iUDN3 != "?"){
      lockedResult.setDouble("OAUDN3", iUDN3.toDouble());
    }
    
    if(iUDN4 != "" && iUDN4 != "?"){
      lockedResult.setDouble("OAUDN4", iUDN4.toDouble());
    }
    
    if(iUDN5 != "" && iUDN5 != "?"){
      lockedResult.setDouble("OAUDN5", iUDN5.toDouble());
    }
    
    if(iUDN6 != "" && iUDN6 != "?"){
      lockedResult.setDouble("OAUDN6", iUDN6.toDouble());
    }
    
    if(iUID1 != "" && iUID1 != "?"){
      lockedResult.setInt("OAUID1", iUID1.toInteger());
    }
    
    if(iUID2 != "" && iUID2 != "?"){
      lockedResult.setInt("OAUID2", iUID2.toInteger());
    }
    
    if(iUID3 != "" && iUID3 != "?"){
      lockedResult.setInt("OAUID3", iUID3.toInteger());
    }
    
    if(iUCT1 != "" && iUCT1 != "?"){
      lockedResult.set("OAUCT1", iUCT1);
    }
  
    lockedResult.set("OALMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
		lockedResult.set("OACHNO", lockedResult.getInt("OACHNO")+1);
		lockedResult.set("OACHID", program.getUser());
    lockedResult.update();
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
  	* validOrder - Validate order number
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validOrder(){
    boolean validRecord = false;
    def parameters = ["ORNO" : iORNO];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    
    miCaller.call("OIS100MI", "GetHead", parameters, handler);
    return validRecord;
  }
  
  /**
  	* validWarehouse - Validate transaction warehouse
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validWarehouse(){
    boolean validRecord = false;
    def parameters = ["WHLO" : iWHLO];
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
    def parameters = ["FACI" : iFACI];
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
  	* validPaymentTerm - Validate transaction payment term
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validPaymentTerm(){
    boolean validRecord = false;
    def parameters = ["TEPY" : iTEPY, "LNCD" : "GB"];
    Closure<?> handler = { Map<String, String> response ->
      if (response.containsKey('errorMsid')){
        validRecord = false;
      } else {
        validRecord = true;
      }
    };
    miCaller.call("CRS075MI", "Get", parameters, handler);
    return validRecord;
  }
  
  /**
  	* validPaymentMethod - Validate transaction payment method
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validPaymentMethod(){
    boolean validRecord = false;
    
    DBAction query = database.table("CSYTAB").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("CTCONO", iCONO.toInteger());
    container.set("CTDIVI", "");
    container.set("CTSTCO", "PYCD");
    container.set("CTSTKY", iPYCD);
    container.set("CTLNCD", "");
    
    // Read information
    if(!query.read(container)){
      return false;
    } else {
      return true;
    }
  }
  
  /**
  	* validCashDiscountTerm - Validate transaction cash discount term
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validCashDiscountTerm(){
    boolean validRecord = false;
    
    DBAction query = database.table("CSYTAB").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("CTCONO", iCONO.toInteger());
    container.set("CTDIVI", "");
    container.set("CTSTCO", "TECD");
    container.set("CTSTKY", iTECD);
    container.set("CTLNCD", "GB");
    
    // Read information
    if(!query.read(container)){
      return false;
    } else {
      return true;
    }
  }
  
  /**
  	* validPackageTerms - Validate package terms
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validPackageTerms(){
    boolean validRecord = false;
    
    DBAction query = database.table("CSYTAB").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("CTCONO", iCONO.toInteger());
    container.set("CTDIVI", "");
    container.set("CTSTCO", "TEPA");
    container.set("CTSTKY", iTEPA);
    container.set("CTLNCD", "GB");
    
    // Read information
    if(!query.read(container)){
      return false;
    } else {
      return true;
    }
  }
  
  /**
  	* validSalesPerson - Validate sales person
  	*
  	* @param  null
  	* @return boolean
  	*/
  def boolean validSalesPerson(){
    boolean validRecord = false;
    
    DBAction query = database.table("CSYTAB").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("CTCONO", iCONO.toInteger());
    container.set("CTDIVI", "");
    container.set("CTSTCO", "SMCD");
    container.set("CTSTKY", iSMCD);
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
		
		if(!(iORDT == null || iORDT == "")){
      if (!validateDate("YYYYMMdd", iORDT)) {
        mi.error("Invalid order date.");
        return false;
      }
    }
    
    if(!(iRLDT == null || iRLDT == "")){
      if (!validateDate("YYYYMMdd", iRLDT)) {
        mi.error("Invalid customer's purchase order date.");
        return false;
      }
    }
    
    if(!(iFDDT == null || iFDDT == "")){
      if (!validateDate("YYYYMMdd", iFDDT)) {
        mi.error("Invalid earliest delivery date.");
        return false;
      }
    }
    
    if(!(iUID1 == null || iUID1 == "")){
      if (!validateDate("YYYYMMdd", iUID1)) {
        mi.error("Invalid user defined date 1.");
        return false;
      }
    }
    
    if(!(iUID2 == null || iUID2 == "")){
      if (!validateDate("YYYYMMdd", iUID2)) {
        mi.error("Invalid user defined date 2.");
        return false;
      }
    }
    
    if(!(iUID3 == null || iUID3 == "")){
      if (!validateDate("YYYYMMdd", iUID3)) {
        mi.error("Invalid user defined date 3.");
        return false;
      }
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
	
	  /**
     * Calling method validDeliveryMethod to read and to validate the delivery method input
     */
    if (!(iTEDL == null || iTEDL == "")) {
      if (!validDeliveryTerms()) {
        mi.error("Invalid delivery terms: " + iTEDL);
  		  return false;
      }
    }
    
    /**
     * Calling method validWarehouse to validate the warehouse input data
     */
    if (!(iORNO == null || iORNO == "")) {
      if (!validOrder()) {
        mi.error("Order data " + iORNO + " is invalid");
  		  return false;
      }
    }
    
    /**
     * Calling method validWarehouse to validate the warehouse input data
     */
    if (!(iWHLO == null || iWHLO == "")) {
      if (iWHLO != "") {
        if (!validWarehouse()) {
          mi.error("Warehouse data " + iWHLO + " is invalid");
    		  return false;
        }
      }
    }
    
    /**
     * Calling method validFacility to validate the facility input data
     */
    if (!(iFACI == null || iFACI == "")) {
      if (!validFacility()) {
        mi.error("Facility data " + iFACI + " is invalid");
  		  return false;
      }
    }
    
    /**
     * Calling method validFacility to validate the facility input data
     */
    if (!(iTEPY == null || iTEPY == "")) {
      if (!validPaymentTerm()) {
        mi.error("Payment term data " + iTEPY + " is invalid");
  		  return false;
      }
    }
    
    /**
     * Calling method validFacility to validate the facility input data
     */
    if (!(iPYCD == null || iPYCD == "")) {
      if (!validPaymentMethod()) {
        mi.error("Payment method data " + iPYCD + " is invalid");
  		  return false;
      }
    }
    
    /**
     * Calling method validFacility to validate the facility input data
     */
    if (!(iTECD == null || iTECD == "")) {
      if (!validCashDiscountTerm()) {
        mi.error("Cash discount term data " + iTECD + " is invalid");
  		  return false;
      }
    }
    
    /**
     * Calling method validFacility to validate the facility input data
     */
    if (!(iTEPA == null || iTEPA == "")) {
      if (!validPackageTerms()) {
        mi.error("Package terms data " + iTEPA + " is invalid");
  		  return false;
      }
    }
    
    /**
     * Calling method validFacility to validate the facility input data
     */
    if (!(iSMCD == null || iSMCD == "")) {
      if (!validSalesPerson()) {
        mi.error("Sales person data " + iSMCD + " is invalid");
  		  return false;
      }
    }

		return true;
	}
	
	/**
	 * validateUpdate - validateUpdate input
	 *
	 * @param  null
	 * @return boolean
	 */
	boolean validateUpdate() {
	  DBAction query = database.table("OOHEAD").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("OACONO", iCONO.toInteger());
    container.set("OAORNO", iORNO);
    
    if (query.read(container)) {
      int orderEntryProgress = container.get("OAHOCD");
      
      if (orderEntryProgress == 1) {
        return false;
      } else {
        return true;
      }
    }
	}
	
	/**
	 * lockUnlockRecord - lockUnlockRecord input
	 *
	 * @param  int orderEntry
	 * @return null
	 */
	void lockUnlockRecord(int orderEntry) {
	  logger.debug("Start database lock/unlock");
	  DBAction query = database.table("OOHEAD").index("00").build();
    DBContainer container = query.getContainer();

    container.setInt("OACONO", iCONO.toInteger());
    container.set("OAORNO", iORNO);
    
    // Update changed information
    if(!query.readLock(container, updateCallBack)){
      mi.error("Record does not exist");
      return;
    }
    
    Closure<?> updateCallBack = { LockedResult lockedResult ->
    
      lockedResult.setInt("OAHOCD", orderEntry);
      
      lockedResult.update();
    }
	}
  
}