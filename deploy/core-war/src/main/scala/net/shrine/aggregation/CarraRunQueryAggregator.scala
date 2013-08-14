package net.shrine.aggregation

import net.shrine.broadcaster.sitemapping.SiteNameMapper
import net.shrine.protocol.{ErrorResponse, AggregatedRunQueryResponse, RunQueryResponse, QueryResult}
import net.shrine.data.UserInfoResponse
import net.shrine.protocol.query.QueryDefinition
import org.spin.message.Result

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
class CarraRunQueryAggregator(
                               private val cQueryId: Long,
                               private val cUserId: String,
                               private val cGroupId: String,
                               private val cRequestDef: QueryDefinition,
                               private val map: SiteNameMapper,
                               private val userInfoResponse: UserInfoResponse,
                               private val cDoAggregation: Boolean) extends RunQueryAggregator(cQueryId, cUserId, cGroupId, cRequestDef, cDoAggregation)
with DataTagging {

  protected def userInfo = userInfoResponse

  protected def mapper = map

  override def withQueryId(qId: Long) = new CarraRunQueryAggregator(qId, userId, groupId,
    requestQueryDefinition, map, userInfoResponse,
    cDoAggregation )


  override def aggregate(spinCacheResults: Seq[SpinResultEntry], errors: Seq[ErrorResponse]) = {
    val response: AggregatedRunQueryResponse = super.aggregate(spinCacheResults, errors).asInstanceOf[AggregatedRunQueryResponse]

    //Filter out results that aren't homesites and
    //aren't identified

    response.withResults(response.results.filter {
      x =>
        !x.description.getOrElse("").equalsIgnoreCase(DataTagging.UNIDENTIFIED)

    })
  }

  override protected def transformResult(n: QueryResult, metaData: Result): QueryResult = {

    if (canIdenitfySite(metaData.getOrigin.getName)) {
      n.withDescription(mapper.getSiteIdentifierFromDN(metaData.getOrigin.getName))
    }
    else {
      n.withDescription(DataTagging.UNIDENTIFIED)
    }
  }
}
