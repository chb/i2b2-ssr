/*********************************************************
*         SQL SERVER SCRIPT TO CREATE DATA TABLES
*           SNM - 8/19/2008
**********************************************************/

-- int in SQL server handles values to 2,147,483,648


/* create Encounter_Mapping table with clustered PK on encounter_ide, encounter_ide_source */
CREATE TABLE Encounter_Mapping ( 
    ENCOUNTER_IDE       	VARCHAR(200)  NOT NULL,
    ENCOUNTER_IDE_SOURCE	VARCHAR(50)  NOT NULL,
    ENCOUNTER_NUM			INT NOT NULL,
    PATIENT_IDE         	VARCHAR(200) NULL,
    PATIENT_IDE_SOURCE  	VARCHAR(50) NULL,
    ENCOUNTER_IDE_STATUS	VARCHAR(50) NULL,
    UPLOAD_DATE         	DATETIME NULL,
    UPDATE_DATE             DATETIME NULL,
    DOWNLOAD_DATE       	DATETIME NULL,
    IMPORT_DATE             DATETIME NULL,
    SOURCESYSTEM_CD         VARCHAR(50) NULL,
    UPLOAD_ID               INT NULL,
    CONSTRAINT ENCOUNTER_MAPPING_PK PRIMARY KEY(ENCOUNTER_IDE,ENCOUNTER_IDE_SOURCE)
 )
;

CREATE  INDEX EM_IDX_ENCPATH ON ENCOUNTER_MAPPING(ENCOUNTER_IDE,ENCOUNTER_IDE_SOURCE,PATIENT_IDE,PATIENT_IDE_SOURCE,
ENCOUNTER_NUM)
;
CREATE  INDEX EM_IDX_UPLOADID ON ENCOUNTER_MAPPING(UPLOAD_ID)
;

CREATE INDEX EM_ENCNUM_IDX ON ENCOUNTER_MAPPING(ENCOUNTER_NUM)
;

/* create Patient_Mapping table with clustered PK on patient_ide, patient_ide_source */
CREATE TABLE Patient_Mapping ( 
    PATIENT_IDE         VARCHAR(200)  NOT NULL,
    PATIENT_IDE_SOURCE	VARCHAR(50)  NOT NULL,
    PATIENT_NUM       	INT NOT NULL,
    PATIENT_IDE_STATUS	VARCHAR(50) NULL,
    UPLOAD_DATE       	DATETIME NULL,
    UPDATE_DATE       	DATETIME NULL,
    DOWNLOAD_DATE     	DATETIME NULL,
    IMPORT_DATE         DATETIME NULL,
    SOURCESYSTEM_CD   	VARCHAR(50) NULL,
    UPLOAD_ID         	INT NULL,
    CONSTRAINT PATIENT_MAPPING_PK PRIMARY KEY(PATIENT_IDE,PATIENT_IDE_SOURCE)
 )
;
CREATE  INDEX PM_IDX_UPLOADID ON PATIENT_MAPPING(UPLOAD_ID)
;
CREATE INDEX PM_PATNUM_IDX ON PATIENT_MAPPING(PATIENT_NUM)
;
CREATE INDEX PM_ENCPNUM_IDX ON 
PATIENT_MAPPING(PATIENT_IDE,PATIENT_IDE_SOURCE,PATIENT_NUM) ;

/* create Code_Lookup table with clustered PK on table_cd, column_cd, code_cd */
CREATE TABLE CODE_LOOKUP ( 
    table_cd            varchar(100) NOT NULL,
    column_cd           varchar(100) NOT NULL,
    code_cd             varchar(50) NOT NULL,
    name_char           varchar(650) NULL,
    LOOKUP_BLOB         TEXT NULL, 
    UPLOAD_DATE       	DATETIME NULL,
    UPDATE_DATE         DATETIME NULL,
    DOWNLOAD_DATE     	DATETIME NULL,
    IMPORT_DATE         DATETIME NULL,
    SOURCESYSTEM_CD   	VARCHAR(50) NULL,
    UPLOAD_ID         	INT NULL,
	CONSTRAINT CODE_LOOKUP_PK PRIMARY KEY(table_cd,column_cd,code_cd)
	)
