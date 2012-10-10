package net.shrine.data

import xml.NodeSeq

class UserInfoResponse(val userName: String,
    val projectId: String,
    val canPdo: Boolean,
    val canIdentify: Boolean,
    val homeSites: Seq[String]) {
}

object UserInfoResponse {
  def fromXml(nodeSeq: NodeSeq): UserInfoResponse = {
    new UserInfoResponse(
      (nodeSeq \ "username").text,
      (nodeSeq \ "project").text,
      (nodeSeq \ "allowPdo").text.toBoolean,
      (nodeSeq \ "allowSiteIdentity").text.toBoolean,
      (nodeSeq \ "homesite").map { x => x.text}
    )
  }

}
