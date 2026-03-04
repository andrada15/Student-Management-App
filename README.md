Student Management Application

## Project Overview
This project is a secure backend REST API developed with Spring Boot, designed to streamline and automate administrative workflows within an educational institution. The application serves as a centralized platform for managing student records, handling secure document transfers, and facilitating automated communication between the system and its users. By replacing manual data entry with scheduled processes and secure endpoints, the system reduces administrative overhead and ensures data consistency.

## Architecture and Core Features
The architecture is built around a secure, role-based access control system, ensuring that sensitive student information and administrative endpoints are only accessible to authorized personnel. Persistent data storage is handled via a relational SQL database, structured to maintain data integrity across multiple user roles and entities.

To automate routine administrative duties, the application integrates the Quartz Job Scheduler. This allows for the execution of background tasks, such as generating periodic reports or executing database maintenance, without requiring manual intervention. Furthermore, an automated email notification service is integrated to keep users proactively informed about critical updates, registration statuses, or system alerts. 

The application also includes a robust file management module. This feature provides secure upload and download capabilities, allowing administrators and students to safely exchange academic documents, assignments, and official records directly through the API.

## Technical Stack
* Framework: Java, Spring Boot
* Security: Authorization & Authentication Implementation
* Database: Relational SQL Database
* Task Scheduling: Quartz Job Scheduler
* Additional Features: File Upload/Download API, SMTP Email Notifications


### Prerequisites
* Java 17 or higher
* Configured SQL Database (e.g., MySQL, PostgreSQL)
* Maven or Gradle

### Installation
1. Clone the repository:
   ```bash
   git clone [https://github.com/andrada15/Student-Management-App.git](https://github.com/andrada15/Student-Management-App.git)
