package com.pragma.model.bootcamps.gateways;

import com.pragma.model.bootcamps.Bootcamp;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import org.springframework.data.domain.Pageable;

public interface BootcampRepository extends ReactiveMongoRepository<Bootcamp,String> {

    Flux<Bootcamp> findAllBy(Pageable pageable);
}
