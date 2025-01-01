package org.example.springbatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "EMPLOYEE_TBL")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "SALARY")
    private double salary;

    @Column(name = "DOJ")
    private String doj;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "BATCH_ID")
    private Long batchId;
}
