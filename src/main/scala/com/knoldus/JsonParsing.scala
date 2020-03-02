package com.knoldus

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object JsonParsing {

  def getUserWithMostPosts: Future[PostUser] = {

    val userWithMostPosts = PostsPerUserModel.listOfPostsPerUser.map { postsPerUser =>
      postsPerUser.foldLeft(postsPerUser.head) { (max, user) =>
        if (max.posts.length < user.posts.length) user else max
      }
    }

    userWithMostPosts.recover({
      case _: Exception => PostUser(User(0, "", "", "", Address("", "", "", "", Geo("", "")), "", "", Company("", "", "")), List.empty[Post])
    })
  }

  def getPostWithMostComments: Future[CommentPost] = {

    val postWithMostComments = CommentsPerPostModel.listOfCommentsPerPost.map { commentsPerPost =>
      commentsPerPost.foldLeft(commentsPerPost.head) { (max, post) =>
        if (max.comments.length < post.comments.length) post else max
      }
    }

    postWithMostComments.recover({
      case _: Exception => CommentPost(Post(0, 0, "", ""), List.empty[Comment])
    })
  }
}
