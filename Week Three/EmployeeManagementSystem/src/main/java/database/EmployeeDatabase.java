package main.java.database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.exception.EmployeeNotFoundException;
import main.java.model.Employee;


/**
 * This class manages all the employees stored in the system. 
 * It handles CRUD operations (Create, Read, Update, Delete), 
 * salary calculations, filtering, and sorting employees.
 */
public class EmployeeDatabase<T>{
    private Map<T, Employee<T>> employeeMap = new HashMap<>();

    // Add employee to database
    public void addEmployee(Employee<T> employee){
        employeeMap.put(employee.getEmployeeId(), employee);
    }

    // Remove employee
    public void removeEmployee(T employeeId){
        Employee<T> employee = employeeMap.get(employeeId);
        if(employee != null){
            employeeMap.remove(employeeId);
        }
    }

    /**
     * This method updates the employee details based on the specified field.
     * It accepts the field name (e.g., "name", "salary") and the new value to update.
     * It performs a case-insensitive check for field names.
     */
    public void updateEmployeeDetails(T employeeId, String field, Object newValue){
        // get the employee
        Employee<T> employee = employeeMap.get(employeeId);
        if(employee != null){
            // check for the field being updated and update with the new value
            switch(field.toLowerCase()){
                case "name": employee.setName( (String) newValue); break;
                case "department": employee.setDepartment((String) newValue); break;
                case "salary": employee.setSalary((Double) newValue); break;
                case "yearsofexperience": employee.setYearsOfExperience((Integer) newValue);break;
                case "performancerating": employee.setPerformanceRating((Double) newValue);
                case "isactive": employee.setActive((Boolean) newValue); break;
                default: System.out.println("Invalid field"); break;
            }
        }else{
            System.out.println("There is no employee with the ID:" + employeeId);
        }
    }

    // Get employees
    public List<Employee<T>> getAllEmployees(){
        return employeeMap.values().stream().collect(Collectors.toList());
    }

    // Get employee by ID
    public Employee<T> getEmployeeById(T employeeId) throws EmployeeNotFoundException {
        Employee<T> employee = employeeMap.get(employeeId);
        if(employee == null){
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
        }
        return employee;
    }

    // Filters employees by department name and collects them into a list
    public List<Employee<T>> getEmployeesByDepartment(String departmentName){
        return employeeMap.values().stream().filter(e-> e.getDepartment().equalsIgnoreCase(departmentName)).collect(Collectors.toList());
    }

    // get employees whose name contains
    public List<Employee<T>> getEmployeesByName(String searchName){
        return employeeMap.values().stream().filter(e->e.getName().toLowerCase().contains(searchName.toLowerCase())).collect(Collectors.toList());
    }

    // get employees by performance rating
    public List<Employee<T>> getEmployeesByRating(double rating){
        return employeeMap.values().stream().filter(e -> e.getPerformanceRating() >= rating).collect(Collectors.toList());
    }
    
    // get employees by salary range
    public List<Employee<T>> getEmployeesBySalary(double minSalary, double maxSalary){
        return employeeMap.values().stream().filter(e -> e.getSalary() >= minSalary && e.getSalary() <= maxSalary ).collect(Collectors.toList());
    }

    // display employees
    public void displayEmployees(){
        for(Iterator<Employee<T>> iterator = employeeMap.values().iterator(); iterator.hasNext(); ){
            System.out.println(iterator.next());
        }
    }

    // Calculate average salary
    public double calculateAverageSalary(){
        return employeeMap.values().stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
    }

    // Calculate total Salary
    public double calculateTotalSalary(){
        return employeeMap.values().stream().mapToDouble(Employee::getSalary).sum();
    }

    // Increase the salary for employees with a performance rating of 4.5 or higher
    public void raiseSalaryOfEmployees(double raiseAmount){
        employeeMap.values().stream().filter(e -> e.getPerformanceRating() >= 4.5).forEach(e -> e.setSalary(e.getSalary() + raiseAmount));
    }

    public List<Employee<T>> getHighPaidEmployees(){
        return employeeMap.values().stream().sorted(Comparator.comparingDouble(Employee<T>::getSalary).reversed()).limit(5).collect(Collectors.toList());
    }

    // Calculate average salary in a department
    public double calculateAverageSalaryInDepartment(String departmentName){
        return employeeMap.values().stream().filter(e->e.getDepartment().equalsIgnoreCase(departmentName)).mapToDouble(Employee::getSalary).average().orElse(0.0);
    }

    // Get employees sorted by salary
    public List<Employee<T>> sortEmployeesBySalary() {
        if (employeeMap.isEmpty()) {
            System.out.println("No employees to sort.");
            return new ArrayList<>();
        }

        return employeeMap.values().stream()
                .sorted(Comparator.comparing(Employee<T>::getSalary, Comparator.nullsLast(Double::compare)))
                .collect(Collectors.toList());
    }

    // Sort employees by a custom field
    public List<Employee<T>> sortEmployeesByCustomField(Comparator<Employee<T>> comparator) {
        return employeeMap.values().stream().sorted(comparator).collect(Collectors.toList());
    }

}   