# Paint Sales Manager

Paint Sales Competition Manager is a JavaFX application that helps manage paint sales and employee data for Rainbow Ace Hardware.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Paint Sales Competition Manager is a desktop application that provides a user-friendly interface for managing paint sales records and employee information for Rainbow Ace Hardware. It utilizes JavaFX for the graphical user interface (GUI) and MySQL as the database management system.

## Features

- Add new paint sale records, including receipt, date, employee, and quantity sold.
- View and edit existing paint sale records.
- Delete unwanted paint sale records.
- Store employee information and associate each sale with the corresponding employee.
- Login system with username and password validation.

## Technologies

- Java
- JavaFX
- MySQL

## Getting Started

To run the application locally, follow these steps:

1. Make sure you have Java and MySQL installed on your machine.
2. Clone this repository to your local machine using the following command:
    ```bash
    git clone https://github.com/khector24/PaintSalesCompetitionManager.git

## Database Schema
The application expects the following database schema:

    ```bash
    CREATE TABLE PaintEmployee (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL
    );

    CREATE TABLE PaintSale (
        id INT PRIMARY KEY AUTO_INCREMENT,
        receipt VARCHAR(20) NOT NULL,
        date DATE NOT NULL,
        employee VARCHAR(100) NOT NULL,
        quantity VARCHAR(10) NOT NULL
    );

    CREATE TABLE userAccounts (
        id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(100) NOT NULL,
        password VARCHAR(100) NOT NULL
    );


