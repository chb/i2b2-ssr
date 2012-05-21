<?xml version="1.0" encoding="UTF-8"?>
<?altova_samplexml getTrans1.xml?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns="http://www.cdisc.org/ns/odm/v1.3" xmlns:pf="http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0">
    <xsl:include href="CarranetInclude.xslt"/>
    <xsl:output method="text" name="text"/>
    <xsl:key name="idlist" match="*:map" use="@odm_code"/>
    <xsl:variable name="root" select="document('mappings.xml')/*:ODMMapping"/>
    <xsl:template match="/">
        <xsl:result-document format="text">
            <xsl:text>
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
                '<xsl:call-template name="HasValue"/>',
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
                    <xsl:otherwise>
                        '<xsl:value-of select="$Concept_Cd"/>'
                    </xsl:otherwise>
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
                    <xsl:otherwise>
                        '<xsl:value-of select="'@'"/>'
                    </xsl:otherwise>
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
                    <xsl:when test='replace($type,"&apos;","")="num" and number(@Value)'>
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
            </xsl:choose>);<xsl:text>&#xA;</xsl:text>
                <xsl:text>&#xA;</xsl:text>
            </xsl:for-each>
<pre>
    <xsl:text>
SELECT dbms_xmlgen.convert(XMLELEMENT("CARRANET",
(SELECT XMLFOREST(
<!--(SELECT COUNT(DISTINCT di.Patient_num)
  FROM TBLRSLTDATAIMPORT di inner join PATIENT_DIMENSION pm ON di.Patient_num = pm.patient_NUM) as EXISTING_PATIENTS
,
(SELECT COUNT(DISTINCT PATIENT_NUM) FROM TBLRSLTDATAIMPORT di
 WHERE NOT EXISTS(SELECT 1 FROM PATIENT_DIMENSION pm WHERE di.Patient_num = pm.patient_NUM)
) as NEW_PATIENTS
,
(SELECT COUNT(*) FROM
   (
  SELECT row_number() over (order by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD) as ENCOUNTER_NUM,
        PATIENT_NUM as PATIENT_NUM,
                LOCATION_PATH as LOCATION_PATH,
                min(START_DATE) as START_DATE,
                max(START_DATE) as END_DATE,
                SOURCESYSTEM_CD as SOURCESYSTEM_CD
                FROM TBLRSLTDATAIMPORT di
                WHERE CONCEPT_CD is not null
              AND EXISTS(SELECT 1 FROM VISIT_DIMENSION vd
                         WHERE vd.PATIENT_NUM = di.PATIENT_NUM AND vd.LOCATION_PATH = di.LOCATION_PATH
                               AND vd.SOURCESYSTEM_CD = di.SOURCESYSTEM_CD)
                group by PATIENT_NUM , LOCATION_PATH, SOURCESYSTEM_CD
   )) EXISTING_ENCOUNTERS
,
(SELECT COUNT(*) FROM
   (SELECT row_number() over (order by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD) as ENCOUNTER_NUM,
        PATIENT_NUM as PATIENT_NUM,
                LOCATION_PATH as LOCATION_PATH,
                min(START_DATE) as START_DATE,
                max(START_DATE) as END_DATE,
                SOURCESYSTEM_CD as SOURCESYSTEM_CD
                FROM TBLRSLTDATAIMPORT di
                WHERE CONCEPT_CD is not null
              AND NOT EXISTS(SELECT 1 FROM VISIT_DIMENSION vd
                         WHERE vd.PATIENT_NUM = di.PATIENT_NUM AND vd.LOCATION_PATH = di.LOCATION_PATH
                               AND vd.SOURCESYSTEM_CD = di.SOURCESYSTEM_CD)
                group by PATIENT_NUM , LOCATION_PATH, SOURCESYSTEM_CD
   )) NEW_ENCOUNTERS
,
(SELECT COUNT(*)
FROM
(
SELECT DISTINCT di.SUBJECTKEY, e.LOCATION_PATH, di.CONCEPTCODE, e.START_DATE
FROM TBLRSLTDATAIMPORT di,
(select
       TO_NUMBER(row_number() over (order by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD)) ENCOUNTER_NUM,
       TO_CHAR(PATIENT_NUM) as PATIENT_NUM ,
       LOCATION_PATH,
       TO_DATE(SUBSTR(min(START_DATE),1,4) || '-' ||
               SUBSTR(min(START_DATE),6,2) || '-' ||
               SUBSTR(min(START_DATE),9,2),'YYYY-MM-DD') as START_DATE
FROM TBLRSLTDATAIMPORT
                where CONCEPT_CD is not null
                group by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD
) e
WHERE di.CONCEPT_CD IS NOT NULL AND
      di.patient_num = e.patient_num AND
      di.LOCATION_PATH = e.LOCATION_PATH AND
        EXISTS(SELECT 1 FROM OBSERVATION_FACT obf
                 WHERE obf.CONCEPT_CD = di.CONCEPT_CD AND
                       obf.Patient_num = obf.patient_NUM AND
                       obf.START_DATE = e.START_DATE AND
                       obf.PROVIDER_ID = di.PROVIDER_ID AND
                       obf.MODIFIER_CD = di.MODIFIER_CD
                       )
)) EXISTING_FACTS
,
(SELECT COUNT(*)
FROM
(
SELECT DISTINCT di.SUBJECTKEY, e.LOCATION_PATH, di.CONCEPTCODE, e.START_DATE
FROM TBLRSLTDATAIMPORT di,
(select
       TO_NUMBER(row_number() over (order by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD)) ENCOUNTER_NUM,
       TO_CHAR(PATIENT_NUM) as PATIENT_NUM ,
       LOCATION_PATH,
       TO_DATE(SUBSTR(min(START_DATE),1,4) || '-' ||
               SUBSTR(min(START_DATE),6,2) || '-' ||
               SUBSTR(min(START_DATE),9,2),'YYYY-MM-DD') as START_DATE
FROM TBLRSLTDATAIMPORT
                where CONCEPT_CD is not null
                group by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD
) e
WHERE di.CONCEPT_CD IS NOT NULL AND
      di.patient_num = e.patient_num AND
      di.LOCATION_PATH = e.LOCATION_PATH AND
        NOT EXISTS(SELECT 1 FROM OBSERVATION_FACT obf
                 WHERE obf.CONCEPT_CD = di.CONCEPT_CD AND
                       obf.Patient_num = obf.patient_NUM AND
                       obf.START_DATE = e.START_DATE AND
                       obf.PROVIDER_ID = di.PROVIDER_ID AND
                       obf.MODIFIER_CD = di.MODIFIER_CD
                       )
)) NEW_FACTS
,
(SELECT COUNT(*)
FROM TBLRSLTDATAIMPORT di,
(select
       TO_NUMBER(row_number() over (order by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD)) ENCOUNTER_NUM,
       TO_CHAR(PATIENT_NUM) as PATIENT_NUM ,
       LOCATION_PATH,
       TO_DATE(SUBSTR(min(START_DATE),1,4) || '-' ||
               SUBSTR(min(START_DATE),6,2) || '-' ||
               SUBSTR(min(START_DATE),9,2),'YYYY-MM-DD') as START_DATE
FROM TBLRSLTDATAIMPORT
                where CONCEPT_CD is not null
                group by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD
) e
WHERE di.CONCEPT_CD IS NOT NULL AND
      di.patient_num = e.patient_num AND
      di.LOCATION_PATH = e.LOCATION_PATH AND
        EXISTS(SELECT 1 FROM OBSERVATION_FACT obf
                 WHERE obf.CONCEPT_CD = di.CONCEPT_CD AND
                       obf.Patient_num = obf.patient_NUM AND
                       obf.START_DATE = e.START_DATE AND
                       obf.PROVIDER_ID = di.PROVIDER_ID AND
                       obf.MODIFIER_CD = di.MODIFIER_CD
                       )) EXISTING_FACTS_TRANSACTIONS
,
(SELECT COUNT(*)
FROM TBLRSLTDATAIMPORT di,
(select
       TO_NUMBER(row_number() over (order by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD)) ENCOUNTER_NUM,
       TO_CHAR(PATIENT_NUM) as PATIENT_NUM ,
       LOCATION_PATH,
       TO_DATE(SUBSTR(min(START_DATE),1,4) || '-' ||
               SUBSTR(min(START_DATE),6,2) || '-' ||
               SUBSTR(min(START_DATE),9,2),'YYYY-MM-DD') as START_DATE
FROM TBLRSLTDATAIMPORT
                where CONCEPT_CD is not null
                group by PATIENT_NUM, LOCATION_PATH, SOURCESYSTEM_CD
) e
WHERE di.CONCEPT_CD IS NOT NULL AND
      di.patient_num = e.patient_num AND
      di.LOCATION_PATH = e.LOCATION_PATH AND
        NOT EXISTS(SELECT 1 FROM OBSERVATION_FACT obf
                 WHERE obf.CONCEPT_CD = di.CONCEPT_CD AND
                       obf.Patient_num = obf.patient_NUM AND
                       obf.START_DATE = e.START_DATE AND
                       obf.PROVIDER_ID = di.PROVIDER_ID AND
                       obf.MODIFIER_CD = di.MODIFIER_CD
                       )) NEW_FACTS_TRANSACTIONS

,-->
(SELECT COUNT(*) FROM TBLRSLTDATAIMPORT WHERE CONCEPT_CD is NULL and hasval = 1) FACTS_NOT_MAPPED
,
(SELECT COUNT(DISTINCT studyevent) FROM TBLRSLTDATAIMPORT) STUDYEVENTS
, 
(SELECT COUNT(DISTINCT form) FROM TBLRSLTDATAIMPORT) FORMS
, 
(SELECT MIN(datetimestamp) FROM TBLRSLTDATAIMPORT di) EARLIEST_START_DATE
, 
(SELECT MAX(datetimestamp) FROM TBLRSLTDATAIMPORT) LATEST_START_DATE
,
(SELECT count(*) FROM TBLRSLTDATAIMPORT di WHERE NOT EXISTS (select cd.concept_cd from CONCEPT_DIMENSION cd where di.concept_cd = cd.concept_cd)) UNKNOWN_CONCEPT_CD
,
(SELECT DISTINCT XMLAGG(XMLELEMENT("VALUE_TRUNCATED",
                   XMLATTRIBUTES(STUDY, SUBJECTKEY, ITEM, DATETIMESTAMP,LOCATION_PATH, VAL)
                 )
                ORDER BY SUBJECTKEY, DATETIMESTAMP
                )
FROM TBLRSLTDATAIMPORT
WHERE LENGTH(ERRORDESCRIPTION) >0
) VALUES_TRUNCATED
,
(SELECT DISTINCT XMLAGG(XMLELEMENT("STUDYEVENT_FORM",
                   XMLATTRIBUTES(STUDYEVENT AS STUDYEVENT, 
                       FORM AS FORM, 
                       COUNT(DISTINCT PATIENT_NUM) as PATIENTS, 
                       COUNT(*) as ITEMS)
                 )
                ORDER BY STUDYEVENT, FORM
                )
FROM TBLRSLTDATAIMPORT
GROUP BY STUDYEVENT,FORM
) STUDYEVENTS_FORMS
, 
(SELECT DISTINCT XMLAGG(
         XMLELEMENT("ITEM_NOT_MAPPED",
                   XMLATTRIBUTES(item as ITEMOID,
                             val as VALUE, 
                             COUNT(distinct SubjectKey) as PATIENTS,
                             COUNT(*) as FACTS)
               )
             ORDER BY item
           )
       FROM TBLRSLTDATAIMPORT
       WHERE CONCEPT_CD is null and hasval = 1
       GROUP BY ITEM, val) ITEMS_NOT_MAPPED
)
FROM DUAL)
).GetClobVal(),1) as result FROM DUAL;


TRUNCATE TABLE TBLRSLTDATAIMPORT;

</xsl:text>
            </pre>
        </xsl:result-document>
    </xsl:template>
</xsl:stylesheet>
