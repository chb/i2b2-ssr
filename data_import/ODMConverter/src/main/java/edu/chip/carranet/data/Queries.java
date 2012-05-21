package edu.chip.carranet.data;


public class Queries {
    public static final String createImportTableQuery = "CREATE TABLE TBLRSLTDATAIMPORT (\n" +
            "        STUDY NVARCHAR2(100),\n" +
            "\tSUBJECTKEY NVARCHAR2(38) NULL,\n" +
            "\tSITELOCATION NVARCHAR2(50) NULL,\n" +
            "\tSTUDYEVENT NVARCHAR2(50) NULL,\n" +
            "\tSTUDYEVENTREPEATKEY NVARCHAR2(100) NULL,\n" +
            "\tFORM NVARCHAR2(100) NULL,\n" +
            "\tITEMGROUP NVARCHAR2(100) NULL,\n" +
            "\tITEMGROUPREPEATKEY NVARCHAR2(100) NULL,\n" +
            "\tITEM NVARCHAR2(100) NULL,\n" +
            "\tVAL NVARCHAR2(100) NULL,\n" +
            "\tHASVAL CHAR(1),\n" +
            "\tITEMLOCATION NVARCHAR2(100) NULL,\n" +
            "\tDATETIMESTAMP NVARCHAR2(100) NULL,\n" +
            "\tREASONFORCHANGE NVARCHAR2(1000) NULL,\n" +
            "\tODMCODE NVARCHAR2(100) NULL,\n" +
            "\tANSWER NVARCHAR2(100) NULL,\n" +
            "\tTYPE NVARCHAR2(255) NULL,\n" +
            "\tCONCEPTCODE NVARCHAR2(500) NULL,\n" +
            "\tMODIFIER NVARCHAR2(100) NULL,\n" +
            "\tLOCATION_PATH NVARCHAR2(50) NOT NULL,\n" +
            "\tPATIENT_NUM NVARCHAR2(100) NULL,\n" +
            "\tCONCEPT_CD NVARCHAR2(500) NULL,\n" +
            "\tPROVIDER_ID NVARCHAR2(50) NOT NULL,\n" +
            "\tSTART_DATE NVARCHAR2(50) NULL,\n" +
            "\tMODIFIER_CD NVARCHAR2(100) NOT NULL,\n" +
            "\tVALTYPE_CD NVARCHAR2(50) NULL,\n" +
            "\tTVAL_CHAR VARCHAR2(255) NULL,\n" +
            "\tNVAL_NUM NUMBER(18,5) NULL,\n" +
            "\tVALUEFLAG_CD NVARCHAR2(50) NULL,\n" +
            "\tSOURCESYSTEM_CD NVARCHAR2(50) NULL,\n" +
            "\tERRORDESCRIPTION NVARCHAR2(2000) NULL\n" +
            "    )";

    public static final String importTableExistsQuery = "SELECT COUNT(*) as RESULT FROM ALL_TABLES WHERE TABLE_NAME = 'TBLRSLTDATAIMPORT'";

    public static final String createImportTmpEncTable =
    "CREATE TABLE tmp_EncTable (\n"+
            "\tENCOUNTER_NUM NUMBER(38,0),\n"+
            "\tPATIENT_NUM NVARCHAR2(100) NULL,\n"+
            "\tLOCATION_PATH NVARCHAR2(50) NOT NULL,\n"+
            "\tSTART_DATE DATE,\n"+
            "\tEND_DATE DATE,\n"+
            "\tSOURCESYSTEM_CD NVARCHAR2(50) NULL,\n"+
            "\tIsNewEncounter NUMBER(3,0) default 1\n"+
            ")";
    public static final String importTmpEncTableExistsQuery = "SELECT COUNT(*) as RESULT FROM ALL_TABLES WHERE TABLE_NAME = 'tmp_EncTable'";

}
