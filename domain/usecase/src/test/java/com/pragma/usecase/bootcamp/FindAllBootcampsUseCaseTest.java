package com.pragma.usecase.bootcamp;

import com.pragma.model.bootcamps.Bootcamp;
import com.pragma.model.bootcamps.Capacity;
import com.pragma.model.bootcamps.gateways.BootcampRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
@MockitoSettings(strictness = Strictness.LENIENT)
class FindAllBootcampsUseCaseTest {

    @Mock
    private BootcampRepository bootcampRepository;

    @InjectMocks
    private FindAllBootcampsUseCase findAllBootcampsUseCase;


    @Test
    void testActionOrderByName() {
        Bootcamp bootcamp1 = new Bootcamp();
        Capacity exCap = new Capacity();
        exCap.setName("Capacity 1");
        bootcamp1.setName("Bootcamp A");
        bootcamp1.setCapacities(List.of(exCap,exCap));

        Bootcamp bootcamp2 = new Bootcamp();
        bootcamp2.setName("Bootcamp B");
        bootcamp2.setCapacities(List.of(exCap,exCap));
        given(bootcampRepository.findAll(Sort.by(Sort.Direction.ASC, "name")))
                .willReturn(Flux.just(bootcamp1, bootcamp2));

        Flux<Bootcamp> result = findAllBootcampsUseCase.action(0, 2, true, false);

        StepVerifier.create(result)
                .expectNext(bootcamp1)
                .expectNext(bootcamp2)
                .verifyComplete();
    }


}