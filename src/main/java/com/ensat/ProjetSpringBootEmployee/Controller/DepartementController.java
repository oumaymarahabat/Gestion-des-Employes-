package com.ensat.ProjetSpringBootEmployee.Controller;

import com.ensat.ProjetSpringBootEmployee.Model.Departement;
import com.ensat.ProjetSpringBootEmployee.Service.DepartementService;
import com.ensat.ProjetSpringBootEmployee.Service.EmployeeService;
import jakarta.persistence.PostUpdate;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class DepartementController {
    @Autowired
    private DepartementService departementService;
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/departements")
    public String ShowAllDep(Model model){
        model.addAttribute("departements", departementService.getAllDepartement());
        return "departements";
    }
    @GetMapping("/departement/{id}")
    public String ShowOne(Model model, @PathVariable String id){
        Departement departement = departementService.getDepartementById(id);
        List<Departement> allDepartments = departementService.getAllDepartement();

        model.addAttribute("departement", departement);
        model.addAttribute("departementNombreEmployees", departementService.getEmployeeCount(id));
        model.addAttribute("departementMasseSalariael", departementService.calculateTotalPayroll(id));
        model.addAttribute("allDepartments", allDepartments);

        return "departement";
    }
    @PostMapping("/departement/{id}/employees/reassign/{idEmp}")
    public String reassignEmployee( @PathVariable String id,@PathVariable int idEmp, @RequestParam String newDept, RedirectAttributes redirectAttributes) {
        employeeService.reassignEmployeeToDepartment(idEmp, newDept);
        redirectAttributes.addFlashAttribute("success", "Employee reassigned successfully.");
        return "redirect:/departement/" + id;
    }

    @GetMapping("/departements/add")
    public String ShowDep(Model model){
        model.addAttribute("departement",new Departement());
        return "addDepartement";
    }
    @PostMapping("/departements/add")
    public String addDepartement(@ModelAttribute("departement") Departement departement){
        departementService.addDepartement(departement);
        return "redirect:/departements";
    }
    @PostMapping("/departements/edit/{id}")
    public String updateDepartement(@ModelAttribute("departement") Departement departement, @PathVariable String id){
        departementService.updateDepartement(departement,id);
        return "redirect:/departement/"+id;
    }
    @GetMapping("/departements/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        departementService.exportToExcel(response);
    }
    @GetMapping("/departements/delete/{id}")
    public String deleteDepartement( @PathVariable String id){
        departementService.deleteDepartement(id);
        return "redirect:/departements";
    }

}
