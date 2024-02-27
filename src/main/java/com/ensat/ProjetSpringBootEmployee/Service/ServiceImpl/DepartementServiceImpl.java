package com.ensat.ProjetSpringBootEmployee.Service.ServiceImpl;

import com.ensat.ProjetSpringBootEmployee.Model.Departement;
import com.ensat.ProjetSpringBootEmployee.Repository.DepartementRepository;
import com.ensat.ProjetSpringBootEmployee.Repository.EmployeeRepository;
import com.ensat.ProjetSpringBootEmployee.Service.DepartementService;
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
public class DepartementServiceImpl implements DepartementService {
    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Departement> getAllDepartement() {
        List<Departement> departements = new ArrayList<>();
        departements=departementRepository.findAll();
        return departements;
    }

    @Override
    public Departement getDepartement(String nomDept) {
        Departement departement= departementRepository.findByNomDept(nomDept).orElse(null);
        return departement;
    }

    @Override
    public Departement getDepartementById(String Id) {
        return departementRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("Department not found with id " + Id));
    }
    @Override
    public void addDepartement(Departement departement) {
        departementRepository.save(departement);
    }
    @Override
    public void updateDepartement(Departement departement,String Id) {
        Departement departement1=new Departement();
        departement1= departementRepository.findById(Id).orElse(null);
        if(departement1 != null)
        {
            departement1.setNomDept(departement.getNomDept());
            departementRepository.save(departement1);
        }
    }

    @Override
    public void deleteDepartement(String IdDepartement) {
        departementRepository.deleteById(IdDepartement);

    }

    @Override
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=departements.xlsx";
        response.setHeader(headerKey, headerValue);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Departements");

        Row headerRow = sheet.createRow(0);

        // Création des entêtes de colonnes
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom du Département");
        // Ajoutez d'autres en-têtes si nécessaire

        // Remplissage des données
        List<Departement> departements = getAllDepartement();

        int rowCount = 1;
        for (Departement departement : departements) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(departement.getIdDept());
            row.createCell(1).setCellValue(departement.getNomDept());
            // Remplissez d'autres cellules si nécessaire
        }

        // Ajustement automatique de la largeur des colonnes
        for (int i = 0; i < departements.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.flush();
        outputStream.close();
    }

    @Override
    public Departement getDepartementAvecPlusDEmployes(){
        return departementRepository.getDepartementWithMaxEmployees();
    }

    @Override
    public Long getEmployeeCount(String departmentId) {
        return departementRepository.countEmployeesByDepartment(departmentId);
    }
    @Override
    public Double calculateTotalPayroll(String departmentId) {
        Double totalPayroll = employeeRepository.sumSalaryByDepartment(departmentId);
        return totalPayroll != null ? totalPayroll : 0.0;
    }

}
