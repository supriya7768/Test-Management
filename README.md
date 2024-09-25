# TestManagement


## Purpose of the project
TestManagement is a Spring Boot application for managing multiple-choice questions via REST API.

## Introduction
This project, TestManagement, is designed to facilitate the management of multiple-choice questions (MCQs) through a RESTful API. It allows users to perform CRUD operations on MCQ questions stored in a PostgreSQL database. Each question includes details such as category, options, correct answer, and scoring.

## Technologies Used
- Spring Boot 3 (latest)
- PostgreSQL
- Hibernate (as the JPA implementation)
- JUnit 5
- Graddle (for dependency management)
- Postman (for API testing)

## Setup Instructions
### Prerequisites
Make sure you have the following installed:
- Java 21 SDK
- Graddle
- PostgreSQL

### Clone the Repository
1. Clone the repository:
    ```bash
    git clone https://github.com/supriya77680/TestManagement.git
    ```

2. Navigate to the project directory:
    ```bash
    cd TestManagement
    ```

3. Checkout to main branch
   ```bash
   git checkout main
   ```

4. Build the application
   ```bash
   ./gradlew clean build
   ```

5. Run application
   ```bash
   ./gradlew bootRun
   ```
 
## Database Configuration

### Create Database

Create a PostgreSQL database named TestManagementDB.

### Configure Database Connection

Update `application.properties` file located in `src/main/resources` with your PostgreSQL database configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/TestManagementDB
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## API Endpoints

### Create MCQ Question

**POST** `(http://localhost:8080/api/question)`

Create a new MCQ question using JSON payload in the request body.

#### Example Request Body:

```json
{
    "question": "Which of the following are not OOPs pillars?",
    "option_one": "Abstraction",
    "option_two": "Inheritance",
    "option_three": "static",
    "option_four": "Polymorphism",
    "correct_option": "static",
    "positive_mark": "3",
    "negative_mark": "-1",
    "subcategory": {
        "subcategory_name": "OOPs",
        "subcategory_description": "OOPs for Java",
        "category": {
            "category_name": "Java",
            "category_description": "Core Java Category"
        }
    }
}

```
## API Endpoints

### Read All Questions

**GET** `(http://localhost:8080/api/question)`

Retrieve all MCQ questions stored in the database.

### Read Specific Question

**GET** `(http://localhost:8080/api/question/{id})`

Retrieve a specific MCQ question by its `questionId`.

### Update Question

**PUT** `(http://localhost:8080/api/question/{id})`

Update an existing MCQ question identified by `questionId` using JSON payload in the request body.

### Delete Question

**DELETE** `(http://localhost:8080/api/question/{id})`

Delete an existing MCQ question identified by `questionId`.

### Create MCQ Question

**POST** `(http://localhost:8080/api/question/upload)`

Create a new MCQ question using excel file. That means we can enter question in database using excel sheet provided
            

# Category and Subcategory Operations

## Category CRUD Operations

### Create Category

**POST** `http://localhost:8080/api/category`

Create a new category using JSON payload in the request body.

#### Example Request Body:

```json
{
    "categoryName": "Spring Core",
    "categoryDescription": "Spring Core category"
}
```

### Read All Categories

**GET** `http://localhost:8080/api/category`

Retrieve all categories stored in the database.

### Update Category

**PUT** `http://localhost:8080/api/category/{id}`

Update an existing category identified by categoryId using JSON payload in the request body.

### Delete Category

**DELETE** `http://localhost:8080/api/categaory/{id}`

Delete an existing category identified by categoryId.

## Subcategory CRUD Operations

### Create Subcategory

**POST** `http://localhost:8080/api/subcategory`

Create a new subcategory using JSON payload in the request body.

#### Example Request Body:

```json
{
    "subcategoryName":"Annotations",
    "subcategoryDescription":"Annotation category",
    "category":{
    "categoryName": "Spring Core",
   "categoryDescription": "Springboot Framework category"
    }
}
```

### Read All Subcategories

**GET** `http://localhost:8080/api/subcategory`

Retrieve all subcategories stored in the database.

### Update Subcategory

**PUT** `http://localhost:8080/api/subcategory/{id}`

Update an existing subcategory identified by subcategoryId using JSON payload in the request body.

### Delete Subcategory

**DELETE** `http://localhost:8080/api/subcategory/{id}`

Delete an existing subcategory identified by subcategoryId.






