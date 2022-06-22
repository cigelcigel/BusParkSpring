# BusParkSpring

## Description

Fleet system. Drivers and Administrators can log into the system. In the fleet there are buses, routes. The Administrator can assign free buses to the Routes, to buses of free Drivers, and also release them, making them free. The driver can see his place of work, and he must also confirm his new Appointment.

## Instalation and running 
### Prerequisites

- JDK, JRE 8 or later
- Spring
- MySQL

### Set up
- Clone the project to local reposiroty and build project.
- Change in application.properties spring.jpa.hibernate.ddl-auto=update. Then create database using entities.
- Init database using insertDatabase.sql. 

### Login data
- Admin - login: admin@gmail.com password: admin
--Driver - login: driver1@gmail.com password: driver
