package com.knoldus.controller

import com.knoldus.Post
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostsSpec extends AnyFunSuite with MockFactory {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val mockJsonData: JsonData = mock[JsonData]

  val posts: Post = Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto")
  val postsJson: String = write(posts)

  (mockJsonData getDetails _).expects("https://jsonplaceholder.typicode.com/posts")
    .returning(Future.successful(postsJson)).anyNumberOfTimes()

  val postModel = new Posts(mockJsonData)

  test("listOfPosts")  {
    postModel.listOfPosts.map(posts => assert(posts ==
    List(Post(1, 1000, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"))))
  }
}
