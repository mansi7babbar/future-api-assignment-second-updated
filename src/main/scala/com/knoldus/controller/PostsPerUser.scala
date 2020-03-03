package com.knoldus.controller

import com.knoldus.PostUser

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostsPerUser(userModel: Users, postModel: Posts) {
  def listOfPostsPerUser: Future[List[PostUser]] = {
    val parsedUsers = userModel.listOfUsers

    val parsedPosts = postModel.listOfPosts

    val postsPerUser = parsedUsers.map { parsedUsers =>
      Future.sequence(
        parsedUsers.map {
          user =>
            parsedPosts.map { parsedPosts =>
              PostUser(user, parsedPosts.filter(user.id == _.id))
            }
        })
    }

    postsPerUser.flatten
  }
}
