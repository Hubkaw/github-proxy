package com.fumology.githubproxy;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/{login}")
    public ResponseEntity<Output> getRepositoriesByLogin(@PathVariable String login) {
        return ResponseEntity.ok(mainService.getRepositoriesByLogin(login));
    }
}
