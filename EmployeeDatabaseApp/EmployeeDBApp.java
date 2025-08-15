import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {

    // MySQL DB credentials
    private static final String URL = "jdbc:mysql://localhost:3306/company_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add Employee
    private static void addEmployee(String name, String position, double salary) {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.executeUpdate();

            System.out.println(" Employee added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View Employees
    private static void viewEmployees() {
        String sql = "SELECT * FROM employees";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Position: %s | Salary: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Employee
    private static void updateEmployee(int id, String name, String position, double salary) {
        String sql = "UPDATE employees SET name=?, position=?, salary=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.setInt(4, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(" Employee updated successfully!");
            } else {
                System.out.println(" Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Employee
    private static void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(" Employee deleted successfully!");
            } else {
                System.out.println(" Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Employee Database App ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Position: ");
                    String position = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    addEmployee(name, position, salary);
                    break;

                case 2:
                    viewEmployees();
                    break;

                case 3:
                    System.out.print("Enter Employee ID to update: ");
                    int upId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String upName = sc.nextLine();
                    System.out.print("Enter New Position: ");
                    String upPosition = sc.nextLine();
                    System.out.print("Enter New Salary: ");
                    double upSalary = sc.nextDouble();
                    updateEmployee(upId, upName, upPosition, upSalary);
                    break;

                case 4:
                    System.out.print("Enter Employee ID to delete: ");
                    int delId = sc.nextInt();
                    deleteEmployee(delId);
                    break;

                case 5:
                    System.out.println(" Exiting...");
                    break;

                default:
                    System.out.println("111 Invalid choice.");
            }

        } while (choice != 5);

        sc.close();
    }
}