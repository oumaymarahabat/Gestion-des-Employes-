package com.ensat.ProjetSpringBootEmployee.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idEmp ;
    private String nomEmp;
    private double salaire;
    private Integer age;
    @ManyToOne
    @JoinColumn(name="departement_id")
    private Departement departement ;

}
