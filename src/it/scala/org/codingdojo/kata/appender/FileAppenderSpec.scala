package org.codingdojo.kata.appender

import java.io.{File, FileOutputStream, PrintWriter}
import java.time.LocalDate

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source


class FileAppenderSpec extends FlatSpec with Matchers {

  val subject = new FileAppender

  "Exist" should "return false if the given file does not exist in the working directory" in {
    subject.exist("unknown.txt") shouldBe false
  }

  it should "return true if the five file exist in the working directory" in {
    val fileName = "testFile.txt"
    val file = new File(fileName)
    file.createNewFile()

    subject.exist(fileName) shouldBe true

    file.delete()
  }


  "Create" should "create a new file with the given name in the working directory" in {
    val fileName = "newTestFile.txt"
    val file = new File(fileName)
    file.exists() shouldBe false

    subject.create(fileName)

    file.exists() shouldBe true

    file.delete()
  }

  it should "do nothing if the file already exist" in {
    val fileName = "newTestFile.txt"
    val file = new File(fileName)
    file.createNewFile()

    subject.create(fileName)

    file.exists() shouldBe true

    file.delete()
  }


  "Delete" should "delete the file with the given name in the working directory" in {
    val fileName = "toDeleteTestFile.txt"
    val file = new File(fileName)
    file.createNewFile()

    subject.delete(fileName)

    file.exists() shouldBe false
  }

  it should "throws an illegal argument exception if the file does not exist" in {
    val fileName = "alreadyDeletedTestFile.txt"
    val file = new File(fileName)
    file.exists() shouldBe false

    an[IllegalArgumentException] shouldBe thrownBy(
      subject.delete(fileName)
    )
  }


  "Get creation date" should "return the creation date of file with the given name in the working directory" in {
    val fileName = "createdTestFile.txt"
    val file = new File(fileName)
    file.createNewFile()

    val creationDate = subject.getCreationDate(fileName)

    creationDate.getDayOfMonth shouldBe LocalDate.now().getDayOfMonth

    file.delete()
  }

  it should "throws an illegal argument exception if the file does not exist" in {
    val fileName = "unknown.txt"
    val file = new File(fileName)
    file.exists() shouldBe false

    an[IllegalArgumentException] shouldBe thrownBy(
      subject.getCreationDate(fileName)
    )
  }


  "Append" should "write the given text if the file is empty" in {
    val fileName = "empty.txt"
    val file = new File(fileName)
    file.createNewFile()

    subject.append(fileName, "First line")

    val content = Source.fromFile(fileName).getLines().toList
    content should have size 1
    content.head shouldBe "First line"

    file.delete()
  }

  it should "append the given text at the end of file if it is not empty" in {
    val fileName = "notEmpty.txt"
    val file = new File(fileName)
    file.createNewFile()
    writeIntoFile(file, List("First line", "Second Line"))

    subject.append(fileName, "Third line")

    val content = Source.fromFile(fileName).getLines().toList
    content should have size 3
    content(2) shouldBe "Third line"

    file.delete()
  }

  it should "throws an illegal argument exception if the file does not exist" in {
    val fileName = "unknown.txt"
    val file = new File(fileName)
    file.exists() shouldBe false

    an[IllegalArgumentException] shouldBe thrownBy(
      subject.append(fileName, "Third line")
    )
  }


  private def writeIntoFile(file: File, content: List[String]) : Unit = {
    val outputStream = new FileOutputStream(file, true)
    val writer = new PrintWriter(outputStream)

    try content.foreach(writer.println)
    finally writer.close()
  }
}
