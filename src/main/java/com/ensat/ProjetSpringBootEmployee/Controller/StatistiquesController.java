package com.ensat.ProjetSpringBootEmployee.Controller;

import com.ensat.ProjetSpringBootEmployee.Model.Departement;
import com.ensat.ProjetSpringBootEmployee.Service.DepartementService;
import com.ensat.ProjetSpringBootEmployee.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistiques")
public class StatistiquesController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartementService departmentService;

    @GetMapping()
    public String afficherNombreTotalEmployes(Model model ,Model model1 ,Model model2 ) {
        int nombreTotalEmployes = employeeService.getNombreTotalEmployes();
        model.addAttribute("nombreTotalEmployes", nombreTotalEmployes);

        Departement departementAvecPlusDEmployes = departmentService.getDepartementAvecPlusDEmployes();
        model1.addAttribute("departementAvecPlusDEmployes", departementAvecPlusDEmployes);

        double masseSalarialeEntreprise = employeeService.calculerMasseSalarialeEntreprise();
        model2.addAttribute("masseSalarialeEntreprise", masseSalarialeEntreprise);
        return "statistiques";
    }

    /*@GetMapping("/departementAvecPlusDEmployes")
    public String afficherDepartementAvecPlusDEmployes(Model model) {
        Departement departementAvecPlusDEmployes = departmentService.getDepartementAvecPlusDEmployes();
        model.addAttribute("departementAvecPlusDEmployes", departementAvecPlusDEmployes);
        return "departement-avec-plus-d-employes";
    }*/

    /*@GetMapping("/masseSalarialeEntreprise")
    public String calculerMasseSalarialeEntreprise(Model model) {
        double masseSalarialeEntreprise = employeeService.calculerMasseSalarialeEntreprise();
        model.addAttribute("masseSalarialeEntreprise", masseSalarialeEntreprise);
        return "masse-salariale-entreprise";
    }*/
}
