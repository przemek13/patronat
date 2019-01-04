# Reservation System

This is a simple reservation system based on REST API - CRUD architecture, realized within Intive Patronage programme.

## Getting started

### Prerequisites

```
Gradle build tool
IDE, f.ex. IntelliJ IDEA
CURL
```

### Building the application

In order to build the application, you should create a project in your IDE, using Gradle and this repository source code.

```
Using IntelliJ IDEA:

1. Open: File -> New -> Project from Version Control -> Git.
2. Clone this repository URL.
3. Import Gradle Project.
4. Choose: Use default gradle wrapper (and Use auto-import).

The Project should have built automatically.
```

## Using the application

You are able to perform CRUD operations in Reservation System, using CURL tool instructions.

```
F.ex. to add an example Conference room to the System:

with your Reservation System application running, in your command line (f.ex. Git Bash shell for Windows) insert:

1. curl -d '{"name":"Organization example name"}' -H "Content-Type: application/json" -X POST localhost:8070/organizations

to add Organization to the System,

then insert:

2. curl -d '{"name": "Conference Room example name","optionalId": null,"floor": 10,"available": true,"sittingPlaces": 10,"standingPlaces": 10,"hangingPlaces": 10}' -H "Content-Type: application/json" -X POST localhost:8070/rooms/<the id number of Organization added earlier to the System>

You should be able to retrieve the related data by simple: curl localhost:8070/rooms.
```
