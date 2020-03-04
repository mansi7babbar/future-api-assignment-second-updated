package com.knoldus.controller

import com.knoldus.User
import net.liftweb.json.{DefaultFormats, parse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Users(jsonData: JsonData) {
  val userUrl = "https://jsonplaceholder.typicode.com/users"

  def listOfUsers: Future[List[User]] = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    val users: Future[String] = jsonData.getDetails(userUrl)
    val parsedUsers = users.map(users => parse(users))
    parsedUsers.map(parsedUsers => parsedUsers.children map {
      user =>
        user.extract[User]
    })
  }
}
