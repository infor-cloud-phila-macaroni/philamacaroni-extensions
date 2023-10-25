/**
 * README
 * Name: EXT007MI.Delete
 * Extend M3 Table EXTD89 Delete
 * Description: Delete EXTD89 Data
 * Date	    Changed By      	               Description
 * 20230929 Hatem Abdellatif - Mohamed Adel  Update EXTD89 Data
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
  private String iDOLN;
  int currentCompany = (Integer)program.getLDAZD().CONO;
  
  public void main() {
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
    iTRNR = mi.inData.get("TRNR") == null ? "" : mi.inData.get("TRNR").trim();
    iTYPE = mi.inData.get("TYPE") == null ? "" : mi.inData.get("TYPE").trim();
    iDOLN = mi.inData.get("DOLN") == null ? "" : mi.inData.get("DOLN").trim();
    
    if (!validate()) {
		  return;
		}
    
    // Delete an existing record in EXTD89 table.
    deleteRecord();
  }
  
  /**
	 * deleteRecord - To delete an existing record in Table EXTD89
	 *
	 * @param  null
	 * @return boolean
	 */
  private void deleteRecord() {
    DBAction query = database.table("EXTD89")
                     .index("00")
                     .build();
		
  	DBContainer container = query.getContainer();
    container.setInt("EXCONO", iCONO.toInteger());
  	container.set("EXTRNR", iTRNR);
  	container.set("EXTYPE", iTYPE);
  	container.setInt("EXDOLN", iDOLN.toInteger());
  	
  	Closure<?> deleteCallback = { LockedResult lockedResult ->
      lockedResult.delete();
    }
    
    if(!query.readLock(container, deleteCallback)) {
      mi.error("Record does not exist");
      return;
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