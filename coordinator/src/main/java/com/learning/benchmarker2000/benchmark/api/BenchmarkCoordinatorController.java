package com.learning.benchmarker2000.benchmark.api;

import com.learning.benchmarker2000.data.Generator;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Configuration
public class BenchmarkCoordinatorController {
    
    private static final String PATH = "/order";
    private final RestTemplate restTemplate;

//    @Value("${solvers.servers}")
    private static final List<String> solverAddresses = List.of("http://localhost:8081");
    
    public BenchmarkCoordinatorController() {
        this.restTemplate = new RestTemplate();
    }
    
    @PostMapping(PATH+"/{size}")
    public void orderBenchmark(@PathVariable int size) {

        var dataset = Generator.generateDataset(size);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(dataset, headers);

        solverAddresses.forEach(address -> restTemplate.exchange(address + "/benchmark", HttpMethod.POST, request, String.class));
    }
}
