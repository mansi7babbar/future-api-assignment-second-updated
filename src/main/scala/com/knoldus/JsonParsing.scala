package com.knoldus

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object JsonParsing extends App {

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

  def getUserWithPostWithMostComments: Future[List[User]] = {

    val getUserWithPostWithMostComments = for (list <- getPostWithMostComments;
         users <- UserModel.listOfUsers) yield users.filter(user => user.id == list.post.userId)

    getUserWithPostWithMostComments.recover({
      case _: Exception => List(User(0, "", "", "", Address("", "", "", "", Geo("", "")), "", "", Company("", "", "")))
    })
  }

}
