package eu.venthe.testcontainers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WebController {
    private final MyEntityRepository myEntityRepository;

    @GetMapping("/value/{id}")
    ResponseEntity<Integer> getValue(@PathVariable @NonNull UUID id) {
        return ResponseEntity.of(myEntityRepository.findById(id).map(MyEntity::getValue));
    }
}
