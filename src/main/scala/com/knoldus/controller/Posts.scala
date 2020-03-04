package com.knoldus.controller

import com.knoldus.Post
import net.liftweb.json.{DefaultFormats, parse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Posts(jsonData: JsonData) {
  val postUrl = "https://jsonplaceholder.typicode.com/posts"

  def listOfPosts: Future[List[Post]] = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    val posts = jsonData.getDetails(postUrl)
    val parsedPosts = posts.map(posts => parse(posts))
    parsedPosts.map(parsedPosts => parsedPosts.children map {
      post =>
        post.extract[Post]
    })
  }
}
