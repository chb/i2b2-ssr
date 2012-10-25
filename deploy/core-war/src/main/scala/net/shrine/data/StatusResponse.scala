package net.shrine.data

import net.shrine.protocol.ShrineResponse
import xml.Utility

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
class StatusResponse(machines: Seq[MachineInfo]) extends ShrineResponse {
  protected def i2b2MessageBody = null

  def toXml = {
    Utility.trim(
    <status_response>
      {machines.map(x=> x.toXml())}
    </status_response>)
  }
}
