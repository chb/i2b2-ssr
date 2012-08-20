package net.shrine.data

import xml.NodeSeq

class UserInfoResponse(val userName: String,
                   val projectId: String,
                   val canPdo: Boolean,
                   val canIdentify: Boolean,
                   val homeSites: Seq[String]) {
}

object UserInfoResponse {
  def fromXml(nodeSeq: NodeSeq) : UserInfoResponse = {
      new UserInfoResponse(
        (nodeSeq \ "user_data" \ "username").text,
        (nodeSeq \ "user_data"\ "project").text,
        (nodeSeq \ "user_data"\ "allowPdo").text.toBoolean,
        (nodeSeq \ "user_data"\ "allowSiteIdentity").text.toBoolean,
        (nodeSeq \ "user_data"\ "homesite" ).map {x => x.text}
      )
  }

}