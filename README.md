# 🏨 Crowny Hotel Management System 🏨

A full-stack hotel booking application developed for our Higher National Diploma in Computer Science. This system allows guests to book rooms and enables administrators to manage hotel operations.

---

## 🚀 How to Install and Run This Project

Follow these instructions to get the project running on your local machine.

### Prerequisites

- **Java JDK 17** or newer
- **Apache Maven**
- **Node.js and npm**
- **MySQL** (or another relational database)

---

### 1. Get the Code

First, clone the project repository from GitHub to your computer.

```bash
git clone https://github.com/GayanKaushalya/Crowny-hotel_Managment_system.git
cd Crowny-hotel_Managment_system

2. Set Up the Backend (Spring Boot)
Navigate to the Backend Folder:
code
Bash
cd Backend
Configure the Database:
Open the file: src/main/resources/application.properties
Update the following lines with your own database details. You must create a database named crowny_hotel_db first.

Set Up the Database

You have two options to set up the database. The recommended method is to restore from the provided backup file.

Option - Restore from Backup (Recommended)

This is the fastest way to get the exact database structure and sample data.

Make sure your MySQL server is running.

Unzip the backup file located in the /Database folder of this project. You should get a file named crowny_hotel_dump.sql.

Log in to MySQL and create a new, empty database:

code
SQL
download
content_copy
expand_less
IGNORE_WHEN_COPYING_START
IGNORE_WHEN_COPYING_END
CREATE DATABASE crowny_hotel_db;

From your command line (CMD or Terminal), run the following command to import the data from the dump file. You will be prompted for your MySQL password.

code
Bash
download
content_copy
expand_less
IGNORE_WHEN_COPYING_START
IGNORE_WHEN_COPYING_END
mysql -u [your_db_username] -p crowny_hotel_db < Database/crowny_hotel_dump.sql

Replace [your_db_username] with your actual MySQL username (e.g., root).


code
Properties
spring.datasource.url=jdbc:mysql://localhost:3306/crowny_hotel_db
spring.datasource.username=[your_db_username]
spring.datasource.password=[your_db_password]
Run the Backend Server:
code
Bash
mvn spring-boot:run
✅ The backend will now be running at http://localhost:8080

3. Set Up the Frontend (React)
Open a new terminal window.
Navigate to the Frontend Folder:
(From the project's root directory)
code
Bash
cd frontend
Install All Dependencies:
(This might take a minute)
code
Bash
npm install
Run the Frontend App:
code
Bash
npm start
✅ The website will open automatically in your browser at http://localhost:3000


📖 API Documentation with Swagger
To facilitate testing and exploration of the API, this project uses Swagger UI. Swagger provides interactive API documentation, allowing you to view and test every endpoint directly from your browser.
How to Access the Swagger UI
    1.Ensure the backend Spring Boot application is running.
    2.Open your web browser and navigate to the following URL:
    http://localhost:8080/swagger-ui.html
    3.You will see a complete list of all available API endpoints. You can expand each one to see details and use the "Try it out" button to send live API requests.


👥 Project Team Members
This project was created by:
Index Number	Name
KAHNDSE25.1F-006	R.M.O. Rathanayaka
KAHNDSE25.1F-029	S.K. Bandara
KAHNDSE25.1F-030	K.M.G.K Kulathunga
KAHNDSE25.1F-034	D.M.K.T.M Dissanayake
```
