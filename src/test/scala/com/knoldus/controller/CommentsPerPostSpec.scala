package com.knoldus.controller

import com.knoldus.{Comment, CommentPost, Post}
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class CommentsPerPostSpec extends AsyncFlatSpec with MockitoSugar {
  val mockPosts: Posts = mock[Posts]
  val mockComments: Comments = mock[Comments]

  when(mockPosts.listOfPosts).thenReturn(Future.successful(
    List(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"))))

  when(mockComments.listOfComments).thenReturn(Future.successful(
    List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))))

  val commentsPerPostModel = new CommentsPerPost(mockComments, mockPosts)

  "listOfCommentsPerPost" should "give list of comments per post" in {
    commentsPerPostModel.listOfCommentsPerPost.map(commentsPerPost => assert(commentsPerPost ==
      List(CommentPost(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"),
        List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))))))
  }
}
