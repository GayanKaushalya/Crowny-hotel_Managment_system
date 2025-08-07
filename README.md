\# Crowny Hotel Management System: Full-Stack Application



!\[Java](https://img.shields.io/badge/Java-17-blue)

!\[Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)

!\[React](https://img.shields.io/badge/React-18-blueviolet)

!\[Database](https://img.shields.io/badge/Database-MySQL-orange)

!\[License](https://img.shields.io/badge/License-MIT-green.svg)



This repository contains the source code for the \*\*Crowny Hotel Management System\*\*, a full-stack web application developed as a major coursework project for the Higher National Diploma in Computer Science. The project demonstrates a comprehensive understanding of modern software development principles, including N-tier architecture, RESTful API design, and user-centric frontend development.



---



\## Table of Contents



1\.  \[\*\*Project Overview\*\*](#1-project-overview)

2\.  \[\*\*Live Demo \& Screenshots\*\*](#2-live-demo--screenshots)

3\.  \[\*\*Core Features\*\*](#3-core-features)

&nbsp;   -   \[Guest Functionality](#guest-functionality)

&nbsp;   -   \[Administrator Functionality](#administrator-functionality)

4\.  \[\*\*System Architecture \& Design\*\*](#4-system-architecture--design)

&nbsp;   -   \[Architectural Model](#architectural-model)

&nbsp;   -   \[Technology Stack Justification](#technology-stack-justification)

&nbsp;   -   \[Database Schema](#database-schema)

&nbsp;   -   \[REST API Endpoints](#rest-api-endpoints)

5\.  \[\*\*Installation \& Local Setup Guide\*\*](#5-installation--local-setup-guide)

&nbsp;   -   \[Prerequisites](#prerequisites)

&nbsp;   -   \[Step 1: Cloning the Repository](#step-1-cloning-the-repository)

&nbsp;   -   \[Step 2: Backend Configuration \& Launch](#step-2-backend-configuration--launch)

&nbsp;   -   \[Step 3: Frontend Configuration \& Launch](#step-3-frontend-configuration--launch)

6\.  \[\*\*Usage Guide\*\*](#6-usage-guide)

7\.  \[\*\*Project Team\*\*](#7-project-team)

8\.  \[\*\*Future Enhancements\*\*](#8-future-enhancements)

9\.  \[\*\*Contact\*\*](#9-contact)

10\. \[\*\*License\*\*](#10-license)



---



\## 1. Project Overview



The Crowny Hotel Management System is an enterprise-level application designed to automate and simplify hotel operations. It provides a seamless digital experience for both guests and hotel administrators through two distinct interfaces: a public-facing portal for booking and a secure administrative dashboard for management. The primary goal is to create a robust, scalable, and secure platform that centralizes room management, booking processing, and user administration.



\## 2. Live Demo \& Screenshots



\*\*\[Note: Insert a link to a live demo if available]\*\*



Below are screenshots demonstrating the key functionalities of the user interface.



\*   \*(Placeholder for Screenshot 1: Homepage / Room Search)\*

\*   \*(Placeholder for Screenshot 2: Admin Dashboard - Room Management)\*

\*   \*(Placeholder for Screenshot 3: Booking Confirmation Page)\*



\## 3. Core Features



\### Guest Functionality



\*   \*\*User Authentication\*\*: Secure registration and login capabilities for guests to manage their profiles and bookings.

\*   \*\*Dynamic Room Search\*\*: Guests can search for available rooms based on check-in and check-out dates, ensuring real-time availability.

\*   \*\*Detailed Room Listings\*\*: View comprehensive details for each room, including high-quality photos, descriptions, capacity, and pricing.

\*   \*\*Seamless Booking Process\*\*: An intuitive, multi-step booking form that securely captures guest information and processes reservations.

\*   \*\*Booking Confirmation\*\*: Upon successful booking, guests receive a unique confirmation code for future reference.

\*   \*\*Personal Booking History\*\*: Registered users can view, manage, and cancel their upcoming and past bookings through their personal dashboard.



\### Administrator Functionality



\*   \*\*Secure Admin Authentication\*\*: Role-based access control ensures only authorized personnel can access the management dashboard.

\*   \*\*Comprehensive Booking Management\*\*: View a complete, filterable list of all bookings in the system. Admins can also cancel bookings on behalf of guests.

\*   \*\*Full CRUD Operations for Rooms\*\*: Admins have complete control over the hotel's inventory.

&nbsp;   \*   \*\*Create\*\*: Add new rooms with details like type, price, description, and upload multiple photos.

&nbsp;   \*   \*\*Read\*\*: View all existing rooms in a centralized table.

&nbsp;   \*   \*\*Update\*\*: Edit the details of any existing room.

&nbsp;   \*   \*\*Delete\*\*: Remove rooms that are no longer in service.

\*   \*\*User Management\*\*: View a list of all registered users in the system.



\## 4. System Architecture \& Design



\### Architectural Model



The application is built upon a robust \*\*Three-Tier Architecture\*\*, promoting a clean separation of concerns:



1\.  \*\*Presentation Layer (Frontend - React)\*\*: A standalone Single Page Application (SPA) responsible for rendering the user interface and managing user interaction. It communicates with the backend via stateless REST API calls.

2\.  \*\*Business Logic Layer (Backend - Spring Boot Service Layer)\*\*: Contains all the core business logic, validation, and orchestration. It processes requests from the frontend, enforces business rules (e.g., a room cannot be double-booked), and coordinates with the data access layer.

3\.  \*\*Data Access Layer (Backend - Spring Data JPA Repository Layer)\*\*: Manages all database interactions. It uses Spring Data JPA and Hibernate to map Java objects to database tables, abstracting away complex SQL queries.



\### Technology Stack Justification



| Component | Technology / Library        | Justification                                                                                                                 |

| :-------- | :-------------------------- | :---------------------------------------------------------------------------------------------------------------------------- |

| \*\*Backend\*\*   | \*\*Spring Boot 3\*\*           | Provides a powerful, convention-over-configuration framework for building production-grade, standalone Java applications quickly. |

|           | \*\*Spring Security (JWT)\*\*   | Chosen for its robust, industry-standard implementation of authentication and authorization, secured with stateless JSON Web Tokens. |

|           | \*\*Spring Data JPA/Hibernate\*\* | Simplifies data access by providing a high-level abstraction over JDBC and reducing boilerplate code for database operations.      |

| \*\*Frontend\*\*  | \*\*React 18\*\*                | A declarative and component-based library that enables the creation of complex, interactive UIs with excellent performance.        |

|           | \*\*React Router\*\*            | The standard for client-side routing in React, enabling a seamless multi-page experience within a Single Page Application.        |

|           | \*\*Axios\*\*                   | A promise-based HTTP client that simplifies making asynchronous requests to the backend REST API.                             |

| \*\*Database\*\*  | \*\*MySQL / PostgreSQL\*\*      | A reliable, open-source relational database management system chosen for its performance, scalability, and strong community support. |

| \*\*Build\*\*     | \*\*Apache Maven\*\*            | Manages the project's build lifecycle and dependencies for the backend, ensuring consistency across development environments. |



\### Database Schema



The core of the database consists of three main entities: `User`, `Room`, and `Booking`.



\*   \*\*User\*\*: Stores user information, including credentials and roles (e.g., `ROLE\_USER`, `ROLE\_ADMIN`).

\*   \*\*Room\*\*: Contains all details related to a hotel room, such as type, price, and photos.

\*   \*\*Booking\*\*: A transactional entity that links a `User` to a `Room` for a specific date range, containing the check-in/out dates and a unique confirmation code.



\*(Suggestion: An Entity-Relationship Diagram (ERD) image would be highly beneficial here.)\*



\### REST API Endpoints



The backend exposes a comprehensive set of RESTful endpoints. Below is a summary of the main resources.



| Method | Endpoint                    | Description                                       | Access      |

| :----- | :-------------------------- | :------------------------------------------------ | :---------- |

| `POST` | `/auth/register`            | Registers a new user account.                     | Public      |

| `POST` | `/auth/login`               | Authenticates a user and returns a JWT.           | Public      |

| `GET`  | `/rooms/all`                | Retrieves a list of all rooms.                    | Public      |

| `GET`  | `/rooms/room/{id}`          | Retrieves details for a single room.              | Public      |

| `POST` | `/rooms/add`                | Adds a new room to the system.                    | Admin       |

| `PUT`  | `/rooms/update/{id}`        | Updates an existing room.                         | Admin       |

| `DELETE`| `/rooms/delete/{id}`       | Deletes a room.                                   | Admin       |

| `POST` | `/bookings/book-room/{id}`  | Books a room for a given user and date range.     | Authenticated |

| `GET`  | `/bookings/all`             | Retrieves all bookings in the system.             | Admin       |

| `GET`  | `/bookings/confirmation/{code}` | Retrieves booking details using a confirmation code. | Public      |

| `DELETE`| `/bookings/cancel/{id}`    | Cancels a booking.                                | Admin/Owner |



\## 5. Installation \& Local Setup Guide



\### Prerequisites



Ensure the following software is installed on your local development machine:

\- \*\*Java Development Kit (JDK)\*\*: Version 17 or higher.

\- \*\*Apache Maven\*\*: Version 3.8 or higher.

\- \*\*Node.js\*\*: Version 18.x or higher (which includes `npm`).

\- \*\*MySQL Server\*\*: Version 8.0 or higher.



\### Step 1: Cloning the Repository

```bash

git clone https://github.com/GayanKaushalya/Crowny-hotel\_Managment\_system.git

cd Crowny-hotel\_Managment\_system

```



\### Step 2: Backend Configuration \& Launch (Spring Boot)

1\.  \*\*Navigate to the Backend Directory:\*\*

&nbsp;   ```bash

&nbsp;   cd Backend

&nbsp;   ```

2\.  \*\*Create and Configure the Database:\*\*

&nbsp;   - Open your MySQL client and create a new database.

&nbsp;     ```sql

&nbsp;     CREATE DATABASE crowny\_hotel\_db;

&nbsp;     ```

3\.  \*\*Update Application Properties:\*\*

&nbsp;   - Open the file `src/main/resources/application.properties`.

&nbsp;   - Modify the `spring.datasource.\*` properties to match your local MySQL setup.

&nbsp;     ```properties

&nbsp;     # \[Customize if needed]

&nbsp;     spring.datasource.url=jdbc:mysql://localhost:3306/crowny\_hotel\_db

&nbsp;     spring.datasource.username=root

&nbsp;     spring.datasource.password=your\_mysql\_password

&nbsp;     spring.jpa.hibernate.ddl-auto=update

&nbsp;     ```

4\.  \*\*Build and Run the Application:\*\*

&nbsp;   - The Maven wrapper will download dependencies and launch the server.

&nbsp;     ```bash

&nbsp;     mvn clean spring-boot:run

&nbsp;     ```

&nbsp;   - The backend will start on `http://localhost:8080`.



\### Step 3: Frontend Configuration \& Launch (React)

1\.  \*\*Open a new terminal window.\*\* Navigate to the frontend directory from the project root.

&nbsp;   ```bash

&nbsp;   cd ../frontend 

&nbsp;   # Or from a new terminal: cd path/to/Crowny-hotel\_Managment\_system/frontend

&nbsp;   ```

2\.  \*\*Install Node Modules:\*\*

&nbsp;   - This command installs all frontend dependencies listed in `package.json`.

&nbsp;     ```bash

&nbsp;     npm install

&nbsp;     ```

3\.  \*\*Start the Development Server:\*\*

&nbsp;   - This will launch the React application in your browser.

&nbsp;     ```bash

&nbsp;     npm start

&nbsp;     ```

&nbsp;   - The frontend will be accessible at `http://localhost:3000`.



\## 6. Usage Guide



\- Once both servers are running, navigate to `http://localhost:3000`.

\- You can register a new user account or log in as an administrator.

\- \*\*Default Admin Credentials\*\*: To use admin features, you may need to manually set a user's role to `ROLE\_ADMIN` in the `users` table in your database.



\## 7. Project Team



This project was proudly developed by:



| Index Number      | Name                 |

| :---------------- | :------------------- |

| `KAHNDSE25.1F-006`  | R.M.O. Rathanayaka   |

| `KAHNDSE25.1F-029`  | S.K. Bandara         |

| `KAHNDSE25.1F-030`  | K.M.G.K Kulathunga   |

| `KAHNDSE25.1F-034`  | D.M.K.T.M Dissanayake|



\## 8. Future Enhancements



\- \*\*Payment Gateway Integration\*\*: Integrate Stripe or PayPal to handle real-time payments for bookings.

\- \*\*Email Notifications\*\*: Send automated confirmation and reminder emails to guests.

\- \*\*Advanced Analytics Dashboard\*\*: Provide admins with charts and reports on occupancy rates and revenue.

\- \*\*Room Service Module\*\*: Allow guests to order room service through the application.



\## 9. Contact



For any inquiries or questions regarding this project, please feel free to contact:

\- \*\*Phone No.\*\*: `\[0778974625]`



\## 10. License



This project is open-source and licensed under the \*\*MIT License\*\*. See the `LICENSE` file for more details.

