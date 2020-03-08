package com.knoldus.controller

import com.knoldus.Comment
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class CommentsSpec extends AsyncFlatSpec with MockitoSugar {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val mockJsonData: JsonData = mock[JsonData]

  val listOfComments: List[Comment] = List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))
  val commentsJson: String = write(listOfComments)

  when(mockJsonData getDetails "https://jsonplaceholder.typicode.com/comments")
    .thenReturn(Future.successful(commentsJson))

  val commentModel = new Comments(mockJsonData)

  "listOfPosts" should "give list of comments" in {
    commentModel.listOfComments.map(comments => assert(comments == List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))))
  }
}
