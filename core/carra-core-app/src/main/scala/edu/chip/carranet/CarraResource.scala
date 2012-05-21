package edu.chip.carranet

import auth.backend.{IAuthenticator, BackendUtil, User, TokenUtil}
import auth.data.AuthResponse
import auth.resource.impl.{TokenResourceImpl, UserManagementResourceImpl, UserResourceImpl}
import ExternalExceptions.ResourceNotFoundError
import jaxb._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.spin.tools.crypto.signature.Identity
import javax.ws.rs._
import core.Response
import org.spin.tools.JAXBUtils
import java.net.URI
import overlay.persistance.{PersistedResource, ResourceStore}
import scala.collection.JavaConversions._
import org.spin.tools.config.{PeerGroupConfig, EndpointType, EndpointConfig, RoutingTableConfig}

import CarraResourceUtil._
import collection.mutable.{HashMap, Buffer, ArrayBuffer}
import xml.XML
import java.io.StringReader


@Path("/users")
@Component
class UserResource @Autowired()(val userResource: UserResourceImpl,
                                val userManagementResource: UserManagementResourceImpl,
                                val tokenResource: TokenResourceImpl) {


  @Path("/{user}/authorization_token")
  @GET
  def getAuthToken(@HeaderParam("username") username: String,
                   @HeaderParam("password") password: String): Response = {
    val token: String = tokenResource.getToken(new User(username, password))
    Response.ok(token).build()
  }

  @GET
  def getUsers(@HeaderParam("x-carra-auth") authString: String): Response = {
    Response.ok(userResource.getUsers(identity(authString))).build()
  }


  @Consumes(Array("text/xml", "text/plain"))
  @POST
  def newUser(@HeaderParam("x-carra-auth") authString: String,
              body: String): Response = {
    val carraUser = JAXBUtils.unmarshal(body, classOf[CreateUserMessage])
    userManagementResource.createUser(carraUser, identity(authString))
    Response.created(new URI("/" + carraUser.getUserName)).build()

  }

  @Path("/{username}")
  @GET
  def getSpecificUser(@HeaderParam("x-carra-auth") authString: String,
                      @PathParam("username") username: String): Response = {
    Response.ok(userManagementResource.getUser(username, identity(authString))).build()
  }

  @Path("/{user}")
  @Consumes(Array("text/xml", "text/plain"))
  @PUT
  def updateUser(@HeaderParam("x-carra-auth") authString: String,
                 @PathParam("user") user: String,
                 content: String): Response = {
    val carraUser = JAXBUtils.unmarshal(content, classOf[CarraUser])
    userManagementResource.updateUser(identity(authString), user, carraUser)
    Response.ok().build()
  }

  @Path("/{user}")
  @Consumes(Array("text/xml"))
  @DELETE
  def deleteUser(@HeaderParam("x-carra-auth") authString: String,
                 @PathParam("user") user: String): Response = {

    userManagementResource.deleteUser(user, identity(authString))
    //the default Response.ok returns 201 on put....stupid
    Response.noContent().build()

  }


}


@Path("/machines")
@Component
class MachineResource @Autowired()(val resourceStore: ResourceStore) {

  @GET
  def getMachines(@HeaderParam("x-carra-auth") authString: String): Response = {
    val machineList = new Machines()
    val machines: Seq[MachineEntry] = resourceStore.getAll("/machines/") map {
      x => CarraDataUtil.machineFromBytes(x.getPayload).getMachineEntry
    }
    machineList.getMachineList.addAll(asJavaCollection[MachineEntry](machines))
    Response.ok(JAXBUtils.marshalToString(machineList)).build()
  }


  @POST
  def createMachine(@HeaderParam("x-carra-auth") authString: String,
                    post: String): Response = {

    val id = identity(authString)
    BackendUtil.verifyTokenAndAdmin(id)
    val machine: Machine = JAXBUtils.unmarshal(post, classOf[Machine])
    val identifier = "/machines/" + machine.getMachineEntry.getMachineId

    resourceStore.create(identifier, new PersistedResource("", CarraDataUtil.toBytes(machine)))
    Response.created(new URI(machine.getMachineEntry.getMachineId)).build()
  }

  @Path("/{machine}")
  @DELETE
  def deleteMachine(@HeaderParam("x-carra-auth") authString: String,
                    @PathParam("machine") machine: String): Response = {
    BackendUtil.verifyTokenAndAdmin(identity(authString))

    if (resourceStore.read("/machines/" + machine) == null) {
      throw new ResourceNotFoundError()
    }
    resourceStore.delete("/machines/" + machine)
    Response.noContent().build()


  }

  @Path("/{machine}")
  @GET
  def getMachine(@HeaderParam("x-carra-auth") authString: String,
                 @PathParam("machine") machine: String): Response = {

    BackendUtil.verifyToken(identity(authString))

    val pr = resourceStore.read("/machines/" + machine)
    if (pr == null) throw new ResourceNotFoundError()
    Response.ok(JAXBUtils.marshalToString(CarraDataUtil.machineFromBytes(pr.getPayload))).build

  }


  @Path("/{machine}")
  @Consumes(Array("text/xml", "text/plain"))
  @PUT
  def updateMachine(@HeaderParam("x-carra-auth") authString: String,
                    @PathParam("machine") machine: String,
                    body: String): Response = {


    BackendUtil.verifyTokenAndAdmin(identity(authString))
    val machineEntry = JAXBUtils.unmarshal(body, classOf[Machine])
    val path = "/machines/" + machine

    if (resourceStore.read(path) == null) {
      throw new ResourceNotFoundError()
    }

    resourceStore.update(path, new PersistedResource(null, CarraDataUtil.toBytes(machineEntry)))

    Response.ok.build

  }


}


