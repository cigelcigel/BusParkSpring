package com.transportpark.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Builder

public class Bus {
    private Long id;

    @Min(0)
    @Max(10000)
    private int code;

    private String model;

    @Min(1)
    @Max(100000)
    private double mileage;

    @Min(1)
    @Max(100000)
    private double consumption;

    private String status;

    private String comments;

    private User driver;

    private List<Assignment> assignments;
}
