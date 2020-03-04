package com.knoldus

import org.scalatest.flatspec.AsyncFlatSpec

class JsonParsingSpec extends AsyncFlatSpec {

  behavior of "getUserWithMostPosts"
  it should "return user having maximum posts" in {
    JsonParsing.getUserWithMostPosts.map(postUser => assert(postUser.user.id == 1))
  }

  behavior of "getPostWithMostComments"
  it should "return post having maximum comments" in {
    JsonParsing.getPostWithMostComments.map(commentPost => assert(commentPost.post.id == 1))
  }

  behavior of "getUserWithPostWithMostComments"
  it should "return user with post having maximum comments" in {
    JsonParsing.getUserWithPostWithMostComments.map(user => assert(user.head.id == 1))
  }
}
