package dao;

import java.util.List;

import dto.Employee;
import exception.EmployeeNotFoundException;

public interface EmployeeDAO {

  public static final String URL = "jdbc:mysql://127.0.0.1:3306/employee_database_project";
  public static final String USER = "root";
  public static final String PASSWORD = "jumpeiyamana";

  public List<Employee> findAlEmployees()throws EmployeeNotFoundException;
  public abstract void addEmployee(Employee e);
  public abstract void deleteEmployee(int id);
  public abstract Employee findEmployee(int id) throws EmployeeNotFoundException;

  public abstract void updateEmployee(Employee e) throws EmployeeNotFoundException;
  // public abstract void updateEmployee(int id);
}
