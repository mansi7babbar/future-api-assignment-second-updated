package com.knoldus.view

import com.knoldus.controller.{CommentsPerPost, Users}
import com.knoldus.{Address, Company, Geo, User}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserWithPostWithMostComments(userModel: Users, commentsPerPostModel: CommentsPerPost) {
  def getUserWithPostWithMostComments: Future[List[User]] = {

    val postWithMostComments = commentsPerPostModel.listOfCommentsPerPost.map { commentsPerPost =>
      commentsPerPost.foldLeft(commentsPerPost.head) { (max, post) =>
        if (max.comments.length < post.comments.length) post else max
      }
    }

    val getUserWithPostWithMostComments =
      for (list <- postWithMostComments;
           users <- userModel.listOfUsers) yield users.filter(user => user.id == list.post.userId)

    getUserWithPostWithMostComments.recover({
      case _: Exception => List(User(0, "", "", "", Address("", "", "", "", Geo("", "")), "", "", Company("", "", "")))
    })
  }
}
