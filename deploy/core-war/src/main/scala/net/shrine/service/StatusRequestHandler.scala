package net.shrine.service

import net.shrine.protocol.ShrineResponse

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

trait StatusRequestHandler {
  def getStatus(userName: String, password: String, peerGroup: String): ShrineResponse
}
