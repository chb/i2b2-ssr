package net.shrine.data

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

class MachineInfo(val origin: String, val numberOfPatients: Long, val responseTimeInMillis: Long) {

  def toXml(){
      Utility.trim(
      <machine>
        <origin>{origin}</origin>
        <numberOfPatients>{numberOfPatients}</numberOfPatients>
        <response_time>{responseTimeInMillis}</response_time>
      </machine>)
  }

}
