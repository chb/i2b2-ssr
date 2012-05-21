<?xml version="1.0" encoding="UTF-8"?>
<?altova_samplexml getTrans1.xml?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="http://www.cdisc.org/ns/odm/v1.3" xmlns:pf="http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0">
<!--=====Include the XSLT where all the templates are defined=====-->
	<xsl:include href="CarranetInclude.xslt"/>
	<xsl:output method="text" name="text"/>
	<xsl:key name="idlist" match="*:map" use="@odm_code"/>
	<xsl:variable name="root" select="document('mappings.xml')/*:ODMMapping"/>
	<xsl:template match="/">
		<xsl:result-document format="text">
            <xsl:text>
                TRUNCATE TABLE tmp_EncTable;
                TRUNCATE TABLE TBLRSLTDATAIMPORT;
            </xsl:text>
			<xsl:for-each select="//*:ItemData">
				<xsl:text>INSERT INTO TBLRSLTDATAIMPORT (STUDY, SUBJECTKEY, SITELOCATION, STUDYEVENT, STUDYEVENTREPEATKEY, FORM, ITEMGROUP, ITEMGROUPREPEATKEY, ITEM, VAL, HASVAL, ITEMLOCATION, DATETIMESTAMP, REASONFORCHANGE, ODMCODE, ANSWER, TYPE, CONCEPTCODE, MODIFIER, LOCATION_PATH, PATIENT_NUM, CONCEPT_CD, PROVIDER_ID, START_DATE, MODIFIER_CD, VALTYPE_CD,TVAL_CHAR, NVAL_NUM, VALUEFLAG_CD, SOURCESYSTEM_CD, ERRORDESCRIPTION) VALUES (</xsl:text>
                '<xsl:call-template name="StudyOID"/>',
                '<xsl:call-template name="SubjectKey"/>',
                '<xsl:call-template name="LocationOID"/>',
                '<xsl:call-template name="StudyEventOID"/>',
                <xsl:call-template name="StudyEventRepeatKey"/>,
                '<xsl:call-template name="FormOID"/>',
                '<xsl:call-template name="ItemGroupOID"/>',
                <xsl:call-template name="ItemGroupRepeatKey"/>,
                '<xsl:call-template name="ItemOID"/>',
                '<xsl:call-template name="Value"/>',
                <xsl:variable name="hasVal">
                    <xsl:call-template name="HasValue"/>
                </xsl:variable>
                '<xsl:value-of select="$hasVal"/>',
                '<xsl:call-template name="LocationOID"/>',
                '<xsl:call-template name="DateTimeStamp"/>',
                '<xsl:call-template name="ReasonForChange"/>',
                '<xsl:call-template name="ItemOID"/>',
                '<xsl:apply-templates select="$root" mode="answer">
                    <xsl:with-param name="curr-data" select="."/>
                </xsl:apply-templates>',
                '<xsl:apply-templates select="$root" mode="type">
					<xsl:with-param name="curr-data" select="."/>
				</xsl:apply-templates>',
                <xsl:variable name="Concept_Cd">
					<xsl:apply-templates select="$root" mode="concept_Code">
						<xsl:with-param name="curr-data" select="."/>
					</xsl:apply-templates>
				</xsl:variable>
                <xsl:choose>
					<xsl:when test="$Concept_Cd = 'NULL'">
						<xsl:value-of select="$Concept_Cd"/>
					</xsl:when>
					<xsl:otherwise>'<xsl:value-of select="$Concept_Cd"/>'</xsl:otherwise>
				</xsl:choose>,
                '<xsl:apply-templates select="$root" mode="modifier">
					<xsl:with-param name="curr-data" select="."/>
				</xsl:apply-templates>',
                '<xsl:call-template name="StudyOID"/>
				<xsl:text>\</xsl:text>
				<xsl:call-template name="StudyEventOID"/>
				<xsl:text>\</xsl:text>
				<xsl:variable name="StudyEventRepeatKey">
					<xsl:call-template name="StudyEventRepeatKey"/>
				</xsl:variable>
				<xsl:value-of select="replace($StudyEventRepeatKey,'&apos;&apos;','')"/>
				<xsl:text>\</xsl:text>',
                '<xsl:call-template name="SubjectKey"/>',
                <xsl:choose>
					<xsl:when test="$Concept_Cd = 'NULL'">
						<xsl:value-of select="$Concept_Cd"/>
					</xsl:when>
					<xsl:otherwise>'<xsl:value-of select="$Concept_Cd"/>'</xsl:otherwise>
				</xsl:choose>,
                <xsl:text>'@'</xsl:text>,
                '<xsl:call-template name="DateTimeStamp"/>',
                <xsl:variable name="modifier">
					<xsl:apply-templates select="$root" mode="modifier">
						<xsl:with-param name="curr-data" select="."/>
					</xsl:apply-templates>
				</xsl:variable>
				<!--<xsl:value-of select="$modifier"/>-->
				<xsl:choose>
					<xsl:when test='replace($modifier,"&apos;","")="repeatKey" '>
						<xsl:call-template name="ItemGroupRepeatKey"/>
					</xsl:when>
					<xsl:when test='replace($modifier,"&apos;","")="value" '>
					    '<xsl:value-of select="substring(@Value,1,100)"/>'
                    </xsl:when>
					<xsl:otherwise>'<xsl:value-of select="'@'"/>'</xsl:otherwise>
				</xsl:choose>,
                <xsl:variable name="type">
					<xsl:apply-templates select="$root" mode="type">
						<xsl:with-param name="curr-data" select="."/>
					</xsl:apply-templates>
				</xsl:variable>
				<xsl:choose>
					<xsl:when test='$type="text" '>'<xsl:value-of select="'T'"/>'</xsl:when>
					<xsl:when test='$type="num" '>'<xsl:value-of select="'N'"/>'</xsl:when>
					<xsl:otherwise>'<xsl:value-of select="'@'"/>'</xsl:otherwise>
				</xsl:choose>,
                <xsl:choose>
					<xsl:when test='$type="text" '>'<xsl:call-template name="Value"/>'</xsl:when>
					<xsl:when test='$type="num" '>'<xsl:value-of select="'E'"/>'</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="'NULL'"/>
					</xsl:otherwise>
				</xsl:choose>,
                <xsl:choose>
                    <!--Okay this crazy looking check is to how you handle floating point number comparisons-->
					<xsl:when test='replace($type,"&apos;","")="num" and number(@Value) = number(@Value)'>
                        '<xsl:value-of select="@Value"/>'
                    </xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="'NULL'"/>
					</xsl:otherwise>
				</xsl:choose>,
                <xsl:choose>
					<xsl:when test='$type="num" '>
						<xsl:value-of select="'NULL'"/>
					</xsl:when>
					<xsl:when test='$type="text" '>
						<xsl:value-of select="'NULL'"/>
					</xsl:when>
					<xsl:otherwise>'<xsl:value-of select="'@'"/>'</xsl:otherwise>
				</xsl:choose>,
                <!--'<xsl:call-template name="LocationOID"/>',-->
                <xsl:text>'CARRANET'</xsl:text>,
                <xsl:choose>
					<xsl:when test="string-length(@Value) &gt; 100 and string-length($Concept_Cd) &gt; 4">
						<xsl:text>'ERROR: Field VAL exceeds length.'</xsl:text>
					</xsl:when>
					<xsl:when test='contains($Concept_Cd,"&apos;&apos;carranet")'>
						<xsl:text>'Error: Duplicate Concept Code'</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>''</xsl:text>
					</xsl:otherwise>
				</xsl:choose>);
                <xsl:text>&#xA;</xsl:text>
				<xsl:text>&#xA;</xsl:text>
                <!-- Process Fact Deletions -->
                <!-- We delete facts for both inform deletes (unchecking a checkbox) and inform updates.
                    The former is represented in inform's ODM as a value with an emptry string value
                    and the latter is represented with only a new value.  In both cases, we do not
                    plainly have the old fact to delete, and must derive it.  This is done by building
                    a 'concept_set' from the mappings.xml that group together all the answers for the
                    given question.  We then delete all matching facts for this
                    patient+encounter+provider+modifier. -->
                <xsl:choose>
                    <!--check that there is a value and this is not an inform annotation-->
                    <xsl:when test="$hasVal = 1">
                        <xsl:text>DELETE FROM OBSERVATION_FACT WHERE
                        ENCOUNTER_NUM = nvl((SELECT vd.ENCOUNTER_NUM FROM visit_dimension vd
                               WHERE vd.PATIENT_NUM = </xsl:text>
                        '<xsl:call-template name="SubjectKey"/>'
                        <xsl:text>AND vd.LOCATION_PATH = </xsl:text>
                        '<xsl:call-template name="StudyOID"/>
                        <xsl:text>\</xsl:text>
                        <xsl:call-template name="StudyEventOID"/>
                        <xsl:text>\</xsl:text>
                        <xsl:variable name="StudyEventRepeatKey">
                            <xsl:call-template name="StudyEventRepeatKey"/>
                        </xsl:variable>
                        <xsl:value-of select="replace($StudyEventRepeatKey,'&apos;&apos;','')"/>
                        <xsl:text>\</xsl:text>'
                        <xsl:text>),0)</xsl:text>
                        <xsl:text>AND PATIENT_NUM =</xsl:text>
                        '<xsl:call-template name="SubjectKey"/>'
                        <xsl:text>AND PROVIDER_ID = '@'</xsl:text>
                        <xsl:text>AND MODIFIER_CD = </xsl:text>
                        <xsl:choose>
                            <xsl:when test='replace($modifier,"&apos;","")="repeatKey" '>
                                <xsl:call-template name="ItemGroupRepeatKey"/>
                            </xsl:when>
                            <xsl:when test='replace($modifier,"&apos;","")="value" '>
                                '<xsl:value-of select="substring(@Value,1,100)"/>'
                            </xsl:when>
                            <xsl:otherwise>'<xsl:value-of select="'@'"/>'</xsl:otherwise>
                        </xsl:choose>
                        <xsl:text>AND CONCEPT_CD IN (select trim(column_value) str from (select rtrim(</xsl:text>
                        <xsl:variable name="Concept_Set">
                            <xsl:apply-templates select="$root" mode="concept_Set">
                                <xsl:with-param name="curr-data" select="."/>
                            </xsl:apply-templates>
                        </xsl:variable>
                        <xsl:choose>
                            <xsl:when test="$Concept_Set = 'NULL'">
                                <xsl:value-of select="$Concept_Set"/>
                            </xsl:when>
                            <xsl:otherwise>'<xsl:value-of select="$Concept_Set"/>'</xsl:otherwise>
                        </xsl:choose>
                        <xsl:text>,',') str from tblrsltdataimport), xmltable(('"' || replace(str, ',', '","') || '"')));</xsl:text>

                    </xsl:when>
                </xsl:choose>
			</xsl:for-each>
			<pre><!--=====This is the footer of the query=====-->
			<xsl:text>
