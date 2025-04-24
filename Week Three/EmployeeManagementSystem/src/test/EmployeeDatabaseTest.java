package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.database.EmployeeDatabase;
import main.java.exception.EmployeeNotFoundException;
import main.java.model.Employee;

public class EmployeeDatabaseTest {

    private EmployeeDatabase<Integer> employeeDatabase;
    private Employee<Integer> employee1;
    private Employee<Integer> employee2;

    /**
     * Sets up the test fixture by creating a new EmployeeDatabase instance, creating two sample Employee objects, and adding them to the database.
     * This method is run before each test to ensure a clean slate.
     */
    @BeforeEach
    public void setUp() {
        employeeDatabase = new EmployeeDatabase<>();
        employee1 = new Employee<>(1, "Michael Adams", "IT", 50000.0, 4.5, 5, true);
        employee2 = new Employee<>(2, "Janet Ofori", "HR", 60000.0, 4.0, 3, true);

        // add employees to the database
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
    }
    
    /**
     * Tests that the getAllEmployees() method returns a list of all employees in the database and that the details of each employee are correct.
     */
    @Test
    public void testGetAllEmployees() {
        // get all employees from the database
        List<Employee<Integer>> employees = employeeDatabase.getAllEmployees();

        // assert that the size of the list is 2
        assert employees.size() == 2;

        // assert that the first employee has the correct details
        assert employees.get(0).getEmployeeId() == 1;
        assert employees.get(0).getName().equals("Michael Adams");
        assert employees.get(0).getDepartment().equals("IT");
        assert employees.get(0).getSalary() == 50000.0;
        assert employees.get(0).getPerformanceRating() == 4.5;
        assert employees.get(0).getYearsOfExperience() == 5;
        assert employees.get(0).isActive() == true;

        // assert that the second employee has the correct details
        assert employees.get(1).getEmployeeId() == 2;
        assert employees.get(1).getName().equals("Janet Ofori");
        assert employees.get(1).getDepartment().equals("HR");
        assert employees.get(1).getSalary() == 60000.0;
        assert employees.get(1).getPerformanceRating() == 4.0;
        assert employees.get(1).isActive() == true;
    }


    /**
     * Tests that the addEmployee() method adds a new employee to the database and that the newly added employee can be retrieved by ID.
     */
    @Test 
    public void addEmployee(){
        Employee<Integer> employee3 = new Employee<>(3, "Dennis Samuel", "Sales", 50000.0, 4.5, 5, true);
        employeeDatabase.addEmployee(employee3);
        List<Employee<Integer>> employees = employeeDatabase.getAllEmployees();
        assertEquals(3, employees.size());
        try {
            Employee<Integer> retrivedEmployee = employeeDatabase.getEmployeeById(3);
            assertEquals("Dennis Samuel", retrivedEmployee.getName());
        } catch (Exception e) {
            e.getMessage();
            fail("Employee with ID 3 not found, but was expected to exist");
        }
    }

    /**
     * Tests that the getEmployeesByDepartment() method returns a list of all employees in a given department and that the details of each employee are correct.
     */
    @Test
    public void getEmployeeByDepartment(){
        List<Employee<Integer>> employees = employeeDatabase.getEmployeesByDepartment("IT");
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("Michael Adams", employees.get(0).getName());

    }

    /**
     * Tests that the removeEmployee() method removes the specified employee from the database and that the removed employee cannot be retrieved by ID.
     */
    @Test
    public void testRemoveEmployee() {
        employeeDatabase.removeEmployee(1);
        assertThrows(EmployeeNotFoundException.class, () -> employeeDatabase.getEmployeeById(1));
    }

    @Test 
    public void testSearchForNonExistingEmployee(){
        try {
            employeeDatabase.getEmployeeById(400);
            fail("Expected EmployeeNotFoundException to be thrown");
        } catch (Exception e) {
            assertEquals("Employee with ID 400 not found.", e.getMessage());
        }
    }

    @Test
    public void testSortEmployeesBySalary() {
        
        // Sorting by salary (descending)
        List<Employee<Integer>> sortedEmployees = employeeDatabase.sortEmployeesBySalary();
        assertEquals("Michael Adams", sortedEmployees.get(0).getName());
        assertEquals("Janet Ofori", sortedEmployees.get(1).getName());
    }

    /**
     * Tests that the sortEmployeesBySalary() method returns an empty list when given an empty employee database.
     */
    @Test
    public void testHandleEmptyListSorting() {
        EmployeeDatabase<Integer> newDatabase = new EmployeeDatabase<>();
        List<Employee<Integer>> sortedEmployees = newDatabase.sortEmployeesBySalary();
        assertTrue(sortedEmployees.isEmpty());
    }

    @Test
    public void testNullValuesHandling() {
        employeeDatabase.removeEmployee(1);
        assertEquals(1, employeeDatabase.getAllEmployees().size());

        List<Employee<Integer>> sortedEmployees = employeeDatabase.sortEmployeesBySalary();
        assertNotNull(sortedEmployees);
    }
}
