<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="xml" doctype-system="topic.dita" doctype-public="-//OASIS//DTD DITA Topic//EN" indent="yes"/>
    <xsl:template match="JSON">
        <topic id="reusables">
            <title>DITA Equivalent of JSON</title>
            <body>
                    <xsl:for-each select="*">
                <p>
                    <ph id="{local-name()}"><xsl:value-of select="."/></ph>    
                </p>
                    </xsl:for-each>
            </body>
        </topic>
    </xsl:template>
</xsl:stylesheet>