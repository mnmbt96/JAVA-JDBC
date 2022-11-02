import java.util.List;

import dto.Employee;
import service.EmployeeService;
import service.EmployeeServiceimpl;

public class App {
    public static void main(String[] args) throws Exception {

        EmployeeService employeeService = new EmployeeServiceimpl();

        Employee employee1 = 
            new Employee(1, "Kubichan","WMAD Instructor", 3, 50000);

        // Employee employee2 = 
        //     new Employee(2, "Francois","WMAD Instructor", 0, 60000);

        // employeeService.addEmployee(employee1);
        // employeeService.addEmployee(employee2);
        // employeeService.deleteEmployee(1);
        // employeeService.findEmployee(1);
        employeeService.updateEmployee(employee1);

        // employeeService.updateSingleColumn("name", "Aya", 1);
        


        List<Employee> employees = employeeService.findAllEmployees();

        for (Employee employee : employees) {
            System.out.println(
                "Employee ID: " + employee.getId() + 
                "| Employee Name " + employee.getName() +
                "| Employee Department " + employee.getDepartment()
            );
        }
    }
}
