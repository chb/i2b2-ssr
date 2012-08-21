package net.shrine.broadcaster.aggregators

import org.spin.query.message.headers.Result
import net.shrine.broadcaster.sitemapping.SiteNameMapper
import net.shrine.aggregation.{SpinResultEntry, ReadInstanceResultsAggregator}
import net.shrine.protocol.{ReadInstanceResultsResponse, QueryResult}
import net.shrine.data.UserInfoResponse

/**
 * @author Dave Ortiz
 * @date 8/25/11
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */
class CarraReadInstanceResultsAggregator(
    private val instanceId: Long,
    private val siteNameMapper: SiteNameMapper,
    private val userInfoResponse: UserInfoResponse) extends ReadInstanceResultsAggregator(instanceId, true)
with DataTagging {

  protected def mapper = siteNameMapper

  protected def userInfo = userInfoResponse

  override def aggregate(spinCacheResults: Seq[SpinResultEntry]) = {

    val results = super.aggregate(spinCacheResults).asInstanceOf[ReadInstanceResultsResponse]
    results.withResults(results.results.filter { x =>
      !x.description.getOrElse("").equalsIgnoreCase(DataTagging.UNIDENTIFIED) ||
          getHomeSites.contains(x.description)
    })
  }

  override protected def transformResult(n: QueryResult, metaData: Result) = {

    if(canIdenitfySite(metaData.getOrigin.getName)) {
      n.withDescription(mapper.getSiteIdentifierFromDN(metaData.getOrigin.getName))
    }
    else {
      n.withDescription(DataTagging.UNIDENTIFIED)
    }
  }
}