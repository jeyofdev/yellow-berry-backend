# Back-end Yellow Berry

<p>
    <h1 align="center">PROJECT SHOPPING KRIST BACK-END </h1>
</p>

<p align="center">
	<img src="https://img.shields.io/github/last-commit/jeyofdev/yellow-berry-backend?style=flat-square&logo=git&logoColor=white&color=157bed" alt="last-commit">
	<img src="https://img.shields.io/github/languages/top/jeyofdev/yellow-berry-backend?style=flat-square&color=157bed" alt="repo-top-language">
	<img src="https://img.shields.io/github/languages/count/jeyofdev/yellow-berry-backend?style=flat-square&color=157bed" alt="repo-language-count">
<p>

<p align="center">
    <em>Developed with the software and tools below.</em>
</p>

<p align="center">
	<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white" alt="Java">
	<img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=flat-square&logo=spring&logoColor=white" alt="Spring">
	<img src="https://img.shields.io/badge/JWT-black?style=flat-square&logo=JSON%20web%20tokens" alt="Jwt">
	<img src="https://img.shields.io/badge/postgres-%23316192.svg?style=flat-square&logo=postgresql&logoColor=white" alt="Postgresql">
	<img src="https://img.shields.io/badge/Apache%20Maven-C71A36.svg?style=flat-square&logo=Apache%20Maven&logoColor=white" alt="Maven">
    <img src="https://img.shields.io/badge/JSON-000000.svg?style=flat-square&logo=JSON&logoColor=white" alt="JSON">
    <img src="https://img.shields.io/badge/GitHub-181717.svg?style=flat-square&logo=GitHub&logoColor=white" alt="GitHub">
</p>
<hr>

<p>
    Back-end Yellow Berry is a Java-based application designed to handle server-side operations for an e-commerce platform. It includes features such as product management, user management.
</p>

## Prerequisites

- Java 22
- Maven 3.6.0 or higher
- Relational database PostgreSQL

## Installation

1. Clone the repository :
    ```sh
    git clone https://github.com/jeyofdev/yellow-berry-backend.git
    cd yellow-berry-backend
    ```

2. create an .env file :
   ```
   # Database
   DB_HOST=
   DB_PORT=
   DB_NAME=shopping_krist
   DB_USER=
   DB_PASSWORD=
   
   # JWT
   TOKEN_PREFIX=
   TOKEN_SECRET_KEY=

   # Email
   MAIL_HOST=
   MAIL_PORT=
   MAIL_USERNAME=
   MAIL_PASSWORD=
   MAIL_SMTP_AUTH=true
   MAIL_SMTP_STARTTLS_ENABLE=true
   MAIL_FROM=
   ```

3. Build the project:
    ```sh
    mvn clean install
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```
