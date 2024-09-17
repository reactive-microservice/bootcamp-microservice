package com.pragma.api;

import com.pragma.api.dto.BootcampDTO;
import com.pragma.api.mapper.BootcampMapper;
import com.pragma.model.bootcamps.Bootcamp;
import com.pragma.usecase.bootcamp.FindAllBootcampsUseCase;
import com.pragma.usecase.bootcamp.SaveBootcampUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final SaveBootcampUseCase saveBootcampUseCase;
    private final BootcampMapper bootcampMapper;
    private final FindAllBootcampsUseCase findAllBootcampsUseCase;

    public Mono<ServerResponse> listenPOSTSaveBootcampUseCase(ServerRequest request) {
        return request.bodyToMono(BootcampDTO.class)
                .map(bootcampMapper::toDomain)
                .flatMap(saveBootcampUseCase::action)
                .flatMap(capacity -> ServerResponse.ok().bodyValue(capacity))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

    public Mono<ServerResponse> listenGETFindAllBootcampsUseCase(ServerRequest request) {
        int page = Integer.parseInt(request.pathVariable("page"));
        int size = Integer.parseInt(request.pathVariable("size"));
        Boolean asc = Boolean.valueOf(request.pathVariable("asc"));
        Boolean orderByCapSize = Boolean.valueOf(request.pathVariable("orderByCapSize"));

        return ServerResponse.ok().body(findAllBootcampsUseCase.action(page, size, asc), Bootcamp.class);
    }

}
