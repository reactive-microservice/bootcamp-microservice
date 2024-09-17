package com.pragma.api.mapper;

import com.pragma.api.dto.BootcampDTO;
import com.pragma.model.bootcamps.Bootcamp;
import com.pragma.model.bootcamps.Capacity;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BootcampMapper {

    default Bootcamp toDomain(BootcampDTO bootcampDTO) {
        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setName(bootcampDTO.getName());
        bootcamp.setDescription(bootcampDTO.getDescription());
        bootcamp.setCapacities(bootcampDTO.getCapacities().stream().map(capacityId -> {
            Capacity capacity = new Capacity();
            capacity.setId(capacityId);
            return capacity;
        }).collect(Collectors.toList()));
        return bootcamp;
    }
}
