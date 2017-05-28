package org.codingdojo.kata.logger

import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

import org.codingdojo.kata.appender.FileAppender
import org.codingdojo.kata.date.DateProvider
import org.codingdojo.kata.logger.FileLogger.Naming
import org.codingdojo.kata.logger.FileLogger.Naming.Naming

object FileLogger {
  val DefaultFileExtension = ".txt"

  val SimpleLogFileName = "log"
  val WeekendLogFileName = "weekend"

  object Naming extends Enumeration {
    type Naming = Value

    val Simple     = Value("Simple")
    val Daily      = Value("Daily")
    val WorkingDay = Value("WorkingDay")
  }
}

class FileLogger(fileAppender: FileAppender, dateProvider: DateProvider, naming: Naming = Naming.Simple) {
  import FileLogger._

  def log(entry: String): Unit = {
    val fileName = getFilename()

    if(naming == Naming.WorkingDay
        && isWeekend()
        && fileAppender.exist(fileName)
        && fileAppender.getCreationDate(fileName).getDayOfMonth != dateProvider.today().getDayOfMonth)
      fileAppender.delete(fileName)

    if(!fileAppender.exist(fileName))
      fileAppender.create(fileName)

    fileAppender.append(fileName, entry)
  }

  private def getFilename(): String = (naming match {
    case Naming.Simple => SimpleLogFileName
    case Naming.Daily => dateProvider.today().format(DateTimeFormatter.BASIC_ISO_DATE)
    case Naming.WorkingDay => if(isWeekend()) WeekendLogFileName
                              else dateProvider.today().format(DateTimeFormatter.BASIC_ISO_DATE)
    case _ => throw new IllegalArgumentException("Not implemented yet")

  }) + DefaultFileExtension

  private def isWeekend(): Boolean = {
    val today = dateProvider.today()

    today.getDayOfWeek == DayOfWeek.SATURDAY ||
      today.getDayOfWeek == DayOfWeek.SUNDAY
  }
}
