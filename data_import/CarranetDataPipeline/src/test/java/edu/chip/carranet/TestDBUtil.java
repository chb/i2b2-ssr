package edu.chip.carranet;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is a utility class that creates fake schemas and such
 */
public class TestDBUtil {


    public static String CREATE_ENCOUTER_MAPPING_TABLE = "CREATE TABLE ENCOUNTER_MAPPING ( \n" +
            "    ENCOUNTER_IDE       VARCHAR2(200) NOT NULL,\n" +
            "    ENCOUNTER_IDE_SOURCE VARCHAR2(50) NOT NULL,\n" +
            "    ENCOUNTER_NUM        NUMBER(38,0) NOT NULL,\n" +
            "    PATIENT_IDE          VARCHAR2(200) ,\n" +
            "    PATIENT_IDE_SOURCE   VARCHAR2(50) ,\n" +
            "    ENCOUNTER_IDE_STATUS VARCHAR2(50) ,\n" +
            "    UPDATE_DATE             DATE ,\n" +
            "    UPLOAD_DATE           DATE ,\n" +
            "    DOWNLOAD_DATE         DATE ,\n" +
            "    IMPORT_DATE    DATE ,\n" +
            "    SOURCESYSTEM_CD       VARCHAR2(50) ,\n" +
            "    UPLOAD_ID             NUMBER(38,0) ,\n" +
            "    CONSTRAINT ENCOUNTER_MAPPING_PK PRIMARY KEY(ENCOUNTER_IDE,ENCOUNTER_IDE_SOURCE)\n" +
            " )";


    public static String CREATE_PATIENT_MAPPING_TABLE = "CREATE TABLE PATIENT_MAPPING ( \n" +
            "    PATIENT_IDE       \tVARCHAR2(200) NOT NULL,\n" +
            "    PATIENT_IDE_SOURCE\tVARCHAR2(50) NOT NULL,\n" +
            "    PATIENT_NUM       \tNUMBER(38,0) NOT NULL,\n" +
            "    PATIENT_IDE_STATUS\tVARCHAR2(50) ,\n" +
            "    UPLOAD_DATE       \tDATE ,\n" +
            "    UPDATE_DATE             DATE ,\n" +
            "    DOWNLOAD_DATE     \tDATE ,\n" +
            "    IMPORT_DATE\t\t\tDATE,\n" +
            "    SOURCESYSTEM_CD   \tVARCHAR2(50) ,\n" +
            "    UPLOAD_ID         \tNUMBER(38,0) ,\n" +
            "    CONSTRAINT PATIENT_MAPPING_PK PRIMARY KEY(PATIENT_IDE,PATIENT_IDE_SOURCE)\n" +
            " )";


    public static String CREATE_CODE_LOOKUP_TABLE = "CREATE TABLE CODE_LOOKUP ( \n" +
            "TABLE_CD            VARCHAR2(100) NOT NULL,\n" +
            "COLUMN_CD           VARCHAR2(100) NOT NULL,\n" +
            "CODE_CD             VARCHAR2(50) NOT NULL,\n" +
            "NAME_CHAR           VARCHAR2(650) NULL ,\n" +
            "LOOKUP_BLOB         CLOB ,\n" +
            "    UPLOAD_DATE     DATE NULL,\n" +
            "    UPDATE_DATE     DATE NULL,\n" +
            "OWNLOAD_DATE        DATE NULL,\n" +
            "IMPORT_DATE         DATE NULL,\n" +
            "SOURCESYSTEM_CD     VARCHAR2(50) NULL,\n" +
            "UPLOAD_ID           NUMBER(38,0) NULL ,\n" +
            "    CONSTRAINT CODE_LOOKUP_PK PRIMARY KEY(TABLE_CD,COLUMN_CD,CODE_CD)\n" +
            ")";


