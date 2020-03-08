package com.knoldus.controller

import com.knoldus._
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class PostsPerUserSpec extends AsyncFlatSpec with MockitoSugar {
  val mockUsers: Users = mock[Users]
  val mockPosts: Posts = mock[Posts]

  val user: User = User(1, "Leanne Graham", "Bret", "Sincere@april.biz",
    Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
      Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org",
    Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"))

  val post: Post = Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto")

  when(mockUsers.listOfUsers).thenReturn(Future.successful(List(user)))

  when(mockPosts.listOfPosts).thenReturn(Future.successful(List(post)))
  val postsPerUserModel = new PostsPerUser(mockUsers, mockPosts)

  "listOfPostsPerUser" should "give list of posts per user" in {
    postsPerUserModel.listOfPostsPerUser.map(postsPerUser => assert(postsPerUser ==
      List(PostUser(user, List(post)))))
  }
}
