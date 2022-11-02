package dao;

import java.sql.*;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import dto.Employee;
import exception.EmployeeNotFoundException;

public class EmployeeDAOMysqlimpl implements EmployeeDAO{

  private Connection connection;
  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  //CREATE, READ, UPDATE, DELATE = CRUD

  //? is placeholder for dynamic data;
  private static final String INSERT_EMPLOYEE = 
  "INSERT INTO employee_table_project" + 
  "(id, name, department, dayAbsent, salary)" +
  "VALUES(?, ?, ?, ?,?)";

  private static final String DELETE_EMPLOYEE = 
  "DELETE FROM employee_table_project WHERE id = ?";

  private static final String FIND_EMPLOYEE = 
  "SELECT * FROM employee_table_project WHERE id = ?";

  private static final String FIND_ALL = 
  "SELECT * FROM employee_table_project";

  private static final String UPDATE_EMPLOYEE = 
  "UPDATE employee_table_project SET name = ?,department = ?,dayAbsent = ?,salary = ? WHERE id = ?";

  // private static final String UPDATE_SINGLE_COLUMN = 
  // "UPDATE employee_table_project SET ? = ? WHERE id = ?";

  public EmployeeDAOMysqlimpl(){
    try{
      connection = DriverManager.getConnection(EmployeeDAO.URL, EmployeeDAO.USER, EmployeeDAO.PASSWORD);
      System.out.println("Connection success");
    }catch(SQLException e){
      System.out.println("Unable to connect");
      e.printStackTrace();
    }
  }

  @Override
  public void addEmployee(Employee e) {
    int rowAffected = 0;

    try {
      preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE);
      preparedStatement.setInt(1,e.getId());
      preparedStatement.setString(2,e.getName());
      preparedStatement.setString(3, e.getDepartment());
      preparedStatement.setInt(4,e.getDayAbsent());
      preparedStatement.setInt(5, e.getSalary());

      rowAffected = preparedStatement.executeUpdate();
      System.out.println(rowAffected + " row(s) affected");

    } catch (SQLException sqlE) {
      System.out.println("Unable to add the employee");
      sqlE.printStackTrace();
      
    }finally{
      try {
        preparedStatement.close();
      } catch (Exception e1) {
        System.out.println("Unable to close the statement");
        e1.printStackTrace();
      }    
    }

    if(rowAffected>0){
      System.out.println("Employee added successfully");
    }
  }

  @Override
  public void deleteEmployee(int id) {
    int rowAffected = 0;

    try {
      preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE);
      preparedStatement.setInt(1, id);
      
      rowAffected = preparedStatement.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Unable to delete this employee");
      e.printStackTrace();

    }finally{
      try{
        preparedStatement.close();
      }catch(SQLException e){
        System.out.println("Unable to close the statement");
        e.printStackTrace();
      }
    }

    if(rowAffected > 0){
      System.out.println("Employee deleted successfully");
    }
    
  }

  @Override
  public Employee findEmployee(int id) throws EmployeeNotFoundException{

    Employee employee = null;
    List<Employee> employees = new LinkedList<>();

    try {
      preparedStatement= connection.prepareStatement(FIND_EMPLOYEE);
      preparedStatement = setInt(1,id);

      resultSet = preparedStatement.executeQuery();

      if(!resultSet.next()){
        throw new EmployeeNotFoundException(id);
      }

      employee = new Employee();
      employee.setId(resultSet.getInt("id"));
      employee.setName(resultSet.getString("name"));
      employee.setDepartment(resultSet.getString("department"));
      employee.setDayAbsent(resultSet.getInt("dayAbsent"));
      employee.setSalary(resultSet.getInt("salary"));
      employees.add(employee);

    } catch (SQLException e) {
      System.out.println("Unable to find the employee with id " + id);
    } finally{
      try {
        preparedStatement.close();
        resultSet.close();
      } catch (SQLException e) {
        System.out.println("Unable to close the statement!");
        e.printStackTrace();
      }
    }
    return employee;
  }

  // private PreparedStatement setInt(int i, int id) {
  //   return null;
  // }

  private PreparedStatement setInt(int i, int id) {
    return null;
  }

  @Override
  public List<Employee> findAlEmployees() throws EmployeeNotFoundException {

    Employee employee = null;
    List<Employee> employees = new LinkedList<>();

    try {
      preparedStatement = connection.prepareStatement(FIND_ALL);
      resultSet = preparedStatement.executeQuery();

      while(resultSet.next()){
        employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setName(resultSet.getString("name"));
        employee.setDepartment(resultSet.getString("department"));
        employee.setDayAbsent(resultSet.getInt("dayAbsent"));
        employee.setSalary(resultSet.getInt("salary"));
      }
    } catch (SQLException e) {
      System.out.println("Unable to find all employees");
      e.printStackTrace();
    }finally{
      try{
        preparedStatement.close();
        resultSet.close();
      }catch(SQLException e){
        System.out.println("Unable to close the statement");
        e.printStackTrace();
      }
    }

    return employees;
  }

  @Override
  public void updateEmployee(Employee e) throws EmployeeNotFoundException {
    int rowAffected = 0;

    try {
      preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);
      preparedStatement.setString(1,e.getName());
      preparedStatement.setString(2, e.getDepartment());
      preparedStatement.setInt(3,e.getDayAbsent());
      preparedStatement.setInt(4, e.getSalary());
      preparedStatement.setInt(5,e.getId());

      rowAffected = preparedStatement.executeUpdate();
      
      System.out.println(rowAffected + " row(s) affected");

    } catch (Exception exception) {
      System.out.println("Unable to update this employee");
      exception.printStackTrace();
    } finally{
      try{
        preparedStatement.close();
      }catch(SQLException exception2){
        System.out.println("Unable to close the statement");
        exception2.printStackTrace();
      }
    }

    if(rowAffected > 0){
      System.out.println("Employee updated successfully");
    }
  }

  // @Override
  // public void updateEmployee(int id) {
  //   int rowAffected = 0;

  //   try {
  //     preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);
  //     preparedStatement.setInt(1, id);
      
  //     rowAffected = preparedStatement.executeUpdate();

  //   } catch (SQLException e) {
  //     System.out.println("Unable to update this employee");
  //     e.printStackTrace();

  //   }finally{
  //     try{
  //       preparedStatement.close();
  //     }catch(SQLException e){
  //       System.out.println("Unable to close the statement");
  //       e.printStackTrace();
  //     }
  //   }

  //   if(rowAffected > 0){
  //     System.out.println("Employee updated successfully");
  //   }

  // }
}
