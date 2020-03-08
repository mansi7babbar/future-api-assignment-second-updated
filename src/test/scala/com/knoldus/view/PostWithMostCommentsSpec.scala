package com.knoldus.view

import com.knoldus.controller.CommentsPerPost
import com.knoldus.{Comment, CommentPost, Post}
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class PostWithMostCommentsSpec extends AsyncFlatSpec with MockitoSugar {
  val mockCommentsPerPost: CommentsPerPost = mock[CommentsPerPost]

  val comment: Comment = Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium")

  val post: Post = Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto")

  when(mockCommentsPerPost.listOfCommentsPerPost)
    .thenReturn(Future.successful(List(CommentPost(post, List(comment)))))

  val postWithMostComments = new PostWithMostComments(mockCommentsPerPost)
  "getPostWithMostComments" should "give post with most comments" in {
    postWithMostComments.getPostWithMostComments.map(postWithMostComments => assert(postWithMostComments
      == CommentPost(post, List(comment))))
  }
}
