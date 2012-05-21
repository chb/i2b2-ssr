/*==============================================================*/
/* Sqlserver Database Script to create CRC query tables         */
/*                                                              */
/*==============================================================*/

/*============================================================================*/
/* Table: QT_QUERY_MASTER 											          */
/*============================================================================*/
CREATE TABLE QT_QUERY_MASTER (
       QUERY_MASTER_ID int  identity(1,1) PRIMARY KEY
     , NAME VARCHAR(50) NOT NULL
     , USER_ID VARCHAR(50) NOT NULL
     , GROUP_ID VARCHAR(50) NOT NULL
     , MASTER_TYPE_CD VARCHAR(2000)
     , PLUGIN_ID INT
     , CREATE_DATE DATEtime NOT NULL
     , DELETE_DATE DATEtime 
	 , delete_flag varchar(3)
     , REQUEST_XML text
    , GENERATED_SQL text
    , I2B2_REQUEST_XML text
)
;
CREATE INDEX QT_IDX_QM_UGID ON QT_QUERY_MASTER(USER_ID,GROUP_ID,MASTER_TYPE_CD);


/*============================================================================*/
/* Table: QT_QUERY_RESULT_TYPE										          */
/*============================================================================*/
CREATE TABLE QT_QUERY_RESULT_TYPE (
       RESULT_TYPE_ID int   PRIMARY KEY
     , NAME VARCHAR(100)
     , DESCRIPTION VARCHAR(200)
     , display_type_id NVARCHAR(500)
     , visual_attribute_type_id NVARCHAR(3)
)
;

/*============================================================================*/
/* Table: QT_QUERY_STATUS_TYPE										          */
/*============================================================================*/
CREATE TABLE QT_QUERY_STATUS_TYPE (
       STATUS_TYPE_ID int   PRIMARY KEY
     , NAME VARCHAR(100)
     , DESCRIPTION VARCHAR(200)
)
;
/*============================================================================*/
/* Table: QT_QUERY_INSTANCE 										          */
/*============================================================================*/
CREATE TABLE QT_QUERY_INSTANCE (
       QUERY_INSTANCE_ID int  identity(1,1) PRIMARY KEY
     , QUERY_MASTER_ID int 
     , USER_ID VARCHAR(50) NOT NULL
     , GROUP_ID VARCHAR(50) NOT NULL
     , BATCH_MODE VARCHAR(50)
     , START_DATE DATEtime NOT NULL
     , END_DATE DATEtime
     , delete_flag varchar(3)
     , STATUS_TYPE_ID int 
     , message text
     , CONSTRAINT QT_FK_QI_MID FOREIGN KEY (QUERY_MASTER_ID)
                  REFERENCES QT_QUERY_MASTER (QUERY_MASTER_ID)
     , CONSTRAINT QT_FK_QI_STID FOREIGN KEY (STATUS_TYPE_ID)
                  REFERENCES QT_QUERY_STATUS_TYPE (STATUS_TYPE_ID)
  
     
)
;
CREATE INDEX QT_IDX_QI_UGID ON QT_QUERY_INSTANCE(USER_ID,GROUP_ID)
;

CREATE INDEX QT_IDX_QI_MSTARTID ON QT_QUERY_INSTANCE(QUERY_MASTER_ID,START_DATE)
;




/*=============================================================================*/
/* Table: QT_QUERY_RESULT_INSTANCE   								          */
/*============================================================================*/
CREATE TABLE QT_QUERY_RESULT_INSTANCE (
       RESULT_INSTANCE_ID int  identity(1,1) PRIMARY KEY
     , QUERY_INSTANCE_ID int 
     , RESULT_TYPE_ID int NOT NULL
     , SET_SIZE int
     , START_DATE DATEtime NOT NULL
     , END_DATE   DATEtime
     , STATUS_TYPE_ID int NOT NULL
     , delete_flag varchar(3)
     , message text
     , description VARCHAR(200)
     , REAL_SET_SIZE  int
     , OBFUSC_METHOD VARCHAR(500)
     , CONSTRAINT QT_FK_QRI_RID FOREIGN KEY (QUERY_INSTANCE_ID)
                  REFERENCES QT_QUERY_INSTANCE (QUERY_INSTANCE_ID)
     , CONSTRAINT QT_FK_QRI_RTID FOREIGN KEY (RESULT_TYPE_ID)
                  REFERENCES QT_QUERY_RESULT_TYPE (RESULT_TYPE_ID)
     , CONSTRAINT QT_FK_QRI_STID FOREIGN KEY (STATUS_TYPE_ID)
                  REFERENCES QT_QUERY_STATUS_TYPE (STATUS_TYPE_ID)
                  
)
;


