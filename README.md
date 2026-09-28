# ChainLoom — Intelligent Supply Chain Monitoring

**ChainLoom** is a full-stack supply chain management platform for monitoring inventory, suppliers, purchase orders, and shipments. It identifies low-stock products and provides secure supplier document storage using AWS S3.

## Overview

ChainLoom helps businesses manage their supply chain operations through a centralized dashboard. It provides inventory visibility, supplier management, purchase order tracking, shipment records, and document management.

## Features

* **Dashboard:** Overview of suppliers, products, purchase orders, and low-stock alerts.
* **Inventory Management:** Track product quantities, reorder thresholds, and stock availability.
* **Supplier Management:** Maintain supplier information and reliability scores.
* **Purchase Orders:** Create and manage orders with status tracking.
* **Shipment Tracking:** Manage shipment records linked to purchase orders.
* **Document Management:** Upload, access, and delete supplier documents using AWS S3.
* **REST API:** Backend endpoints with input validation and centralized exception handling.

## Tech Stack

| Layer    | Technologies                       |
| -------- | ---------------------------------- |
| Frontend | React, Vite, React Router, Axios   |
| Backend  | Java, Spring Boot, Spring Data JPA |
| Database | PostgreSQL                         |
| Cloud    | AWS S3, IAM                        |
| Tools    | Git, GitHub, Maven, npm            |

## Architecture

```text
React + Vite Frontend
        |
        | REST API (JSON)
        v
Spring Boot Backend
        |
        +------ PostgreSQL
        |
        +------ AWS S3
                (Supplier Documents)
```

The backend follows a layered architecture with controllers, services, repositories, and entities.

## Project Structure

```text
ChainLoom/
├── supplychain-iq-frontend/
│   └── src/
├── supplychain-iq-v2/
│   └── supplychain-iq-backend/
│       └── src/
│           └── main/
│               ├── java/
│               └── resources/
├── README.md
└── .gitignore
```

## Getting Started

### Prerequisites

* Java JDK (version specified in the backend `pom.xml`)
* Node.js and npm
* PostgreSQL
* AWS account with an S3 bucket (required for document storage)
* Git

### 1. Clone the Repository

```bash
git clone https://github.com/afreen707/ChainLoom.git
cd ChainLoom
```

### 2. Configure the Database

Create a PostgreSQL database:

```sql
CREATE DATABASE supplychain_iq;
```

### 3. Configure Environment Variables

Create a `.env` file in the backend directory:

```env
DB_URL=jdbc:postgresql://localhost:5432/supplychain_iq
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password

AWS_ACCESS_KEY_ID=your_aws_access_key
AWS_SECRET_ACCESS_KEY=your_aws_secret_key
```

Configure the AWS region and S3 bucket name in `application.properties`:

```properties
aws.region=ap-south-1
aws.s3.bucket-name=your_s3_bucket_name
```

**Never commit your `.env` file or actual credentials to GitHub.**

### 4. Run the Backend

Open a terminal:

```bash
cd supplychain-iq-v2/supplychain-iq-backend
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend runs at:

`http://localhost:8080`

### 5. Run the Frontend

Open another terminal:

```bash
cd supplychain-iq-frontend
npm install
npm run dev
```

Open the local URL displayed by Vite, typically:

`http://localhost:5173`

Ensure the backend and database are running before using the application.

## API Endpoints

Base URL: `http://localhost:8080/api`

| Resource           | Operations                                   |
| ------------------ | -------------------------------------------- |
| Suppliers          | Create, read, update, and delete suppliers   |
| Supplier Documents | Upload, access, and delete documents         |
| Products           | Manage products and retrieve low-stock items |
| Purchase Orders    | Manage orders and update their status        |
| Shipments          | Create, view, and update shipment records    |

### Main API Routes

```text
/api/suppliers
/api/suppliers/{id}/document
/api/products
/api/products/low-stock
/api/purchase-orders
/api/purchase-orders/{id}/status
/api/shipments
```

## Security

* Credentials are managed through environment variables.
* Supplier documents are stored in a private AWS S3 bucket.
* IAM permissions should follow the principle of least privilege.
* Document access uses time-limited pre-signed URLs.
* File uploads are restricted by size limits.

## Future Enhancements

* Shipment delay-risk prediction.
* Authentication and role-based access control.
* Expanded inventory and purchase order workflows.
* Cloud deployment.

## Author

**Md. Afreen**

* GitHub: [@afreen707](https://github.com/afreen707)
* LinkedIn: [md-afreen](https://linkedin.com/in/md-afreen)
