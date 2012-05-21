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
import com.yammer.metrics.reporting.GraphiteReporter
import java.util.concurrent.TimeUnit
import net.shrine.protocol.{ReadApprovedQueryTopicsRequest, DeleteQueryRequest, RenameQueryRequest, ReadPreviousQueriesRequest, ReadQueryInstancesRequest, ReadQueryDefinitionRequest, ReadInstanceResultsRequest, BroadcastMessage, RunQueryRequest, ReadPdoRequest}
import com.yammer.metrics.Instrumented
import net.shrine.broadcaster.dao.hibernate.AuditEntry

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
    private val graphiteHost: String) extends ShrineService(auditDao, authorizationService, identityService, shrineConfig, spinClient)
with Instrumented {

  // instrumentation
  GraphiteReporter.enable(1, TimeUnit.MINUTES, graphiteHost, 2003)
  protected val runQueryTimer = metrics.timer("requests.runQuery")
  protected val readQueryDefinitionTimer = metrics.timer("requests.readQueryDefinition")
  protected val readPdoTimer = metrics.timer("requests.readPdo")
  protected val readInstanceResultsTimer = metrics.timer("requests.readInstanceResult")
  protected val readQueryInstancesTimer = metrics.timer("requests.readQueryInstances")
  protected val readPreviousQueriesTimer = metrics.timer("requests.readPreviousQueries")
  protected val renameQueryTimer = metrics.timer("requests.renameQuery")
  protected val deleteQueryTimer = metrics.timer("requests.renameQuery")
  protected val readApprovedQueryTopicsTimer = metrics.timer("requests.readApprovedQueryTopics")

  lazy val mapper = new RoutingTableSiteNameMapper(olsURI);

  override def readPdo(request: ReadPdoRequest) = readPdoTimer.time {

    this.executeRequest(request,
      new CarraReadPdoResponseAggregator(mapper, generateIdentity(request.authn)))
  }

  @Transactional
  override def runQuery(request: RunQueryRequest) = runQueryTimer.time {
    val message = BroadcastMessage(request)
    val identity = generateIdentity(request.authn)
    auditDao.addAuditEntry(new AuditEntry(request.projectId, identity.getDomain, identity.getUsername, request.queryDefinitionXml, request.topicId))
    val aggregator = new CarraRunQueryAggregator(message.masterId.get, request.authn.username, request.projectId,
      request.queryDefinitionXml, message.instanceId.get, mapper, identity, true)

    executeRequest(identity, message, aggregator)
  }

  override def readInstanceResults(request: ReadInstanceResultsRequest) = readInstanceResultsTimer.time {
    executeRequest(request,
      new CarraReadInstanceResultsAggregator(request.instanceId, mapper, generateIdentity(request.authn)))
  }

  override def readQueryDefinition(request: ReadQueryDefinitionRequest) = readQueryDefinitionTimer.time {
    super.readQueryDefinition(request)
  }

  override def readQueryInstances(request: ReadQueryInstancesRequest) = readQueryInstancesTimer.time {
    super.readQueryInstances(request)
  }

  override def readPreviousQueries(request: ReadPreviousQueriesRequest) = readPreviousQueriesTimer.time {
    super.readPreviousQueries(request)
  }

  override def renameQuery(request: RenameQueryRequest) = renameQueryTimer.time {
    super.renameQuery(request)
  }

  override def deleteQuery(request: DeleteQueryRequest) = deleteQueryTimer.time {
    super.deleteQuery(request)
  }

  override def readApprovedQueryTopics(request: ReadApprovedQueryTopicsRequest) = readApprovedQueryTopicsTimer.time {
    super.readApprovedQueryTopics(request)
  }
}