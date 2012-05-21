package edu.chip.carranet.auth.resource.impl

import org.spin.tools.crypto.signature.Identity
import org.springframework.transaction.annotation.Transactional
import edu.chip.carranet.auth.data.AuthResponse
import org.springframework.beans.factory.annotation.Autowired
import java.io.StringReader
import edu.chip.carranet.auth.backend.IAuthenticator
import xml.XML

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


class I2B2PmResourceImpl @Autowired()(val authenticator : IAuthenticator)  {

  @Transactional(readOnly = true)
  def authorizeI2B2(message: String): String = {
    val xml = XML.load(new StringReader(message))
    val userName: String = (xml \ "i2b2:request" \ "message_header" \ "security" \ "username").text
    val password: String = (xml \ "i2b2:request" \ "message_header" \ "security" \ "password").text
    val domain: String = (xml \ "i2b2:equest" \ "message_header" \ "security" \ "domain").text
    val id: Identity = authenticator.authenticate(userName, password)
    new AuthResponse(id).toi2b2XML
  }

}