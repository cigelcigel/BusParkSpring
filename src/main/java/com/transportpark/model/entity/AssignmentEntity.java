package com.transportpark.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "assignments")
public class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "tax")
    private double tax;

    @Column(name = "journey")
    private Integer journey;

    @Basic(optional = false)
    @Column(name = "status", columnDefinition = "integer default 0")
    private Integer canceled;

    @JoinColumn(name = "id_route", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RouteEntity route;

    @JoinColumn(name = "id_bus", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BusEntity bus;
}

