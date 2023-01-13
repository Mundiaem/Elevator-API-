##PreRequisites:
- JAVA Version 19 (openjdk:19)
- Maven Version 3.8.6
##To deploy the project (Locally):
- Clone the repository
- Navigate to the project's root directory
- Open a terminal session using Windows Terminal, GitBash or any other CLI tool

###Run The Application
- `mvn spring-boot:run`
### Package Jar
- `mvn clean package spring-boot:repackage`
#### Run the package
- `java -jar target/ElevatorAPI-0.0.1-SNAPSHOT.jar`
##Database
The application uses a h2-database instance
On startup, a local instance will be created on root application path (/data/db)
To access the console, visit: [hb console](http://localhost:8081/h2-console)

The username and passwords are defined at application.properties
Make sure the database file path is correct on the login console

### Documentation UI
- The application has an embedded OpenAPI  web page showcasing all the endpoints
- To access the endpoint visit:
[OpenAPI Documentation](http://127.0.0.1:8081/swagger-ui/index.html#/)  

- For the spring doc alternative visit
 [API](http://127.0.0.1:8081/v3/api-docs)



##Elevator Working 

A person is on a particular floor. Suppose Ground Floor. He wants to go to 5th Floor. So he clicks on the elevator button with up direction.

We will be calling this the ExternalRequest. 
This Request will be having the direction and the floor on which the button has been pressed by the user
i.e. source floor. The elevator will check the available requests if any and then process this request depending on some priority. 
The elevator reaches the source floor i.e. the 0th or the ground floor. The person enters the elevator.
The person enters the elevator. The person then presses the 5th floor button in the elevator to indicate the elevator to go to 5th floor.

This will be the internal request. 
So the internal request will be having only the floor to which the person wants to go to 
i.e. the destination floor. The elevator moves to the fifth floor. And the person then exits the elevator.
If suppose when the elevator is moving from the ground floor to the fifth floor and it reaches the first floor.
At this moment suppose another person on the second floor want’s to go in the UP direction.
Then the elevator will stop for this request and the person on second floor will enter the elevator. 

Suppose he presses the 4th floor button. Then the elevator will first stop at 4th floor which was the destination of the person which entered on the second floor. 
Later the elevator stops on the fifth floor and the person from the ground floor exits.
If suppose when the elevator is moving from the ground floor to the fifth floor and it reaches the first floor.
At this moment suppose another person on the second floor want’s to go in the DOWN direction.

Then the elevator will not stop for this request immediately.
Elevator will first go to the fifth floor where the person from the ground floor will exit. 
Elevator will then go to the second floor. The person will enter the elevator and press 0. 
The elevator will then move to the zeroth floor.

