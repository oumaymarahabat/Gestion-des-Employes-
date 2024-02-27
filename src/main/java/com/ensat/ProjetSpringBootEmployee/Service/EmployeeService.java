package com.ensat.ProjetSpringBootEmployee.Service;

import com.ensat.ProjetSpringBootEmployee.Model.Employee;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface EmployeeService {
    public List<Employee> getAllEmployees();
    public Employee getEmployee(Integer id);
    public void addEmployee(Employee employee);
    public List<Employee> findByNameLike(String nom);
    public void updateEmployee(Employee employee , Integer id);
    public int getNombreTotalEmployes();
    public double calculerMasseSalarialeEntreprise();
    public void deleteEmployee(Integer idEmployee);
    public void exportToExcel(HttpServletResponse response) throws IOException;
    public void reassignEmployeeToDepartment(int employeeId, String newDepartmentId);
}