<!--/* 	Inserts the Patient information. */-->
MERGE INTO PATIENT_DIMENSION pm
USING ( SELECT DISTINCT di.PATIENT_NUM,
            (SELECT TO_DATE( replace(regexp_substr(TVAL_CHAR,'[0-9\-]+?-',1,1),'-','') ||
                nvl(replace(regexp_substr(TVAL_CHAR,'[0-9\-]+?-',1,2),'-',''), '01') ||
                nvl(replace(regexp_substr(TVAL_CHAR,'\d+T',1,1),'T',''), '01'), 'YYYYMMDD')
	     FROM TBLRSLTDATAIMPORT tblDOB WHERE tblDOB.subjectKey = di.SubjectKey AND tblDOB.ITEM ='frDEM.stDEM1.SBIRTHDT.SBIRTHDT' and ROWNUM=1 and tblDOB.hasval=1) NEW_BIRTH_DATE,
            (SELECT TO_DATE( replace(regexp_substr(TVAL_CHAR,'[0-9\-]+?-',1,1),'-','') ||
                nvl(replace(regexp_substr(TVAL_CHAR,'[0-9\-]+?-',1,2),'-',''), '01') ||
                nvl(replace(regexp_substr(TVAL_CHAR,'\d+T',1,1),'T',''), '01'), 'YYYYMMDD')
	     FROM TBLRSLTDATAIMPORT tblDOD WHERE tblDOD.subjectKey = di.SubjectKey AND tblDOD.ITEM ='frSTATUS.frSTATUS.STATUS.STATUS.GC_STATUS4.DEATHDT' and ROWNUM=1 and tblDOD.hasval=1) NEW_DEATH_DATE,
	    (SELECT (CASE TO_CHAR(Answer) WHEN '2' THEN 'F' WHEN '1' THEN 'M' ELSE '' END) as GENDER 
	        FROM TBLRSLTDATAIMPORT tblGender
	        WHERE tblGender.subjectKey = di.SubjectKey AND tblGender.ITEM='frDEM.stDEM1.SGENDER.SGENDER' and ROWNUM=1 and tblGender.hasval=1) as NEW_SEX_CD,
	     <!--floor((SYSDATE - -->
		       <!--(SELECT TO_DATE(SUBSTR(REPLACE(TVAL_CHAR,'T-:-:-+00:00',''),9,2) || '-' ||-->
				       <!--SUBSTR(REPLACE(TVAL_CHAR,'T-:-:-+00:00',''),6,2) || '-' ||-->
				       <!--SUBSTR(REPLACE(TVAL_CHAR,'T-:-:-+00:00',''),1,4),'DD-MM-YYYY')-->
			<!--FROM TBLRSLTDATAIMPORT tblDOB-->
			<!--WHERE tblDOB.subjectKey = di.SubjectKey AND tblDOB.ITEM ='frDEM.stDEM1.SBIRTHDT.SBIRTHDT' and ROWNUM=1))/365) NEW_AGE_IN_YEARS_NUM, -->
                null NEW_AGE_IN_YEARS_NUM,
	    (SELECT (CASE TO_CHAR(REPLACE(REPLACE(ITEM,'frDEM.stDEM1.RACE.RACE.cpRACEALL.',''),'.1',''))
		         WHEN 'RACEW' THEN 'white' WHEN 'RACEAIAN' THEN 'asian' WHEN 'RACEB' THEN 'black' WHEN 'RACEAS' THEN 'asian'
		         WHEN 'RACENHPI' THEN 'hispanic' WHEN 'RACEOTH' THEN 'other' ELSE TO_CHAR(val) End) as RACE_CD
	         FROM TBLRSLTDATAIMPORT tblRace
	         WHERE  tblRace.subjectKey = di.SubjectKey AND tblRace.ITEM like 'frDEM.stDEM1.RACE.RACE.cpRACEALL%' and ROWNUM=1 and tblRace.hasval=1) NEW_RACE_CD,
            (SELECT substr(trim(TVAL_CHAR),0,10)
                    FROM TBLRSLTDATAIMPORT tblZip
                     WHERE tblZip.subjectkey = di.subjectkey AND tblZip.ITEM='frDEM.stDEM2.ZIPUNK.ZIPUNK.ZIPCODE' and ROWNUM=1 and tblZip.hasval=1) NEW_ZIP_CD,
             SOURCESYSTEM_CD
        FROM TBLRSLTDATAIMPORT di) di1
