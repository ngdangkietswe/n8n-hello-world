package dev.ngdangkietswe.n8nhelloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class N8nHelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(N8nHelloWorldApplication.class, args);
    }

    @GetMapping("/greet")
    public ResponseEntity<?> greet(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.ok(
                Map.ofEntries(
                        Map.entry("message", "Hello, " + name + "!"),
                        Map.entry("status", "success")
                )
        );
    }
}
