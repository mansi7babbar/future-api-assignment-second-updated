package com.knoldus.controller

import com.knoldus.Post
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class PostsSpec extends AsyncFlatSpec with MockitoSugar {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val mockJsonData: JsonData = mock[JsonData]

  val listOfPosts: List[Post] = List(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"))
  val postsJson: String = write(listOfPosts)

  when(mockJsonData getDetails "https://jsonplaceholder.typicode.com/posts")
    .thenReturn(Future.successful(postsJson))

  val postModel = new Posts(mockJsonData)

  "listOfPosts" should "give list of posts" in {
    postModel.listOfPosts.map(posts => assert(posts == List(Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"))))
  }
}
