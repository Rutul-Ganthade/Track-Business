# Easy Business Billing and Stock Management

## 📌 About the Project
This is a fully functional **Billing and Stock Management System** designed for startups, small businesses, and students looking to present it as a **minor or major academic project**.  
It offers a ready-to-use solution for managing sales, billing, and inventory efficiently.

---

## 🚀 Features
- **User Authentication** (Admin login)
- **Billing System** with auto-calculation
- **Stock Management** for adding, updating, and tracking products
- **MySQL Database Integration**
- **Cross-Platform Compatibility** (works with VS Code, IntelliJ, NetBeans, etc.)

---

## 🛠️ Prerequisites
Before running the project, ensure you have the following installed:
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- Java JDK (version 8 or later)
- Your preferred Java IDE (VS Code, IntelliJ, NetBeans, etc.)

---

## 📂 Project Setup Guide

### **Step 1: Create the Database**
1. Open **MySQL Workbench** and log in as the root user.
2. Copy the SQL script from `SQL.txt`.
3. Paste it into the SQL editor and execute it to create the database.

---

### **Step 2: Add User Data**
1. Open the `ADD_User.txt` file.
2. Copy and execute the SQL commands in MySQL Workbench.
3. This will insert the required default user credentials.

---

### **Step 3: Configure Database Credentials**
1. Open `src/DB.java`.
2. Locate line 13:
   ```java
   private static final String PASS = "Your Own password here";
   ```
3. Replace `"Your Own password here"` with your **MySQL root password**.

---

### **Step 4: Add Required Libraries**
- Navigate to the `Jar` folder in the project directory.
- Add all `.jar` files to your project's library dependencies.  
  *(The process depends on your IDE — search for tutorials based on your platform: VS Code, IntelliJ, NetBeans, etc.)*

---

### **Step 5: Run the Project**
1. Open and run `src/Login.java`.
2. Use the following default credentials:
   ```
   Username: admin
   Password: admin
   ```

---

## 📌 Notes
- Ensure MySQL server is running before launching the application.
- You can modify the default admin username and password in the database for security purposes.

---

## 👨‍💻 Author
Developed as a functional academic and business solution for billing and inventory management.
