package microproject;

import java.sql.*;
import java.util.Scanner;

public class  HospitalPatientRecordSystem {
    static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_db";
    static final String USER = "root"; // Change to your
    static final String PASS = "sivas200424mcr104"; // Change to your DB password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n=== Hospital Patient Record System ===");
                System.out.println("1. Admit New Patient");
                System.out.println("2. View All Patients");
                System.out.println("3. Discharge Patient");
                System.out.println("4. Add Diagnosis/Treatment");
                System.out.println("5. Generate Bill");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine();  // consume newline

                switch (choice) {
                    case 1 -> admitPatient(conn, sc);
                    case 2 -> viewAllPatients(conn);
                    case 3 -> dischargePatient(conn, sc);
                    case 4 -> addDiagnosis(conn, sc);
                    case 5 -> generateBill(conn, sc);
                    case 6 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void admitPatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter contact number: ");
        String contact = sc.nextLine();

        System.out.print("Enter admit date (YYYY-MM-DD): ");
        String admitDate = sc.nextLine();

        String sql = "INSERT INTO patients (name, age, gender, contact, admit_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, contact);
            pstmt.setDate(5, Date.valueOf(admitDate));

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " patient admitted successfully.");
        }
    }

    static void viewAllPatients(Connection conn) throws SQLException {
        String sql = "SELECT * FROM patients";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- All Patients ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Age: %d | Gender: %s | Admit: %s | Discharge: %s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getDate("admit_date"),
                        rs.getDate("discharge_date"));
            }
        }
    }

    static void dischargePatient(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient ID to discharge: ");
        int patientId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter discharge date (YYYY-MM-DD): ");
        String dischargeDate = sc.nextLine();

        String sql = "UPDATE patients SET discharge_date = ? WHERE patient_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(dischargeDate));
            pstmt.setInt(2, patientId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Patient discharged successfully.");
            } else {
                System.out.println("Patient ID not found.");
            }
        }
    }

    static void addDiagnosis(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter symptoms: ");
        String symptoms = sc.nextLine();

        System.out.print("Enter diagnosis details: ");
        String diagnosisDetails = sc.nextLine();

        System.out.print("Enter doctor name: ");
        String doctorName = sc.nextLine();

        System.out.print("Enter treatment: ");
        String treatment = sc.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        String sql = "INSERT INTO diagnosis (patient_id, symptoms, diagnosis_details, doctor_name, treatment, date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            pstmt.setString(2, symptoms);
            pstmt.setString(3, diagnosisDetails);
            pstmt.setString(4, doctorName);
            pstmt.setString(5, treatment);
            pstmt.setDate(6, Date.valueOf(date));

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " diagnosis record added.");
        }
    }

    static void generateBill(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter total amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter payment status (Paid/Unpaid): ");
        String status = sc.nextLine();

        System.out.print("Enter billing date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        String sql = "INSERT INTO billing (patient_id, amount, payment_status, billing_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, status);
            pstmt.setDate(4, Date.valueOf(date));

            int rows = pstmt.executeUpdate();
            System.out.println("Bill generated successfully.");
        }
    }
}