    public static String CREATE_CONCEPT_DIMENSION_TABLE = "CREATE TABLE CONCEPT_DIMENSION ( \n" +
            "CONCEPT_CD          VARCHAR2(50) NOT NULL,\n" +
            "CONCEPT_PATH        VARCHAR2(700) NOT NULL,\n" +
            "NAME_CHAR           VARCHAR2(2000) NULL,\n" +
            "CONCEPT_BLOB        CLOB NULL,\n" +
            "UPDATE_DATE         DATE NULL,\n" +
            "DOWNLOAD_DATE       DATE NULL,\n" +
            "IMPORT_DATE         DATE NULL,\n" +
            "SOURCESYSTEM_CD     VARCHAR2(50) NULL,\n" +
            "UPLOAD_ID           NUMBER(38,0) NULL,\n" +
            "    CONSTRAINT CONCEPT_DIMENSION_PK PRIMARY KEY(CONCEPT_PATH)\n" +
            ")";


    public static String CREATE_OBS_FACT_TABLE = "CREATE TABLE OBSERVATION_FACT (\n" +
            "\tENCOUNTER_NUM   \tNUMBER(38,0) NOT NULL,\n" +
            "\tPATIENT_NUM     \tNUMBER(38,0) NOT NULL,\n" +
            "\tCONCEPT_CD      \tVARCHAR2(50) NOT NULL,\n" +
            "\tPROVIDER_ID     \tVARCHAR2(50) NOT NULL,\n" +
            "\tSTART_DATE      \tDATE NOT NULL,\n" +
            "\tMODIFIER_CD     \tVARCHAR2(100) NOT NULL,\n" +
            "\tVALTYPE_CD      \tVARCHAR2(50) NULL,\n" +
            "\tTVAL_CHAR       \tVARCHAR2(255) NULL,\n" +
            "\tNVAL_NUM        \tNUMBER(18,5) NULL,\n" +
            "\tVALUEFLAG_CD    \tVARCHAR2(50) NULL,\n" +
            "\tQUANTITY_NUM    \tNUMBER(18,5) NULL,\n" +
            "\tINSTANCE_NUM\t    NUMBER(18,0) NULL,\n" +
            "\tUNITS_CD        \tVARCHAR2(50) NULL,\n" +
            "\tEND_DATE        \tDATE NULL,\n" +
            "\tLOCATION_CD     \tVARCHAR2(50) NULL,\n" +
            "\tCONFIDENCE_NUM  \tNUMBER(18,5) NULL,\n" +
            "\tOBSERVATION_BLOB\tCLOB NULL,\n" +
            "\tUPDATE_DATE     \tDATE NULL,\n" +
            "\tDOWNLOAD_DATE   \tDATE NULL,\n" +
            "\tIMPORT_DATE     \tDATE NULL,\n" +
            "\tSOURCESYSTEM_CD \tVARCHAR2(50) NULL,\n" +
            "\tUPLOAD_ID       \tNUMBER(38,0) NULL, \n" +
            "    CONSTRAINT OBSERVATION_FACT_PK PRIMARY KEY(ENCOUNTER_NUM,CONCEPT_CD,PROVIDER_ID,START_DATE,MODIFIER_CD)\n" +
            ")";

    public static String CREATE_PATIENT_DIMENSION_TABLE = "CREATE TABLE PATIENT_DIMENSION ( \n" +
            "\tPATIENT_NUM      \tNUMBER(38,0) NOT NULL,\n" +
            "\tVITAL_STATUS_CD  \tVARCHAR2(50) NULL,\n" +
            "\tBIRTH_DATE       \tDATE NULL,\n" +
            "\tDEATH_DATE       \tDATE NULL,\n" +
            "\tSEX_CD           \tVARCHAR2(50) NULL,\n" +
            "\tAGE_IN_YEARS_NUM \tNUMBER(38,0) NULL,\n" +
            "\tLANGUAGE_CD      \tVARCHAR2(50) NULL,\n" +
            "\tRACE_CD          \tVARCHAR2(50) NULL,\n" +
            "\tMARITAL_STATUS_CD\tVARCHAR2(50) NULL,\n" +
            "\tRELIGION_CD      \tVARCHAR2(50) NULL,\n" +
            "\tZIP_CD           \tVARCHAR2(10) NULL,\n" +
            "\tSTATECITYZIP_PATH\tVARCHAR2(700) NULL,\n" +
            "\tPATIENT_BLOB     \tCLOB NULL,\n" +
            "\tUPDATE_DATE      \tDATE NULL,\n" +
            "\tDOWNLOAD_DATE    \tDATE NULL,\n" +
            "\tIMPORT_DATE      \tDATE NULL,\n" +
            "\tSOURCESYSTEM_CD  \tVARCHAR2(50) NULL,\n" +
            "\tUPLOAD_ID        \tNUMBER(38,0) NULL ,\n" +
            "    CONSTRAINT PATIENT_DIMENSION_PK PRIMARY KEY(PATIENT_NUM)\n" +
            "\t)";

