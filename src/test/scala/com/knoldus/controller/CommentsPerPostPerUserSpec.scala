package com.knoldus.controller

import com.knoldus._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class CommentsPerPostPerUserSpec extends AsyncFlatSpec with MockitoSugar {
  val mockUsers: Users = mock[Users]
  val mockCommentsPerPost: CommentsPerPost = mock[CommentsPerPost]

  when(mockUsers.listOfUsers)
    .thenReturn(Future.successful(List(User(1, "Leanne Graham", "Bret", "Sincere@april.biz",
      Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
        Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org",
      Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")))))

  when(mockCommentsPerPost.listOfCommentsPerPost).thenReturn(Future.successful(
    List(CommentPost(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"),
      List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))))))

  val commentsPerPostPerUserModel = new CommentsPerPostPerUser(mockUsers, mockCommentsPerPost)

  "listOfCommentsPerPostPerUser" should "give list of comments per post per user" in {
    commentsPerPostPerUserModel.listOfCommentsPerPostPerUser.map(commentsPerPostPerUser => assert(commentsPerPostPerUser ==
      List(CommentPostUser(User(1, "Leanne Graham", "Bret", "Sincere@april.biz", Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org", Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")),
        List(CommentPost(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"),
          List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))))))))
  }
}
