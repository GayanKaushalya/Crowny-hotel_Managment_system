Instructions
Open the README.md file in the root of your project (E:\crowny-hotel-app\README.md).
Copy and paste the entire content from the block below into that file.
Fill in your specific details where you see placeholders like [your_db_username] and [your_db_password].
Add, commit, and push the updated file to your GitHub repository.
code
Bash
# In your project root directory
git add README.md
git commit -m "docs: Simplify README to core instructions"
git push origin main
README.md Content to Copy
code
Markdown
# 🏨 Crowny Hotel Management System 🏨

A full-stack hotel booking application developed for our Higher National Diploma in Computer Science. This system allows guests to book rooms and enables administrators to manage hotel operations.

---

## 🚀 How to Install and Run This Project

Follow these instructions to get the project running on your local machine.

### Prerequisites

*   **Java JDK 17** or newer
*   **Apache Maven**
*   **Node.js and npm**
*   **MySQL** (or another relational database)

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
👥 Project Team Members
This project was created by:
Index Number	Name
KAHNDSE25.1F-006	R.M.O. Rathanayaka
KAHNDSE25.1F-029	S.K. Bandara
KAHNDSE25.1F-030	K.M.G.K Kulathunga
KAHNDSE25.1F-034	D.M.K.T.M Dissanayake
