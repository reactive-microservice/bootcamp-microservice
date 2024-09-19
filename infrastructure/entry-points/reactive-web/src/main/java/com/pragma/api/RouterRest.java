package com.pragma.api;

import com.pragma.api.dto.BootcampDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/bootcamp",
                    beanClass = Handler.class,
                    beanMethod = "listenPOSTSaveBootcampUseCase",
                    operation = @Operation(
                            operationId = "saveBootcamp",
                            summary = "Save a new bootcamp",
                            requestBody = @RequestBody(
                                    description = "Bootcamp to be saved",
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = BootcampDTO.class)
                                    )
                            )
                    )
            ),
            @RouterOperation(
                    path = "/api/bootcamp/{page}/{size}/{asc}/{orderByCapSize}",
                    beanClass = Handler.class,
                    beanMethod = "listenGETFindAllBootcampsUseCase",
                    operation = @Operation(
                            operationId = "findAllBootcamps",
                            summary = "Find all bootcamps",
                            parameters = {
                                    @Parameter(name = "page", description = "Page number", required = true, in = ParameterIn.PATH),
                                    @Parameter(name = "size", description = "Page size", required = true, in = ParameterIn.PATH),
                                    @Parameter(name = "asc", description = "Sort ascending", required = true, in = ParameterIn.PATH),
                                    @Parameter(name = "orderByCapSize", description = "Order by capacity size", required = true, in = ParameterIn.PATH)
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {

        return route(POST("/api/bootcamp"), handler::listenPOSTSaveBootcampUseCase)
                .andRoute(GET("/api/bootcamp/{page}/{size}/{asc}/{orderByCapSize}"), handler::listenGETFindAllBootcampsUseCase);
    }
}
