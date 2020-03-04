package com.knoldus.controller

import com.knoldus.Comment
import net.liftweb.json.{DefaultFormats, parse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Comments(jsonData: JsonData) {
  val commentUrl = "https://jsonplaceholder.typicode.com/comments"

  def listOfComments: Future[List[Comment]] = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    val comments = jsonData.getDetails(commentUrl)
    val parsedComments = comments.map(comments => parse(comments))
    parsedComments.map(parsedComments => parsedComments.children map {
      comment =>
        comment.extract[Comment]
    })
  }
}
