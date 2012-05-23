package edu.chip.carranet.auth.data

import scala.collection.JavaConversions._
import xml.Utility
import org.spin.tools.crypto.signature.Identity
import edu.chip.carranet.Config
import edu.chip.carranet.auth.backend.{TokenUtil, UserPermissions, UserUtil}

/**
 * @author Dave Ortiz
 * @date 3/28/12
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */
class AuthResponse(val id: Identity) {

  def toi2b2XML: String = {
    Utility.trim(
<ns2:response xmlns:ns2="http://www.i2b2.org/xsd/hive/msg/1.1/" xmlns:ns4="http://www.i2b2.org/xsd/cell/pm/1.1/" xmlns:ns3="http://www.i2b2.org/xsd/hive/msg/version/" xmlns:tns="http://ws.pm.i2b2.harvard.edu">
    <message_header>
        <i2b2_version_compatible>1.1</i2b2_version_compatible>
        <hl7_version_compatible>2.4</hl7_version_compatible>
        <sending_application>
            <application_name>PM Cell</application_name>
            <application_version>1.601</application_version>
        </sending_application>
        <sending_facility>
            <facility_name>i2b2 Hive</facility_name>
        </sending_facility>
        <receiving_application>
            <application_name>PM Cell</application_name>
            <application_version>1.5</application_version>
        </receiving_application>
        <receiving_facility>
            <facility_name>i2b2 Hive</facility_name>
        </receiving_facility>
        <datetime_of_message>2012-03-28T17:53:19.704Z</datetime_of_message>
        <message_control_id>
            <message_num>Pv4Jp6VAQ53ibEQ1Gznpv</message_num>
            <instance_num>1</instance_num>
        </message_control_id>
        <processing_id>
            <processing_id>P</processing_id>
            <processing_mode>I</processing_mode>
        </processing_id>
        <accept_acknowledgement_type>AL</accept_acknowledgement_type>
        <application_acknowledgement_type>AL</application_acknowledgement_type>
        <country_code>US</country_code>
        <project_id>undefined</project_id>
    </message_header>
    <response_header>
        <result_status>
            <status type="DONE">PM processing completed</status>
        </result_status>
    </response_header>
    <message_body>
        <ns4:configure>
            <environment>PRODUCTION</environment>
            <helpURL>http://www.i2b2.org</helpURL>
            <user>
                <full_name>i2b2 User</full_name>
                <user_name>{id.getUsername}</user_name>
                <password token_ms_timeout="1800000" is_token="true">{TokenUtil.IdentityToString(id)}</password>
                <domain>{id.getDomain}</domain>
                <is_admin></is_admin>
                {UserUtil.getStudiesFromIdentity(id).map {x =>
                <project id={x}>
                    <name>i2b2 Demo</name>
                    <wiki>http://www.i2b2.org</wiki>
                    <role>USER</role>
                    {if(UserUtil.getRolesFromIdentity(id).contains(UserPermissions.pdo)) <role>DATA_AGG</role>}
                    <role>DATA_OBFSC</role>
                    <role>EDITOR</role>
                </project>}
                }
            </user>
            <domain_name>HarvardDemo</domain_name>
            <domain_id>i2b2</domain_id>
            <active>true</active>
            <cell_datas>
                <cell_data id="CRC">
                    <name>Data Repository</name>
                    <url>{Config.crcURI}</url>
                    <project_path>/</project_path>
                    <method>REST</method>
                    <can_override>true</can_override>
                </cell_data>
                <cell_data id="ONT">
                    <name>Ontology Cell</name>
                    <url>{Config.ontologyURI}</url>
                    <project_path>/</project_path>
                    <method>REST</method>
                    <can_override>true</can_override>
                </cell_data>
            </cell_datas>
            <global_data />
        </ns4:configure>
    </message_body>
</ns2:response>).toString()

  }

}