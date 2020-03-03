package com.knoldus.controller

import com.knoldus.CommentPostUser

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CommentsPerPostPerUser(userModel: Users, commentsPerPostModel: CommentsPerPost) {
  def listOfCommentsPerPostPerUser: Future[List[CommentPostUser]] = {
    val parsedUsers = userModel.listOfUsers

    val commentsPerPost = commentsPerPostModel.listOfCommentsPerPost

    val commentsPerPostPerUser = parsedUsers.map { parsedUsers =>
      Future.sequence(
        parsedUsers.map {
          user =>
            commentsPerPost.map { commentsPerPost =>
              CommentPostUser(user, commentsPerPost.filter(user.id == _.post.userId))
            }
        })
    }

    commentsPerPostPerUser.flatten
  }
}
