package com.acmecorp.xtendm3extensions

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

package com.acmecorp.xtendm3extensions

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * README
 * Name: EXT002MI.Update
 * Standard Table MPDOPE Update
 * Description: Update MPDOPE Data
 * Date	    Changed By      	Description
 * 20230608 Hatem Abdellatif - Update MPDOPE Data
 */
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Update extends ExtendM3Transaction {
  private final MIAPI mi;
  private final DatabaseAPI database;
	private final LoggerAPI logger;
	private final ProgramAPI program;
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
	
	
	public Update(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
  }
  
  
  /**
   * Input Fields of PDS002MI
   */
	private String iCONO;
	private String iFACI;
	private String iPRNO;
	private String iSTRT;
	private String iMSEQ;
	private String iOPNO;
	private String iFDAT;
	private String iSTRD;
	private String iECVE;
	private String iMTPL;
	private String iPLGR;
	private String iOPDS;
	private String iPITI;
	private String iCTCD;
	private String iSETI;
	private String iFXTI;
	private String iOSET;
	private String iPRNM;
	private String iPRNP;
	private String iPHOP;
	private String iSENP;
	private String iODBF;
	private String iPRDY;
	private String iSLAC;
	private String iVAPC;
	private String iSCPC;
	private String iSWQT;
	private String iDOID;
	private String iTOOL;
	private String iWLDE;
	private String iSUBC;
	private String iSSUF;
	private String iLASK;
	private String iSDCD;
	private String iINSK;
	private String iPIPR;
	private String iWAFA;
	private String iSEPR;
	private String iWCRF;
	private String iKIWG;
	private String iCAMP;
	private String iINWE;
	private String iOPV3;
	private String iMFPR;
	private String iAURP;
	private String iOPCM;
	private String iAUIN;
	private String iCFID;
	private String iCFRI;
	private String iSUNO;
	private String iNSUN;
	private String iTXT1;
	private String iTXT2;
	private String iASOP;
	private String iSDUP;
	private String iTDAT;
	private String iVRSN;
	private String iAMAO;
	private String iAPON;
	private String iNXOP;
	private String iSLAT;
	private String iPOOC;
	private String iRESR;
	private String iPRET;
	private String iSPLK;
	private String iSPLN;
	private String iSPSZ;
	private String iMDOP;
	private String iPOTM;
	private String iPLMG;
	private String iPLOQ;
	private String iPLTQ;
	
	/**
	 * Input fields ExtendM3Transaction
	 */
	private String iPLLT;
	int currentCompany = (Integer)program.getLDAZD().CONO;
	//String currentFacility = program.LDAZD.FACI.toString();
	int changeNumber = 0;
	int sequence = 0;
	
	/**
	 * Output Fields of COS100MI
	 */
	private String oPRNO    = "";
	private String oOPNO    = "";
	private String oFACI    = "";
  
  public void main() {
    
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
   	iFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim();
   	iPRNO = mi.inData.get("PRNO") == null ? "" : mi.inData.get("PRNO").trim();
    iSTRT = mi.inData.get("STRT") == null ? "" : mi.inData.get("STRT").trim();
    iMSEQ = mi.inData.get("MSEQ") == null ? "" : mi.inData.get("MSEQ").trim();
    iOPNO = mi.inData.get("OPNO") == null ? "" : mi.inData.get("OPNO").trim();
   	iFDAT = mi.inData.get("FDAT") == null ? "" : mi.inData.get("FDAT").trim();
   	iSTRD = mi.inData.get("STRD") == null ? "" : mi.inData.get("STRD").trim();
    iECVE = mi.inData.get("ECVE") == null ? "" : mi.inData.get("ECVE").trim();
    iMTPL = mi.inData.get("MTPL") == null ? "" : mi.inData.get("MTPL").trim();
   	iPLGR = mi.inData.get("PLGR") == null ? "" : mi.inData.get("PLGR").trim();
   	iOPDS = mi.inData.get("OPDS") == null ? "" : mi.inData.get("OPDS").trim();
    iPITI = mi.inData.get("PITI") == null ? "" : mi.inData.get("PITI").trim();
    iCTCD = mi.inData.get("CTCD") == null ? "" : mi.inData.get("CTCD").trim();
    iSETI = mi.inData.get("SETI") == null ? "" : mi.inData.get("SETI").trim();
  	iFXTI = mi.inData.get("FXTI") == null ? "" : mi.inData.get("FXTI").trim();
  	iOSET = mi.inData.get("OSET") == null ? "" : mi.inData.get("OSET").trim();
  	iPRNM = mi.inData.get("PRNM") == null ? "" : mi.inData.get("PRNM").trim();
  	iPRNP = mi.inData.get("PRNP") == null ? "" : mi.inData.get("PRNP").trim();
  	iPHOP = mi.inData.get("PHOP") == null ? "" : mi.inData.get("PHOP").trim();
  	iSENP = mi.inData.get("SENP") == null ? "" : mi.inData.get("SENP").trim();
	iODBF = mi.inData.get("ODBF") == null ? "" : mi.inData.get("ODBF").trim();
    iPRDY = mi.inData.get("PRDY") == null ? "" : mi.inData.get("PRDY").trim();
    iSLAC = mi.inData.get("SLAC") == null ? "" : mi.inData.get("SLAC").trim();
    iVAPC = mi.inData.get("VAPC") == null ? "" : mi.inData.get("VAPC").trim();
    iSCPC = mi.inData.get("SCPC") == null ? "" : mi.inData.get("SCPC").trim();
    iSWQT = mi.inData.get("SWQT") == null ? "" : mi.inData.get("SWQT").trim();
    iDOID = mi.inData.get("DOID") == null ? "" : mi.inData.get("DOID").trim();
    iTOOL = mi.inData.get("TOOL") == null ? "" : mi.inData.get("TOOL").trim();
    iWLDE = mi.inData.get("WLDE") == null ? "" : mi.inData.get("WLDE").trim();
    iSUBC = mi.inData.get("SUBC") == null ? "" : mi.inData.get("SUBC").trim();
    iSSUF = mi.inData.get("SSUF") == null ? "" : mi.inData.get("SSUF").trim();
    iLASK = mi.inData.get("LASK") == null ? "" : mi.inData.get("LASK").trim();
    iSDCD = mi.inData.get("SDCD") == null ? "" : mi.inData.get("SDCD").trim();
    iINSK = mi.inData.get("INSK") == null ? "" : mi.inData.get("INSK").trim();
    iPIPR = mi.inData.get("PIPR") == null ? "" : mi.inData.get("PIPR").trim();
    iWAFA = mi.inData.get("WAFA") == null ? "" : mi.inData.get("WAFA").trim();
    iSEPR = mi.inData.get("SEPR") == null ? "" : mi.inData.get("SEPR").trim();
    iWCRF = mi.inData.get("WCRF") == null ? "" : mi.inData.get("WCRF").trim();
    iKIWG = mi.inData.get("KIWG") == null ? "" : mi.inData.get("KIWG").trim();
    iCAMP = mi.inData.get("CAMP") == null ? "" : mi.inData.get("CAMP").trim();
    iINWE = mi.inData.get("INWE") == null ? "" : mi.inData.get("INWE").trim();
    iOPV3 = mi.inData.get("OPV3") == null ? "" : mi.inData.get("OPV3").trim();
    iMFPR = mi.inData.get("MFPR") == null ? "" : mi.inData.get("MFPR").trim();
    iAURP = mi.inData.get("AURP") == null ? "" : mi.inData.get("AURP").trim();
    iOPCM = mi.inData.get("OPCM") == null ? "" : mi.inData.get("OPCM").trim();
    iAUIN = mi.inData.get("AUIN") == null ? "" : mi.inData.get("AUIN").trim();
    iCFID = mi.inData.get("CFID") == null ? "" : mi.inData.get("CFID").trim();
    iCFRI = mi.inData.get("CFRI") == null ? "" : mi.inData.get("CFRI").trim();
    iSUNO = mi.inData.get("SUNO") == null ? "" : mi.inData.get("SUNO").trim();
    iNSUN = mi.inData.get("NSUN") == null ? "" : mi.inData.get("NSUN").trim();
    iTXT1 = mi.inData.get("TXT1") == null ? "" : mi.inData.get("TXT1").trim();
    iTXT2 = mi.inData.get("TXT2") == null ? "" : mi.inData.get("TXT2").trim();
    iASOP = mi.inData.get("ASOP") == null ? "" : mi.inData.get("ASOP").trim();
    iSDUP = mi.inData.get("SDUP") == null ? "" : mi.inData.get("SDUP").trim();
    iTDAT = mi.inData.get("TDAT") == null ? "" : mi.inData.get("TDAT").trim();
    iVRSN = mi.inData.get("VRSN") == null ? "" : mi.inData.get("VRSN").trim();
    iAMAO = mi.inData.get("AMAO") == null ? "" : mi.inData.get("AMAO").trim();
    iAPON = mi.inData.get("APON") == null ? "" : mi.inData.get("APON").trim();
    iNXOP = mi.inData.get("NXOP") == null ? "" : mi.inData.get("NXOP").trim();
    iSLAT = mi.inData.get("SLAT") == null ? "" : mi.inData.get("SLAT").trim();
    iPOOC = mi.inData.get("POOC") == null ? "" : mi.inData.get("POOC").trim();
    iRESR = mi.inData.get("RESR") == null ? "" : mi.inData.get("RESR").trim();
    iPRET = mi.inData.get("PRET") == null ? "" : mi.inData.get("PRET").trim();
    iSPLK = mi.inData.get("SPLK") == null ? "" : mi.inData.get("SPLK").trim();
    iSPLN = mi.inData.get("SPLN") == null ? "" : mi.inData.get("SPLN").trim();
    iSPSZ = mi.inData.get("SPSZ") == null ? "" : mi.inData.get("SPSZ").trim();
    iMDOP = mi.inData.get("MDOP") == null ? "" : mi.inData.get("MDOP").trim();
    iPOTM = mi.inData.get("POTM") == null ? "" : mi.inData.get("POTM").trim();
    iPLMG = mi.inData.get("PLMG") == null ? "" : mi.inData.get("PLMG").trim();
    iPLOQ = mi.inData.get("PLOQ") == null ? "" : mi.inData.get("PLOQ").trim();
    iPLTQ = mi.inData.get("PLTQ") == null ? "" : mi.inData.get("PLTQ").trim();
    iPLLT = mi.inData.get("PLLT") == null ? "" : mi.inData.get("PLLT").trim();
    
    
    if (!validate()) {
		  return;
		}
		
    
    /**
      * Execute PDS002MI/UpdateOperation
      */

    def callback = {
    Map <String, String> response ->
  
     if (response.error) {
        mi.error(response.errorMessage);
        return;
     }

     /**
      * Setting up out data in transaction using MI API
      */
     if(response.PRNO != null){
        oPRNO = response.PRNO;
     }

     if(response.OPNO != null){
        oOPNO = response.OPNO;
     }
     
     if(response.FACI != null){
        oFACI = response.FACI;
     }
       logger.debug("Response = ${response}");
    }
    
    /**
     * PDS002MI/UpdateOperation In Data parametrers
     */

    def params = [  "CONO" : iCONO,"SETI" : iSETI,"TOOL" : iTOOL,"OPV3" : iOPV3
                   ,"FACI" : iFACI,"FXTI" : iFXTI,"WLDE" : iWLDE,"MFPR" : iMFPR
                   ,"PRNO" : iPRNO,"OSET" : iOSET,"SUBC" : iSUBC,"AURP" : iAURP
                   ,"STRT" : iSTRT,"PRNM" : iPRNM,"SSUF" : iSSUF,"OPCM" : iOPCM
                   ,"MSEQ" : iMSEQ,"PRNP" : iPRNP,"LASK" : iLASK,"AUIN" : iAUIN
                   ,"OPNO" : iOPNO,"PHOP" : iPHOP,"SDCD" : iSDCD,"CFID" : iCFID
                   ,"FDAT" : iFDAT,"SENP" : iSENP,"INSK" : iINSK,"CFRI" : iCFRI
                   ,"STRD" : iSTRD,"ODBF" : iODBF,"PIPR" : iPIPR,"SUNO" : iSUNO
                   ,"ECVE" : iECVE,"PRDY" : iPRDY,"WAFA" : iWAFA,"NSUN" : iNSUN
                   ,"MTPL" : iMTPL,"SLAC" : iSLAC,"SEPR" : iSEPR,"TXT1" : iTXT1
                   ,"PLGR" : iPLGR,"VAPC" : iVAPC,"WCRF" : iWCRF,"TXT2" : iTXT2
                   ,"OPDS" : iOPDS,"SCPC" : iSCPC,"KIWG" : iKIWG,"ASOP" : iASOP
                   ,"PITI" : iPITI,"SWQT" : iSWQT,"CAMP" : iCAMP,"SDUP" : iSDUP
                   ,"CTCD" : iCTCD,"DOID" : iDOID,"INWE" : iINWE,"TDAT" : iTDAT
                   ,"VRSN" : iVRSN,"AMAO" : iAMAO,"APON" : iAPON,"NXOP" : iNXOP
                   ,"SLAT" : iSLAT,"POOC" : iPOOC,"RESR" : iRESR,"PRET" : iPRET
                   ,"SPLK" : iSPLK,"SPLN" : iSPLN,"SPSZ" : iSPSZ,"MDOP" : iMDOP
                   ,"POTM" : iPOTM,"PLMG" : iPLMG,"PLOQ" : iPLOQ,"PLTQ" : iPLTQ]
                   
    
                   
    miCaller.call("PDS002MI", "UpdateOperation", params, callback);
    
    //If no error then update the record
    changeRecord();
    
  }
  

  /**
   * Updates record in the MPDOPE table.
   *
   * @param  null
   * @return void
   */
  private void changeRecord() {
    DBAction query = database.table("MPDOPE").index("00").build();
    DBContainer container = query.getContainer();
    container.setInt("POCONO", currentCompany);
    container.set("POFACI", iFACI);
    container.set("POPRNO", iPRNO);
    container.setInt("POOPNO", iOPNO.toInteger());
    container.set("POSTRT", iSTRT);
    container.setInt("POFDAT", iFDAT.toInteger());
    query.readLock(container, updateCallBack);
    
    // Update changed information
    if(!query.readLock(container, updateCallBack)){
      mi.error("Record does not exist");
      return;
    }

  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
    lockedResult.set("POLMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
		lockedResult.set("POCHNO", (changeNumber + 1));
		lockedResult.set("POCHID", program.getUser());
		//lockedResult.set("POFACI", currentFacility);
		//lockedResult.set("POPRNO", iPRNO);
		//lockedResult.setInt("POOPNO", iOPNO.toInteger());
		lockedResult.setInt("POPLLT", iPLLT.toInteger());
    lockedResult.update();
  }
  
   /**
	 * validate - Validate input
	 *
	 * @return boolean
	 */
	boolean validate() {

		if (iCONO == "") {
			iCONO = (Integer)program.getLDAZD().CONO;
		} else if (!iCONO.isInteger()) {
			mi.error("Company number " + iCONO + " is invalid");
			return false;
		}
		
		if (iFACI == "") {
			mi.error("Facility value " + iFACI + " is empty (note: Facility is required )");
			return false;
		}

		if (iPRNO == "") {
			mi.error("Product NO. " + iPRNO + " is empty (note: Product number is required )");
			return false;
		}

		if (iOPNO != "") {

			if (!iOPNO.isInteger()) {
				mi.error("Operation number " + iOPNO + " is invalid");
				return false;
			}
		} else {
			mi.error("Operation NO. " + iOPNO + " is empty (note: Operation number is required )");
			return false;
		}

	/**
     * Valudate Fixes Amount 0 - 1
     */

		if (iPLLT != "") {

			if (iPLLT != "0" && iPLLT != "1") {
				mi.error("Lot NO. control value (" + iPLLT + ") should be 0 or 1");
				return false;
			}
		}	

		return true;
	}
}

