package com.learning.benchmarker2000.results.api.solver;

import com.learning.benchmarker2000.results.Result;
import com.learning.benchmarker2000.results.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class SolverResultController {
    
    private final ResultRepository resultRepository;
    
    @PostMapping("/result")
    public HttpStatus sendResult(@RequestBody Result result) {
        resultRepository.save(result);
        return HttpStatus.OK;
    }
}
