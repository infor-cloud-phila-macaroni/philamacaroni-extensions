/**
 * README
 * Name: EXT008MI.Reset
 * Standard Table OODOCU Reset
 * Description: Reset OODOCU Data
 * Date	    Changed By      	 Description
 * 20231108 Hatem Abdellatif - Reset OODOCU Data
 */
 
import java.time.*;
import java.time.format.DateTimeFormatter;


public class Reset extends ExtendM3Transaction {
  private final MIAPI mi;
  private final DatabaseAPI database;
	private final LoggerAPI logger;
	private final ProgramAPI program;
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
  private final ExtensionAPI extension;
  private final InteractiveAPI interactive;
	
	
	public Reset(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, ExtensionAPI extension, LoggerAPI logger) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
    this.extension = extension;
    this.logger = logger;
  }
  
  
  /**
   * Input Fields
   */
	private String iCONO;
	private String iDIVI;
	private String iORNO;
	private String iDOVA;
	private String iDONR;
	
	
	/**
	 * Input fields ExtendM3Transaction
	 */
	private String iPLLT;
	int changeNumber = 0;
	int sequence = 0;
	
	/**
	 * Global vriables
	 */
	private String company;
	private String division;
	private String customerOrder;
	private String documentNumber;
	private String documentVariant;
	private String externalInternalDocument;
	private String copiesNumber;
	private String printDocument;
	private String lastPrintoutDate;
	private String testIdentity;
	private String entryDate;
	private String entryTime;
	private String changeDate;
	private String changeNumber;
	private String changedBy;
	private String electronicMailAddress;
	private String printer;
	private String timeNow;
	
  
  public void main() {
    
    iCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
   	iDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
   	iORNO = mi.inData.get("ORNO") == null ? "" : mi.inData.get("ORNO").trim();
   	iDOVA = mi.inData.get("DOVA") == null ? "" : mi.inData.get("DOVA").trim();
    iDONR = mi.inData.get("DONR") == null ? "" : mi.inData.get("DONR").trim();
   	
    if (!validate()) {
		  return;
		}
		
		timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
		
		if (fetchRecord()) {
		  //If no error then add the record in EXTD90 database table
		  if (addRecord()) {
		    //If no error then update the record
		    changeRecord();
		  } else {
		    mi.error("Data did not saved in (EXTD90) database table!");
		    return;
		  }
		} else {
		  mi.error("Can not select the desired record.");
		  return;
		}
    
  }
  
  /**
	 * fetchRecord - To get an existing record in Table EXTD90
	 *
	 * @param  null
	 * @return boolean
	 */
  private boolean fetchRecord() {
    DBAction query = database.table("OODOCU")
                             .index("00")
                             .selection("OFCONO", "OFDIVI", "OFORNO", "OFDONR", "OFDOVA", "OFDOTP", 
                                        "OFNOEX", "OFDOCD", "OFDODT", "OFTXID", "OFRGDT", "OFRGTM", 
                                        "OFLMDT", "OFCHNO", "OFCHID", "OFEMAL", "OFDEV")
                             .build();
                                      
    DBContainer container = query.getContainer();
    container.setInt("OFCONO", iCONO.toInteger());
    container.set("OFORNO", iORNO);
    container.set("OFDOVA", iDOVA);
    container.set("OFDONR", iDONR);
      
    if (query.read(container)) {
      company = container.get("OFCONO");
      division = container.get("OFDIVI");
      customerOrder = container.get("OFORNO");
      documentNumber = container.get("OFDONR");
      documentVariant = container.get("OFDOVA");
      externalInternalDocument = container.get("OFDOTP");
      copiesNumber = container.get("OFNOEX");
      printDocument = container.get("OFDOCD");
      lastPrintoutDate = container.get("OFDODT");
      testIdentity = container.get("OFTXID");
      entryDate = container.get("OFRGDT");
      entryTime = container.get("OFRGTM");
      changeDate = container.get("OFLMDT");
      changeNumber = container.get("OFCHNO");
      changedBy = container.get("OFCHID");
      electronicMailAddress = container.get("OFEMAL");
      printer = container.get("OFDEV");
      return true;
    } else {
      mi.error("Record does not exist in (OODOCU) database table!");
      return false;
    }
  }
  
  /**
	 * addRecord - To add a new record in Table EXTD90
	 *
	 * @param  null
	 * @return boolean
	 */
  private boolean addRecord() {
    DBAction query = database.table("EXTD90")
                             .index("00")
                             .build();
      
    DBContainer container = query.getContainer();
    container.setInt("EXCOMP", company.toInteger());
    container.set("EXCURN", customerOrder);
    container.set("EXDUNO", documentNumber);
    container.set("EXDUVA", documentVariant);
    
    container.set("EXDIVS", division);
    container.setInt("EXNOCO", copiesNumber.toInteger());
    container.setInt("EXLMDT", lastPrintoutDate.toInteger());
    container.set("EXIEDU", externalInternalDocument);
    container.setInt("EXPRDU", printDocument.toInteger());
    container.setLong("EXTIDE", testIdentity.toLong());
    container.setInt("EXRGDT", entryDate.toInteger());
    container.setInt("EXRGTM", entryTime.toInteger());
    container.setInt("EXCHDT", changeDate.toInteger());
    container.setInt("EXCHNO", changeNumber.toInteger());
    container.set("EXCHID", changedBy);
    container.set("EXMAIL", electronicMailAddress);
    container.set("EXPRNT", printer);
    container.setInt("EXTMSU", timeNow.toInteger());

    if (query.insert(container)) {
      return true;
    } else {
      return false;
    }
  }
  
  
  /**
   * Resets record in the OODOCU table.
   *
   * @param  null
   * @return void
   */
  private void changeRecord() {
    
    DBAction query = database.table("OODOCU")
                             .index("00")
                             .build();
                             
    DBContainer container = query.getContainer();
    container.setInt("OFCONO", iCONO.toInteger());
    container.set("OFDIVI", iDIVI);
    container.set("OFORNO", iORNO);
    container.set("OFDOVA", iDOVA);
    container.set("OFDONR", iDONR);
    
    // Reset changed information
    if(!query.readLock(container, updateCallBack)){
      mi.error("Record does not exist");
      return;
    }

  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
  
    lockedResult.setInt("OFDODT", 0);
    lockedResult.setInt("OFNOEX", 2);
    lockedResult.setInt("OFLMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
		lockedResult.setInt("OFCHNO", lockedResult.getInt("OFCHNO")+1);
		lockedResult.set("OFCHID", program.getUser());
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
	  * validateDivision - Validate division
	  *
	  * @param  null
	  * @return boolean
	  */
  def boolean validateDivision(){
    boolean validRecord = false;
    def parameters = ["CONO" : iCONO, "DIVI" : iDIVI];
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
	boolean validate() {

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

		return true;
	}
}