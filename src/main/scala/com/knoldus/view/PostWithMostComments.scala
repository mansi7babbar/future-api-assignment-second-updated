package com.knoldus.view

import com.knoldus.controller.CommentsPerPost
import com.knoldus.{Comment, CommentPost, Post}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostWithMostComments(commentsPerPostModel: CommentsPerPost) {
  def getPostWithMostComments: Future[CommentPost] = {

    val postWithMostComments = commentsPerPostModel.listOfCommentsPerPost.map { commentsPerPost =>
      commentsPerPost.foldLeft(commentsPerPost.head) { (max, post) =>
        if (max.comments.length < post.comments.length) post else max
      }
    }

    postWithMostComments.recover({
      case _: Exception => CommentPost(Post(0, 0, "", ""), List.empty[Comment])
    })
  }
}
