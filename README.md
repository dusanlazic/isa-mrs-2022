# pecaj.ga โ ISA/MRS Project

![CircleCI](https://img.shields.io/circleci/build/github/dusanlazic/isa-mrs-2022/development)

Full stack web application for advertising and renting resorts, boats and adventures. Built using Spring Boot ๐, React โ๏ธ and Tailwind CSS.

## Authors
- Duลกan Laziฤ SW 4 2019
- Ana Anฤeliฤ SW 14 2019
- Milan Sekuliฤ SW 54 2019

## Getting started ๐

### Live demo (Heroku) ๐

- Frontend: **[www.pecaj.ga](http://www.pecaj.ga)** 
  - or [pecaj-ga-frontend.herokuapp.com](https://pecaj-ga-frontend.herokuapp.com)


- Backend: **[api.pecaj.ga](http://api.pecaj.ga)** 
  - or [pecaj-ga-backend.herokuapp.com](https://pecaj-ga-backend.herokuapp.com)

### Running locally ๐  

#### Dependencies ๐ ๏ธ
- JDK 18
- Node v16.3.0
- PostgreSQL 14.2

#### Backend โ Spring Boot application ๐

Make sure all dependencies are satisfied. In `src/main/application.properties` configure the datasource for your database, and set your email sender credentials.

Start application using IntelliJ by running `ProjectApplication` configuration, or manually using:
```
$ mvn spring-boot:run
```
Your API will be available at `http://localhost:8080` and Swagger API docs at `http://localhost:8080/swagger-ui.html`.

#### Frontend โ React application โ๏ธ
Make sure all dependencies are satisfied. Inside `/client` run:
```
$ npm install
$ npm run dev
```
Your SPA will be served at `http://localhost:3000`

## Credentials ๐
### Password: `cascaded`
- superuser (main admin): `admin@pecaj.ga`
- resort owner: `sinisa@pecaj.ga`
- boat owner: `draganb@pecaj.ga`
- fishing instructor: `lazard@pecaj.ga`
- customer: `stevade@pecaj.ga`

## Screenshots ๐ธ

### Frontend

![](docs/img/ss1.png)

![](docs/img/ss4.png)

![](docs/img/ss2.png)

![](docs/img/ss3.png)

### Swagger API documentation

![](docs/img/ss5.png)

### DevOps (micro) flow

#### CircleCI

![](docs/img/ss6.png)

#### SonarCloud

![](docs/img/ss7.png)

#### Heroku (with Freenom domain name)

![](docs/img/ss9.png)

![](docs/img/ss8.png)

