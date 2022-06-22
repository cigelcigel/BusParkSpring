package com.transportpark.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "buses")
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "code")
    private int code;

    @Basic(optional = false)
    @Column(name = "model")
    private String model;

    @Basic(optional = false)
    @Column(name = "mileage")
    private double mileage;

    @Basic(optional = false)
    private double consumption;

    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private String comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bus")
    private List<AssignmentEntity> assignments;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "driver_id")
    private UserEntity driver;
}
