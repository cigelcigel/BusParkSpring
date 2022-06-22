package com.transportpark.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Builder

public class Assignment {
    private Long id;

    @Min(1)
    @Max(100000)
    private double tax;

    @Min(0)
    @Max(100)
    private Integer journey;

    @Min(0)
    @Max(1)
    private Integer canceled;

    private Route route;

    private Bus bus;
}
