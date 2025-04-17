package main.java.model;

/**
 * Represents an employee with a generic employee ID, personal details,
 * performance, and employment status.
 *
 * @param <T> Type of the employee ID (e.g., Integer, String)
 */
public class Employee<T> implements Comparable<Employee<T>>{
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
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
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
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setPerformanceRating(double performanceRating) { this.performanceRating = performanceRating; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    public void setActive(boolean active) { isActive = active; }

    /**
     * Natural ordering: by years of experience (descending).
     */
    @Override
    public int compareTo(Employee<T> other){
        return Integer.compare(other.yearsOfExperience, this.yearsOfExperience);
    }
}