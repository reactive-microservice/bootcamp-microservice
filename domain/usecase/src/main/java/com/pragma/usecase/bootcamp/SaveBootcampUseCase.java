package com.pragma.usecase.bootcamp;

import com.pragma.model.bootcamps.Bootcamp;
import com.pragma.model.bootcamps.Capacity;
import com.pragma.model.bootcamps.gateways.BootcampRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Validated
public class SaveBootcampUseCase {

    private final BootcampRepository bootcampRepository;
    private final FindCapacityByIdUseCase findCapacityByIdUseCase;


    public Mono<Bootcamp> action(@Valid Bootcamp bootcamp) {
        return Mono.just(bootcamp)
                .flatMapMany(boot -> Flux.fromIterable(boot.getCapacities()))
                .flatMap(this::fetchCapacity)
                .then(Mono.just(bootcamp))
                .flatMap(bootcampRepository::save);
    }

    private Mono<Capacity> fetchCapacity(Capacity capacity) {
        return findCapacityByIdUseCase.action(capacity.getId())
                .map(fetchedCapacity -> {
                    capacity.setName(fetchedCapacity.getName());
                    capacity.setTechnologies(fetchedCapacity.getTechnologies());
                    return capacity;
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Capacity not found")));
    }




}
