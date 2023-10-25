/**
 * README
 * Name: EXT989MI.Delete
 * Extend M3 Table EXTD89 Delete
 * Description: Delete EXTD89 Data
 * Date	    Changed By      	               Description
 * 20231004 Mohamed Adel - Hatem Abdellatif  Update EXTD89 Data
 */
public class Delete extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final LoggerAPI logger
  private final UtilityAPI utility;
  private final MICallerAPI miCaller;
  
  public Delete(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
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
  private String iTRNR;
  private String iTYPE;
  private String iDONR;
  int currentCompany = (Integer)program.getLDAZD().CONO;
  
  public void main() {
	iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
	iTRNR = mi.inData.get("TRNR") == null ? "" : mi.inData.get("TRNR").trim();
	iTYPE = mi.inData.get("TYPE") == null ? "" : mi.inData.get("TYPE").trim();
	iDONR = mi.inData.get("DONR") == null ? "" : mi.inData.get("DONR").trim();
	
	logger.debug("Before miCall");
	
	DBAction query = database.table("EXTD89")
					 .index("00")
					 .build();
		
	  DBContainer container = query.getContainer();
	container.set("EXCONO", currentCompany);
	  container.set("EXTRNR", iTRNR);
	  container.set("EXTYPE", iTYPE);
	  container.setInt("EXDONR", iDONR.toInteger());
	  
	  Closure<?> deleteCallback = { LockedResult lockedResult ->
	  lockedResult.delete();
	}
	
	if(!query.readLock(container, deleteCallback)) {
	  mi.error("Record does not exist");
	  return;
		}
		
		logger.debug("After miCall");
  }
  
}