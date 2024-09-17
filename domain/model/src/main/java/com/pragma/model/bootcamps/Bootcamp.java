package com.pragma.model.bootcamps;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bootcamp {

    private String id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;

    @Size(min = 1, max = 4, message = "Capacities must have a minimum of 1 and a maximum of 4 elements")
    private List<Capacity> capacities;

}
