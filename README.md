Online Movie Booking Website
Overview
This is an online movie booking website designed to make movie ticket booking easy and seamless.
Users can browse movies, select theaters, choose seats, and confirm bookings.
Upon successful booking, users receive a booking confirmation email.
Prerequisites
MySQL Database:

Ensure you have MySQL installed and running on your system.
Provide the correct MySQL username and password in the application.properties file.
Mail Configuration:

Update the spring.mail.username field in the application.properties file with a valid email address.
This email ID will be used to send booking confirmation emails to users.
Setup Instructions
Clone the repository:

bash
Copy code
git clone https://github.com/your-repo-name.git
cd your-repo-name
Configure the application.properties file:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.mail.username=your_email@example.com
spring.mail.password=your_email_password
Create the MySQL database:

Open your MySQL client and create a database:
sql
Copy code
CREATE DATABASE showspot;
Run the application:

bash
Copy code
mvn spring-boot:run
Access the website:

Open your browser and navigate to http://localhost:8080.
Features
Search Movies: Find movies by name or city.
Book Tickets: Choose your preferred theater, date, and seats.
Email Notifications: Receive booking confirmation emails directly to your inbox.
Technologies Used
Backend: Spring Boot, Java
Frontend: JSP, HTML, CSS, JavaScript
Database: MySQL
Mail Service: Spring Mail
Important Notes
Mail Configuration: Ensure you enable "Allow less secure apps" or app-specific passwords if using Gmail or similar services.
Database Connection: Verify the MySQL server is running and accessible before launching the application.
