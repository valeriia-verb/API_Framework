# API_Framework

This project is a RestAssured-based API testing framework built with Java and JUnit 5. It is designed to automate API testing and provide a robust structure for creating and managing API tests.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Restful_Booker](#restful-booker)
- [Usage](#usage)
- [Packages and Files](#packages-and-files)
- [SexOffender](#sexoffender)
- [Contributing](#contributing)

## Prerequisites
- **Java JDK 11** or higher
- **Maven**: Apache Maven 3.6.3 or higher


## Installation
1. **Clone the repository**:
    ```bash
    git clone https://github.com/valeriia-verb/API_Framework.git
    cd API_Framework
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the tests**:
    ```bash
    mvn test
    ```
    
## Restful_Booker

***API Overview***
This project is a open-source API for a booking functionality (hotel etc.), that supports CRUD calls and requires Basic Auth for selected requests (PUT, PATCH, DELETE). 

- **Notes from the creator**:
  - Welcome to Restful-booker an API that you can use to learn more about API Testing or try out API testing tools against.
  - Restful-booker is a Create Read Update Delete Web API that comes with authentication features and loaded with a bunch of bugs for you to explore.
  - The API comes pre-loaded with 10 records for you to work with and resets itself every 10 minutes back to that default state.
  - Restful-booker also comes with detailed API documentation to help get you started with your API testing straight away.

## Usage
1. **Documentation**: [https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking)
2. **Run the automation script**: The RestAssured script will interact with the api to verify responses.

## Packages and Files

### `src/test/java`

- **`com.restful_booker.pojo`**: Contains POJO classes for deserialization.
    - **`Booking.java`**: Represents the middle part of nested JSON response body that includes firstname, lastname, totalprice, depositpaid, BookingDates object and additionalneeds.
    - **`BookingDates.java`**: Represents the last part of nested JSON response body that includes checkin and checkout.
    - **`BookingResponse.java`**: Represents the outer part of nested JSON response body that includes bookingId and Booking object.

- **`com.restful_booker.tests`**: Contains the test classes for Junit.
    - **`CreateBookingTest.java`**: Contains automation scripts for POST, PUT, DELETE as well as negative scenarios.
    - **`DDT_ParameterizedTest.java`**: Contains automation scripts for POST and PUT using @ParameterizedTest from JUnit5 and CSV/Excel files as test data source.
    - **`GetBookingIdTest.java`**: Contains automation scripts for GET including retrieving all id's and retrieving specific booking by ID.

- **`com.restful_booker.utilities`**: Contains utility classes used across the module.
    - **`ExcelUtil.java`**: Provides Excel-related utilities.
    - **`TestBase.java`**: Contains @BeforeAll where baseURI is passed and @AfterAll where all values are set back to default.

### `src/test/resources`

 - **`bookings.csv`**: CSV file containing 10 valid data sets used for creating a booking.
 - **`updated_bookings.xlsx`**: Excel containing 10 valid data sets for updating a booking.

### `pom.xml`

- Maven configuration file managing the project dependencies and build process.

## SexOffender

***API Overview***
Nationalwide Sex Offender API, Extensive database of National Registered Sex Offenders API for the United States. 
Supports both criteria-based search (name, nicknames [e.g. Jon vs Jonathan], city, zip, state) and geospatial search (lat/lng, radius).

***Usage***
1. **Documentation**: [https://rapidapi.com/offenders-offenders-default/api/sex-offenders)
2. **Run the automation script**: The RestAssured script will interact with the api to verify responses.

***Packages and Files***

### `src/test/java`

- **`com.offenders.tests`**: Contains the test classes for Junit.
    - **`GetRecordsTest.java`**: Contains automation scripts for retrieving sex offenders' records by their name or by the city name.
    - **`SchemaValidation.java`**: Contains automation scripts for schema validation including matching and not matching one (a bug).
    
- **`com.restful_booker.utilities`**: Contains utility classes used across the module.
    - **`TestBase.java`**: Contains @BeforeAll where baseURI is passed and @AfterAll where all values are set back to default.

### `src/test/resources`

 - **`schema.json`**: JSON file that contains schema for /sexoffender endpoint.

### `pom.xml`

- Maven configuration file managing the project dependencies and build process.

## Contributing
1. **Fork the repository**.
2. **Create a new branch**: `git checkout -b feature/your-feature-name`.
3. **Commit your changes**: `git commit -m 'Add some feature'`.
4. **Push to the branch**: `git push origin feature/your-feature-name`.
5. **Open a pull request**.
