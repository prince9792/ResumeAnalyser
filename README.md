# Resume Analyser

Resume Analyser is a web-based application developed using Java,
Spring Boot, MySQL and Thymeleaf.

The application allows users to upload their resume in PDF format,
extract skills from the resume, compare those skills with the
required skills of a job and calculate a match percentage.

The system also provides skill recommendations and maintains
analysis history.

---

## Features

### User Features

- User Registration
- User Login
- BCrypt Password Encryption
- Resume Upload
- PDF Text Extraction
- Automatic Skill Detection
- Resume Analysis
- Matched Skills
- Missing Skills
- Match Percentage
- Score
- Grade
- Performance Evaluation
- Skill Recommendations
- Analysis History
- Delete Own Analysis History

### Admin Features

- Admin Login
- Admin Dashboard
- Manage Users
- Change User Role
- Delete Users
- Create Jobs
- View Jobs
- Delete Jobs
- View Uploaded Resumes
- View All Analysis History
- Delete Analysis History
- Dashboard Statistics

---

## Technology Stack

### Frontend

- HTML5
- CSS3
- JavaScript
- Thymeleaf

### Backend

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA

### Database

- MySQL 8

### Libraries

- Apache PDFBox
- BCrypt Password Encoder

### Server

- Embedded Tomcat

---

## How the Application Works

The basic workflow of the application is:

```text
User Registration
       |
       v
User Login
       |
       v
Upload Resume PDF
       |
       v
Extract Resume Text
       |
       v
Detect Skills
       |
       v
Select Job
       |
       v
Compare Resume Skills
with Job Skills
       |
       v
Calculate Match Percentage
       |
       v
Show Result
       |
       v
Show Skill Recommendations
       |
       v
Save Analysis History