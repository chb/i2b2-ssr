package net.shrine.broadcaster.aggregators

import org.spin.query.message.headers.Result
import net.shrine.protocol.{ParamResponse, PatientResponse, ReadPdoResponse}
import net.shrine.broadcaster.sitemapping.SiteNameMapper
import org.spin.tools.crypto.signature.Identity
import net.shrine.aggregation.{SpinResultEntry, ReadPdoResponseAggregator}
import com.yammer.metrics.Instrumented


class CarraReadPdoResponseAggregator(
    val siteNameMapper: SiteNameMapper,
    val id: Identity) extends ReadPdoResponseAggregator with DataTagging with Instrumented {

  protected def identity = id
  protected def mapper = siteNameMapper

  val aggTimer = metrics.timer("aggregation.readPdo")
  val aggMeter = metrics.meter("aggregation.meter", "aggregatedItems")
  val aggCount = metrics.counter("aggCount")

  override def aggregate(spinCacheResults: Seq[SpinResultEntry]) = {
    aggTimer.time{super.aggregate(spinCacheResults)}
  }

  override protected def transform(response: ReadPdoResponse, metadata: Result) = {
    aggMeter.mark()
    aggCount+=1
    def createSiteOrigin: ParamResponse = {
      new ParamResponse("siteOrigin", "siteOrigin", mapper.getSiteIdentifierFromDN(metadata.getOrigin.getName))
    }
    if(canIdenitfySite(metadata.getOrigin.getName)) {
      new ReadPdoResponse(
        response.events,
        response.patients map (patient =>
          new PatientResponse(patient.patientId, patient.params :+ createSiteOrigin)),
        response.observations)
    }
    else {
      response
    }
  }


}