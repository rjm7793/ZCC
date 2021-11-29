# Riley Muessig ZCC

Usage:
Download the repository and open it up in the Java
IDE of your choice (I used IntelliJ). In order to 
run the program, run ZCCMain.java. To pass in the
subdomain, username, and password that you will be
using to make API calls, you will need to pass in
program arguments into ZCCMain. The first argument
is the subdomain, the second argument is the
username, and the third argument is the password.

Some third party dependencies need to be configured
in order for the program to be run. These can be
found at the following links:

JSON Parsing Framework:
https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5

Unit Testing Frameworks:

https://mvnrepository.com/artifact/org.mockito/mockito-all/1.10.19

https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api/5.8.1

https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine/5.8.1

https://mvnrepository.com/artifact/org.junit.platform/junit-platform-engine/1.3.2

https://mvnrepository.com/artifact/org.junit.platform/junit-platform-commons/1.8.1

To configure these in IntelliJ, I installed the .jar
files from each of these links, then in IntelliJ, I
went to File -> Project Structure -> Libraries and
added each of the .jar files by clicking on the + symbol
marked as "New Project Library", then clicking Java, and
choosing each .jar file.

Each of the Unit Testing files can be run individually
to test each component of my program.