# Patient Similarity Application

CSE6242 cBioPortal Patient Similarity Prototype by Team 15

## Set up

### Prerequisites
* [Java 8](https://www.java.com/en/download/help/download_options.xml)
* [Maven](https://maven.apache.org/install.html)
* IDE like [Eclipse](https://www.eclipse.org/downloads) or [IntellJ Community](https://www.jetbrains.com/idea/download)
 
### Build and Run Application

You have can build and run the application through the command line or your IDE.

**1. via Command line**  
In cbioportal/ directory run below commands.  
If you're using Mac or Linux in cbioportal/ directory run  
```
./mvnw clean install -DskipTests && java -jar target/cbioportal-0.0.1-SNAPSHOT.jar
```
If you're using Windows, use the cmd maven wrapper.
```
./mvnw.cmd clean install -DskipTests && java -jar target/cbioportal-0.0.1-SNAPSHOT.jar
```
*Note: If you have mvn in your $PATH, you can directly use mvn instead of the wrapper (mvnw).
You will need to add java to your $PATH, if not already added.*

![command line build run](readme_img/cl_build_run.png)

**2. via IDE (Intellij)**

 - Build the project with maven 1) clean then 2) install.
 - Once the build is successful, run the application by right clicking CbioportalApplication.java -> Run

![intellij build run](readme_img/intellij_build_run.png)


## Application

Once your application is running, go to http://localhost:8080/   
You will see something like the screenshot below. Clicking on say hello, makes an api call and populates the div above with "hello world".

![hello world](readme_img/hello_world.png)


