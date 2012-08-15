package net.shrine.broadcaster.aggregators

import org.spin.query.message.headers.Result
import net.shrine.protocol.{ParamResponse, PatientResponse, ReadPdoResponse}
import net.shrine.broadcaster.sitemapping.SiteNameMapper
import org.spin.tools.crypto.signature.Identity
import net.shrine.aggregation.{SpinResultEntry, ReadPdoResponseAggregator}
import net.shrine.data.UserInfoResponse


class CarraReadPdoResponseAggregator(
    val siteNameMapper: SiteNameMapper, userInfoResponse: UserInfoResponse) extends ReadPdoResponseAggregator with DataTagging {


  protected def userInfo = userInfoResponse

  protected def mapper = siteNameMapper


  override def aggregate(spinCacheResults: Seq[SpinResultEntry]) = {
    super.aggregate(spinCacheResults)
  }

  override protected def transform(response: ReadPdoResponse, metadata: Result) = {
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