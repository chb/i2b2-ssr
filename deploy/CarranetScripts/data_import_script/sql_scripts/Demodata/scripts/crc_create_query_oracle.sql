--==============================================================
-- Database Script to create CRC query tables                   
--                                                            
-- This script will create tables, indexes and sequences. 	    
-- User should have permission to create VARRAY type            										                       
--==============================================================

--===========================================================================
-- Table: QT_QUERY_MASTER 											          
--============================================================================
CREATE TABLE QT_QUERY_MASTER (
       QUERY_MASTER_ID NUMBER(5,0) PRIMARY KEY
     , NAME VARCHAR2(250) NOT NULL
     , USER_ID VARCHAR2(50) NOT NULL
     , GROUP_ID VARCHAR2(50) NOT NULL
     , MASTER_TYPE_CD VARCHAR2(2000)
     , PLUGIN_ID NUMBER(10,0)
     , CREATE_DATE DATE NOT NULL
     , DELETE_DATE DATE
     , DELETE_FLAG  VARCHAR2(3)
     , GENERATED_SQL CLOB 
     , REQUEST_XML CLOB
     , I2B2_REQUEST_XML CLOB
);
CREATE INDEX QT_IDX_QM_UGID ON QT_QUERY_MASTER(USER_ID,GROUP_ID,MASTER_TYPE_CD);

--============================================================================
-- Table: QT_QUERY_RESULT_TYPE										          
--============================================================================
CREATE TABLE QT_QUERY_RESULT_TYPE (
       RESULT_TYPE_ID NUMBER(3,0) PRIMARY KEY
     , NAME VARCHAR2(100)
     , DESCRIPTION VARCHAR2(200)
     ,display_type_id varchar2(500)
     ,visual_attribute_type_id varchar2(3)
);
 



--============================================================================
-- Table: QT_QUERY_STATUS_TYPE										          
--============================================================================
CREATE TABLE QT_QUERY_STATUS_TYPE (
       STATUS_TYPE_ID NUMBER(3,0) PRIMARY KEY
     , NAME VARCHAR2(100)
     , DESCRIPTION VARCHAR2(200)
);
--============================================================================
-- Table: QT_QUERY_INSTANCE 										          
--============================================================================
CREATE TABLE QT_QUERY_INSTANCE (
       QUERY_INSTANCE_ID NUMBER(5,0) PRIMARY KEY
     , QUERY_MASTER_ID NUMBER(5,0) 
     , USER_ID VARCHAR2(50) NOT NULL
     , GROUP_ID VARCHAR2(50) NOT NULL
     , BATCH_MODE VARCHAR2(50)
     , START_DATE DATE NOT NULL
     , END_DATE DATE
     , DELETE_FLAG VARCHAR2(3)
     , STATUS_TYPE_ID NUMBER(5,0) 
     , message clob
     , CONSTRAINT QT_FK_QI_MID FOREIGN KEY (QUERY_MASTER_ID)
                  REFERENCES QT_QUERY_MASTER (QUERY_MASTER_ID)
     , CONSTRAINT QT_FK_QI_STID FOREIGN KEY (STATUS_TYPE_ID)
                  REFERENCES QT_QUERY_STATUS_TYPE (STATUS_TYPE_ID)
);
CREATE INDEX QT_IDX_QI_UGID ON QT_QUERY_INSTANCE(USER_ID,GROUP_ID);
CREATE INDEX QT_IDX_QI_MSTARTID ON QT_QUERY_INSTANCE(QUERY_MASTER_ID,START_DATE);




--=============================================================================
-- Table: QT_QUERY_RESULT_INSTANCE   								        
--============================================================================
CREATE TABLE QT_QUERY_RESULT_INSTANCE (
       RESULT_INSTANCE_ID NUMBER(5,0) PRIMARY KEY
     , QUERY_INSTANCE_ID NUMBER(5,0) 
     , RESULT_TYPE_ID NUMBER(3,0) NOT NULL
     , SET_SIZE NUMBER(10,0)
     , START_DATE DATE NOT NULL
     , END_DATE   DATE
     , DELETE_FLAG VARCHAR2(3)
     , STATUS_TYPE_ID NUMBER(3,0) NOT NULL
     , message clob
     , description VARCHAR2(200)
     , REAL_SET_SIZE  NUMBER(10,0)
     , OBFUSC_METHOD VARCHAR2(500)
     , CONSTRAINT QT_FK_QRI_RID FOREIGN KEY (QUERY_INSTANCE_ID)
                  REFERENCES QT_QUERY_INSTANCE (QUERY_INSTANCE_ID)
     , CONSTRAINT QT_FK_QRI_RTID FOREIGN KEY (RESULT_TYPE_ID)
                  REFERENCES QT_QUERY_RESULT_TYPE (RESULT_TYPE_ID)
     , CONSTRAINT QT_FK_QRI_STID FOREIGN KEY (STATUS_TYPE_ID)
                  REFERENCES QT_QUERY_STATUS_TYPE (STATUS_TYPE_ID)
);






