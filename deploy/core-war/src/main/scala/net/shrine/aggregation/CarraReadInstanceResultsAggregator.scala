package net.shrine.aggregation

import net.shrine.broadcaster.sitemapping.SiteNameMapper
import net.shrine.protocol.{AggregatedReadInstanceResultsResponse, ErrorResponse, ReadInstanceResultsResponse, QueryResult}
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
class CarraReadInstanceResultsAggregator(private val instanceId: Long,
                                         private val siteNameMapper: SiteNameMapper,
                                         private val userInfoResponse: UserInfoResponse) extends ReadInstanceResultsAggregator(instanceId, true) with DataTagging {

  protected def mapper = siteNameMapper

  protected def userInfo = userInfoResponse

  override def aggregate(spinCacheResults: Seq[SpinResultEntry], errors: Seq[ErrorResponse]) = {

    val aggregation = super.aggregate(spinCacheResults, errors).asInstanceOf[AggregatedReadInstanceResultsResponse]

    //Filter out "Unidentified" results from the final list
    aggregation.withResults(aggregation.results.filter {
      x =>
        x != null && (!x.description.getOrElse("").equalsIgnoreCase(DataTagging.UNIDENTIFIED) ||
          getHomeSites.contains(x.description))
    })


  }

  override protected[aggregation] def transformResult(n: net.shrine.protocol.QueryResult,metaData: org.spin.message.Result):  net.shrine.protocol.QueryResult = {

    if (canIdenitfySite(metaData.getOrigin.getName)) {
      n.withDescription(mapper.getSiteIdentifierFromDN(metaData.getOrigin.getName))
    }
    else {
      n.withDescription(DataTagging.UNIDENTIFIED)
    }
  }
}