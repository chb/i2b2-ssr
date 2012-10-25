package net.shrine.service

import javax.ws.rs.{GET, Produces, Path}
import scala.Array
import javax.ws.rs.core.MediaType
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Scope
import org.springframework.beans.factory.annotation.Autowired
import net.shrine.util.Loggable
import net.shrine.data.StatusRequest
import xml.XML

/**
 * @author David Ortiz
 * @date 10/18/12
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */

@Path("/statusCheck")
@Produces(Array(MediaType.APPLICATION_XML))
@Component
@Scope("singleton")
class HeartbeatResource @Autowired()(private val statusRequestHandler: StatusRequestHandler) extends Loggable {

  @GET
  def getStatus(payload: String): String ={
    val request = StatusRequest.fromXml(XML.load(payload))
    statusRequestHandler.getStatus(request.userName, request.password, request.project).toXml.toString()

  }


}
