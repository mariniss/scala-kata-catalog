package org.codingdojo.kata.date

import java.time.LocalDate


sealed trait DateProvider {

  def today(): LocalDate

}

class LocalDateProvider extends DateProvider {
  override def today(): LocalDate = LocalDate.now()
}

class FakeWeekendDateProvider extends DateProvider {
  override def today(): LocalDate = LocalDate.of(2017, 5, 28) //Sunday
}
