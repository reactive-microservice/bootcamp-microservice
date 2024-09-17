package com.pragma.usecase.bootcamp;

import com.pragma.model.bootcamps.Bootcamp;
import com.pragma.model.bootcamps.gateways.BootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindAllBootcampsUseCase {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final BootcampRepository bootcampRepository;

    public Flux<Bootcamp> action(int page, int size, Boolean asc, Boolean orderByCapSize) {
        Sort.Direction direction = Boolean.TRUE.equals(asc) ? Sort.Direction.ASC : Sort.Direction.DESC;

        if (Boolean.FALSE.equals(orderByCapSize)) {
            return bootcampRepository.findAll(Sort.by(direction, "name"))
                    .skip((long) page * size)
                    .take(size)
                    .flatMap(this::fetchCategoriesWithTechnologies);
        }
        // Aggregation to count the number of capacities and sort by that number
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("name", "capacities")
                        .and("capacities").size().as("capacityCount"),
                Aggregation.sort(direction, "capacityCount"),
                Aggregation.skip((long) page * size),
                Aggregation.limit(size)
        );

        return reactiveMongoTemplate.aggregate(aggregation, "bootcamp", Bootcamp.class);
    }

    private Mono<Bootcamp> fetchCategoriesWithTechnologies(Bootcamp bootcamp) {
        return Flux.fromIterable(bootcamp.getCapacities())
                .collectList()
                .map(capacities -> {
                    bootcamp.setCapacities(capacities);
                    return bootcamp;
                });
    }

}
