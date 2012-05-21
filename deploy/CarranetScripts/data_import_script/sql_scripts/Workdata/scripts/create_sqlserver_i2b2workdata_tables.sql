CREATE TABLE WORKPLACE ( 
	C_NAME     	VARCHAR(255) NOT NULL,
	C_USER_ID	VARCHAR(255) NOT NULL,
	C_GROUP_ID	VARCHAR(255) NOT NULL,
	C_SHARE_ID	VARCHAR(255) NULL,
	C_INDEX  	VARCHAR(255) NOT NULL,
	C_PARENT_INDEX  	VARCHAR(255) NULL,
	C_VISUALATTRIBUTES   	CHAR(3) NOT NULL,
	C_PROTECTED_ACCESS    	CHAR(1) NULL,
	C_TOOLTIP      	VARCHAR(255) NULL,
	C_WORK_XML      	TEXT NULL,
	C_WORK_XML_SCHEMA      	TEXT NULL,
	C_WORK_XML_I2B2_TYPE      	VARCHAR(255) NULL,
	C_ENTRY_DATE   	DATETIME NULL,
	C_CHANGE_DATE  	DATETIME NULL,
	C_STATUS_CD    	CHAR(1) NULL,
 CONSTRAINT WORKPLACE_PK PRIMARY KEY(C_INDEX)  
	);

	
CREATE TABLE WORKPLACE_ACCESS ( 
	C_TABLE_CD   	VARCHAR(255) NOT NULL,
	C_TABLE_NAME      VARCHAR(255) NOT NULL,
	C_PROTECTED_ACCESS    	CHAR(1) NULL,
	C_HLEVEL	INT NOT NULL,
	C_NAME     	VARCHAR(255) NOT NULL,
	C_USER_ID	VARCHAR(255) NOT NULL,
	C_GROUP_ID	VARCHAR(255) NOT NULL,
	C_SHARE_ID	VARCHAR(255) NULL,
	C_INDEX  	VARCHAR(255) NOT NULL,
	C_PARENT_INDEX  	VARCHAR(255) NULL,
	C_VISUALATTRIBUTES   	CHAR(3) NOT NULL,
	C_TOOLTIP      	VARCHAR(255) NULL,
	C_ENTRY_DATE   	DATETIME NULL,
	C_CHANGE_DATE  	DATETIME NULL,
	C_STATUS_CD    	CHAR(1) NULL,
 CONSTRAINT WORKPLACE_ACCESS_PK PRIMARY KEY(C_INDEX) 
	);
	



	
	