ON (pm.PATIENT_NUM = di1.PATIENT_NUM)
   WHEN MATCHED THEN UPDATE
	SET
           VITAL_STATUS_CD = (CASE WHEN (NEW_DEATH_DATE IS NULL) THEN 'N' ELSE 'Y' END),
           BIRTH_DATE = nvl(NEW_BIRTH_DATE, BIRTH_DATE),
	   DEATH_DATE = NEW_DEATH_DATE,
           SEX_CD = nvl(NEW_SEX_CD, SEX_CD),
           AGE_IN_YEARS_NUM = nvl(NEW_AGE_IN_YEARS_NUM, AGE_IN_YEARS_NUM),
           RACE_CD = nvl(NEW_RACE_CD, RACE_CD),
           ZIP_CD = nvl(NEW_ZIP_CD, ZIP_CD),
           UPDATE_DATE = SYSDATE,
           DOWNLOAD_DATE = SYSDATE,
           IMPORT_DATE = SYSDATE
   WHEN NOT MATCHED THEN
        INSERT (pm.PATIENT_NUM, pm.VITAL_STATUS_CD, pm.BIRTH_DATE, pm.DEATH_DATE, pm.SEX_CD, pm.AGE_IN_YEARS_NUM, pm.RACE_CD, pm.ZIP_CD, pm.UPDATE_DATE, pm.DOWNLOAD_DATE, pm.IMPORT_DATE, pm.SOURCESYSTEM_CD)
           VALUES (di1.PATIENT_NUM, (CASE WHEN (NEW_DEATH_DATE IS NULL) THEN 'N' ELSE 'Y' END), NEW_BIRTH_DATE, NEW_DEATH_DATE, NEW_SEX_CD, NEW_AGE_IN_YEARS_NUM, NEW_RACE_CD, NEW_ZIP_CD, SYSDATE, SYSDATE, SYSDATE, di1.SOURCESYSTEM_CD);