    public static String CREATE_PROVIDER_DIMENSION_TABLE = "CREATE TABLE PROVIDER_DIMENSION ( \n" +
            "\tPROVIDER_ID         VARCHAR2(50) NOT NULL,\n" +
            "\tPROVIDER_PATH       VARCHAR2(700) NOT NULL,\n" +
            "\tNAME_CHAR       \tVARCHAR2(850) NULL,\n" +
            "\tPROVIDER_BLOB       CLOB NULL,\n" +
            "\tUPDATE_DATE     \tDATE NULL,\n" +
            "\tDOWNLOAD_DATE       DATE NULL,\n" +
            "\tIMPORT_DATE         DATE NULL,\n" +
            "\tSOURCESYSTEM_CD     VARCHAR2(50) NULL,\n" +
            "\tUPLOAD_ID        \tNUMBER(38,0) NULL ,\n" +
            "    CONSTRAINT  PROVIDER_DIMENSION_PK PRIMARY KEY(PROVIDER_PATH,provider_id)\n" +
            "\t)";

    public static String CREATE_VISIT_DIMENSION_TABLE = "CREATE TABLE VISIT_DIMENSION ( \n" +
            "\tENCOUNTER_NUM       NUMBER(38,0) NOT NULL,\n" +
            "\tPATIENT_NUM         NUMBER(38,0) NOT NULL,\n" +
            "    ACTIVE_STATUS_CD    VARCHAR2(50) NULL,\n" +
            "\tSTART_DATE          DATE NULL,\n" +
            "\tEND_DATE            DATE NULL,\n" +
            "\tINOUT_CD            VARCHAR2(50) NULL,\n" +
            "\tLOCATION_CD         VARCHAR2(50) NULL,\n" +
            "\tLOCATION_PATH  \t    VARCHAR2(900) NULL,\n" +
            "\tVISIT_BLOB      \tCLOB NULL,\n" +
            "\tUPDATE_DATE         DATE NULL,\n" +
            "\tDOWNLOAD_DATE       DATE NULL,\n" +
            "\tIMPORT_DATE         DATE NULL,\n" +
            "\tSOURCESYSTEM_CD     VARCHAR2(50) NULL,\n" +
            "\tUPLOAD_ID       \tNUMBER(38,0) NULL , \n" +
            "    CONSTRAINT  VISIT_DIMENSION_PK PRIMARY KEY(ENCOUNTER_NUM,PATIENT_NUM)\n" +
            "\t)";


    private static String[] createI2b2Statements = {
            CREATE_ENCOUTER_MAPPING_TABLE,
            CREATE_PATIENT_MAPPING_TABLE,
            CREATE_CODE_LOOKUP_TABLE,
            CREATE_CONCEPT_DIMENSION_TABLE,
            CREATE_OBS_FACT_TABLE,
            CREATE_PATIENT_DIMENSION_TABLE,
            CREATE_PROVIDER_DIMENSION_TABLE,
            CREATE_VISIT_DIMENSION_TABLE
    };


    public static void createI2b2TableStructure(Connection c) throws SQLException {
        for (String i : createI2b2Statements) {
            c.createStatement().executeUpdate(i);
        }
        c.commit();
    }


}
