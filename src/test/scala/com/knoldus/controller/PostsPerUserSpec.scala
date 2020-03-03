package com.knoldus.controller

import com.knoldus._
import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostsPerUserSpec extends AnyFunSuite with MockFactory {
  val mockUsers: Users = mock[Users]
  val mockPosts: Posts = mock[Posts]

  (mockUsers.listOfUsers _).expects().returning(Future.successful(List(User(1, "Leanne Graham", "Bret", "Sincere@april.biz",
      Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
        Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org",
      Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")))))

  (mockPosts.listOfPosts _).expects().returning(Future.successful(
    List(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"))))

  val postsPerUserModel = new PostsPerUser(mockUsers, mockPosts)

  test("listOfPostsPerUser") {
    postsPerUserModel.listOfPostsPerUser.map(postsPerUser => assert(postsPerUser ==
      List(PostUser(User(1,"Leanne Graham","Bret","Sincere@april.biz",Address("Kulas Light","Apt. 556","Gwenborough","92998-3874",Geo("-37.3159","81.1496")),"1-770-736-8031 x56442","hildegard.org",Company("Romaguera-Crona","Multi-layered client-server neural-net","harness real-time e-markets")),List(Post(1,1,"sunt aut facere repellat provident occaecati excepturi optio reprehenderit","quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"))))))
  }
}