INSERT INTO tmp_EncTable (ENCOUNTER_NUM, PATIENT_NUM, LOCATION_PATH, START_DATE, END_DATE, SOURCESYSTEM_CD, IsNewEncounter)
select nvl((SELECT MAX(ENCOUNTER_NUM) FROM VISIT_DIMENSION),0) +
       TO_NUMBER(row_number() over (order by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD)) ENCOUNTER_NUM,   
       TO_CHAR(PATIENT_NUM) as PATIENT_NUM ,
       LOCATION_PATH,
       <!--TO_DATE(SUBSTR(min(START_DATE),1,4) || '-' ||-->
               <!--SUBSTR(min(START_DATE),6,2) || '-' ||-->
               <!--SUBSTR(min(START_DATE),9,2),'YYYY-MM-DD') as START_DATE,-->
       (SELECT TO_DATE( replace(regexp_substr(TVAL_CHAR,'[0-9\-]+?-',1,1),'-','') ||
                nvl(replace(regexp_substr(TVAL_CHAR,'[0-9\-]+?-',1,2),'-',''), '01') ||
                nvl(replace(regexp_substr(TVAL_CHAR,'\d+T',1,1),'T',''), '01'), 'YYYYMMDD')
	           FROM TBLRSLTDATAIMPORT tbl WHERE tbl.patient_num = di.patient_num
             and tbl.location_path = di.location_path
             and tbl.sourcesystem_cd = di.sourcesystem_cd
             and tbl.ITEM ='frVIS.frVIS.VISDT.VISDT' and ROWNUM=1 and tbl.hasval=1) as START_DATE,
       <!--null as START_DATE,-->
       <!--TO_DATE(SUBSTR(max(START_DATE),1,4) || '-' ||-->
               <!--SUBSTR(max(START_DATE),6,2) || '-' ||-->
               <!--SUBSTR(max(START_DATE),9,2),'YYYY-MM-DD') as END_DATE,-->
       null as END_DATE,
       SOURCESYSTEM_CD, '1' as IsNewEncounter 
