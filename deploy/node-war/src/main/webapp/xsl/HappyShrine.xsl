<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" version="1.0" encoding="iso-8859-1"
                indent="yes"/>
    <xsl:template match="/HappyShrine">
        <html>
            <head>
                <title>Happy SHRINE!</title>
                <link rel="stylesheet" href="css/style.css" type="text/css"/>
            </head>
            <body>
                <center>
                    <table>
                        <xsl:apply-templates select="report"/>
                    </table>
                </center>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="report">
        <xsl:param name="class">
            <xsl:choose>
                <xsl:when test="exception">error</xsl:when>
                <xsl:otherwise>okay</xsl:otherwise>
            </xsl:choose>
        </xsl:param>
        <tr>
            <xsl:attribute name="class">
                <xsl:value-of select="$class"/>
            </xsl:attribute>
            <th colspan="2">
                <xsl:value-of select="title"/>
            </th>
        </tr>
        <xsl:if test="exception">
            <tr class="error">
                <td class="error" colspan="2">
                    <b>Exception :</b>
                    <xsl:value-of select="exception"/>
                </td>
            </tr>
        </xsl:if>
        <xsl:apply-templates select="entry">
            <xsl:with-param name="class">
                <xsl:value-of select="$class"/>
            </xsl:with-param>
        </xsl:apply-templates>
        <tr class="fill">
            <td colspan="2" class="fill">&#160;</td>
        </tr>
    </xsl:template>
    <xsl:template match="entry">
        <xsl:param name="class"/>
        <tr>
            <xsl:attribute name="class">
                <xsl:value-of select="$class"/>
            </xsl:attribute>
            <td>
                <xsl:attribute name="class">
                    <xsl:value-of select="$class"/>
                </xsl:attribute>
                <xsl:value-of select="label"/>
            </td>
            <td>
                <xsl:apply-templates select="value"/>
            </td>
        </tr>
    </xsl:template>
    <xsl:template match="value">
        <xsl:value-of select="."/>
        <br/>
    </xsl:template>
</xsl:stylesheet> 
