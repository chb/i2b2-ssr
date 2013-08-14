package net.shrine.aggregation

import net.shrine.protocol.{ParamResponse, PatientResponse, ReadPdoResponse}
import net.shrine.broadcaster.sitemapping.SiteNameMapper
import net.shrine.data.UserInfoResponse
import org.spin.message.Result


class CarraReadPdoResponseAggregator(
                                      val siteNameMapper: SiteNameMapper,
                                      val userInfoResponse: UserInfoResponse) extends ReadPdoResponseAggregator(true) with DataTagging {

  protected def userInfo = userInfoResponse

  protected def mapper = siteNameMapper


  override protected[aggregation] def transform(response: ReadPdoResponse, metadata: Result): ReadPdoResponse = {

    def createSiteOrigin: ParamResponse = {
      new ParamResponse("siteOrigin", "siteOrigin", mapper.getSiteIdentifierFromDN(metadata.getOrigin.getName))
    }
    if (canIdenitfySite(metadata.getOrigin.getName)) {
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