FROM TBLRSLTDATAIMPORT di
                where CONCEPT_CD is not null
                group by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD;


<!--/*  DETERMINE NEW/EXISTING RECORDS  -->
 <!--*  UPDATE Encounter_NUM for the existing Encounters. */-->
 UPDATE Tmp_encTable e
 SET IsNewEncounter = nvl((SELECT 0
                       FROM visit_dimension vd
                       WHERE e.PATIENT_NUM = vd.PATIENT_NUM
                         AND e.LOCATION_PATH = vd.LOCATION_PATH),1),
 ENCOUNTER_NUM= nvl((SELECT vd.ENCOUNTER_NUM
                       FROM visit_dimension vd
                       WHERE e.PATIENT_NUM = vd.PATIENT_NUM
                         AND e.LOCATION_PATH = vd.LOCATION_PATH),ENCOUNTER_NUM)
 WHERE EXISTS(SELECT 1
              FROM visit_dimension vd
              WHERE e.PATIENT_NUM = vd.PATIENT_NUM
              AND e.LOCATION_PATH = vd.LOCATION_PATH);

<!--/* re-assign the ENCOUNTER_NUM, so we do not have any gaps */-->
UPDATE Tmp_encTable e
   SET e.ENCOUNTER_NUM =  (SELECT e1.NEW_ENCOUNTER_NUM FROM
                    (SELECT ENCOUNTER_NUM, 
                            nvl((SELECT MAX(ENCOUNTER_NUM) FROM VISIT_DIMENSION),0) + 
                                        TO_NUMBER(row_number() over (order by encounter_num)) as NEW_ENCOUNTER_NUM
                     FROM Tmp_encTable e1 WHERE IsNewEncounter = 1) e1
                     WHERE e.ENCOUNTER_NUM = e1.ENCOUNTER_NUM)
   WHERE IsNewEncounter = 1;



