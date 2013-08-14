package net.shrine.service

import org.springframework.beans.factory.annotation.Autowired
import net.shrine.authorization.{I2b2ssrUserInfoService, AuthorizationException, QueryAuthorizationService}


import net.shrine.broadcaster.sitemapping.RoutingTableSiteNameMapper
import net.shrine.protocol.{ShrineRequest, ReadApprovedQueryTopicsRequest, DeleteQueryRequest, RenameQueryRequest, ReadPreviousQueriesRequest, ReadQueryInstancesRequest, ReadQueryDefinitionRequest, ReadInstanceResultsRequest, BroadcastMessage, RunQueryRequest, ReadPdoRequest}
import net.shrine.data.UserInfoResponse
import org.spin.tools.crypto.signature.Identity
import net.shrine.broadcaster.BroadcastService
import net.shrine.broadcaster.dao.AuditDao
import net.shrine.aggregation.{CarraReadPdoResponseAggregator, CarraReadInstanceResultsAggregator, CarraRunQueryAggregator}
import org.spin.identity.{IdentityServiceException, IdentityService}
import org.spin.tools.crypto.XMLSignatureUtil
import scala.concurrent.duration._



/**
 * @author David Ortiz
 * @date 8/11/11
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */


@Autowired
class CarranetShrineService(private val auditDao: AuditDao,
                            private val authorizationService: QueryAuthorizationService,
                            private val identityService: IdentityService,
                            private val broadcastService: BroadcastService,
                            private val olsURI: String,
                            private val userInfoService: I2b2ssrUserInfoService) extends ShrineService(auditDao, authorizationService, true, broadcastService, 30 minutes) {


  import broadcastService.sendAndAggregate
  lazy val mapper = new RoutingTableSiteNameMapper(olsURI)

  /**
   * We're generating identities from UserInfo Responses
   *
   * @param userInfo
   */
  def generateIdentity(request: ShrineRequest, userInfo: UserInfoResponse): Identity = {
    if (userInfo == null) {
      throw new AuthorizationException("No user info response")
    }
    try {
      XMLSignatureUtil.getDefaultInstance.sign(new Identity(request.authn.domain, userInfo.userName))
    }
    catch {
      case e: Exception => {
        throw new IdentityServiceException("Not authorized", e)
      }
    }

  }

  override def readPdo(request: ReadPdoRequest, shouldBroadcast: Boolean) = {
    val info = userInfoService.authorizeRunQueryRequest(request)
    if (info.canPdo) {
      waitFor(sendAndAggregate(request, new CarraReadPdoResponseAggregator(mapper, info), shouldBroadcast))
    }
    else {
      throw new AuthorizationException("User can't perform this query on this project")
    }
  }


  override def runQuery(request: RunQueryRequest, shouldBroadcast: Boolean) = {
    val message = BroadcastMessage(request)
    val info = userInfoService.authorizeRunQueryRequest(request)
    val identity = generateIdentity(request, info)
    auditDao.addAuditEntry(request.projectId, identity.getDomain, identity.getUsername, request.toI2b2String, request.topicId)
    val aggregator = new CarraRunQueryAggregator(message.requestId, request.authn.username, request.projectId,
      request.queryDefinition, mapper, info, true)
    waitFor(sendAndAggregate(request, aggregator, shouldBroadcast))
  }


  override def readInstanceResults(request: ReadInstanceResultsRequest, shouldBroadcast: Boolean) = {
    val info = userInfoService.authorizeRunQueryRequest(request)
    waitFor(sendAndAggregate(request,
      new CarraReadInstanceResultsAggregator(request.shrineNetworkQueryId, mapper, info), true))
  }

  override def readQueryDefinition(request: ReadQueryDefinitionRequest, shouldBroadcast: Boolean) = {
    super.readQueryDefinition(request)
  }

  override def readQueryInstances(request: ReadQueryInstancesRequest, shouldBroadcast: Boolean) = {
    super.readQueryInstances(request)
  }

  override def readPreviousQueries(request: ReadPreviousQueriesRequest, shouldBroadcast: Boolean) = {
    super.readPreviousQueries(request)
  }

  override def renameQuery(request: RenameQueryRequest, shouldBroadcast: Boolean) = {
    super.renameQuery(request)
  }

  override def deleteQuery(request: DeleteQueryRequest, shouldBroadcast: Boolean) = {
    super.deleteQuery(request)
  }

  override def readApprovedQueryTopics(request: ReadApprovedQueryTopicsRequest, shouldBroadcast: Boolean) = {
    super.readApprovedQueryTopics(request)
  }

}