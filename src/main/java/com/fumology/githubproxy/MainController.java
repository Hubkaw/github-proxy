package com.fumology.githubproxy;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
@AllArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/{login}")
    public ResponseEntity<Object> getRepositoriesByLogin(@PathVariable String login) {
        Object output = mainService.getRepositoriesByLogin(login);
        if (output == null) {
            Map<String, Object> body = Map.of(
                    "status", 404,
                    "message", "There is no user named %s."
                            .formatted(login));
            return ResponseEntity.status(404).body(body);
        }

        return ResponseEntity.ok(output);

    }
}
