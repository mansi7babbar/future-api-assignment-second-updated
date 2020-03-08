package com.knoldus.view

import com.knoldus.controller.PostsPerUser
import com.knoldus.{Address, Company, Geo, Post, PostUser, User}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserWithMostPosts(postsPerUserModel: PostsPerUser) {
  def getUserWithMostPosts: Future[PostUser] = {

    val userWithMostPosts = postsPerUserModel.listOfPostsPerUser.map { postsPerUser =>
      postsPerUser.foldLeft(postsPerUser.head) { (max, user) =>
        if (max.posts.length < user.posts.length) user else max
      }
    }

    userWithMostPosts.recover({
      case _: Exception => PostUser(User(0, "", "", "", Address("", "", "", "", Geo("", "")), "", "", Company("", "", "")), List.empty[Post])
    })
  }
}
