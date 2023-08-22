/**
 * README
 * Name: EXT004MI.UpdateStatus
 * Standard Table MITLOC Update
 * Description: Update MITLOC Data
 * Date	    Changed By      	Description
 * 20230814 Hatem Abdellatif - Mohamed Adel  Update MITLOC Data
 */
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
 
public class UpdateStatus extends ExtendM3Transaction {
  private final MIAPI mi;
  private final DatabaseAPI database;
	private final LoggerAPI logger;
	private final ProgramAPI program;
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
  
  public UpdateStatus(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
	this.mi = mi;
	this.database = database;
	this.program = program;
	this.utility = utility;
	this.miCaller = miCaller;
	this.logger = logger;
  }
  
  
  /**
	 * Input fields ExtendM3Transaction
	 */
	private String iCONO;
	private String iWHLO;
	private String iITNO;
	private String iWHSL;
	private String iBANO;
	private String iCAMU;
	private String iFACI;
	private String iREPN;
	private String iSTAS;
	private String iATV4;
	private String iATV5;
	int currentCompany = (Integer)program.getLDAZD().CONO;
	String currentFacility = program.LDAZD.FACI.toString();
	int changeNumber = 0;
	int sequence = 0;
  
  public void main() {
	iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
	   iWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
	   iITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim();
	iWHSL = mi.inData.get("WHSL") == null ? "" : mi.inData.get("WHSL").trim();
	iBANO = mi.inData.get("BANO") == null ? "" : mi.inData.get("BANO").trim();
	iCAMU = mi.inData.get("CAMU") == null ? "" : mi.inData.get("CAMU").trim();
	   iFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim();
	   iREPN = mi.inData.get("REPN") == null ? "" : mi.inData.get("REPN").trim();
	iSTAS = mi.inData.get("STAS") == null ? "" : mi.inData.get("STAS").trim();
	iATV4 = mi.inData.get("ATV4") == null ? "" : mi.inData.get("ATV4").trim();
	iATV5 = mi.inData.get("ATV5") == null ? "" : mi.inData.get("ATV5").trim();
	
	if (!validate()) {
		  return;
		}
	
	//If no error then update the record
	updateRecord();
	
  }
  
  /**
   * updates record in the MITLOC table
   *
   */
  private void updateRecord() {
	
	DBAction query = database.table("MITLOC")
	.index("00")
	.selection("MLCONO", "MLWHLO", "MLITNO", "MLWHSL", "MLBANO", "MLCAMU", "MLREPN")
	.build();
	
	DBContainer container = query.getContainer();
	container.setInt("MLCONO", currentCompany);
	container.set("MLWHLO", iWHLO);
	container.set("MLITNO", iITNO);
	container.set("MLWHSL".trim(), iWHSL.trim());
	container.set("MLBANO", iBANO);
	container.set("MLCAMU", iCAMU);
	
	if (iREPN != "") {
	  container.setLong("MLREPN", iREPN.toLong());
	} else {
	  container.setLong("MLREPN", 0);
	}
	
	query.readLock(container, updateCallBack);
	
	// Update changed information
	if(!query.readLock(container, updateCallBack)){
	  mi.error("Record does not exist");
	  return;
	}

  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
	lockedResult.set("MLLMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
		lockedResult.set("MLCHNO", (changeNumber + 1));
		lockedResult.set("MLCHID", program.getUser());
	lockedResult.set("MLSTAS", iSTAS);
	lockedResult.set("MLATV4", iATV4);
	lockedResult.set("MLATV5", iATV5);
	lockedResult.update();
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