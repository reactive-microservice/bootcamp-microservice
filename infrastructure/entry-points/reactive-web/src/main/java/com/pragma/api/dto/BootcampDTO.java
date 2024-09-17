package com.pragma.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampDTO {

    private String name;
    private String description;
    private List<String> capacities;
}
