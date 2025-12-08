# Employee Management System (EMS)

A complete Employee Management System built with **Spring Boot 3**, **Spring Security 6**, **Thymeleaf**, and **MySQL**.  
The system provides two dashboards — **Admin** and **Employee** — with rich features for real-world usage.

---

## 🚀 Features

### 👨‍💼 Admin Features
- Manage Employees (Create, Update, Delete)
- Company Information Management
- Approve/Reject Leave Requests
- Generate Payroll for Employees
- View Payroll History
- Attendance Reports
- Secure Admin Dashboard

### 👨‍🔧 Employee Features
- View Profile
- Mark Attendance
- Apply for Leave
- View Leave Status
- View Payroll History

---

## 🏗️ Project Structure (Simple Overview)

```
src/
 ├── main/
 │   ├── java/com/example/ems/
 │   │   ├── Controller/     → All controllers
 │   │   ├── Model/          → Entities (User, Employee, Payroll…)
 │   │   ├── Repository/     → Spring Data JPA Repos
 │   │   ├── Service/        → Business logic
 │   │   ├── Security/       → Spring Security config
 │   │   └── EmsApplication  → Main class
 │   └── resources/
 │       ├── templates/      → Thymeleaf HTML files
 │       ├── static/         → CSS, JS
 │       └── application.properties
```

---

## 🛠️ Technologies Used

- **Java 21**
- **Spring Boot 3.2**
- **Spring Security 6**
- **Spring Data JPA**
- **Thymeleaf**
- **MySQL**
- **Tailwind CSS**

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the Project
```
git clone https://github.com/your-repo/ems.git
cd ems
```

### 2️⃣ Configure MySQL
Create a database:
```
CREATE DATABASE ems;
```

### 3️⃣ Update `application.properties`
```
spring.datasource.url=jdbc:mysql://localhost:3306/ems
spring.datasource.username=your_user
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
```

### 4️⃣ Run the Project
```
mvn spring-boot:run
```

Server starts at:
```
http://localhost:3030
```

---

## 🔐 Default Login (Example)
| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |

---

## 📁 Important Modules

### 🔹 Authentication
- Spring Security with Role-based access (ADMIN, EMPLOYEE)

### 🔹 Employee Management
- CRUD operations
- Linked with User Login system

### 🔹 Payroll Module
- Generate monthly salary
- View payroll history
- Automatic net salary calculation

### 🔹 Company Information
Admin can update:
- Company name  
- Address  
- Contact details  

### 🔹 Leave Management
- Employee applies leave
- Admin approves/rejects

---

## 🖼️ UI Overview (Simple)
- TailwindCSS based modern clean UI
- Separate dashboards
- Responsive design

---

## 📌 Future Enhancements
- Add PDF salary slip download
- Add email notifications
- Add department-wise reports

---

## 🤝 Contributing
Feel free to fork and contribute!

---

## 📜 License
This project is free for educational and personal use.
