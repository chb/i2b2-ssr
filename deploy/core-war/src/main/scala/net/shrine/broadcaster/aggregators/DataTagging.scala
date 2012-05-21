package net.shrine.broadcaster.aggregators

import org.spin.tools.crypto.signature.Identity
import collection.JavaConversions._
import net.shrine.broadcaster.sitemapping.SiteNameMapper
import org.bouncycastle.jce.X509Principal
import org.bouncycastle.asn1.x509.X509Name

/**
 * @author Dave Ortiz
 * @date 8/18/11
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */
trait DataTagging {


  protected def mapper: SiteNameMapper

  protected def identity: Identity

  private def isAdmin = getRoles.contains("admin")

  private def canIdentifyAllSites = getRoles.contains("sitebreakdown")

  protected def getHomeSites = getValueWithPrefix("homesite")

  protected def getRoles = getValueWithPrefix("role")

  protected def getStudies = getValueWithPrefix("study")


  protected def canIdenitfySite(siteDN: String): Boolean = {
    val principal: X509Principal = new X509Principal(siteDN)
    val values = principal.getValues(X509Name.CN)
    val cn: String = values.get(0).asInstanceOf[String]

    if(canIdentifyAllSites || getHomeSites.contains(cn)) {
      true;
    }
    else {
      false
    }
  }


  private def getValueWithPrefix(prefix: String): Seq[String] = {
    //this line is straight cash homie
    for(val s <- asScalaBuffer(identity.getAssertion) if s.startsWith(prefix + ":") && s.split(":").length > 0)
    yield s.split(":")(1)
  }

}

object DataTagging {
  val UNIDENTIFIED: String = "Unidentified"
}