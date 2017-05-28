package org.codingdojo.kata.logger

import java.time.{LocalDate, LocalDateTime}

import org.codingdojo.kata.appender.FileAppender
import org.codingdojo.kata.date.{DateProvider, LocalDateProvider}
import org.codingdojo.kata.logger.FileLogger.Naming
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class FileLoggerSpec  extends FlatSpec with Matchers with MockitoSugar {

  "Kata Logger simple name" should "create the log file if it does not exist and write the given string" in {
    val mockKataFileSystem = mock[FileAppender]
    val dateProvider = new LocalDateProvider

    when(mockKataFileSystem.exist("log.txt")) thenReturn false

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = dateProvider)


    kataLogger.log("test")

    verify(mockKataFileSystem).create("log.txt")
    verify(mockKataFileSystem).append("log.txt", "test")
  }

  it should "only append the a new line it the existing file" in {
    val mockKataFileSystem = mock[FileAppender]
    val dateProvider = new LocalDateProvider

    when(mockKataFileSystem.exist("log.txt")) thenReturn true

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = dateProvider)


    kataLogger.log("test")

    verify(mockKataFileSystem).append("log.txt", "test")
  }

  "Kata Logger daily name" should "create the log file if it does not exist with name yyyyMMdd and write the given string" in {
    val mockKataFileSystem = mock[FileAppender]
    val mockDateProvider = mock[DateProvider]

    when(mockDateProvider.today()) thenReturn LocalDate.of(2017, 5, 28) //Sunday
    when(mockKataFileSystem.exist("20170528.txt")) thenReturn false

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = mockDateProvider, naming = Naming.Daily)


    kataLogger.log("test")

    verify(mockKataFileSystem).create("20170528.txt")
    verify(mockKataFileSystem).append("20170528.txt", "test")
  }

  it should "only append the a new line it the existing file" in {
    val mockKataFileSystem = mock[FileAppender]
    val mockDateProvider = mock[DateProvider]

    when(mockDateProvider.today()) thenReturn LocalDate.of(2017, 5, 28) //Sunday
    when(mockKataFileSystem.exist("20170528.txt")) thenReturn true

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = mockDateProvider, naming = Naming.Daily)


    kataLogger.log("test")

    verify(mockKataFileSystem).append("20170528.txt", "test")
  }

  "Kata Logger working days name" should "create the log file if it does not exist, and it is not weekend, with name yyyyMMdd then write the given string" in {
    val mockKataFileSystem = mock[FileAppender]
    val mockDateProvider = mock[DateProvider]

    when(mockDateProvider.today()) thenReturn LocalDate.of(2017, 5, 26) //Friday
    when(mockKataFileSystem.exist("20170526.txt")) thenReturn false

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = mockDateProvider, naming = Naming.WorkingDay)


    kataLogger.log("test")

    verify(mockKataFileSystem).create("20170526.txt")
    verify(mockKataFileSystem).append("20170526.txt", "test")
  }

  it should "only append the a new line it the existing file" in {
    val mockKataFileSystem = mock[FileAppender]
    val mockDateProvider = mock[DateProvider]

    when(mockDateProvider.today()) thenReturn LocalDate.of(2017, 5, 26) //Friday
    when(mockKataFileSystem.exist("20170526.txt")) thenReturn true

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = mockDateProvider, naming = Naming.WorkingDay)


    kataLogger.log("test")

    verify(mockKataFileSystem).append("20170526.txt", "test")
  }

  it should "create the log file if it does not exist with name weekend and write the given string" in {
    val mockKataFileSystem = mock[FileAppender]
    val mockDateProvider = mock[DateProvider]

    when(mockDateProvider.today()) thenReturn LocalDate.of(2017, 5, 27) //Saturday
    when(mockKataFileSystem.exist("weekend.txt")) thenReturn false

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = mockDateProvider, naming = Naming.WorkingDay)


    kataLogger.log("test")

    verify(mockKataFileSystem).append("weekend.txt", "test")
  }

  it should "only append the a new line it the existing weekend file created today" in {
    val mockKataFileSystem = mock[FileAppender]
    val mockDateProvider = mock[DateProvider]

    when(mockDateProvider.today()) thenReturn LocalDate.of(2017, 5, 27) //Saturday
    when(mockKataFileSystem.exist("weekend.txt")) thenReturn true
    when(mockKataFileSystem.getCreationDate("weekend.txt")) thenReturn LocalDateTime.of(2017, 5, 27, 12, 0, 59)

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = mockDateProvider, naming = Naming.WorkingDay)


    kataLogger.log("test")

    verify(mockKataFileSystem).append("weekend.txt", "test")
  }

  it should "write the entry in a clean weekend file if it is the first time and the file already exist" in {
    val mockKataFileSystem = mock[FileAppender]
    val mockDateProvider = mock[DateProvider]

    when(mockDateProvider.today()) thenReturn LocalDate.of(2017, 5, 27) //Saturday
    when(mockKataFileSystem.exist("weekend.txt")) thenReturn (true, false) //first invocation returns true, second returns false
    when(mockKataFileSystem.getCreationDate("weekend.txt")) thenReturn LocalDateTime.of(2017, 5, 20, 12, 0, 59)

    val kataLogger = new FileLogger(fileAppender = mockKataFileSystem, dateProvider = mockDateProvider, naming = Naming.WorkingDay)


    kataLogger.log("test")

    verify(mockKataFileSystem).delete("weekend.txt")
    verify(mockKataFileSystem).create("weekend.txt")
    verify(mockKataFileSystem).append("weekend.txt", "test")
  }

}
