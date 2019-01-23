# Reservation System

This is a simple reservation system based on REST API - CRUD architecture, realized within Intive Patronage programme.

## Getting started

### Prerequisites

```
Gradle build tool
alternatively: IDE, f.ex. IntelliJ IDEA
CURL
```

### Building the application

In order to build the application easily please install Gradle.

```
In the command line shell:
1. Go to the directory with "gradlew" file of the Project folder.
2. Insert: ./gradlew -v
3. Insert: ./gradlew build

The Project should have built automatically.

Insert gradle bootRun. The application should run and be ready to use i.e. consume your CURL inquiries.


```

## Using the application

You are able to perform CRUD operations in Reservation System, using CURL tool instructions.

```
F.ex. to add an example Conference room to the System:

with your Reservation System application running, in your command line (f.ex. Git Bash shell for Windows) insert:
curl -d '{"name":"Organization example name"}' -H "Content-Type: application/json" -X POST localhost:8070/organizations
to add Organization to the System,

then insert:
curl -d '{"name": "Conference Room example name","optionalId": null,"floor": 10,"available": true,"sittingPlaces": 10,"standingPlaces": 10,"hangingPlaces": 10}' -H "Content-Type: application/json" -X POST localhost:8070/rooms/<the id number of Organization added earlier to the System>

You should be able to retrieve the related data by simple: 
curl localhost:8070/rooms
in your command line now.

If you need to retrieve the Reservations for particular Conference Room in particular Organization insert:
curl –l localhost:8070/reservations/1/2
where, {1} is the exemplary Organization id and {2} is the exemplary Reservation id.

You may find Rest API documentation generated here: http://localhost:8070/swagger-ui.html
```
