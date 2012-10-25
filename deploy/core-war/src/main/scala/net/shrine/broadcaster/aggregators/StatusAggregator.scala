package net.shrine.broadcaster.aggregators

import net.shrine.aggregation.{SpinResultEntry, Aggregator}
import net.shrine.data.{MachineInfo, StatusResponse}
import net.shrine.broadcaster.sitemapping.SiteNameMapper

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
class StatusAggregator(mapper: SiteNameMapper) extends Aggregator{
  def aggregate(spinCacheResults: Seq[SpinResultEntry]) = {
    new StatusResponse(spinCacheResults.map {
      result => new MachineInfo(result.spinResultMetadata.getOrigin.getName,
                                20L,
                                result.spinResultMetadata.getExecutionTime.duration)
    })

  }
}