;
/* add index on name_char field */
CREATE INDEX CL_IDX_NAME_CHAR ON CODE_LOOKUP(name_char)
;
CREATE INDEX CL_IDX_UPLOADID ON CODE_LOOKUP(UPLOAD_ID)
;

/* create concept_dimension table with clustered PK on concept_path */
CREATE TABLE concept_dimension ( 
	concept_path   	varchar(700) NOT NULL,
	concept_cd     	varchar(50) NULL,
	name_char      	varchar(2000) NULL,
	concept_blob   	text NULL,
	update_date    	datetime NULL,
	download_date  	datetime NULL,
	import_date    	datetime NULL,
	sourcesystem_cd	varchar(50) NULL,
      UPLOAD_ID       INT NULL,
    CONSTRAINT CONCEPT_DIMENSION_PK PRIMARY KEY(concept_path)
	)
;
CREATE INDEX CD_IDX_UPLOADID ON CONCEPT_DIMENSION(UPLOAD_ID)
;

/* create observation_fact table with NONclustered PK on encounter_num,concept_cd,provider_id,start_date,modifier_cd  */
CREATE TABLE Observation_Fact ( 
	Encounter_Num  	int NOT NULL,
	Patient_Num    	int NOT NULL,
	Concept_Cd     	varchar(50) NOT NULL,
	Provider_Id    	varchar(50) NOT NULL,
	Start_Date     	datetime NOT NULL,
	Modifier_Cd    	varchar(100) NOT NULL,
	ValType_Cd     	varchar(50) NULL,
	TVal_Char      	varchar(255) NULL,
	NVal_Num       	decimal(18,5) NULL,
	INSTANCE_NUM	int NULL,
	ValueFlag_Cd   	varchar(50) NULL,
	Quantity_Num   	decimal(18,5) NULL,
	Units_Cd       	varchar(50) NULL,
	End_Date       	datetime NULL,
	Location_Cd    	varchar(50) NULL,
	Observation_Blob text NULL,
	Confidence_Num 	decimal(18,5) NULL,
	Update_Date    	datetime NULL,
	Download_Date  	datetime NULL,
	Import_Date    	datetime NULL,
	Sourcesystem_Cd	varchar(50) NULL, 
    UPLOAD_ID         	INT NULL,
    CONSTRAINT OBSERVATION_FACT_PK PRIMARY KEY nonclustered (encounter_num,concept_cd,provider_id,start_date,modifier_cd)
	)
;

/* add index on concept_cd */
CREATE CLUSTERED INDEX OF_IDX_ClusteredConcept ON Observation_Fact
(
	Concept_Cd 
)
;
/* add an index on most of the observation_fact fields */
CREATE INDEX OF_IDX_ALLObservation_Fact ON Observation_Fact
(
	Encounter_Num ,
	Patient_Num ,
	Concept_Cd ,
	Start_Date ,
	Provider_Id ,
	Modifier_Cd ,
	ValType_Cd ,
	TVal_Char ,
	NVal_Num ,
	ValueFlag_Cd ,
	Quantity_Num ,
	Units_Cd ,
	End_Date ,
	Location_Cd ,
	Confidence_Num ,
	Sourcesystem_Cd
)
;
/* add additional indexes on observation_fact fields */
CREATE INDEX OF_IDX_Start_Date ON OBSERVATION_FACT(Start_Date, Patient_Num)
;
CREATE INDEX OF_IDX_Modifier ON OBSERVATION_FACT(Modifier_Cd)
;
CREATE INDEX OF_IDX_Encounter_Patient ON OBSERVATION_FACT(Encounter_Num, Patient_Num)
;
CREATE INDEX OF_IDX_UPLOADID ON OBSERVATION_FACT(UPLOAD_ID)
;
/* create Patient_Dimension table with clustered PK on patient_num */
CREATE TABLE Patient_Dimension ( 
	Patient_Num      	int NOT NULL,
	Vital_Status_Cd  	varchar(50) NULL,
	Birth_Date       	datetime NULL,
	Death_Date       	datetime NULL,
	Sex_Cd           	varchar(50) NULL,
	Age_In_Years_Num 	int NULL,
	Language_Cd      	varchar(50) NULL,
	Race_Cd          	varchar(50) NULL,
	Marital_Status_Cd	varchar(50) NULL,
	Religion_Cd      	varchar(50) NULL,
	Zip_Cd           	varchar(10) NULL,
	StateCityZip_Path	varchar(700) NULL,
	Patient_Blob     	text NULL,
	Update_Date      	datetime NULL,
	Download_Date    	datetime NULL,
	Import_Date      	datetime NULL,
	Sourcesystem_Cd  	varchar(50) NULL,
    UPLOAD_ID         	INT NULL, 
    CONSTRAINT PATIENT_DIMENSION_PK PRIMARY KEY(patient_num)
	)