/*============================================================================*/
/* Table: QT_PATIENT_SET_COLLECTION									          */
/*============================================================================*/
CREATE TABLE QT_PATIENT_SET_COLLECTION ( 
	PATIENT_SET_COLL_ID bigint  identity(1,1) PRIMARY KEY
	,RESULT_INSTANCE_ID int
	,SET_INDEX int
	,PATIENT_NUM int
	,CONSTRAINT QT_FK_PSC_RI FOREIGN KEY (RESULT_INSTANCE_ID )
                  REFERENCES QT_QUERY_RESULT_INSTANCE (RESULT_INSTANCE_ID)
)
;

CREATE INDEX QT_IDX_QPSC_RIID ON QT_PATIENT_SET_COLLECTION(RESULT_INSTANCE_ID)
;

/*============================================================================*/
/* Table: QT_PATIENT_ENC_COLLECTION									          */
/*============================================================================*/
CREATE TABLE QT_PATIENT_ENC_COLLECTION (
	 PATIENT_ENC_COLL_ID int  identity(1,1) PRIMARY KEY
	, RESULT_INSTANCE_ID int
	, SET_INDEX int
	, PATIENT_NUM int
	, ENCOUNTER_NUM int
	, CONSTRAINT QT_FK_PESC_RI FOREIGN KEY (RESULT_INSTANCE_ID)
                  REFERENCES QT_QUERY_RESULT_INSTANCE(RESULT_INSTANCE_ID)
)
;

/*============================================================================*/
/* Table: QT_XML_RESULT												          */
/*============================================================================*/
CREATE TABLE QT_XML_RESULT (
       XML_RESULT_ID int  identity(1,1) PRIMARY KEY
     , RESULT_INSTANCE_ID int
     , XML_VALUE text
     , CONSTRAINT QT_FK_XMLR_RIID FOREIGN KEY (RESULT_INSTANCE_ID)
                  REFERENCES QT_QUERY_RESULT_INSTANCE (RESULT_INSTANCE_ID)
)
;

/*============================================================================*/
/* Table: QT_ANALYSIS_PLUGIN										          */
/*============================================================================*/
CREATE TABLE QT_ANALYSIS_PLUGIN
(
    PLUGIN_ID                 INT NOT NULL,
    PLUGIN_NAME             VARCHAR(2000),
    DESCRIPTION              VARCHAR(2000),
    VERSION_CD                      VARCHAR(50),           --support for version
    PARAMETER_INFO     TEXT,                           -- plugin parameter stored as xml
    PARAMETER_INFO_XSD  VARCHAR(2000),
    COMMAND_LINE VARCHAR(2000),
    WORKING_FOLDER VARCHAR(2000),
    COMMANDOPTION_CD VARCHAR(2000),
   PLUGIN_ICON         VARCHAR(2000),
    STATUS_CD                   VARCHAR(50),           -- active,deleted,..
   USER_ID     VARCHAR(50),
   GROUP_ID VARCHAR(50),
   CREATE_DATE DATETIME,
   UPDATE_DATE DATETIME,
CONSTRAINT ANALYSIS_PLUGIN_PK PRIMARY KEY(PLUGIN_ID)
);

CREATE INDEX QT_APNAMEVERGRP_IDX ON QT_ANALYSIS_PLUGIN(PLUGIN_NAME,VERSION_CD,GROUP_ID);

