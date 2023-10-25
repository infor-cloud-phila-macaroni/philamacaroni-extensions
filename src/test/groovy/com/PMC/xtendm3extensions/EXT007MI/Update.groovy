/**
 * README
 * Name: EXT989MI.Lst
 * Extend M3 Table EXTD89 Lst
 * Description: Lst EXTD89 Data
 * Date	    Changed By      	               Description
 * 20230929 Mohamed Adel - Hatem Abdellatif  Update EXTD89 Data
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class Lst extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  private final LoggerAPI logger
	private final UtilityAPI utility;
	private final MICallerAPI miCaller;
  
  /**
   * input Fields of EXTD89 Table
  */
  private int inCONO = 0;
  private String inDIVI;
  private String inWHLO;
  private String inFACI;
  private String inTRNR;
  private String inTYPE;
  private int inDONR = 0;
  
  
  public Lst(MIAPI mi, DatabaseAPI database, ProgramAPI program, UtilityAPI utility, MICallerAPI miCaller, LoggerAPI logger) {
	this.mi = mi;
	this.database = database;
	this.program = program;
	this.utility = utility;
	this.miCaller = miCaller;
	this.logger = logger;
	
  }

  public void main() {
	if (mi.inData.get("CONO") == null || mi.inData.get("CONO").trim() =="") {
		inCONO = Integer.parseInt(program.LDAZD.CONO.toString())
	} else {
		inCONO = mi.inData.get("CONO") as int
	}
	if (mi.inData.get("DONR") == null || mi.inData.get("DONR").trim() =="") {
		inDONR = 0;
	} else {
		inDONR = mi.inData.get("DONR") as int
	}
	 inTRNR = (mi.in.get("TRNR").toString().trim()==null) ? "" : mi.in.get("TRNR").toString().trim();
	 inTYPE = (mi.in.get("TYPE").toString().trim()==null) ? "" : mi.in.get("TYPE").toString().trim();
	 
	 DBAction dbaEXTD89 = database.table("EXTD89").index("00").selection("EXCONO", "EXTRNR","EXDONR", "EXTYPE","EXDIVI","EXWHLO","EXFACI","EXA030","EXA130","EXA230","EXRGTM","EXA330","EXA430","EXA530","EXA630","EXA730","EXA830","EXA930","EXB030","EXB130","EXB230","EXB330","EXB430","EXB530","EXN030","EXN130","EXN230","EXN330","EXN430","EXN530","EXD030","EXD130","EXD230","EXD330","EXD430","EXCHNO","EXCHID","EXRGDT","EXLMDT","EXRHLM","EXLMTS").build();
	 DBContainer conEXTD89 = dbaEXTD89.getContainer();
	 conEXTD89.set("EXCONO", inCONO);
	 conEXTD89.set("EXTRNR", inTRNR);
	 conEXTD89.set("EXTYPE", inTYPE);
	 conEXTD89.set("EXDONR", inDONR);
	 Closure<?> readEXTD89Data = { DBContainer DataEXTD89 ->
		mi.outData.put("CONO", DataEXTD89.get("EXCONO").toString());
		mi.outData.put("DIVI", DataEXTD89.get("EXDIVI").toString());
		mi.outData.put("TRNR", DataEXTD89.get("EXTRNR").toString());
		mi.outData.put("DONR", DataEXTD89.get("EXDONR").toString());
		mi.outData.put("TYPE", DataEXTD89.get("EXTYPE").toString());
		mi.outData.put("WHLO", DataEXTD89.get("EXWHLO").toString());
		mi.outData.put("FACI", DataEXTD89.get("EXFACI").toString());
		mi.outData.put("A030", DataEXTD89.get("EXA030").toString());
		mi.outData.put("A130", DataEXTD89.get("EXA130").toString());
		mi.outData.put("A230", DataEXTD89.get("EXA230").toString());
		mi.outData.put("A330", DataEXTD89.get("EXA330").toString());
		mi.outData.put("A430", DataEXTD89.get("EXA430").toString());
		mi.outData.put("A530", DataEXTD89.get("EXA530").toString());
		mi.outData.put("A630", DataEXTD89.get("EXA630").toString());
		mi.outData.put("A730", DataEXTD89.get("EXA730").toString());
		mi.outData.put("A830", DataEXTD89.get("EXA830").toString());
		mi.outData.put("A930", DataEXTD89.get("EXA930").toString());
		mi.outData.put("B030", DataEXTD89.get("EXB030").toString());
		mi.outData.put("B130", DataEXTD89.get("EXB130").toString());
		mi.outData.put("B230", DataEXTD89.get("EXB230").toString());
		mi.outData.put("B330", DataEXTD89.get("EXB330").toString());
		mi.outData.put("B430", DataEXTD89.get("EXB430").toString());
		mi.outData.put("B530", DataEXTD89.get("EXB530").toString());
		mi.outData.put("N030", DataEXTD89.get("EXN030").toString());
		mi.outData.put("N130", DataEXTD89.get("EXN130").toString());
		mi.outData.put("N230", DataEXTD89.get("EXN230").toString());
		mi.outData.put("N330", DataEXTD89.get("EXN330").toString());
		mi.outData.put("N430", DataEXTD89.get("EXN430").toString());
		mi.outData.put("N530", DataEXTD89.get("EXN530").toString());
		mi.outData.put("D030", DataEXTD89.get("EXD030").toString());
		mi.outData.put("D130", DataEXTD89.get("EXD130").toString());
		mi.outData.put("D230", DataEXTD89.get("EXD230").toString());
		mi.outData.put("D330", DataEXTD89.get("EXD330").toString());
		mi.outData.put("D430", DataEXTD89.get("EXD430").toString());
		mi.outData.put("CHID", DataEXTD89.get("EXCHID").toString());
		mi.outData.put("LMDT", DataEXTD89.get("EXLMDT").toString());
		mi.outData.put("CHNO", DataEXTD89.get("EXCHNO").toString());
		mi.outData.put("RGDT", DataEXTD89.get("EXRGDT").toString());
		mi.outData.put("LMTS", DataEXTD89.get("EXLMTS").toString());
		mi.outData.put("RHLM", DataEXTD89.get("EXRHLM").toString());
		mi.outData.put("RGTM", DataEXTD89.get("EXRGTM").toString());
		mi.outData.put("TIME", DataEXTD89.get("EXTIME").toString());
		mi.outData.put("TWLO", DataEXTD89.get("EXTWLO").toString());
		mi.write();
	 }
	  if (!dbaEXTD89.readAll(conEXTD89, 1, readEXTD89Data)) {
	  mi.error("Records does not exist in EXTD89");
	}
  }
}