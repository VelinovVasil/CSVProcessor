# CSV Processing API
## The CSV Processing API is a Spring Boot application that provides endpoints to retrieve and analyze cost data from a large CSV file. It enables users to calculate total and grouped costs, and to search records based on specific labels and country information.

## Features
Total Cost Calculation: Calculate the total cost based on optional filters such as start time, end time, location, and SKU ID.
Grouped Cost Calculation: Group total costs by optional criteria like date, country, or service.
Record Search: Paginated search of records based on label key-value pairs and country.
## API Documentation
The API documentation is accessible through Swagger:

Swagger UI: http://localhost:8080/swagger-ui/index.html#/
## Getting Started
## Prerequisites
Java 23
Maven for building the project
Docker (optional, for containerization)

## Getting started
Clone this repository locally on your computer, then build and run the application. The empty costs_export.csv file is located in the `src/main/resources/static` directory.

## API Endpoints
### Total Cost
Endpoint: /api/csv/total-cost
Method: GET
Parameters:
startTime (optional): Filter by start date.
endTime (optional): Filter by end date.
location (optional): Filter by location.
skuId (optional): Filter by SKU ID.
Description: Calculates the total cost based on the specified parameters.
### Grouped Cost
Endpoint: /api/csv/grouped-cost
Method: GET
Parameters:
date (boolean): Group costs by date.
country (boolean): Group costs by country.
service (boolean): Group costs by service.
Description: Returns the total cost grouped by the specified criteria.
### Search by Label and Country
Endpoint: /api/csv/search
Method: GET
Parameters:
labelKeyValue (optional): Search by label key-value pair.
country (optional): Filter by country.
pageSize: Number of results per page.
pageNumber: Page number for paginated results.
Description: Searches records based on label and country, with pagination support.

## Speed and memory optimisation
The app reads and parses the dataset only once. Then, the information is saved in a List to prevent reading the file again. When accessing the parsed records, the List is parallel streamed `.parallelStream()` to save time compared to `.stream()`.
