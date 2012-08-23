package net.shrine.service

import org.springframework.beans.factory.annotation.Autowired
import net.shrine.broadcaster.dao.AuditDAO
import net.shrine.authorization.QueryAuthorizationService
import org.spin.query.message.identity.IdentityService
import net.shrine.config.ShrineConfig
import org.spin.query.message.agent.SpinAgent
import org.springframework.transaction.annotation.Transactional
import net.shrine.broadcaster.sitemapping.RoutingTableSiteNameMapper
import net.shrine.broadcaster.aggregators.{CarraReadInstanceResultsAggregator, CarraRunQueryAggregator, CarraReadPdoResponseAggregator}
import net.shrine.protocol.{ReadApprovedQueryTopicsRequest, DeleteQueryRequest, RenameQueryRequest, ReadPreviousQueriesRequest, ReadQueryInstancesRequest, ReadQueryDefinitionRequest, ReadInstanceResultsRequest, BroadcastMessage, RunQueryRequest, ReadPdoRequest}
import net.shrine.I2b2ssrUserInfoService

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
class CarranetShrineService(private val auditDao: AuditDAO,
                            private val authorizationService: QueryAuthorizationService,
                            private val identityService: IdentityService,
                            private val shrineConfig: ShrineConfig,
                            private val spinClient: SpinAgent,
                            private val olsURI: String,
                            private val userInfoService: I2b2ssrUserInfoService) extends ShrineService(auditDao, authorizationService, identityService, shrineConfig, spinClient) {


  lazy val mapper = new RoutingTableSiteNameMapper(olsURI)

  override def readPdo(request: ReadPdoRequest) = {
    val info = userInfoService.authorizeRunQueryRequest(request)
    this.executeRequest(request,
      new CarraReadPdoResponseAggregator(mapper, info))
  }

  @Transactional
  override def runQuery(request: RunQueryRequest) = {
    val message = BroadcastMessage(request)
    val info = userInfoService.authorizeRunQueryRequest(request)
    // auditDao.addAuditEntry(new AuditEntry(request.projectId, identity.getDomain, identity.getUsername, request.queryDefinitionXml, request.topicId))
    val aggregator = new CarraRunQueryAggregator(message.masterId.get, request.authn.username, request.projectId,
      request.queryDefinitionXml, message.instanceId.get, mapper, info, true)
    executeRequest(generateIdentity(request.authn), message, aggregator)
  }

  override def readInstanceResults(request: ReadInstanceResultsRequest) = {
    val info = userInfoService.authorizeRunQueryRequest(request)
    executeRequest(request,
      new CarraReadInstanceResultsAggregator(request.instanceId, mapper, info))
  }

  override def readQueryDefinition(request: ReadQueryDefinitionRequest) = {
    super.readQueryDefinition(request)
  }

  override def readQueryInstances(request: ReadQueryInstancesRequest) = {
    super.readQueryInstances(request)
  }

  override def readPreviousQueries(request: ReadPreviousQueriesRequest) = {
    super.readPreviousQueries(request)
  }

  override def renameQuery(request: RenameQueryRequest) = {
    super.renameQuery(request)
  }

  override def deleteQuery(request: DeleteQueryRequest) = {
    super.deleteQuery(request)
  }

  override def readApprovedQueryTopics(request: ReadApprovedQueryTopicsRequest) = {
    super.readApprovedQueryTopics(request)
  }
}