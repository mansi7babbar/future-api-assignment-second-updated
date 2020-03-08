package com.knoldus.view

import com.knoldus.controller.PostsPerUser
import com.knoldus._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class UserWithMostPostsSpec extends AsyncFlatSpec with MockitoSugar {
  val mockPostsPerUser: PostsPerUser = mock[PostsPerUser]
  when(mockPostsPerUser.listOfPostsPerUser)
    .thenReturn(Future.successful(List(PostUser(User(1, "Leanne Graham", "Bret", "Sincere@april.biz", Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org", Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")), List(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"))))))

  val userWithMostPosts = new UserWithMostPosts(mockPostsPerUser)
  "getUserWithMostPosts" should "give user with most posts" in {
    userWithMostPosts.getUserWithMostPosts.map(userWithMostPosts => assert(userWithMostPosts == PostUser(User(1, "Leanne Graham", "Bret", "Sincere@april.biz", Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org", Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")), List(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto")))))
  }
}