--============================================================================
-- Table: QT_PATIENT_SET_COLLECTION									         
--============================================================================
CREATE TABLE QT_PATIENT_SET_COLLECTION ( 
	PATIENT_SET_COLL_ID NUMBER(10,0) PRIMARY KEY
	,RESULT_INSTANCE_ID NUMBER(5,0)
	,SET_INDEX NUMBER(10,0)
	,PATIENT_NUM NUMBER(10,0)
	,CONSTRAINT QT_FK_PSC_RI FOREIGN KEY (RESULT_INSTANCE_ID )
                  REFERENCES QT_QUERY_RESULT_INSTANCE (RESULT_INSTANCE_ID)
);

CREATE INDEX QT_IDX_QPSC_RIID ON QT_PATIENT_SET_COLLECTION(RESULT_INSTANCE_ID);

--============================================================================
-- Table: QT_PATIENT_ENC_COLLECTION									         
--============================================================================
CREATE TABLE QT_PATIENT_ENC_COLLECTION (
	 PATIENT_ENC_COLL_ID NUMBER(10,0) PRIMARY KEY
	, RESULT_INSTANCE_ID NUMBER(5,0)
	, SET_INDEX NUMBER(10,0)
	, PATIENT_NUM NUMBER(10,0)
	, ENCOUNTER_NUM NUMBER(10,0)
	, CONSTRAINT QT_FK_PESC_RI FOREIGN KEY (RESULT_INSTANCE_ID)
                  REFERENCES QT_QUERY_RESULT_INSTANCE(RESULT_INSTANCE_ID)
);

--============================================================================
-- Table: QT_XML_RESULT												          
--============================================================================
CREATE TABLE QT_XML_RESULT (
       XML_RESULT_ID NUMBER(5,0) PRIMARY KEY
     , RESULT_INSTANCE_ID NUMBER(5,0)
     , XML_VALUE VARCHAR2(4000)
     , CONSTRAINT QT_FK_XMLR_RIID FOREIGN KEY (RESULT_INSTANCE_ID)
                  REFERENCES QT_QUERY_RESULT_INSTANCE (RESULT_INSTANCE_ID)
);


-- QT_ANALYSIS_PLUGIN 
CREATE TABLE QT_ANALYSIS_PLUGIN
(
    PLUGIN_ID                    NUMBER(10,0) NOT NULL,
    PLUGIN_NAME             VARCHAR2(2000),
    DESCRIPTION              VARCHAR2(2000),
    VERSION_CD                      VARCHAR2(50),           --support for version
    PARAMETER_INFO     CLOB,                           -- plugin parameter stored as xml
    PARAMETER_INFO_XSD  VARCHAR2(2000),
    COMMAND_LINE VARCHAR2(2000),
    WORKING_FOLDER VARCHAR2(2000),
    COMMANDOPTION_CD VARCHAR2(2000),
   PLUGIN_ICON         VARCHAR2(2000),
    STATUS_CD                   VARCHAR2(50),           -- active,deleted,..
   USER_ID     VARCHAR2(50),
   GROUP_ID VARCHAR2(50),
   CREATE_DATE DATE,
   UPDATE_DATE DATE,
CONSTRAINT ANALYSIS_PLUGIN_PK PRIMARY KEY(PLUGIN_ID)
);

CREATE INDEX QT_APNAMEVERGRP_IDX ON QT_ANALYSIS_PLUGIN(PLUGIN_NAME,VERSION_CD,GROUP_ID);

--============================================================================
-- Table: QT_ANALYSIS_PLUGIN_RESULT_TYPE											          
--============================================================================
CREATE TABLE QT_ANALYSIS_PLUGIN_RESULT_TYPE
(
    PLUGIN_ID                    NUMBER(10,0),
    RESULT_TYPE_ID         NUMBER(10,0),
  CONSTRAINT ANALYSIS_PLUGIN_RESULT_PK PRIMARY KEY(PLUGIN_ID,RESULT_TYPE_ID)
);

--============================================================================
-- Table: QT_PDO_QUERY_MASTER											          
--============================================================================
CREATE TABLE QT_PDO_QUERY_MASTER (
       QUERY_MASTER_ID NUMBER(5,0) PRIMARY KEY
     , USER_ID VARCHAR2(50) NOT NULL
     , GROUP_ID VARCHAR2(50) NOT NULL
     , CREATE_DATE DATE NOT NULL
     , REQUEST_XML CLOB
     , I2B2_REQUEST_XML CLOB
);
CREATE INDEX QT_IDX_PQM_UGID ON QT_PDO_QUERY_MASTER(USER_ID,GROUP_ID);

