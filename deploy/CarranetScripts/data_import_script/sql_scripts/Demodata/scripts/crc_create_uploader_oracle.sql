--==============================================================
-- Uploader service create script                               
--                                                            
-- This script will create tables for the uploader service.   
-- Run this script after the datamart create script             
--==============================================================


--============================================================================
-- Table: ARCHIVE_OBSERVATION_FACT (HOLDS DELETED ENTRIES OF OBSERVATION_FACT) 
--============================================================================
CREATE TABLE ARCHIVE_OBSERVATION_FACT NOLOGGING AS (
	SELECT * FROM OBSERVATION_FACT WHERE 1= 2) ;

ALTER TABLE ARCHIVE_OBSERVATION_FACT  ADD ( ARCHIVE_UPLOAD_ID NUMBER(22,0)); 

CREATE INDEX PK_ARCHIVE_OBSFACT ON ARCHIVE_OBSERVATION_FACT
 		(ENCOUNTER_NUM , PATIENT_NUM , CONCEPT_CD , PROVIDER_ID , START_DATE , MODIFIER_CD , ARCHIVE_UPLOAD_ID) ; 


--==============================================================
-- Table: DATAMART_REPORT			                    	
--==============================================================
create table DATAMART_REPORT ( 
	TOTAL_PATIENT         NUMBER(38,0), 
	TOTAL_OBSERVATIONFACT NUMBER(38,0), 
	TOTAL_EVENT           NUMBER(38,0),
	REPORT_DATE           DATE); 




--==============================================================
-- Table: UPLOAD_STATUS 					                    
--==============================================================
CREATE TABLE UPLOAD_STATUS (
	UPLOAD_ID 		    NUMBER(38,0), 	
    UPLOAD_LABEL 		VARCHAR2(500) NOT NULL, 
    USER_ID      		VARCHAR2(100) NOT NULL, 
    SOURCE_CD   		VARCHAR2(50) NOT NULL,
    NO_OF_RECORD 		NUMBER,
    LOADED_RECORD 		NUMBER,
    DELETED_RECORD		NUMBER, 
    LOAD_DATE    		DATE			  NOT NULL,
	END_DATE 	        DATE , 
    LOAD_STATUS  		VARCHAR2(100), 
    MESSAGE				VARCHAR2(4000),
    INPUT_FILE_NAME 	VARCHAR2(500), 
    LOG_FILE_NAME 		VARCHAR2(500), 
    TRANSFORM_NAME 		VARCHAR2(500),
    CONSTRAINT PK_UP_UPSTATUS_UPLOADID  PRIMARY KEY (UPLOAD_ID)
) ;

--==============================================================
-- Table: SET_TYPE						                        
--==============================================================
CREATE TABLE SET_TYPE (
	ID 				INTEGER, 
    NAME			VARCHAR2(500),
    CREATE_DATE     DATE,
    CONSTRAINT PK_ST_ID PRIMARY KEY (ID)
) ;


--==============================================================
-- Table: SOURCE_MASTER					                        
--==============================================================
CREATE TABLE SOURCE_MASTER ( 
   SOURCE_CD 				VARCHAR(50) NOT NULL,
   DESCRIPTION  			VARCHAR(300),
   CREATE_DATE 				DATE,
   CONSTRAINT PK_SOURCEMASTER_SOURCECD  PRIMARY KEY (SOURCE_CD)
);

-- ==============================================================
-- Table: SET_UPLOAD_STATUS				                        
--==============================================================
CREATE TABLE SET_UPLOAD_STATUS  (
    UPLOAD_ID			NUMBER,
    SET_TYPE_ID         INTEGER,
    SOURCE_CD  		    VARCHAR(50) NOT NULL,
    NO_OF_RECORD 		NUMBER,
    LOADED_RECORD 		NUMBER,
    DELETED_RECORD		NUMBER, 
    LOAD_DATE    		DATE NOT NULL,
    END_DATE            DATE ,
    LOAD_STATUS  		VARCHAR2(100), 
    MESSAGE			    VARCHAR2(4000),
    INPUT_FILE_NAME 	VARCHAR2(500), 
    LOG_FILE_NAME 		VARCHAR2(500), 
    TRANSFORM_NAME 		VARCHAR2(500),
    CONSTRAINT PK_UP_UPSTATUS_IDSETTYPEID  PRIMARY KEY (UPLOAD_ID,SET_TYPE_ID),
    CONSTRAINT FK_UP_SET_TYPE_ID FOREIGN KEY (SET_TYPE_ID) REFERENCES SET_TYPE(ID)
) ;





--=============================================================
-- Sequences for generating primary keys.					
--==============================================================
CREATE SEQUENCE sq_uploadstatus_uploadid
  INCREMENT BY 1
  START WITH 1
  MINVALUE 1
  MAXVALUE 9999999999999
  NOCYCLE
  ORDER
  NOCACHE
;

CREATE SEQUENCE sq_up_encdim_encounternum
  INCREMENT BY 1
  START WITH 1
  MINVALUE 1
  MAXVALUE 9999999999999
  NOCYCLE
  ORDER
  NOCACHE
;

CREATE SEQUENCE sq_up_patdim_patientnum
  INCREMENT BY 1
  START WITH 1
  MINVALUE 1
  MAXVALUE 9999999999999
  NOCYCLE
  ORDER
  NOCACHE
;


--==============================================================--
--  Adding seed edu.chip.carranet.data for SOURCE_MASTER table.  					--
--==============================================================--
INSERT INTO SOURCE_MASTER(source_cd,description,create_date) values ('I2B2PulmX','i2b2 Pulminory Extract',sysdate);
 
--==============================================================
--  Adding seed edu.chip.carranet.data for SET_TYPE table.
--==============================================================
INSERT INTO SET_TYPE(id,name,create_date) values (1,'event_set',sysdate);
INSERT INTO SET_TYPE(id,name,create_date) values (2,'patient_set',sysdate);
INSERT INTO SET_TYPE(id,name,create_date) values (3,'concept_set',sysdate);
INSERT INTO SET_TYPE(id,name,create_date) values (4,'observer_set',sysdate);
INSERT INTO SET_TYPE(id,name,create_date) values (5,'observation_set',sysdate);
INSERT INTO SET_TYPE(id,name,create_date) values (6,'pid_set',sysdate);
INSERT INTO SET_TYPE(id,name,create_date) values (7,'eid_set',sysdate);
 
