<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="JSON">
        <topic xmlns:ditaarch="http://dita.oasis-open.org/architecture/2005/" id="reusables" ditaarch:DITAArchVersion="1.3" domains="(topic abbrev-d)                            a(props deliveryTarget)                            (topic equation-d)                            (topic hazard-d)                            (topic hi-d)                            (topic indexing-d)                            (topic markup-d)                            (topic mathml-d)                            (topic pr-d)                            (topic relmgmt-d)                            (topic sw-d)                            (topic svg-d)                            (topic ui-d)                            (topic ut-d)                            (topic markup-d xml-d)   " class="- topic/topic ">
            <title class="- topic/title ">DITA Equivalent of JSON</title>
            <body class="- topic/body ">
                <xsl:for-each select="*">
                    <p class="- topic/p ">
                        <ph id="{local-name()}" class="- topic/ph "><xsl:value-of select="."/></ph>    
                    </p>
                </xsl:for-each>
            </body>
        </topic>
    </xsl:template>
</xsl:stylesheet>