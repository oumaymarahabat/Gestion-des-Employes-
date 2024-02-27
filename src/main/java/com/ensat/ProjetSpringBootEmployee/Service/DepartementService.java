package com.ensat.ProjetSpringBootEmployee.Service;

import com.ensat.ProjetSpringBootEmployee.Model.Departement;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface DepartementService {
    public List<Departement> getAllDepartement() ;
    public Departement getDepartement(String nomDept);
    public Departement getDepartementById(String Id);
    public void addDepartement(Departement departement);
    public void updateDepartement(Departement departement , String Id);
    public void deleteDepartement(String IdDepartement);
    public void exportToExcel(HttpServletResponse response) throws IOException;
    public Departement getDepartementAvecPlusDEmployes();
    public Long getEmployeeCount(String departmentId);

    public Double calculateTotalPayroll(String departmentId);
}
