package net.shrine.broadcaster.aggregators

import collection.JavaConversions._
import net.shrine.broadcaster.sitemapping.SiteNameMapper
import org.bouncycastle.jce.X509Principal
import org.bouncycastle.asn1.x509.X509Name
import net.shrine.data.UserInfoResponse

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

  protected def userInfo: UserInfoResponse

  private def canIdentifyAllSites = userInfo.canIdentify

  protected def getHomeSites = userInfo.homeSites

  protected def canIdenitfySite(siteDN: String): Boolean = {
    val principal: X509Principal = new X509Principal(siteDN)
    val values = principal.getValues(X509Name.CN)
    val cn: String = values.get(0).asInstanceOf[String]

    if(canIdentifyAllSites || getHomeSites.contains(cn)) {
      true
    }
    else {
      false
    }
  }
}

object DataTagging {
  val UNIDENTIFIED: String = "Unidentified"
}