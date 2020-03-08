package com.knoldus.controller

import com.knoldus.{Address, Company, Geo, User}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class UsersSpec extends AsyncFlatSpec with MockitoSugar {
  implicit val formats: DefaultFormats.type = DefaultFormats

  val mockJsonData: JsonData = mock[JsonData]

  val listOfUsers: List[User] = List(User(1, "Leanne Graham", "Bret", "Sincere@april.biz",
    Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
      Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org",
    Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")))
  val usersJson: String = write(listOfUsers)

  when(mockJsonData getDetails "https://jsonplaceholder.typicode.com/users")
    .thenReturn(Future.successful(usersJson))

  val userModel = new Users(mockJsonData)

  "listOfUsers" should "give list of users" in {
    userModel.listOfUsers.map(users => assert(users == List(User(1, "Leanne Graham", "Bret", "Sincere@april.biz",
      Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
        Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org",
      Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")))))
  }
}
