## TESTARCHITEST AUTOMATION REPOSITORY

## Summary
This repository is used to test the [Demo-TestArchitest](https://demo.testarchitect.com/) website

## Prerequisites

### Application
IntelliJ IDEA Community Edition (download here: https://www.jetbrains.com/idea/download/?section=windows)
+ Should be used because this is the application that wrote this repository
+ Other IDEAs can be used, but installation and usage will be slightly different

### Environments
+ Java 17 (download here: https://www.java.com/download/ie_manual.jsp)
+ Maven (visit here: https://mvnrepository.com/)
+ TestNG (visit here: https://mvnrepository.com/artifact/org.testng/testng)
+ Selenide (download here: https://selenide.org/2024/09/15/selenide-7.5.0/)

## How to run the code
1. Download source code (location, unzip,...)
2. Open IntelliJ IDEA. ```File``` -> ```Open``` -> ```Select file``` -> ```New Window``` or ```This Window```
3. Wait for dependencies to be installed (reimport if needed)
4. Make sure that at least the following plugins are imported: ```testNG```, ```lombok```
5. Make sure the browser is on the correct version (updated if the version is too old)
6. Run tests by commandline or using UI

### Run tests with Maven from the Command Line (Local)
- Run all TCs with specific Browser:
  ```mvn test -Dbrowser=#browser```

- Run tests with 'testng.xml' file:
  ```mvn clean test -Dbrowser=#browser```

- Run a specific class with specific Browser:
  ```mvn test -Dtest=SampleTest -Dbrowser=#browser```

- Run a method in a class with specific Browser:
  ```mvn test -Dtest=SampleTest#methodName -Dbrowser=#browser```

- Run with custom profile:
  ```mvn test -PprofileName```

## How to run Selenium Grid
### Setup Selenium Grid with Docker
1. Install Docker: Download and install Docker Desktop: https://www.docker.com/products/docker-desktop/
2. Verify installation: `docker -v`
3. Start Selenium Grid with Docker Compose: This project contains a docker-compose.yml file. Start the grid with: `docker compose up -d` By default, the Selenium Hub UI is available at:
   👉 http://localhost:4444/ui
4. Run tests on Selenium Grid
### Setup Selenium Grid with JAR file
1. User can download the Selenium Server at the following link:
   ```https://www.selenium.dev/downloads/```
2. Create hub: enter the following string in the first cmd
   ```java -jar selenium-server-4.25.0.jar hub```
3. Create node(s): enter the following string in the second cmd or more
   ```java -jar selenium-server-4.25.0.jar node  --selenium-manager true```
4. Enter to localhost:
   ```http://localhost:4444/ui/#``` (default)
![Selenium_Grid.png](src/main/resources/Selenium_Grid.png)
5. Run test cases as usual and observe the localhost site
### Run tests with Selenium Grid
- Run all TCs with specific Browser:
  ```mvn test -Dbrowser=#browser -DrunMode=remote```

- Run tests with 'testng.xml' file:
  ```mvn clean test -Dbrowser=#browser -DrunMode=remote```

- Run a specific class with specific Browser:
  ```mvn test -Dtest=SampleTest -Dbrowser=#browser -DrunMode=remote```

- Run a method in a class with specific Browser:
  ```mvn test -Dtest=SampleTest#methodName -Dbrowser=#browser -DrunMode=remote```

- Run with custom profile:
  ```mvn test -PprofileName -DrunMode=remote```

## How to get the results
1. Run tests by commandline or using UI
2. After running the program, point to the current project
3. Enter '''allure serve allure-results''' into the Terminal and then press Enter
4. You should see the results like this:

   ![allure_results_samp!](src/main/resources/Allure_Results.png)
5. You can visit the result website to get more information

## Project Structure
**\src\main\java\auto: contain constructor, pages

**\src\test\java\auto: contain test cases

**\src\main\resources: contain data, config file

### The features are applied
+ Github: Gitflow
+ Configuration: pom.xml: Dependencies, Commandline
+ Code: Name convention, OOP, Json, Data driven
+ Report: Allure report

#### FINAL RESULT
![img.png](src/main/resources/Final_result.jpg)
