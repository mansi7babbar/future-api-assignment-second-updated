package com.knoldus.controller

import com.knoldus.{Comment, CommentPost, Post}
import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CommentsPerPostSpec extends AnyFunSuite with MockFactory {
  val mockPosts: Posts = mock[Posts]
  val mockComments: Comments = mock[Comments]

  (mockPosts.listOfPosts _).expects().returning(Future.successful(
    List(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"))))

  (mockComments.listOfComments _).expects().returning(Future.successful(
    List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))))

  val commentsPerPostModel = new CommentsPerPost(mockComments, mockPosts)

  test("listOfCommentsPerPost") {
    commentsPerPostModel.listOfCommentsPerPost.map(commentsPerPost => assert(commentsPerPost ==
      List(CommentPost(Post(1,1,"sunt aut facere repellat provident occaecati excepturi optio reprehenderit","quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"),
        List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))))))
  }
}
