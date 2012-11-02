package net.shrine.service

import org.springframework.beans.factory.annotation.Autowired
import net.shrine.broadcaster.dao.AuditDAO
import net.shrine.authorization.{I2b2ssrUserInfoService, AuthorizationException, QueryAuthorizationService}
import org.spin.query.message.identity.{IdentityServiceException, IdentityService}
import net.shrine.config.ShrineConfig
import org.spin.query.message.agent.SpinAgent
import org.springframework.transaction.annotation.Transactional
import net.shrine.broadcaster.sitemapping.RoutingTableSiteNameMapper
import net.shrine.broadcaster.aggregators.{StatusAggregator, CarraReadInstanceResultsAggregator, CarraRunQueryAggregator, CarraReadPdoResponseAggregator}
import net.shrine.protocol.{ShrineRequest, ResultOutputType, Credential, AuthenticationInfo, ReadApprovedQueryTopicsRequest, DeleteQueryRequest, RenameQueryRequest, ReadPreviousQueriesRequest, ReadQueryInstancesRequest, ReadQueryDefinitionRequest, ReadInstanceResultsRequest, BroadcastMessage, RunQueryRequest, ReadPdoRequest}
import net.shrine.broadcaster.dao.hibernate.AuditEntry
import net.shrine.data.UserInfoResponse
import org.spin.tools.crypto.signature.{XMLSignatureUtil, Identity}

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
    private val userInfoService: I2b2ssrUserInfoService) extends ShrineService(auditDao, authorizationService, identityService, shrineConfig, spinClient) with StatusRequestHandler {


  private val query: String = """
      <ns4:query_definition xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                        xmlns:ns8="http://www.i2b2.org/xsd/hive/msg/result/1.1/"
                        xmlns:ns6="http://www.i2b2.org/xsd/cell/crc/psm/analysisdefinition/1.1/"
                        xmlns:ns5="http://www.i2b2.org/xsd/cell/crc/psm/querydefinition/1.1/"
                        xmlns:imsg="http://www.i2b2.org/xsd/hive/msg/1.1/"
                        xmlns:ns3="http://www.i2b2.org/xsd/hive/plugin/"
                        xmlns:ns4="http://www.i2b2.org/xsd/cell/crc/psm/1.1/"
                        xmlns:ns2="http://www.i2b2.org/xsd/hive/pdo/1.1/">
                  <query_name>CARRAnet </query_name>
                  <query_description>Status Query</query_description>
                  <query_timing>ANY</query_timing>
                  <specificity_scale>0</specificity_scale>
                  <panel name="Panel_31">
                      <panel_number>1</panel_number>
                      <panel_accuracy_scale>0</panel_accuracy_scale>
                      <invert>0</invert>
                      <total_item_occurrences>1</total_item_occurrences>
                      <item>
                          <hlevel>1</hlevel>
                          <item_name>root</item_name>
                          <item_key>\</item_key>
                          <dim_tablename>concept_dimension</dim_tablename>
                          <dim_columnname>concept_path</dim_columnname>
                          <dim_dimcode>\</dim_dimcode>
                          <dim_columndatatype>T</dim_columndatatype>
                          <facttablecolumn>concept_cd</facttablecolumn>
                          <item_is_synonym>false</item_is_synonym>
                      </item>
                  </panel>
              </ns4:query_definition>
                              """

  lazy val mapper = new RoutingTableSiteNameMapper(olsURI)

  /**
   * We're generating identities from UserInfo Responses
   *
   * @param userInfo
   */
  def generateIdentity(request: ShrineRequest, userInfo: UserInfoResponse): Identity =  {
    if(userInfo == null) {
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

  override def readPdo(request: ReadPdoRequest) = {
    val info = userInfoService.authorizeRunQueryRequest(request)
    if(info.canPdo) {
      this.executeRequest(request, new CarraReadPdoResponseAggregator(mapper, info))
    }
    else {
      throw new AuthorizationException("User can't perform this query on this project")
    }
  }


  def getStatus(userName: String, password: String, peerGroup: String) = {
    val request = new RunQueryRequest(peerGroup, 60000,
      new AuthenticationInfo(peerGroup, userName, new Credential(password, true)),
      "noTopic",
      Set[ResultOutputType](ResultOutputType.PATIENT_COUNT_XML), query)

    val message = BroadcastMessage(request)
    val identity : Identity = generateIdentity(request, userInfoService.authorizeRunQueryRequest(request))
    auditDao.addAuditEntry(new AuditEntry(request.projectId, identity.getDomain, identity.getUsername, request.queryDefinitionXml, request.topicId))
    val aggregator = new StatusAggregator(mapper)
    executeRequest(identity, message, aggregator)
  }

  @Transactional
  override def runQuery(request: RunQueryRequest) = {
    val message = BroadcastMessage(request)
    val info = userInfoService.authorizeRunQueryRequest(request)
    val identity = generateIdentity(request, info)
    auditDao.addAuditEntry(new AuditEntry(request.projectId, identity.getDomain, identity.getUsername, request.queryDefinitionXml, request.topicId))
    val aggregator = new CarraRunQueryAggregator(message.masterId.get, request.authn.username, request.projectId,
      request.queryDefinitionXml, message.instanceId.get, mapper, info, true)
    executeRequest(identity, message, aggregator)
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