;
/* add indexes on additional Patient_Dimension fields */
CREATE  INDEX PD_IDX_DATES ON Patient_Dimension(Patient_Num, Vital_Status_Cd, Birth_Date, Death_Date)
;
CREATE  INDEX PD_IDX_AllPatientDim ON Patient_Dimension(Patient_Num, Vital_Status_Cd, Birth_Date, Death_Date, Sex_Cd, Age_In_Years_Num, Language_Cd, Race_Cd, Marital_Status_Cd, Religion_Cd, Zip_Cd)
;
CREATE  INDEX PD_IDX_StateCityZip ON Patient_Dimension (StateCityZip_Path, Patient_Num)
;
CREATE INDEX PA_IDX_UPLOADID ON PATIENT_DIMENSION(UPLOAD_ID)
;
/* create Provider_Dimension table with clustered PK on provider_path, provider_id */
CREATE TABLE Provider_Dimension ( 
	Provider_Id    	varchar(50) NOT NULL,
	Provider_Path  	varchar(700) NOT NULL,
	Name_Char      	varchar(850) NULL,
	Provider_Blob  	text NULL,
	Update_Date    	datetime NULL,
	Download_Date  	datetime NULL,
	Import_Date    	datetime NULL,
	Sourcesystem_Cd	varchar(50) NULL ,
    UPLOAD_ID         	INT NULL,
    CONSTRAINT PROVIDER_DIMENSION_PK PRIMARY KEY(provider_path, Provider_Id)
	)
;
/* add index on provider_id, name_char */
CREATE INDEX PD_IDX_NAME_CHAR ON Provider_Dimension(Provider_Id,name_char)
;
CREATE INDEX PD_IDX_UPLOADID ON Provider_Dimension(UPLOAD_ID)
;

/* create Visit_Dimension table with clustered PK on encounter_num */
CREATE TABLE Visit_Dimension ( 
	Encounter_Num  	int NOT NULL,
	Patient_Num    	int NOT NULL,
	Active_status_cd varchar(50) NULL,
	Start_Date     	datetime NULL,
	End_Date       	datetime NULL,
	InOut_Cd       	varchar(50) NULL,
	Location_Cd    	varchar(50) NULL,
    LOCATION_PATH  	VARCHAR(900) NULL,
	Visit_Blob     	text NULL,
	Update_Date    	datetime NULL,
	Download_Date  	datetime NULL,
	Import_Date    	datetime NULL,
	Sourcesystem_cd	varchar(50) NULL ,
    UPLOAD_ID         	INT NULL, 
    CONSTRAINT VISIT_DIMENSION_PK PRIMARY KEY(encounter_num)
	)
;
/* add indexes on addtional visit_dimension fields */
CREATE  INDEX VD_IDX_DATES ON dbo.Visit_Dimension(Encounter_Num, Start_Date, End_Date)
;
CREATE  INDEX VD_IDX_AllVisitDim ON dbo.Visit_Dimension(Encounter_Num, Patient_Num, InOut_Cd, Location_Cd, Start_Date, End_Date)
;
CREATE  INDEX VD_IDX_UPLOADID ON Visit_Dimension(UPLOAD_ID)
;