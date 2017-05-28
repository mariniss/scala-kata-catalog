# scala-kata-catalog
Simple scala implementation of Kata: https://github.com/ardalis/kata-catalog.
It has been implemented in TDD style using mocked objects as central focus.  
It implements all three versions requested in the Kata. 

The main test is 
`org.codingdojo.kata.logger.FileLoggerSpec`, it uses the [mokito spy](http://static.javadoc.io/org.mockito/mockito-core/2.8.9/org/mockito/Mockito.html#13) as test assertions.
There are also other two tests, a simple unit test for the DateProvider: `org.codingdojo.kata.date.DateProviderSpec` and an integration test for the `FileAppender` class that provides a direct access to the file system.
The main method has been implemented too and it executes all three versions.


It is built with sbt 0.13.8 and scala 2.11.8, please check the official documentation for the installation and configuration: http://www.scala-sbt.org/0.13/docs/Setup.html

- `sbt test`: to run the unit tests
- `sbt it:test`: to run the integration tests
- `sbt run`: to run main method
