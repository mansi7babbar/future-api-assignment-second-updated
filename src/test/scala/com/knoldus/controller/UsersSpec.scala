package com.knoldus.controller

import com.knoldus.{Address, Company, Geo, User}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UsersSpec extends AnyFunSuite with MockFactory {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val mockJsonData: JsonData = mock[JsonData]

  val users: User = User(1, "Leanne Graham", "Bret", "Sincere@april.biz",
    Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
      Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org",
    Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"))
  val usersJson: String = write(users)

  (mockJsonData getDetails _).expects("https://jsonplaceholder.typicode.com/users")
    .returning(Future.successful(usersJson)).once()

  val userModel = new Users(mockJsonData)

  test("listOfUsers")  {
    userModel.listOfUsers.map(users => assert(users ==
      List(User(1, "Leanne Graham", "Bret", "Sincere@april.biz",
        Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
          Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org",
        Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")))))
  }

}
