package org.codingdojo.kata

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.codingdojo.kata.appender.FileAppender
import org.codingdojo.kata.date.{FakeWeekendDateProvider, LocalDateProvider}
import org.codingdojo.kata.logger.FileLogger
import org.codingdojo.kata.logger.FileLogger.Naming._

import scala.io.Source


object kata {

  def main(args: Array[String]): Unit = {

    val fileAppender = new FileAppender
    val dataProvider = new LocalDateProvider
    val fakeWeekendProvider = new FakeWeekendDateProvider

    //First version
    val simpleFileLogger = new FileLogger(fileAppender = fileAppender, dateProvider = dataProvider)
    simpleFileLogger.log("First entry")
    simpleFileLogger.log("Second entry")

    println("** Content of first log **")
    Source.fromFile("log.txt").getLines().foreach(println)


    //Second version
    val dailyFileLogger = new FileLogger(fileAppender = fileAppender, dateProvider = dataProvider, naming = Daily)
    dailyFileLogger.log("Daily first entry")
    dailyFileLogger.log("Daily second entry")

    println("** Content of Second log **")
    val secondFileName = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".txt"
    Source.fromFile(secondFileName).getLines().foreach(println)


    //Final version -
    //Working day
    val workingDayFileLogger = new FileLogger(fileAppender = fileAppender, dateProvider = dataProvider, naming = WorkingDay)
    workingDayFileLogger.log("Working day first entry")
    workingDayFileLogger.log("Working day second entry")

    println("** Content of third log - working day**")
    val lastFileName = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".txt"
    Source.fromFile(lastFileName).getLines().foreach(println)

    //Weekend day
    val weekendDayFileLogger = new FileLogger(fileAppender = fileAppender, dateProvider = fakeWeekendProvider, naming = WorkingDay)
    weekendDayFileLogger.log("Weekend day first entry")
    weekendDayFileLogger.log("Weekend day second entry")

    println("** Content of third log - working day**")
    Source.fromFile("weekend.txt").getLines().foreach(println)
  }

}
