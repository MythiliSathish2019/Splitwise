# Splitwise Backend API

A RESTful backend system for managing group expenses, built using Spring Boot and PostgreSQL, inspired by the functionality of Splitwise. This system provides APIs for user management, group creation, expense tracking, and automated settlement calculations.

---

## Features

- User Management  
  - Register new users  
  - Manage friendships  
  - View user profiles  

- Group Management  
  - Create and manage groups  
  - Add or remove members  
  - Track group-level expenses  

- Expense Management  
  - Add expenses with multiple splitting strategies  
  - Associate expenses with users and groups  
  - Track who paid and who owes  

- Settlement Calculation  
  - Auto-generate optimal settlement transactions  
  - Minimize total number of transactions  
  - Maintain balance history for transparency  

- Security & Integrity  
  - Password hashing using BCrypt  
  - ACID compliance for financial operations  

---

## Tech Stack

| Layer      | Technology     |
|------------|----------------|
| Language   | Java 17        |
| Framework  | Spring Boot    |
| Database   | PostgreSQL     |
| ORM        | JPA + Hibernate|
| Caching    | Redis (Planned)|
| Diagrams   | draw.io        |
| Tools      | Maven, Git     |

---

## Database Schema
- A normalized PostgreSQL schema ensures referential integrity and performance.
- Main Tables:
  - users
  - groups
  - user_groups
  - expenses
  - user_expenses
  - settlement_transactions

