package com.learning.solver.controller;

import com.learning.solver.benchmarker.BenchmarkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BenchmarkController {
    private final BenchmarkService benchmarkService;

    @PostMapping("/benchmark")
    public HttpStatus benchmark(@RequestBody Dataset dataset) {
        benchmarkService.benchmark(dataset);
        return HttpStatus.OK;
    }
}

