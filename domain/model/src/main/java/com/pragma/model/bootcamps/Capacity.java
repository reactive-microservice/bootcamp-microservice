package com.pragma.model.bootcamps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Capacity {
    private String id;
    private String name;
    private List<Technology> technologies;
}

