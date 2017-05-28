package org.codingdojo.kata.date

import java.time.LocalDate

import org.scalatest.{FlatSpec, Matchers}


class DateProviderSpec extends FlatSpec with Matchers {

  "Local Date Provider" should "return the correct today date" in {
    val dateProvider = new LocalDateProvider

    dateProvider.today() shouldBe LocalDate.now()
  }

  "Fake Weekend Date Provider" should "return the a fixed date that correspond to a weekend day" in {
    val dateProvider = new FakeWeekendDateProvider

    dateProvider.today() shouldBe LocalDate.of(2017, 5, 28) //Sunday
  }
}
