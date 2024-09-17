package com.pragma.usecase.bootcamp;

import com.pragma.model.bootcamps.Capacity;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

public class FindCapacityByIdUseCase {

    private final WebClient webClient;

    public FindCapacityByIdUseCase(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
    }

    public Mono<Capacity> action(String id) {
        return webClient.get()
                .uri("/api/capacity/{id}", id)
                .retrieve()
                .bodyToMono(Capacity.class);
    }

}
