/**
 * README
 * Name: EXT003MI.Update
 * Standard Table MITTRA Update
 * Description: Update MITTRA Data
 * Date	    Changed By      	Description
 * 20230619 Hatem Abdellatif - Update MITTRA Data
 */
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


public class Update extends ExtendM3Transaction {
  private final MIAPI mi;
  private final DatabaseAPI database;
	private final LoggerAPI logger;
	private final ProgramAPI program;
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
  private final ExtensionAPI extension;
  private final InteractiveAPI interactive;
	
	/**
	 * Global variables
	 */
	private int company;
	private String wareHouse;
	private String itemNumber;
	private String messageNumber;
	private int entryDate = 0;
	private String entryTime = "";
	private String timeNow = "";
	private int timeSuffix = 1;
	private String currDateFormatted;
	private int currTimeFormatted;
	
	
	public Update(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, ExtensionAPI extension, LoggerAPI logger) {
	this.mi = mi;
	this.database = database;
	this.program = program;
	this.utility = utility;
	this.miCaller = miCaller;
	this.extension = extension;
	this.logger = logger;
  }
  
  /**
   * Input Fields of MMS850MI
   */
	private String iPRFL;
	private String iCONO;
	private String iMSNR;
	private String iMSLN;
	private String iMSGS;
	private String iGEDT;
	private String iGETM;
	private String iE0PA;
	private String iE065;
	private String iWHLO;
	private String iWHSL;
	private String iITNO;
	private String iBANO;
	private String iCAMU;
	private String iREPN;
	private String iPOPN;
	private String iALWQ;
	private String iALWT;
	private String iQLQT;
	private String iQLUN;
	private String iBREM;
	private String iBREF;
	private String iBRE2;
	private String iUSD1;
	private String iUSD2;
	private String iUSD3;
	private String iUSD4;
	private String iUSD5;
	private String iRSCD;
	private String iQLDT;
	private String iQLTM;
	private String iCAWE;
	private String iPMSN;
	private String iSTAG;
	private String iPRDT;
	private String iRESP;
	
	
	/**
	 * Input fields ExtendM3Transaction
	 */
	private String iTRPR;
	int currentCompany = (Integer)program.getLDAZD().CONO;
	String currentUser = program.getUser();
	String customer = null;
	int changeNumber = 0;
	int sequence = 0;
	private ExpressionFactory finalexpression;
	
  
  public void main() {
	
	iPRFL = mi.inData.get("PRFL") == null ? "" : mi.inData.get("PRFL").trim();
	   iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
	   iMSNR = mi.inData.get("MSNR") == null ? "" : mi.inData.get("MSNR").trim();
	iMSLN = mi.inData.get("MSLN") == null ? "" : mi.inData.get("MSLN").trim();
	iMSGS = mi.inData.get("MSGS") == null ? "" : mi.inData.get("MSGS").trim();
	iGEDT = mi.inData.get("GEDT") == null ? "" : mi.inData.get("GEDT").trim();
	   iGETM = mi.inData.get("GETM") == null ? "" : mi.inData.get("GETM").trim();
	   iE0PA = mi.inData.get("E0PA") == null ? "" : mi.inData.get("E0PA").trim();
	iE065 = mi.inData.get("E065") == null ? "" : mi.inData.get("E065").trim();
	iWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
	   iWHSL = mi.inData.get("WHSL") == null ? "" : mi.inData.get("WHSL").trim();
	   iITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim();
	iBANO = mi.inData.get("BANO") == null ? "" : mi.inData.get("BANO").trim();
	iCAMU = mi.inData.get("CAMU") == null ? "" : mi.inData.get("CAMU").trim();
	iREPN = mi.inData.get("REPN") == null ? "" : mi.inData.get("REPN").trim();
	  iPOPN = mi.inData.get("POPN") == null ? "" : mi.inData.get("POPN").trim();
	  iALWQ = mi.inData.get("ALWQ") == null ? "" : mi.inData.get("ALWQ").trim();
	  iALWT = mi.inData.get("ALWT") == null ? "" : mi.inData.get("ALWT").trim();
	  iQLQT = mi.inData.get("QLQT") == null ? "" : mi.inData.get("QLQT").trim();
	  iQLUN = mi.inData.get("QLUN") == null ? "" : mi.inData.get("QLUN").trim();
	  iBREM = mi.inData.get("BREM") == null ? "" : mi.inData.get("BREM").trim();
	  iBREF = mi.inData.get("BREF") == null ? "" : mi.inData.get("BREF").trim();
	iBRE2 = mi.inData.get("BRE2") == null ? "" : mi.inData.get("BRE2").trim();
	iUSD1 = mi.inData.get("USD1") == null ? "" : mi.inData.get("USD1").trim();
	iUSD2 = mi.inData.get("USD2") == null ? "" : mi.inData.get("USD2").trim();
	iUSD3 = mi.inData.get("USD3") == null ? "" : mi.inData.get("USD3").trim();
	iUSD4 = mi.inData.get("USD4") == null ? "" : mi.inData.get("USD4").trim();
	iUSD5 = mi.inData.get("USD5") == null ? "" : mi.inData.get("USD5").trim();
	iRSCD = mi.inData.get("RSCD") == null ? "" : mi.inData.get("RSCD").trim();
	iQLDT = mi.inData.get("QLDT") == null ? "" : mi.inData.get("QLDT").trim();
	iQLTM = mi.inData.get("QLTM") == null ? "" : mi.inData.get("QLTM").trim();
	iCAWE = mi.inData.get("CAWE") == null ? "" : mi.inData.get("CAWE").trim();
	iPMSN = mi.inData.get("PMSN") == null ? "" : mi.inData.get("PMSN").trim();
	iSTAG = mi.inData.get("STAG") == null ? "" : mi.inData.get("STAG").trim();
	iPRDT = mi.inData.get("PRDT") == null ? "" : mi.inData.get("PRDT").trim();
	iRESP = mi.inData.get("RESP") == null ? "" : mi.inData.get("RESP").trim();
	iTRPR = mi.inData.get("TRPR") == null ? "" : mi.inData.get("TRPR").trim();
	
	if (!validate()) {
		  return;
		}
		
	
	/**
	  * Execute MMS850MI/AddAdjust
	  */
	def callback = {
	  Map <String, String> response ->
	  
		logger.debug(response.errorMessage);

		 if (response.error) {
			mi.error(response.errorMessage);
			return;
		 }

	}
	
	/**
	 * MMS850MI/AddAdjust In Data parametrers
	 */

	def params = [  "PRFL" : iPRFL,"QLQT" : iQLQT
				   ,"CONO" : iCONO,"QLUN" : iQLUN
				   ,"MSNR" : iMSNR,"BREM" : iBREM
				   ,"MSLN" : iMSLN,"BREF" : iBREF
				   ,"MSGS" : iMSGS,"BRE2" : iBRE2
				   ,"GEDT" : iGEDT,"USD1" : iUSD1
				   ,"GETM" : iGETM,"USD2" : iUSD2
				   ,"E0PA" : iE0PA,"USD3" : iUSD3
				   ,"E065" : iE065,"USD4" : iUSD4
				   ,"WHLO" : iWHLO,"USD5" : iUSD5
				   ,"WHSL" : iWHSL,"RSCD" : iRSCD
				   ,"ITNO" : iITNO,"QLDT" : iQLDT
				   ,"BANO" : iBANO,"QLTM" : iQLTM
				   ,"CAMU" : iCAMU,"CAWE" : iCAWE
				   ,"REPN" : iREPN,"PMSN" : iPMSN
				   ,"POPN" : iPOPN,"STAG" : iSTAG
				   ,"ALWQ" : iALWQ,"PRDT" : iPRDT
				   ,"ALWT" : iALWT,"RESP" : iRESP]
	 
	logger.debug("Before miCall");
	
	timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
	miCaller.call("MMS850MI", "AddAdjust", params, callback);
	
	logger.debug("After miCall");
	
	entryDate = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger();
	
	// Wait 5 seconds then fetch the last added record in MITTRA table.
	TimeUnit.MILLISECONDS.sleep(5000);
	fetchRecord();
	
	// Update the Inventory accounting price in the last added record in MITTRA table.
	if (entryTime >= timeNow) {
	  updatePrice();
	}
  }
  
