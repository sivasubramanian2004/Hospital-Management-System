CREATE DATABASE hospital_db;

USE hospital_db;

CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    contact VARCHAR(20),
    admit_date DATE,
    discharge_date DATE
);
CREATE TABLE diagnosis (
    diagnosis_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    symptoms TEXT,
    diagnosis_details TEXT,
    doctor_name VARCHAR(100),
    treatment TEXT,
    date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

CREATE TABLE billing (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    amount DECIMAL(10, 2),
    payment_status VARCHAR(50),
    billing_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);
