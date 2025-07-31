# Server Requirements Gathered

## Objective

Create a RESTful API backend for a Digital Video Store Web Application using Spring Boot and MongoDB. This is a continuation of Assignment 1, building only the RESTful API without connecting to the React Front-End app.

## Submission Requirements

- [ ] Source code and GitHub link uploaded to the Blackboard

## Technical Requirements

- [ ] API must be created using Spring Boot framework
- [ ] Must connect to MongoDB database
- [ ] Use Spring Web Dependency
- [ ] Use Spring Data MongoDB dependency

## Database Requirements

- [ ] Setup and configure MongoDB cloud service using MongoDB Atlas
- [ ] Connect API to MongoDB database using Spring Data MongoDB dependency
- [ ] Name database and collections appropriately

## Application Architecture

- [ ] Must follow MVC (with service layer) design pattern
- [ ] Create Controller classes
- [ ] Create Service Classes
- [ ] Create Model or Repository Classes

## Endpoints

All endpoints must return pertinent JSON data and a status code.

### Customer Endpoints

- [ ] **POST** - User registration endpoint
  - [ ] Accept same data (in JSON) from Assignment 1's registration form
  - [ ] Insert data into MongoDB database
  - [ ] Send appropriate status code and message (in JSON) for successful operations
  - [ ] Provide validation logic (missing data, data in incorrect format, etc.)
  - [ ] Return appropriate status code and messages for validation failures
  - [ ] Store passwords in encrypted format (use Bcrypt Library or any encryption library)

- [ ] **GET** - Retrieve specific customer endpoint
  - [ ] Retrieve specific customer and all associating information
  - [ ] Provide validation logic for endpoints that do not contain valid customer id

### Movie and TV Show Endpoints

- [ ] **POST** - Create movies/tv shows endpoint
  - [ ] Movie or TV Show name
  - [ ] Movie/TV Show price
  - [ ] Synopsis of the movie or tv show
  - [ ] A value to represent if the item is a movie or tv show
  - [ ] Movie/TV show small poster (image path)
  - [ ] Movie/TV show large poster (image path)
  - [ ] The price to rent the movie or tv show
  - [ ] The price to purchase the movie or tv show outright
  - [ ] A field to determine if the movie or tv show is a featured movie or tv show

- [ ] **GET** - Retrieve all movies endpoint

- [ ] **GET** - Retrieve all tv shows endpoint

- [ ] **GET** - Search by title endpoint
  - [ ] Allow user to supply a title of a movie and/or tv show
  - [ ] Return a list of movies and/or tv shows that CONTAINS the supplied title

- [ ] **GET** - Retrieve featured movies endpoint
  - [ ] Must contain a query string

- [ ] **GET** - Retrieve featured tv shows endpoint
  - [ ] Must contain a query string

- [ ] **GET** - Retrieve specific movie or tv show endpoint
  - [ ] Provide validation logic for endpoints that do not contain valid movie id

- [ ] **PUT** - Update existing movie endpoint
  - [ ] Update and change existing movie in database (e.g., changing movie rent prices)
  - [ ] Provide validation logic for endpoints that do not contain valid movie id and missing incoming data

- [ ] **DELETE** - Delete existing movie or tv show endpoint
  - [ ] Provide validation logic for endpoints that do not contain valid movie id

### User and Authentication Endpoints

- [ ] **POST** - Create users endpoint
  - [ ] First Name
  - [ ] Last Name
  - [ ] Email
  - [ ] Password

- [ ] **POST** - Authenticate user endpoint
  - [ ] Successful authentication when email and password pair exists in database
  - [ ] Unsuccessful authentication when email and password pair does not exist in database

## Testing Requirements

- [ ] Test all endpoints using Postman or any other API Client
- [ ] Ensure each endpoint is functional and works
- [ ] Ensure ALL endpoints return pertinent JSON data and a status code

## Tools Required

- [ ] Spring Boot Java Framework
- [ ] Spring initializer tool
- [ ] MongoDB Atlas
- [ ] Spring Data MongoDB dependency
- [ ] Bcrypt Library (or any encryption library)