--============================================================================
-- Table: QT_PRIVILEGE											          
--============================================================================
create table QT_PRIVILEGE(
	protection_label_cd varchar2(1500)
	, dataprot_cd varchar2(1000)
	, hivemgmt_cd varchar2(1000)
	, plugin_id number(10,0)) ;

--============================================================================
-- Table: QT_BREAKDOWN_PATH											          
--============================================================================
create table QT_BREAKDOWN_PATH ( 
name varchar2(100), 
value varchar2(2000), 
create_date date,
update_date date,
user_id varchar2(50));




CREATE GLOBAL TEMPORARY TABLE DX  (
 ENCOUNTER_NUM NUMBER(22,0),
 PATIENT_NUM NUMBER(22,0)
 ) on COMMIT PRESERVE ROWS ;
 
 CREATE GLOBAL TEMPORARY TABLE QUERY_GLOBAL_TEMP   ( 
 ENCOUNTER_NUM NUMBER(22,0),
 PATIENT_NUM NUMBER(22,0),
 PANEL_COUNT number(5,0),
 fact_count number(22,0),
 fact_panels number(5,0)
 ) on COMMIT PRESERVE ROWS ;



 CREATE GLOBAL TEMPORARY TABLE GLOBAL_TEMP_PARAM_TABLE   ( 
	set_index int, 
	char_param1 varchar2(500), 
	char_param2 varchar2(500),
	num_param1 int, 
	num_param2 int
) ON COMMIT PRESERVE ROWS ;

CREATE GLOBAL TEMPORARY TABLE GLOBAL_TEMP_FACT_PARAM_TABLE   ( 
	set_index int, 
	char_param1 varchar2(500), 
	char_param2 varchar2(500),
	num_param1 int, 
	num_param2 int
) ON COMMIT PRESERVE ROWS ;

--------------------------------------------------------
--SEQUENCE CREATION
--------------------------------------------------------
--QUERY MASTER SEQUENE
create sequence QT_SQ_QM_QMID start with 1;

--QUERY RESULT 
create sequence QT_SQ_QR_QRID start with 1;
create sequence QT_SQ_QS_QSID start with 1;

--QUERY INSTANCE SEQUENE
create sequence QT_SQ_QI_QIID start with 1;

--QUERY RESULT INSTANCE ID
create sequence QT_SQ_QRI_QRIID start with 1;

--QUERY PATIENT SET RESULT COLLECTION ID
create sequence QT_SQ_QPR_PCID start with 1;

--QUERY PATTIENT ENCOUNTER SET RESULT COLLECTION ID
create sequence QT_SQ_QPER_PECID start with 1;

--QUERY XML RESULT INSTANCE ID
create sequence QT_SQ_QXR_XRID start with 1;

--QUERY PDO MASTER SEQUENE
create sequence QT_SQ_PQM_QMID start with 1;

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
insert into QT_QUERY_RESULT_TYPE(RESULT_TYPE_ID,NAME,DESCRIPTION,display_type_id,visual_attribute_type_id) values(9,'PATIENTSET','Timeline','LIST','LA');



insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('PDO_WITHOUT_BLOB','DATA_LDS','USER');
insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('PDO_WITH_BLOB','DATA_DEID','USER');
insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('SETFINDER_QRY_WITH_DATAOBFSC','DATA_OBFSC','USER');
insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('SETFINDER_QRY_WITHOUT_DATAOBFSC','DATA_AGG','USER');
insert into QT_PRIVILEGE(protection_label_cd, dataprot_cd, hivemgmt_cd) values ('UPLOAD','DATA_OBFSC','MANAGER');

insert into QT_BREAKDOWN_PATH(name,value,create_date) values ('PATIENT_GENDER_COUNT_XML','\\i2b2\i2b2\Demographics\Gender\',sysdate);
insert into QT_BREAKDOWN_PATH(name,value,create_date) values ('PATIENT_RACE_COUNT_XML','\\i2b2\i2b2\Demographics\Race\',sysdate);
insert into QT_BREAKDOWN_PATH(name,value,create_date) values ('PATIENT_VITALSTATUS_COUNT_XML','\\i2b2\i2b2\Demographics\Vital Status\',sysdate);
insert into QT_BREAKDOWN_PATH(name,value,create_date) values ('PATIENT_AGE_COUNT_XML','\\i2b2\i2b2\Demographics\Age\',sysdate);




--------------------------------------------------------
-- ARRAY TYPE FOR PDO QUERY
--------------------------------------------------------
CREATE OR REPLACE TYPE QT_PDO_QRY_INT_ARRAY AS varray(100000) of  NUMBER(20); 

CREATE OR REPLACE TYPE QT_PDO_QRY_STRING_ARRAY AS varray(100000) of  VARCHAR2(150) ;




