/*============================================================================*/
/* Table: QT_ANALYSIS_PLUGIN_RESULT_TYPE							          */
/*============================================================================*/
CREATE TABLE QT_ANALYSIS_PLUGIN_RESULT_TYPE
(
    PLUGIN_ID                    INT,
    RESULT_TYPE_ID         INT,
  CONSTRAINT ANALYSIS_PLUGIN_RESULT_PK PRIMARY KEY(PLUGIN_ID,RESULT_TYPE_ID)
);

create table QT_PRIVILEGE(
	protection_label_cd varchar(1500)
	, dataprot_cd varchar(1000)
	, hivemgmt_cd varchar(1000)
	, plugin_id int) ;


create table QT_BREAKDOWN_PATH (
name varchar(100), 
value varchar(2000), 
create_date datetime,
update_date datetime,
user_id varchar(50));
    
/*============================================================================*/
/* Table:QT_PDO_QUERY_MASTER 										          */
/*============================================================================*/
CREATE TABLE QT_PDO_QUERY_MASTER (
       QUERY_MASTER_ID int  identity(1,1) PRIMARY KEY
     , USER_ID VARCHAR(50) NOT NULL
     , GROUP_ID VARCHAR(50) NOT NULL
     , CREATE_DATE DATEtime NOT NULL
     , REQUEST_XML text
     , I2B2_REQUEST_XML text
)
;
CREATE INDEX QT_IDX_PQM_UGID ON QT_PDO_QUERY_MASTER(USER_ID,GROUP_ID);



--------------------------------------------------------
--INIT WITH SEED DATA
--------------------------------------------------------
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(1,'QUEUED',' WAITING IN QUEUE TO START PROCESS');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(2,'PROCESSING','PROCESSING');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(3,'FINISHED','FINISHED');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(4,'ERROR','ERROR');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(5,'INCOMPLETE','INCOMPLETE');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(6,'COMPLETED','COMPLETED');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(7,'MEDIUM_QUEUE','MEDIUM QUEUE');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(8,'LARGE_QUEUE','LARGE QUEUE');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(9,'CANCELLED','CANCELLED');
insert into QT_QUERY_STATUS_TYPE(STATUS_TYPE_ID,NAME,DESCRIPTION) values(10,'TIMEDOUT','TIMEDOUT');


insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(1,'PATIENTSET','Patient list','LIST','LA');
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(2,'PATIENT_ENCOUNTER_SET','Event list','LIST','LH');
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(3,'XML','Generic query result','CATNUM','LH');
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(4,'PATIENT_COUNT_XML','Number of patients','CATNUM','LA');
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(5,'PATIENT_GENDER_COUNT_XML','Gender patient breakdown','CATNUM','LA');
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(6,'PATIENT_VITALSTATUS_COUNT_XML','Vital Status patient breakdown','CATNUM','LA');
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(7,'PATIENT_RACE_COUNT_XML','Race patient breakdown','CATNUM','LA');
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(8,'PATIENT_AGE_COUNT_XML','Age patient breakdown','CATNUM','LA');
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(9,'PATIENTSET','TimeLine','LIST','LA');


insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('PDO_WITHOUT_BLOB','DATA_LDS','USER');
insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('PDO_WITH_BLOB','DATA_DEID','USER');
insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('SETFINDER_QRY_WITH_DATAOBFSC','DATA_OBFSC','USER');
insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('SETFINDER_QRY_WITHOUT_DATAOBFSC','DATA_AGG','USER');
insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('UPLOAD','DATA_OBFSC','MANAGER');


insert into QT_BREAKDOWN_PATH(name,value,create_date) values ('PATIENT_GENDER_COUNT_XML','\\i2b2\i2b2\Demographics\Gender\',getdate())
insert into QT_BREAKDOWN_PATH(name,value,create_date) values ('PATIENT_RACE_COUNT_XML','\\i2b2\i2b2\Demographics\Race\',getdate())
insert into QT_BREAKDOWN_PATH(name,value,create_date) values ('PATIENT_VITALSTATUS_COUNT_XML','\\i2b2\i2b2\Demographics\Vital Status\',getdate())
insert into QT_BREAKDOWN_PATH(name,value,create_date) values('PATIENT_AGE_COUNT_XML','\\i2b2\i2b2\Demographics\Age\',getdate())













