package com.ensat.ProjetSpringBootEmployee.Repository;

import com.ensat.ProjetSpringBootEmployee.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Integer> {
    @Query("SELECT COUNT(e) FROM Employee e")
    int countTotalEmployees();
    @Query("SELECT sum(e.salaire) FROM Employee e")
    double countTotalSalaire() ;

    @Query("SELECT SUM(e.salaire) FROM Employee e WHERE e.departement.idDept = :departmentId")
    Double sumSalaryByDepartment(@Param("departmentId") String departmentId);
    List<Employee> findByNomEmpContainingIgnoreCase(String nom);
}