@Path("/studies")
@Component
class StudyResource @Autowired()(val resourceStore: ResourceStore) {

  @POST
  @Consumes(Array("text/xml", "text/plain"))
  def createNewStudy(@HeaderParam("x-carra-auth") authString: String,
                     post: String): Response = {
    BackendUtil.verifyTokenAndAdmin(identity(authString))
    val study = JAXBUtils.unmarshal(post, classOf[Study])
    val path = "/studies/" + study.getStudyEntry.getStudyId
    resourceStore.create(path, new PersistedResource("", CarraDataUtil.toBytes(study)))
    Response.created(new URI(study.getStudyEntry.getStudyId)).build
  }


  @GET
  def getStudies(@HeaderParam("x-carra-auth") authString: String): Response = {
    BackendUtil.verifyTokenAndAdmin(identity(authString))
    val studies = new Studies()
    val allStudies = resourceStore.getAll("/studies/") map {
      x => CarraDataUtil.studyFromBytes(x.getPayload).getStudyEntry
    }
    studies.getStudyList.addAll(asJavaCollection[StudyEntry](allStudies))
    Response.ok(JAXBUtils.marshalToString(studies)).build
  }

  @Path("/{study}")
  @GET
  def getStudy(@HeaderParam("x-carra-auth") authString: String,
               @PathParam("study") study: String): Response = {

    BackendUtil.verifyTokenAndAdmin(identity(authString))
    val pr = resourceStore.read("/studies/" + study)
    if (pr == null) throw new ResourceNotFoundError()
    Response.ok(JAXBUtils.marshalToString(CarraDataUtil.studyFromBytes(pr.getPayload))).build

  }

  @Path("/{study}")
  @DELETE
  def deleteStudy(@HeaderParam("x-carra-auth") authString: String,
                  @PathParam("study") study: String): Response = {


    BackendUtil.verifyTokenAndAdmin(identity(authString))
    val path = "/studies/" + study

    if (resourceStore.read(path) == null) {
      throw new ResourceNotFoundError()
    }

    resourceStore.delete(path)
    Response.noContent().build()

  }

  @Path("/{study}")
  @Consumes(Array("text/xml", "text/plain"))
  @PUT
  def updateStudy(@HeaderParam("x-carra-auth") authString: String,
                  @PathParam("study") study: String,
                  body: String): Response = {

    BackendUtil.verifyTokenAndAdmin(identity(authString))
    val studyEntry = JAXBUtils.unmarshal(body, classOf[Study])
    val path = "/studies/" + study

    if (resourceStore.read(path) == null) {
      throw new ResourceNotFoundError()
    }

    resourceStore.update("/studies/" + study, new PersistedResource("null", CarraDataUtil.toBytes(studyEntry)))
    Response.ok.build
  }
}

@Path("/pmservice")
@Component
class PM @Autowired()(var authenticator: IAuthenticator) {

  @Path("/getServices")
  @Consumes(Array("text/xml", "text/plain"))
  @POST
  def pmAuthorizationCall(post: String): Response = {
    val xml = XML.load(new StringReader(post))
    val userName: String = (xml \ "message_header" \ "security" \ "username").text
    val password: String = (xml \ "message_header" \ "security" \ "password").text
    val domain: String = (xml \ "message_header" \ "security" \ "domain").text
    val id: Identity = authenticator.authenticate(userName, password)
    Response.ok(new AuthResponse(id).toi2b2XML).build()

  }
}


@Path("/integration")
@Component
class OtherOperations @Autowired()(val resourceStore: ResourceStore) {


  @Path("/healthcheck")
  @GET
  def healthCheck: Response = {
    Response.ok("Healthy").build
  }

  @Path("/peergroups")
  @GET
  def getPeerGroups = {
    var locatorMap = HashMap[Study, Buffer[String]]()
    val resourceList = resourceStore.getAll("/studies/")
    for (p <- resourceList) {
      val s = CarraDataUtil.studyFromBytes(p.getPayload)

      locatorMap.put(s, new ArrayBuffer[String])
      val machineIds: Seq[String] = s.getStudyEntry.getMachineIds
      for (machineId <- machineIds) {
        val res = resourceStore.read("/machines/" + machineId)
        if (res != null) {
          var m = CarraDataUtil.machineFromBytes(res.getPayload)
          locatorMap.get(s).get.add(m.getMachineEntry.getLocator)
        }
      }
    }
    Response.ok(getRoutingTable(locatorMap)).build

  }

  /**
   * Takes a map of Studies to a list of their machine locations
   * and returns a peergroup file
   *
   * @param locatorMap
   * @return
   */
  def getRoutingTable(locatorMap: HashMap[Study, Buffer[String]]): String = {
    var rtc = new RoutingTableConfig
    for (s <- locatorMap.keySet) {
      val endpointList: Buffer[EndpointConfig] = new ArrayBuffer[EndpointConfig]()
      for (val locator <- (locatorMap.get(s).get)) {
        var ec: EndpointConfig = new EndpointConfig(EndpointType.SOAP, locator)
        endpointList.add(ec)
      }
      var pg: PeerGroupConfig = new PeerGroupConfig(s.getStudyEntry.getStudyId, null, endpointList)
      rtc = rtc.withPeerGroup(pg)
    }
    JAXBUtils.marshalToString(rtc)
  }
}

object CarraResourceUtil {
  def identity(authString: String): Identity = {
    TokenUtil.authStringToIdentity(authString)
  }
}




