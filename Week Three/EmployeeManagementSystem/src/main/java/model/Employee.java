package main.java.model;

import java.util.Set;
import java.util.logging.Logger;

import main.java.exception.InvalidDepartmentException;
import main.java.exception.InvalidSalaryException;

/**
 * Represents an employee with a generic employee ID, personal details,
 * performance, and employment status.
 *
 * @param <T> Type of the employee ID (e.g., Integer, String)
 */
public class Employee<T> implements Comparable<Employee<T>>{
    private static final Logger logger = Logger.getLogger(Employee.class.getName());
    private static final Set<String> VALID_DEPARTMENTS = Set.of("IT", "HR", "Sales", "Finance", "Marketing");

    private T employeeId;
    private String name;
    private String department;
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;


    /**
     * Constructs an employee with the specified attributes.
     *
     */
    public Employee(T employeeId, String name, String department, double salary, double performanceRating, int yearsOfExperience, boolean isActive){

        if(employeeId == null) throw new IllegalArgumentException("Employee ID cannot be null.");
        this.employeeId = employeeId;
        setName(name);
        setDepartment(department);
        setSalary(salary);
        setPerformanceRating(performanceRating);
        setYearsOfExperience(yearsOfExperience);    
        this.isActive = isActive;
    }

    // Getters
    public T getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public double getPerformanceRating() { return performanceRating; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public boolean isActive() { return isActive; }
    


    // Setters
    public void setName(String name) { 
        if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty.");
        this.name = name; 
    }

    public void setDepartment(String department) { 
        if(department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }else if(!VALID_DEPARTMENTS.contains(department)) {
            throw new InvalidDepartmentException("Invalid department: " + department);
        }
        this.department = department; 
    }

    public void setSalary(double salary) { 
        if(salary < 0){
            logger.severe("Attempted to set a negative salary: " + salary);
            throw new InvalidSalaryException("Salary cannot be negative.");
        } 
        this.salary = salary; 
    }

    
    public void setPerformanceRating(double performanceRating) { 
        if(performanceRating < 0 || performanceRating > 5) {
            logger.severe("Attempted to set an invalid performance rating: " + performanceRating);
            throw new IllegalArgumentException("Performance rating must be between 0 and 5.");
        }
        this.performanceRating = performanceRating; 
    }

    public void setYearsOfExperience(int yearsOfExperience) { 
        if (yearsOfExperience < 0){
            logger.severe("Attempting to set a negative years of experience" + yearsOfExperience);
            throw new IllegalArgumentException("Years of experience cannot be negative.");
        } 
        this.yearsOfExperience = yearsOfExperience; 
    }
    
    public void setActive(boolean active) { isActive = active; }

    /**
     * Natural ordering: by years of experience (descending).
     */
    @Override
    public int compareTo(Employee<T> other){
        return Integer.compare(other.yearsOfExperience, this.yearsOfExperience);
    }
}