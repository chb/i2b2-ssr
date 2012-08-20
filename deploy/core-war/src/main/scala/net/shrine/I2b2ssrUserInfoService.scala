package net.shrine


import authorization.AuthorizationException
import data.UserInfoResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import protocol.ShrineRequest
import serializers.crc.CRCRequestType
import xml.XML
import java.io.IOException
import org.apache.http.client.ClientProtocolException


class I2b2ssrUserInfoService(callbackAddress: String) {
  /**
   * Takes a ShrineRequest and if user is allowed to perform it
   * returns a UserInfoResponse
   * @param request
   * @return
   */
  def authorizeRunQueryRequest(request: ShrineRequest): UserInfoResponse = {
    val client = new DefaultHttpClient()
    val httpGet = new HttpGet(callbackAddress)


    httpGet.getParams.setParameter("username", request.authn.username)
    httpGet.getParams.setParameter("sessionKey", request.authn.credential)
    httpGet.getParams.setParameter("project", request.projectId)

    try {
      val response = client.execute(httpGet)

      if (response.getStatusLine.getStatusCode == 200) {
        throw new AuthorizationException("Not Authorized")
      }
      response.getEntity.getContent
      val info = UserInfoResponse.fromXml(XML.load(response.getEntity.getContent))

      //If the request is a PDO and the user can't PDO, throw an exception
      if(request.requestType == CRCRequestType.GetPDOFromInputListRequestType && !info.canPdo){
        throw new AuthorizationException("User sending PDO request on project they can't PDO")
      }

      info
    }
    catch {
      case e: IOException => throw new AuthorizationException("IOException throw trying to contact the Auth Service at %s")
      case e: ClientProtocolException => throw new AuthorizationException("ClientProtocolException throw trying to contact the Auth Service at %s")
    }
  }
}
