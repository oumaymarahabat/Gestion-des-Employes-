package com.ensat.ProjetSpringBootEmployee.Repository;

import com.ensat.ProjetSpringBootEmployee.Model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement , String > {
    Optional<Departement> findByNomDept(String s);
    @Query("SELECT d FROM Departement d WHERE SIZE(d.employees) = (SELECT MAX(SIZE(d2.employees)) FROM Departement d2)")
    Departement getDepartementWithMaxEmployees();

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.departement.idDept = ?1")
    Long countEmployeesByDepartment(String departmentId);



}
