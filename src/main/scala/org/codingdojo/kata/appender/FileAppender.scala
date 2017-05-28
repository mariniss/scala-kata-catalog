package org.codingdojo.kata.appender

import java.io.{File, FileOutputStream, PrintWriter}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, Paths}
import java.time.{LocalDateTime, ZoneId}


class FileAppender {

  def exist(fileName: String) : Boolean =
    new File(fileName).exists

  def create(fileName: String): Unit =
    new File(fileName).createNewFile()

  def delete(fileName: String): Unit =
    applyIfExist(fileName, {
      new File(_).delete()
    })

  def getCreationDate(fileName: String): LocalDateTime =
    applyIfExist(fileName, { fileName =>
      LocalDateTime.ofInstant(
        Files.readAttributes(Paths.get(fileName), classOf[BasicFileAttributes]).creationTime().toInstant,
        ZoneId.systemDefault()) })

  def append(fileName: String, entry: String): Unit =
    applyIfExist(fileName, { file =>
        val outputStream = new FileOutputStream(file, true)
        val writer = new PrintWriter(outputStream)

        try writer.println(entry)
        finally writer.close()
    })

  private def applyIfExist[T](fileName: String, operation : String => T) =
    if(!exist(fileName))
      throw new IllegalArgumentException("The give file name does not exist!")
    else
      operation(fileName)
}