  /**
	 * fetchRecord - To select the latest added record in MITTRA table by MMS850MI.AddAdjust
	 *
	 * @param  null
	 * @return boolean
	 */
  private void fetchRecord() {
	
	  Closure<?> fetchCallBack = { DBContainer lockedResult ->
	   
		logger.debug("select in fetchRecord()");
		
		entryTime = lockedResult.get("MTRGTM");
		mi.outData.put("RGTM", "Time ${entryTime} not updated. Error updating.");
		
	  }
	  
	  int currentCompany = (Integer)program.getLDAZD().CONO;
	  DBAction query = database.table("MITTRA")
		.index("00")
		.selection("MTCONO", "MTWHLO", "MTITNO", "MTRGDT", "MTRGTM", "MTTMSX").reverse()
		.build();
		
		logger.debug("Before database read");

	  DBContainer container = query.getContainer();
		  container.setInt("MTCONO", currentCompany);
		  container.set("MTWHLO", iWHLO);
		  container.set("MTITNO", iITNO);
		  container.set("MTRGDT", entryDate.toInteger());
		
	  if (!query.readAll(container, 4, 1, fetchCallBack)) {
		mi.error("Record does not exist.");
	  }
	  
	  logger.debug("After database read");
  }
  
  
  /**
	 * updatePrice - To update the inventory accounting price
	 *
	 * @param  null
	 * @return boolean
	 */
  private void updatePrice() {
	
	  Closure<?> updateCallBack = { LockedResult lockedResult ->
	   
		logger.debug("Before price update");
		
		if (iTRPR != ""){
			  lockedResult.setDouble("MTTRPR", iTRPR.toDouble());
			}
			
			lockedResult.update();
			
			logger.debug("After price update");
	  }
	  
	  int currentCompany = (Integer)program.getLDAZD().CONO;
	  DBAction query = database.table("MITTRA")
		.index("00")
		.selection("MTCONO", "MTWHLO", "MTITNO", "MTRGTM", "MTRGDT", "MTTMSX").reverse()
		.build();
		
		logger.debug("Before database update");

	  DBContainer container = query.getContainer();
		  container.setInt("MTCONO", currentCompany);
		  container.set("MTWHLO", iWHLO);
		  container.set("MTITNO", iITNO);
		  container.setInt("MTRGDT", entryDate.toInteger());
		  container.setInt("MTRGTM", entryTime.toInteger());
		  container.setInt("MTTMSX", 1);
		
	  if (query.readAllLock(container, 6, updateCallBack)) {
		mi.outData.put("TRPR", "Successfully updated Price ${iTRPR}.");
			mi.outData.put("RGTM", "Successfully updated Time ${entryTime}.");
		  } else {
			mi.outData.put("TRPR", "Price ${iTRPR} not updated. Error updating.");
			mi.outData.put("RGTM", "Time ${entryTime} not updated. Error updating.");
		  }
	  
	  logger.debug("After database update");
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