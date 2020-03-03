package com.knoldus.controller

import com.knoldus.Comment
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CommentsSpec extends AnyFunSuite with MockFactory {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val mockJsonData: JsonData = mock[JsonData]

  val comments: Comment = Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium")
  val commentsJson: String = write(comments)

  (mockJsonData getDetails _).expects("https://jsonplaceholder.typicode.com/comments")
    .returning(Future.successful(commentsJson)).anyNumberOfTimes()

  val commentModel = new Comments(mockJsonData)

  test("listOfPosts")  {
    commentModel.listOfComments.map(comments => assert(comments ==
    List(Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))))
  }
}
