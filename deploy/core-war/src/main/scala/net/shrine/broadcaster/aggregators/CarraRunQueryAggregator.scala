package net.shrine.broadcaster.aggregators

import net.shrine.broadcaster.sitemapping.SiteNameMapper
import org.spin.query.message.headers.Result
import net.shrine.aggregation.{SpinResultEntry, RunQueryAggregator}
import net.shrine.protocol.{RunQueryResponse, QueryResult}
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
class CarraRunQueryAggregator(
    private val queryId: Long,
    private val userId: String,
    private val groupId: String,
    private val requestXml: String,
    private val queryInstance: Long,
    private val map: SiteNameMapper,
    private val userInfoResponse: UserInfoResponse,
    private val doAggregation: Boolean) extends RunQueryAggregator(queryId, userId, groupId, requestXml, queryInstance, doAggregation)
with DataTagging  {

  protected def userInfo = userInfoResponse
  protected def mapper = map

  override def aggregate(spinCacheResults: Seq[SpinResultEntry]) = {
      val response: RunQueryResponse = super.aggregate(spinCacheResults).asInstanceOf[RunQueryResponse]

      //Filter out results that aren't homesites and
      //aren't identified

      response.withResults(response.results.filter {x =>
        !x.description.getOrElse("").equalsIgnoreCase(DataTagging.UNIDENTIFIED)

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
