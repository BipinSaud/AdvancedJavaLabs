# Student Details CRUD – JSP + JDBC (Maven, MySQL)

This Maven project is configured to use **MySQL on localhost** with **user: root / password: root**.
It creates the database **studentdb** and the **students** table automatically on first load.

## Run in IntelliJ Community with Smart Tomcat (GUI-only)

1. Open the folder in IntelliJ → let Maven import.
2. Build once:
   ```bash
   mvn clean package
   ```
   This creates:
   - WAR: `target/student-crud-jsp.war`
   - **Exploded dir**: `target/student-crud-jsp/`
3. Smart Tomcat run configuration:
   - **Tomcat Home**: your Tomcat install (must contain `lib/catalina.jar`)
   - **Tomcat Base**: an empty folder (plugin will populate it)
   - **JRE**: JDK 19
   - **Deployment → + Directory**
     - **DocBase**: `<project>/target/student-crud-jsp`
     - **Context Path**: `/`
4. Run the config → open `http://localhost:8080/`

## MySQL prerequisites
- MySQL server running on `localhost:3306`
- Root login with password `root`
- The app will execute:
  ```sql
  CREATE DATABASE IF NOT EXISTS studentdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  CREATE TABLE IF NOT EXISTS students (...);
  ```

## Troubleshooting
- **Driver not found** → Ensure you rebuilt (`mvn clean package`) and that DocBase points to `target/student-crud-jsp` (the exploded folder that contains `WEB-INF/lib/mysql-connector-j-*.jar`).
- **Access denied** → Your local MySQL root password isn’t `root`. Edit `src/main/webapp/index.jsp` (`jdbcUser`, `jdbcPass`) to match your setup.
- **Can’t connect** → Start MySQL (e.g., `brew services start mysql`) and confirm `localhost:3306` is open.

## Project layout
```
lab8_bipin_jsp/
  ├─ pom.xml
  └─ src/
     └─ main/
        └─ webapp/
           ├─ index.jsp
           └─ WEB-INF/
              └─ web.xml
```