<!--/* INSERT NEW ENCOUNTERS INTO VISIT DIMENSIONS */-->
INSERT INTO VISIT_DIMENSION(ENCOUNTER_NUM, PATIENT_NUM, ACTIVE_STATUS_CD, START_DATE, END_DATE, INOUT_CD, LOCATION_CD, LOCATION_PATH, VISIT_BLOB, UPDATE_DATE, DOWNLOAD_DATE, IMPORT_DATE, SOURCESYSTEM_CD, UPLOAD_ID)
SELECT ENCOUNTER_NUM,
       PATIENT_NUM,
       '-' AS ACTIVE_STATUS_CD,
       START_DATE,
       END_DATE,
       '@' as INOUT_CD,
       '@' as LOCATION_CD,
       LOCATION_PATH,
       '-' AS VISIT_BLOB,
       SYSDATE AS UPDATE_DATE,
       SYSDATE AS DOWNLOAD_DATE,
       SYSDATE AS IMPORT_DATE,
       SOURCESYSTEM_CD,
       NULL as UPLOAD_ID
FROM tmp_Enctable e
WHERE IsNewEncounter = 1;

<!--/* UPDATE START_DATE/END_DATE of Existing VISIT_DIMENSIONS */-->
MERGE INTO VISIT_DIMENSION vd
USING (SELECT ENCOUNTER_NUM, PATIENT_NUM, LOCATION_PATH, START_DATE, END_DATE
         FROM tmp_encTable
         WHERE IsNewEncounter = 0) e
ON (vd.ENCOUNTER_NUM = e.ENCOUNTER_NUM)
WHEN MATCHED THEN UPDATE
    <!--SET vd.START_DATE = (CASE WHEN (e.START_DATE &lt; vd.START_DATE) THEN e.START_DATE ELSE vd.START_DATE END),-->
    SET vd.START_DATE = (CASE WHEN (e.START_DATE is not null) THEN e.START_DATE ELSE vd.START_DATE END),
        vd.END_DATE = (CASE WHEN (e.END_DATE > vd.END_DATE) THEN e.END_DATE ELSE vd.END_DATE END);


<!--/* Process Fact Deletions */-->
<!--Because inform represents deletes as emptystring values, we don't know which fact to delete.  so delete them all-->
<!--DELETE FROM OBSERVATION_FACT obf-->
<!--WHERE EXISTS(-->
    <!--SELECT 1-->
    <!--FROM TBLRSLTDATAIMPORT di, tmp_Enctable e-->
    <!--WHERE di.PATIENT_NUM = e.PATIENT_NUM-->
        <!--AND di.location_path = e.location_path-->
        <!--AND di.CONCEPT_CD is not null-->
        <!--AND obf.ENCOUNTER_NUM = e.ENCOUNTER_NUM-->
        <!--AND obf.PATIENT_NUM = e.PATIENT_NUM-->
        <!--AND obf.PROVIDER_ID = di.PROVIDER_ID-->
        <!--AND obf.MODIFIER_CD = di.MODIFIER_CD-->
        <!--AND obf.CONCEPT_CD in (select trim(column_value) str-->
            <!--from (select rtrim(conceptset, ',') str from tblrsltdataimport), xmltable(('"' || replace(str, ',', '","') || '"')))-->
        <!--AND di.HASVAL = 1-->
    <!--);-->

