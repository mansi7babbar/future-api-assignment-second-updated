package com.knoldus.controller

import com.knoldus.CommentPost

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CommentsPerPost(commentModel: Comments, postModel: Posts) {
  def listOfCommentsPerPost: Future[List[CommentPost]] = {
    val parsedPosts = postModel.listOfPosts

    val parsedComments = commentModel.listOfComments

    val commentsPerPost = parsedPosts.map { parsedPosts =>
      Future.sequence(
        parsedPosts.map {
          post =>
            parsedComments.map { parsedComments =>
              CommentPost(post, parsedComments.filter(post.id == _.postId))
            }
        })
    }

    commentsPerPost.flatten
  }
}
