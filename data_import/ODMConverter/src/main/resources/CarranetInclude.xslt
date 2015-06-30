<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.cdisc.org/ns/odm/v1.3"
                xmlns:pf="http://www.phaseforward.com/InFormAdapter/ODM/Extensions/2.0">
	<!--===== This template returns the attribute SubjectKey when called =====-->
	<xsl:template name="SubjectKey">
		<xsl:value-of select="ancestor::*:SubjectData/@SubjectKey"/>
	</xsl:template>
	<!--===== This template returns the attribute DateOfBirth when called =====-->
	<xsl:template name="DateOfBirth">
		<xsl:value-of select="preceding::pf:Candidate[1]/@DateOfBirth"/>
	</xsl:template>
	<!--===== This template returns the attribute ItemOID when called =====-->
	<xsl:template name="ItemOID">
		<xsl:value-of select="@ItemOID"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="StudyOID">
		<xsl:value-of select="ancestor::*:ClinicalData/@StudyOID"/>
	</xsl:template>
	<xsl:template match="*:ODMMapping" mode="concept_Code">
		<xsl:param name="curr-data"/>
		<!--===The first choose condition selects all the ItemOID codes which have mapping===-->
		<xsl:choose> 
			<xsl:when test="key('idlist', $curr-data/@ItemOID)">
			<xsl:variable name="answers">
				<xsl:value-of select="key('idlist', $curr-data/@ItemOID)/@answer" separator=","/>
			</xsl:variable>
			<xsl:variable name="type">
				<xsl:value-of select="key('idlist', $curr-data/@ItemOID)/@type" separator=","/>			
			</xsl:variable>
				<xsl:for-each select="key('idlist', $curr-data/@ItemOID)">
					<!--=== The second choose condition checks for the @type and @answer in the mapping===-->
					<xsl:choose>
                        <xsl:when test="$curr-data/@Value= ''"><xsl:value-of select="@concept_code"/>,</xsl:when>
						<xsl:when test="@type or @answer">
							<xsl:choose>
								<xsl:when test="@type='text'"><xsl:value-of select="@concept_code"/></xsl:when>
								<xsl:when test="@type='num'"><xsl:value-of select="@concept_code"/></xsl:when>
								<xsl:when test="$curr-data/@Value= @answer"><xsl:value-of select="@concept_code"/></xsl:when>
								<xsl:when test="$curr-data/@Value!= @answer"></xsl:when>
							</xsl:choose>
						</xsl:when>
						<!--===If there is no @type or @answer but there is a mapping ItemOID will map with the Concept Code===-->
						<xsl:otherwise>
						<xsl:choose>
							<!--<xsl:when test="$curr-edu.chip.carranet.data/@Value= $answers"></xsl:when>-->
							<xsl:when test="contains($answers,$curr-data/@Value) or contains($type,'text') or contains($type,'num')">
								<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="@concept_code"/>
							</xsl:otherwise>
						</xsl:choose>
							<!--<xsl:value-of select="@concept_code"/>-->
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</xsl:when>
			<!--===The following condition selects all the ItemOID codes which have does not have mapping and mark them as NULL===-->
			<xsl:otherwise>
				<xsl:value-of select="'NULL'"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
    <xsl:template match="*:ODMMapping" mode="concept_Set">
        <xsl:param name="curr-data"/>
        <!--===The first choose condition selects all the ItemOID codes which have mapping===-->
        <xsl:choose>
            <xsl:when test="key('idlist', $curr-data/@ItemOID)">
                <xsl:for-each select="key('idlist', $curr-data/@ItemOID)">
                    <xsl:value-of select="@concept_code"/>,
                </xsl:for-each>
            </xsl:when>
            <!--===The following condition selects all the ItemOID codes which have does not have mapping and mark them as NULL===-->
            <xsl:otherwise>
                <xsl:value-of select="'NULL'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
	<xsl:template match="*:ODMMapping" mode="answer">
		<xsl:param name="curr-data"/>
		<xsl:choose>
			<xsl:when test="key('idlist', $curr-data/@ItemOID)">
				<xsl:for-each select="key('idlist', $curr-data/@ItemOID)">
					<xsl:choose>
						<xsl:when test="$curr-data/@Value= @answer"><xsl:value-of select="@answer"/></xsl:when>
						<xsl:when test="not(@answer)">
							<xsl:text></xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text></xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="*:ODMMapping" mode="modifier">
		<xsl:param name="curr-data"/>
		<xsl:choose>
			<xsl:when test="key('idlist', $curr-data/@ItemOID)">
				<xsl:for-each select="key('idlist', $curr-data/@ItemOID)">
					<xsl:choose>
						<xsl:when test="@type and @modifier"><xsl:value-of select="@modifier"/></xsl:when>
						<xsl:when test="@type and not(@modifier)">
							<xsl:text></xsl:text>
						</xsl:when>
						<xsl:when test="$curr-data/@Value= @answer and @modifier"><xsl:value-of select="@modifier"/></xsl:when>
						<xsl:when test="$curr-data/@Value= @answer and not(@modifier)">
							<xsl:text></xsl:text>
						</xsl:when>
						<xsl:when test="not(@answer)and not(@type)">
							<xsl:text></xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text></xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--=================================================================-->
	<xsl:template match="*:ODMMapping" mode="type">
		<xsl:param name="curr-data"/>
		<xsl:choose>
			<xsl:when test="key('idlist', $curr-data/@ItemOID)">
				<xsl:for-each select="key('idlist', $curr-data/@ItemOID)">
					<xsl:choose>
						<xsl:when test="@type"><xsl:value-of select="@type"/></xsl:when>
						<xsl:when test="$curr-data/@Value= @answer">
							<xsl:text></xsl:text>
						</xsl:when>
						<xsl:when test="not(@type or @answer)">
							<xsl:text></xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text></xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--======================================================================-->
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="DateTimeStamp">
		<xsl:value-of select="*:AuditRecord/*:DateTimeStamp"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="ReasonForChange">
		<xsl:value-of select='replace(replace(*:AuditRecord/*:ReasonForChange,"&apos;","&apos;&apos;"), ";", ",")'/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="MetaDataVersionOID">
		<xsl:value-of select="ancestor::*:ClinicalData/@MetaDataVersionOID"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="TransactionGuid">
		<xsl:value-of select="ancestor::*:ClinicalData/@pf:TransactionGuid"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="LocationOID">
		<xsl:value-of select="ancestor::*:SubjectData/*:SiteRef/@LocationOID"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="StudyEventOID">
		<xsl:value-of select="ancestor::*:StudyEventData/@StudyEventOID"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="EnrollmentNumber">
		<xsl:value-of select="preceding::pf:Candidate[1]/@EnrollmentNumber"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="UserOID">
		<xsl:value-of select="*:AuditRecord/*:UserRef/@UserOID"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyEventRepeatKey when called =====-->
	<xsl:template name="StudyEventRepeatKey">
		<xsl:choose>
			<!--===Test @StudyEventRepeatKey exists or not==-->
			<xsl:when test="ancestor::*:StudyEventData/@StudyEventRepeatKey">'<xsl:value-of select="ancestor::*:StudyEventData/@StudyEventRepeatKey"/>'</xsl:when>
			<!--===If it does not exist put two single quotes ('') ===-->
			<xsl:otherwise>
				<xsl:text>''</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--===== This template returns the attribute FormOID when called =====-->
	<xsl:template name="FormOID">
		<xsl:value-of select="ancestor::*:FormData/@FormOID"/>
	</xsl:template>
	<!--===== This template returns the attribute ItemGroupOID when called =====-->
	<xsl:template name="ItemGroupOID">
		<xsl:value-of select="ancestor::*:ItemGroupData/@ItemGroupOID"/>
	</xsl:template>
	<!--===== This template returns the attribute StudyOID when called =====-->
	<xsl:template name="ItemGroupRepeatKey">
		<xsl:choose>
			<xsl:when test="ancestor::*:ItemGroupData/@ItemGroupRepeatKey">'<xsl:value-of select="ancestor::*:ItemGroupData/@ItemGroupRepeatKey"/>'</xsl:when>
			<xsl:otherwise>
				<xsl:text>''</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="Value">
		<xsl:choose>
			<xsl:when test="@Value = '---'">
				<xsl:text></xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select='replace(replace(substring(@Value,1,100),"&apos;","&apos;&apos;"),";"," ")'/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
    <xsl:template name="HasValue">
        <xsl:choose>
            <xsl:when test="@Value">
                <xsl:text>1</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>0</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
