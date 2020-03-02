package com.knoldus

import net.liftweb.json.{DefaultFormats, parse}
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object JsonData {
  def getDetails(url: String): Future[String] = Future {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }
}

object UserModel {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val userUrl = "https://jsonplaceholder.typicode.com/users"

  def listOfUsers: Future[List[User]] = {
    val users = JsonData.getDetails(userUrl)
    val parsedUsers = users.map(users => parse(users))
    parsedUsers.map(parsedUsers => parsedUsers.children map {
      user =>
        user.extract[User]
    })
  }
}

object PostModel {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val postUrl = "https://jsonplaceholder.typicode.com/posts"

  def listOfPosts: Future[List[Post]] = {
    val posts = JsonData.getDetails(postUrl)
    val parsedPosts = posts.map(posts => parse(posts))
    parsedPosts.map(parsedPosts => parsedPosts.children map {
      post =>
        post.extract[Post]
    })
  }
}

object CommentModel {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val commentUrl = "https://jsonplaceholder.typicode.com/comments"

  def listOfComments: Future[List[Comment]] = {
    val comments = JsonData.getDetails(commentUrl)
    val parsedComments = comments.map(comments => parse(comments))
    parsedComments.map(parsedComments => parsedComments.children map {
      comment =>
        comment.extract[Comment]
    })
  }
}

object PostsPerUserModel {
  def listOfPostsPerUser: Future[List[PostUser]] = {
    val parsedUsers = UserModel.listOfUsers

    val parsedPosts = PostModel.listOfPosts

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

object CommentsPerPostModel {
  def listOfCommentsPerPost: Future[List[CommentPost]] = {
    val parsedPosts = PostModel.listOfPosts

    val parsedComments = CommentModel.listOfComments

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

object CommentsPerPostPerUserModel {
  def listOfCommentsPerPostPerUser: Future[List[CommentPostUser]] = {
    val parsedUsers = UserModel.listOfUsers

    val commentsPerPost = CommentsPerPostModel.listOfCommentsPerPost

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
