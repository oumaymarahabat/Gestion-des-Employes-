package com.ensat.ProjetSpringBootEmployee.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "departement")
public class Departement {
    @Id
    private String idDept ;
    private String nomDept ;
    @OneToMany(mappedBy = "departement")
    private List<Employee> employees;
}
