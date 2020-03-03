package com.knoldus

import com.knoldus.controller.{Comments, CommentsPerPost, JsonData, Posts, PostsPerUser, Users}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object JsonParsing {
  val jsonData = new JsonData
  val userModel = new Users(jsonData)
  val postModel = new Posts(jsonData)
  val commentModel = new Comments(jsonData)
  val postsPerUserModel = new PostsPerUser(userModel, postModel)
  val commentsPerPostModel = new CommentsPerPost(commentModel, postModel)

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

  def getUserWithPostWithMostComments: Future[List[User]] = {

    val getUserWithPostWithMostComments = for (list <- getPostWithMostComments;
         users <- userModel.listOfUsers) yield users.filter(user => user.id == list.post.userId)

    getUserWithPostWithMostComments.recover({
      case _: Exception => List(User(0, "", "", "", Address("", "", "", "", Geo("", "")), "", "", Company("", "", "")))
    })
  }

}
