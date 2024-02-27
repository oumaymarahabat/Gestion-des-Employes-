package com.ensat.ProjetSpringBootEmployee.Controller;

import com.ensat.ProjetSpringBootEmployee.Model.Departement;
import com.ensat.ProjetSpringBootEmployee.Model.Employee;
import com.ensat.ProjetSpringBootEmployee.Service.DepartementService;
import com.ensat.ProjetSpringBootEmployee.Service.EmployeeService;
import com.ensat.ProjetSpringBootEmployee.Service.ServiceImpl.EmployeeServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartementService departementService;

    @GetMapping("/employees")
    public String getAll(Model model){
        model.addAttribute("employees" , employeeService.getAllEmployees());
        return "employees";
    }
    @GetMapping("/employees/add")
    public String ShowEmployee(Model model)
    {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departements",departementService.getAllDepartement());
        return "addEmployee" ;
    }
    @PostMapping("/employees/add")
    public String addemployee(@ModelAttribute("employee") Employee employee){
        employeeService.addEmployee(employee);
        return "redirect:/employees";
    }
    @GetMapping("/employee/edit/{id}")
    public String ShowEditEmployee(Model model,@PathVariable Integer id)
    {
        Employee employee=employeeService.getEmployee(id);
        model.addAttribute("employee",employee );
        model.addAttribute("departements",departementService.getAllDepartement());
        return "editEmployee" ;
    }
    @PostMapping("/employee/edit/{id}")
    public String editEmployee(@ModelAttribute("employee") Employee employee,@PathVariable Integer id){
        employeeService.updateEmployee(employee,id);
        return "redirect:/employees";

    }
    @GetMapping("/employees/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        employeeService.exportToExcel(response);
    }
    /*@GetMapping("/employees/search")
    @ResponseBody
    public ResponseEntity<List<Employee> >search(@RequestParam("name") String name) {
        List<Employee> employees = employeeService.findByNameLike(name);
        return ResponseEntity.ok(employees);
    }*/
    @PostMapping("/employees/reassign/{idEmp}")
    public String reassignEmployee(@PathVariable int idEmp, @RequestParam String newDept, RedirectAttributes redirectAttributes) {
        employeeService.reassignEmployeeToDepartment(idEmp, newDept);
        redirectAttributes.addFlashAttribute("success", "Employee reassigned successfully.");
        return "redirect:/departement/" + newDept;
    }

    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee( @PathVariable Integer id){
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
