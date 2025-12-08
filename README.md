# 💻 Programming Challenge Platform

This is a **Java-based Programming Challenge Platform** that allows users to participate in quizzes and coding challenges. The platform includes features for managing user progress, medals, and coding tasks. Users can choose languages (Python, C++, Java) to solve problems interactively.

---

## 💡 Features

### 👨‍💼 Admin Panel
- Add, update, and delete coding challenges.
- View users' quiz and challenge progress.
- Manage medal counts.

### 👥 User Panel
- Sign up and log in securely.
- Browse available quizzes and coding challenges.
- Submit solutions in C, C++, Java, and Python.
- View results and real-time feedback.
- Track progress and medals earned.

### ⭐ Special Features
- **Randomized Quizzes** from uncompleted sets.
- **Multilanguage Code Execution** with `ProcessBuilder`.
- **Auto Code Validation** against test cases.
- **Session Handling** and security features.
- **Dynamic Difficulty** adjustment based on user progress.

---

## 📁 File Structure

- `ProgrammingChallengePlatform.java` – Main backend controller.
- `User.java`, `SessionManager.java` – User login/session management.
- `Challenge.java`, `Quiz.java` – Handles challenges and quizzes.
- `testcases/` – Stores test cases for each coding challenge.
- `quizdata/` – JSON or DB-backed quiz question storage.

---

## 🔧 Technologies Used

- **Language:** Java (Servlets, JDBC)
- **Frontend:** HTML, CSS, JavaScript (minimal)
- **Database:** MySQL
- **Concepts:** OOP, File Handling, Code Execution, JSON
- **Security:** Email, phone, and DOB validation

---

## 📸 Sample Screens

> _Run on localhost using a Java-supported IDE or deploy to a servlet container like Apache Tomcat._

---

## 🧠 Concepts Demonstrated

- Classes and Objects
- Inheritance and Polymorphism
- File Handling & JSON Parsing
- ProcessBuilder for code execution
- Servlet session management
- MySQL integration using JDBC

---

## 🚀 Getting Started

```bash
1. Clone this repository:
   git clone https://github.com/your-username/programming-challenge-platform.git

2. Set up MySQL and import tables from database.sql.

3. Open the project in Eclipse or IntelliJ.

4. Configure Tomcat server and run the project.

5. Visit: http://localhost:8080/ProgrammingPlatform/
```
---

