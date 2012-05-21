package edu.chip.carranet.auth.resource.impl

import collection.JavaConversions._
import edu.chip.carranet.auth.backend.IAuthenticator
import org.spin.tools.crypto.signature.Identity
import xml.{XML, Utility}
import org.scalatest.matchers.ShouldMatchers
import org.junit.{Ignore, Test}
import edu.chip.carranet.PM


/**
 * @author Dave Ortiz
 * @date 4/2/12
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */


class TestAuthenticator(val assertions: Seq[String]) extends IAuthenticator {
  def getAuthCapabilities = null

  def changeUserPassword(id: Identity, username: String, oldPassword: String, newPassword: String) {}

  def findUser(username: String) = null

  def authenticate(username: String, password: String) = {
    new Identity(username, password, assertions)
  }
}


@Ignore
class TestI2B2PmResourceImpl extends ShouldMatchers{


  @Test
  def testPlainResponse() {
    val authenticator = new TestAuthenticator(List("study:hi", "role:pdo"))
    val impl = new PM(authenticator)
    val response = impl.pmAuthorizationCall(TestI2B2PmResourceImpl.message.toString())
  }

  @Test
  def testNumberOfProjects {
    val authenticator = new TestAuthenticator(List("study:hi","study:hi2","study:hi3", "role:pdo"))
    val impl = new PM(authenticator)
    val response = impl.pmAuthorizationCall(TestI2B2PmResourceImpl.message.toString())
    val xml = XML.loadString(response.getEntity.toString)
    (xml \\ "project").size should equal(3)

  }


}

object TestI2B2PmResourceImpl {
  val message = Utility.trim(
    <i2b2:request xmlns:i2b2="http://www.i2b2.org/xsd/hive/msg/1.1/"
                  xmlns:pm="http://www.i2b2.org/xsd/cell/pm/1.1/">
      <message_header>

        <i2b2_version_compatible>1.1</i2b2_version_compatible>
        <hl7_version_compatible>2.4</hl7_version_compatible>
        <sending_application>
          <application_name>i2b2 Project Management</application_name>
          <application_version>1.1</application_version>
        </sending_application>
        <sending_facility>
          <facility_name>i2b2 Hive</facility_name>
        </sending_facility>
        <receiving_application>
          <application_name>Project Management Cell</application_name>
          <application_version>1.1</application_version>
        </receiving_application>
        <receiving_facility>
          <facility_name>i2b2 Hive</facility_name>
        </receiving_facility>
        <datetime_of_message>2010-04-26T15:08:43-04:00</datetime_of_message>
        <security>
          <domain>HarvardDemo</domain>
          <username>admin</username>
          <password>admin</password>
        </security>
        <message_control_id>
          <message_num>bjh550i6kJlq0BXkhJ397</message_num>
          <instance_num>0</instance_num>
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
      <request_header>
        <result_waittime_ms>180000</result_waittime_ms>
      </request_header>
      <message_body>
        <pm:get_user_configuration>
          <project>undefined</project>
        </pm:get_user_configuration>
      </message_body>
    </i2b2:request>)

}