package net.shrine.data

import xml.NodeSeq

/**
 * @author David Ortiz
 * @date 10/19/12
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */

class StatusRequest(val userName: String, val password: String, val project: String) {}

object StatusRequest {
  def fromXml(nodeSeq: NodeSeq): StatusRequest = {
    new StatusRequest(
      (nodeSeq \ "username").text,
      (nodeSeq \ "password").text,
      (nodeSeq \ "project").text
    )
  }
}
