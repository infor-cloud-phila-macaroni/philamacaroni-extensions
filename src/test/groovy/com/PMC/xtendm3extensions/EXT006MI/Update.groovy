/**
*  EXT-006MI - Update records in MHDISH table
*/
/****************************************************************************************
Extension Name: Update
Type : ExtendM3Transaction
Script Authors: Mohamed Adel - Hatem Abdellatif
Date: 2023-08-28
 
Description:
      Update records in MHDISH table in MWS410
         
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
  
  /*** Input Fields of EXTUBH Table */
  private String inCONO;
  private String inINOU;
  private String inDLIX;
  private String inDPOL;
  private String inDSDT;
  private String inDSHM;
  private String inSROT;
  private String inSROD;
  private String inROUT;
  private String inRODN;
  private String inMODL;
  private String inMODF;
  private String inTEDL;
  private String inTEDF;
  private String inDTDT;
  private String inDTHM;
  private String inHAFE;
  private String inLODO;
  private String inETRN;
  private String inFWNS;
  private String inFWNO;
  private String inIRST;
  private String inTSID;
  private String inRASN;
  private String inDFDT;
  private String inDFHM;
  private String inPWDT;
  private String inPWHM;
  private String inAWDT;
  private String inAWHM;
  private String inRSCD;
  int changeNumber = 0;


  public Update(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
    this.mi = mi;
    this.database = database;
    this.program = program;
    this.utility = utility;
    this.miCaller = miCaller;
    this.logger = logger;
  }
  
  public void main() {
    
    if(mi.inData.get("CONO") == null || mi.inData.get("CONO").trim() =="") {
      inCONO = Integer.parseInt(program.LDAZD.CONO.toString())
    }else {
      inCONO = mi.inData.get("CONO") as int
    }
    
    //Get All input Fields Values:
    inCONO = mi.inData.get("CONO") == null ? "" : mi.inData.get("CONO").trim();
    inINOU = mi.inData.get("INOU") == null ? "" : mi.inData.get("INOU").trim();
    inDLIX = mi.inData.get("DLIX") == null ? "" : mi.inData.get("DLIX").trim();
    inDPOL = mi.inData.get("DPOL") == null ? "" : mi.inData.get("DPOL").trim();
    inDSDT = mi.inData.get("DSDT") == null ? "" : mi.inData.get("DSDT").trim();
    inDSHM = mi.inData.get("DSHM") == null ? "" : mi.inData.get("DSHM").trim();
    inSROT = mi.inData.get("SROT") == null ? "" : mi.inData.get("SROT").trim();
    inSROD = mi.inData.get("SROD") == null ? "" : mi.inData.get("SROD").trim();
    inROUT = mi.inData.get("ROUT") == null ? "" : mi.inData.get("ROUT").trim();
    inRODN = mi.inData.get("RODN") == null ? "" : mi.inData.get("RODN").trim();
    inMODL = mi.inData.get("MODL") == null ? "" : mi.inData.get("MODL").trim();
    inMODF = mi.inData.get("MODF") == null ? "" : mi.inData.get("MODF").trim();
    inTEDL = mi.inData.get("TEDL") == null ? "" : mi.inData.get("TEDL").trim();
    inTEDF = mi.inData.get("TEDF") == null ? "" : mi.inData.get("TEDF").trim();
    inDTDT = mi.inData.get("DTDT") == null ? "" : mi.inData.get("DTDT").trim();
    inDTHM = mi.inData.get("DTHM") == null ? "" : mi.inData.get("DTHM").trim();
    inHAFE = mi.inData.get("HAFE") == null ? "" : mi.inData.get("HAFE").trim();
    inLODO = mi.inData.get("LODO") == null ? "" : mi.inData.get("LODO").trim();
    inETRN = mi.inData.get("ETRN") == null ? "" : mi.inData.get("ETRN").trim();
    inFWNS = mi.inData.get("FWNS") == null ? "" : mi.inData.get("FWNS").trim();
    inFWNO = mi.inData.get("FWNO") == null ? "" : mi.inData.get("FWNO").trim();
    inIRST = mi.inData.get("IRST") == null ? "" : mi.inData.get("IRST").trim();
    inTSID = mi.inData.get("TSID") == null ? "" : mi.inData.get("TSID").trim();
    inRASN = mi.inData.get("RASN") == null ? "" : mi.inData.get("RASN").trim();
    inDFDT = mi.inData.get("DFDT") == null ? "" : mi.inData.get("DFDT").trim();
    inDFHM = mi.inData.get("DFHM") == null ? "" : mi.inData.get("DFHM").trim();
    inPWDT = mi.inData.get("PWDT") == null ? "" : mi.inData.get("PWDT").trim();
    inPWHM = mi.inData.get("PWHM") == null ? "" : mi.inData.get("PWHM").trim();
    inAWDT = mi.inData.get("AWDT") == null ? "" : mi.inData.get("AWDT").trim();
    inAWHM = mi.inData.get("AWHM") == null ? "" : mi.inData.get("AWHM").trim();
    inRSCD = mi.inData.get("RSCD") == null ? "" : mi.inData.get("RSCD").trim();
    
    if (!validate()) {
			 return;
		}
    

    DBAction dbaMHDISH = database.table("MHDISH")
          .index("00")
          .build();
          
    DBContainer conMHDISH = dbaMHDISH.getContainer();
    conMHDISH.setInt("OQCONO", inCONO.toInteger());
    conMHDISH.setInt("OQINOU", inINOU.toInteger());
    conMHDISH.setLong("OQDLIX", inDLIX.toLong());
    
    
    // Update changed information
    if(!dbaMHDISH.readLock(conMHDISH, updateCallBack)){
      mi.error("Record does not exist");
      return;
    }

  }
  
  Closure<?> updateCallBack = { LockedResult lockedResult ->
  
    if(!(inDPOL == null || inDPOL == "")){
      lockedResult.set("OQDPOL", inDPOL);
    }
    
    if(!(inDSDT == null || inDSDT == "")){
      lockedResult.setInt("OQDSDT", inDSDT.toInteger());
    }
    
    if(!(inDSHM == null || inDSHM == "")){
      lockedResult.setInt("OQDSHM", inDSHM.toInteger());
    } else {
      lockedResult.setInt("OQDSHM", 0);
    }
    
    if(!(inSROT == null || inSROT == "")){
      lockedResult.set("OQSROT", inSROT);
    }
    
    if(!(inSROD == null || inSROD == "")){
      lockedResult.setInt("OQSROD", inSROD.toInteger());
    }
    
    if(!(inROUT == null || inROUT == "")){
      lockedResult.set("OQROUT", inROUT);
    }
    
    if(!(inRODN == null || inRODN == "")){
      lockedResult.setInt("OQRODN", inRODN.toInteger());
    }
    
    if(!(inMODL == null || inMODL == "")){
      lockedResult.set("OQMODL", inMODL);
    }
    
    if(!(inMODF == null || inMODF == "")){
      lockedResult.set("OQMODF", inMODF);
    }
    
    if(!(inTEDL == null || inTEDL == "")){
      lockedResult.set("OQTEDL", inTEDL);
    }
    
    if(!(inTEDF == null || inTEDF == "")){
      lockedResult.set("OQTEDF", inTEDF);
    }
    
    if(!(inDTDT == null || inDTDT == "")){
      lockedResult.setInt("OQDTDT", inDTDT.toInteger());
    }
    
    if(!(inDTHM == null || inDTHM == "")){
      lockedResult.setInt("OQDTHM", inDTHM.toInteger());
    }
    
    if(!(inHAFE == null || inHAFE == "")){
      lockedResult.set("OQHAFE", inHAFE);
    }
    
    if(!(inLODO == null || inLODO == "")){
      lockedResult.set("OQLODO", inLODO);
    }
    
    if(!(inETRN == null || inETRN == "")){
      lockedResult.set("OQETRN", inETRN);
    }
    
    if(!(inFWNS == null || inFWNS == "")){
      lockedResult.set("OQFWNS", inFWNS);
    }
    
    if(!(inFWNO == null || inFWNO == "")){
      lockedResult.set("OQFWNO", inFWNO);
    }
    
    if(!(inIRST == null || inIRST == "")){
      lockedResult.set("OQIRST", inIRST);
    }
    
    if(!(inTSID == null || inTSID == "")){
      lockedResult.set("OQTSID", inTSID);
    }
    
    if(!(inRASN == null || inRASN == "")){
      lockedResult.set("OQRASN", inRASN);
    }
    
    if(!(inDFDT == null || inDFDT == "")){
      lockedResult.setInt("OQDFDT", inDFDT.toInteger());
    }
    
    if(!(inDFHM == null || inDFHM == "")){
      lockedResult.setInt("OQDFHM", inDFHM.toInteger());
    }
    
    if(!(inPWDT == null || inPWDT == "")){
      lockedResult.setInt("OQPWDT", inPWDT.toInteger());
    }
    
    if(!(inPWHM == null || inPWHM == "")){
      lockedResult.setInt("OQPWHM", inPWHM.toInteger());
    }
    
    if(!(inAWDT == null || inAWDT == "")){
      lockedResult.setInt("OQAWDT", inAWDT.toInteger());
    }
    
    if(!(inAWHM == null || inAWHM == "")){
      lockedResult.setInt("OQAWHM", inAWHM.toInteger());
    }
    
    if(!(inRSCD == null || inRSCD == "")){
      lockedResult.set("OQRSCD", inRSCD);
    }
    
    lockedResult.set("OQLMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
		lockedResult.set("OQCHNO", lockedResult.getInt("OQCHNO")+1);
		lockedResult.set("OQCHID", program.getUser());
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

		if (inCONO == "") {
			inCONO = (Integer)program.getLDAZD().CONO;
		} else if (!inCONO.isInteger()) {
			mi.error("Company number " + inCONO + " is invalid");
			return false;
		}
		
		if(!(inDSDT == null || inDSDT == "")){
      if (!validateDate(inDSDT)) {
        mi.error("Invalid departure date.");
        return false;
      }
    }
    
    if(!(inDTDT == null || inDTDT == "")){
      if (!validateDate(inDTDT)) {
        mi.error("Invalid requested departure date.");
        return false;
      }
    }
    
    if(!(inDFDT == null || inDFDT == "")){
      if (!validateDate(inDFDT)) {
        mi.error("Invalid actual departure date.");
        return false;
      }
    }

		return true;
	}
}