<!--/* DELETE EXISTING OBSERVATION FACTS */-->
<!--DELETE FROM OBSERVATION_FACT obf-->
<!--WHERE EXISTS(-->
      <!--SELECT 1-->
      <!--FROM TBLRSLTDATAIMPORT di, tmp_Enctable e-->
      <!--WHERE di.PATIENT_NUM = e.PATIENT_NUM-->
        <!--AND di.location_path = e.location_path-->
        <!--AND di.CONCEPT_CD is not null-->
        <!--AND obf.ENCOUNTER_NUM = e.ENCOUNTER_NUM-->
        <!--AND obf.CONCEPT_CD = di.CONCEPT_CD -->
        <!--AND obf.PATIENT_NUM = e.PATIENT_NUM -->
        <!--AND obf.PROVIDER_ID = di.PROVIDER_ID-->
        <!--AND obf.MODIFIER_CD = di.MODIFIER_CD-->
      <!--);-->


<!--/* OBSERVATION_FACT -->
 <!--* INSERT NEW  */-->
INSERT INTO OBSERVATION_FACT (ENCOUNTER_NUM, CONCEPT_CD, PROVIDER_ID, START_DATE, MODIFIER_CD, PATIENT_NUM, VALTYPE_CD, TVAL_CHAR, NVAL_NUM, VALUEFLAG_CD, QUANTITY_NUM, INSTANCE_NUM, UNITS_CD, END_DATE, LOCATION_CD, UPDATE_DATE, DOWNLOAD_DATE, IMPORT_DATE, SOURCESYSTEM_CD)
SELECT DISTINCT e.ENCOUNTER_NUM, di.CONCEPT_CD, di.PROVIDER_ID,
       TO_DATE( replace(regexp_substr(di.START_DATE,'[0-9\-]+?-',1,1),'-','') ||
                nvl(replace(regexp_substr(di.START_DATE,'[0-9\-]+?-',1,2),'-',''), '01') ||
                nvl(replace(regexp_substr(di.START_DATE,'\d+T',1,1),'T',''), '01'), 'YYYYMMDD'),
       nvl(di.MODIFIER_CD,'@') as MODIFIER_CD, di.PATIENT_NUM, di.VALTYPE_CD, di.TVAL_CHAR, to_number(di.NVAL_NUM), di.VALUEFLAG_CD,
       '' as QUANTITY_NUM, 
       '' as INSTANCE_NUM,
       '' as UNITS_CD,
        e.END_DATE,
       di.LOCATION_PATH,
       SYSDATE AS UPDATE_DATE,
        SYSDATE AS DOWNLOAD_DATE,
        SYSDATE AS IMPORT_DATE,
       e.SOURCESYSTEM_CD
FROM TBLRSLTDATAIMPORT di, tmp_Enctable e
WHERE di.patient_num = e.patient_num 
   AND di.location_path = e.location_path
   AND di.CONCEPT_CD is not null
   AND  di.START_DATE = 
         (SELECT max(di_1.START_DATE) FROM TBLRSLTDATAIMPORT di_1
          WHERE di.PATIENT_NUM = di_1.patient_num 
            AND di.CONCEPT_CD = di_1.CONCEPT_CD
            AND di.PROVIDER_ID = di_1.PROVIDER_ID 
            AND di.MODIFIER_CD = di_1.MODIFIER_CD
            AND di.LOCATION_PATH = di_1.LOCATION_PATH)
   AND di.HASVAL = 1
   AND di.VAL is not null;


TRUNCATE TABLE tmp_EncTable;
TRUNCATE TABLE TBLRSLTDATAIMPORT;
</xsl:text>
			</pre>
		</xsl:result-document>
	</xsl:template>
</xsl:stylesheet>
