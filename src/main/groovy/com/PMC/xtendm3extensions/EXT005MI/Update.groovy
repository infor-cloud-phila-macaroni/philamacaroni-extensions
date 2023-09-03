/**
*  EXT-005MI - Update records in OOHEAD table
*/
/****************************************************************************************
 Extension Name: Update
 Type : ExtendM3Transaction
 Script Authors: Mohamed Adel - Hatem Abdellatif
 Date: 2023-08-28
  
 Description:
	   Update records in OOHEAD table in OIS300
		  
 Revision History:
 Name                               Date                    Version         Description of Changes
 Hatem Abdellatif - Mohamed Adel    2023-09-02              1.0             Initial Version
 ******************************************************************************************/
 import java.time.LocalDate;
 import java.time.LocalTime;
 import java.time.LocalDateTime;
 import java.time.format.DateTimeFormatter;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.text.ParseException;
 import java.time.format.DateTimeParseException;
 
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
	 int currentCompany = (Integer)program.getLDAZD().CONO;
	 String currentFacility = program.LDAZD.FACI.toString();
	 int changeNumber = 0;
	 int sequence = 0;
	 
	 
	 /**
	  * Output Fields of COS100MI
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
	 iRLHM = mi.inData.get("RLHM") == null ? "" : mi.inData.get("RLHM").trim();
	 iRLDZ = mi.inData.get("RLDZ") == null ? "" : mi.inData.get("RLDZ").trim();
	 iRLHZ = mi.inData.get("RLHZ") == null ? "" : mi.inData.get("RLHZ").trim();
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
		 
	 // If no error then update the record
	 changeRecord();
   }
   
   /**
	* updates record in the OOHEAD table
	*
	*/
   private void changeRecord() {
	 DBAction query = database.table("OOHEAD").index("00").build();
	 DBContainer container = query.getContainer();
 
	 container.setInt("OACONO", currentCompany);
	 container.set("OAORNO", iORNO);
	 container.set("OAFACI", currentFacility);
	 
	 // Update changed information
	 if(!query.readLock(container, updateCallBack)){
	   mi.error("Record does not exist");
	   return;
	 }
 
   }
   
   Closure<?> updateCallBack = { LockedResult lockedResult ->
   
	 if(!(iWHLO == null || iWHLO == "")){
	   lockedResult.set("OAWHLO", iWHLO);
	 }
	 
	 if(!(iORDT == null || iORDT == "")){
	   lockedResult.setInt("OAORDT", iORDT.toInteger());
	 }
	 
	 if(!(iRLDT == null || iRLDT == "")){
	   lockedResult.setInt("OARLDT", iRLDT.toInteger());
	 }
	 
	 if(!(iRLHM == null || iRLHM == "")){
	   lockedResult.setInt("OARLHM", iRLHM.toInteger());
	 }
	 
	 if(!(iRLDZ == null || iRLDZ == "")){
	   lockedResult.setInt("OARLDZ", iRLDZ.toInteger());
	 }
	 
	 if(!(iRLHZ == null || iRLHZ == "")){
	   lockedResult.setInt("OARLHZ", iRLHZ.toInteger());
	 }
	 
	 if(!(iFDDT == null || iFDDT == "")){
	   lockedResult.setInt("OAFDDT", iFDDT.toInteger());
	 }
	 
	 if(!(iOPRI == null || iOPRI == "")){
	   lockedResult.setInt("OAOPRI", iOPRI.toInteger());
	 }
	 
	 if(!(iOBLC == null || iOBLC == "")){
	   lockedResult.setInt("OAOBLC", iOBLC.toInteger());
	 }
	 
	 if(!(iTEPY == null || iTEPY == "")){
	   lockedResult.set("OATEPY", iTEPY);
	 }
	 
	 if(!(iPYCD == null || iPYCD == "")){
	   lockedResult.set("OAPYCD", iPYCD);
	 }
	 
	 if(!(iTECD == null || iTECD == "")){
	   lockedResult.set("OATECD", iTECD);
	 }
	 
	 if(!(iMODL == null || iMODL == "")){
	   lockedResult.set("OAMODL", iMODL);
	 }
	 
	 if(!(iTEDL == null || iTEDL == "")){
	   lockedResult.set("OATEDL", iTEDL);
	 }
	 
	 if(!(iTEL2 == null || iTEL2 == "")){
	   lockedResult.set("OATEL2", iTEL2);
	 }
	 
	 if(!(iTEPA == null || iTEPA == "")){
	   lockedResult.set("OATEPA", iTEPA);
	 }
	 
	 if(!(iADID == null || iADID == "")){
	   lockedResult.set("OAADID", iADID);
	 }
	 
	 if(!(iSMCD == null || iSMCD == "")){
	   lockedResult.set("OASMCD", iSMCD);
	 }
	 
	 if(!(iOFNO == null || iOFNO == "")){
	   lockedResult.set("OAOFNO", iOFNO);
	 }
	 
	 if(!(iOREF == null || iOREF == "")){
	   lockedResult.set("OAOREF", iOREF);
	 }
	 
	 if(!(iYREF == null || iYREF == "")){
	   lockedResult.set("OAYREF", iYREF);
	 }
	 
	 if(!(iCUOR == null || iCUOR == "")){
	   lockedResult.set("OACUOR", iCUOR);
	 }
	 
	 if(!(iEXCD == null || iEXCD == "")){
	   lockedResult.set("OAEXCD", iEXCD);
	 }
	 
	 if(!(iHAFE == null || iHAFE == "")){
	   lockedResult.set("OAHAFE", iHAFE);
	 }
	 
	 if(!(iROUT == null || iROUT == "")){
	   lockedResult.set("OAROUT", iROUT);
	 }
	 
	 if(!(iRODN == null || iRODN == "")){
	   lockedResult.setInt("OARODN", iRODN.toInteger());
	 }
	 
	 if(!(iRASN == null || iRASN == "")){
	   lockedResult.set("OARASN", iRASN);
	 }
	 
	 if(!(iUCA1 == null || iUCA1 == "")){
	   lockedResult.set("OAUCA1", iUCA1);
	 }
	 
	 if(!(iUCA2 == null || iUCA2 == "")){
	   lockedResult.set("OAUCA2", iUCA2);
	 }
	 
	 if(!(iUCA3 == null || iUCA3 == "")){
	   lockedResult.set("OAUCA3", iUCA3);
	 }
	 
	 if(!(iUCA4 == null || iUCA4 == "")){
	   lockedResult.set("OAUCA4", iUCA4);
	 }
	 
	 if(!(iUCA5 == null || iUCA5 == "")){
	   lockedResult.set("OAUCA5", iUCA5);
	 }
	 
	 if(!(iUCA6 == null || iUCA6 == "")){
	   lockedResult.set("OAUCA6", iUCA6);
	 }
	 
	 if(!(iUCA7 == null || iUCA7 == "")){
	   lockedResult.set("OAUCA7", iUCA7);
	 }
	 
	 if(!(iUCA8 == null || iUCA8 == "")){
	   lockedResult.set("OAUCA8", iUCA8);
	 }
	 
	 if(!(iUCA9 == null || iUCA9 == "")){
	   lockedResult.set("OAUCA9", iUCA9);
	 }
	 
	 if(!(iUCA0 == null || iUCA0 == "")){
	   lockedResult.set("OAUCA0", iUCA0);
	 }
	 
	 if(!(iUDN1 == null || iUDN1 == "")){
	   lockedResult.setDouble("OAUDN1", iUDN1.toDouble());
	 }
	 
	 if(!(iUDN2 == null || iUDN2 == "")){
	   lockedResult.setDouble("OAUDN2", iUDN2.toDouble());
	 }
	 
	 if(!(iUDN3 == null || iUDN3 == "")){
	   lockedResult.setDouble("OAUDN3", iUDN3.toDouble());
	 }
	 
	 if(!(iUDN4 == null || iUDN4 == "")){
	   lockedResult.setDouble("OAUDN4", iUDN4.toDouble());
	 }
	 
	 if(!(iUDN5 == null || iUDN5 == "")){
	   lockedResult.setDouble("OAUDN5", iUDN5.toDouble());
	 }
	 
	 if(!(iUDN6 == null || iUDN6 == "")){
	   lockedResult.setDouble("OAUDN6", iUDN6.toDouble());
	 }
	 
	 if(!(iUID1 == null || iUID1 == "")){
	   lockedResult.setInt("OAUID1", iUID1.toInteger());
	 }
	 
	 if(!(iUID2 == null || iUID2 == "")){
	   lockedResult.setInt("OAUID2", iUID2.toInteger());
	 }
	 
	 if(!(iUID3 == null || iUID3 == "")){
	   lockedResult.setInt("OAUID3", iUID3.toInteger());
	 }
	 
	 if(!(iUCT1 == null || iUCT1 == "")){
	   lockedResult.set("OAUCT1", iUCT1);
	 }
   
	 lockedResult.set("OALMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
		 lockedResult.set("OACHNO", lockedResult.getInt("OACHNO")+1);
		 lockedResult.set("OACHID", program.getUser());
	 lockedResult.update();
   }
   
   /**
	  * validateDate - Validate input
	  *
	  * @param  String - strDate
	  * @return boolean
	  */
   private boolean validateDate(String strDate){
	 String strDateRegEx =  "\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|[3][01])";
		 
	 if(strDate.matches(strDateRegEx)){
	   SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
	   try{
		   sdf.parse(strDate);
		   return true;
	   }catch(ParseException e){
		 return false;
	   }
		 
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
		 
		 if(!(iORDT == null || iORDT == "")){
	   if (!validateDate(iORDT)) {
		 mi.error("Invalid order date.");
		 return false;
	   }
	 }
	 
	 if(!(iRLDT == null || iRLDT == "")){
	   if (!validateDate(iRLDT)) {
		 mi.error("Invalid customer's purchase order date.");
		 return false;
	   }
	 }
	 
	 if(!(iFDDT == null || iFDDT == "")){
	   if (!validateDate(iFDDT)) {
		 mi.error("Invalid earliest delivery date.");
		 return false;
	   }
	 }
	 
	 if(!(iRLDZ == null || iRLDZ == "")){
	   if (!validateDate(iRLDZ)) {
		 mi.error("Invalid requested delivery date.");
		 return false;
	   }
	 }
	 
	 if(!(iUID1 == null || iUID1 == "")){
	   if (!validateDate(iUID1)) {
		 mi.error("Invalid user defined date 1.");
		 return false;
	   }
	 }
	 
	 if(!(iUID2 == null || iUID2 == "")){
	   if (!validateDate(iUID2)) {
		 mi.error("Invalid user defined date 2.");
		 return false;
	   }
	 }
	 
	 if(!(iUID3 == null || iUID3 == "")){
	   if (!validateDate(iUID3)) {
		 mi.error("Invalid user defined date 3.");
		 return false;
	   }
	 }
 
		 return true;
	 }
   
 }