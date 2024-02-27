package com.ensat.ProjetSpringBootEmployee.Service.ServiceImpl;

import com.ensat.ProjetSpringBootEmployee.Model.Departement;
import com.ensat.ProjetSpringBootEmployee.Model.Employee;
import com.ensat.ProjetSpringBootEmployee.Repository.EmployeeRepository;
import com.ensat.ProjetSpringBootEmployee.Service.DepartementService;
import com.ensat.ProjetSpringBootEmployee.Service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartementService departementService;
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees=employeeRepository.findAll();
        return employees;
    }
    @Override
    public Employee getEmployee(Integer id) {
        Employee employee=employeeRepository.findById(id).orElse(null);
        return employee;
    }
    @Override
    public void addEmployee(Employee employee) {

        if(employee.getDepartement().getNomDept()!=null){

            Departement departement= departementService.getDepartement(employee.getDepartement().getNomDept());
            employee.setDepartement(departement);
        }
        employeeRepository.save(employee);
    }
    @Override
    public List<Employee> findByNameLike(String nom){
        return employeeRepository.findByNomEmpContainingIgnoreCase(nom);
    }
    @Override
    public void updateEmployee(Employee employee ,  Integer id) {
        Employee employee1=new Employee();
        employee1=employeeRepository.findById(id).orElse(null);
        if(employee1!=null)
        {
            employee1.setNomEmp(employee.getNomEmp());
            employee1.setAge(employee.getAge());
            employee1.setSalaire(employee.getSalaire());
            employee1.setDepartement(employee.getDepartement());
            employeeRepository.save(employee1);
        }

    }
    @Override
    public void deleteEmployee(Integer idEmployee) {
        employeeRepository.deleteById(idEmployee);
    }
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employees.xlsx";
        response.setHeader(headerKey, headerValue);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        Row headerRow = sheet.createRow(0);

        // Création des entêtes de colonnes
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Salary");
        headerRow.createCell(3).setCellValue("Age");
        headerRow.createCell(4).setCellValue("Department");

        // Remplissage des données
        List<Employee> employees = getAllEmployees();

        int rowCount = 1;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(employee.getIdEmp());
            row.createCell(1).setCellValue(employee.getNomEmp());
            row.createCell(2).setCellValue(employee.getSalaire());
            row.createCell(3).setCellValue(employee.getAge());
            row.createCell(4).setCellValue(employee.getDepartement().getNomDept());
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    @Override
    public int getNombreTotalEmployes() {
        return employeeRepository.countTotalEmployees();

    }

    @Override
    public double calculerMasseSalarialeEntreprise(){

        return employeeRepository.countTotalSalaire();
    }
    @Override
    public void reassignEmployeeToDepartment(int employeeId, String newDepartmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + employeeId));
        Departement newDepartment = departementService.getDepartementById(newDepartmentId);

        employee.setDepartement(newDepartment);
        employeeRepository.save(employee);
    }

}
