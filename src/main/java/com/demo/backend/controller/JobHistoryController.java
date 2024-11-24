package com.demo.backend.controller;

import com.demo.backend.entity.JobHistory;
import com.demo.backend.service.JobHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobhistory")
@RequiredArgsConstructor
@Tag(name = "jobhistory", description = "직무이력 API")
public class JobHistoryController {

    private final JobHistoryService jobHistoryService;

    @GetMapping("/{employeeid}")
    @Operation(
            summary = "특정 사원의 이력 정보 조회 가능한 API",
            description = "사번을 통해 특정 사원의 과거 직무 이력 정보를 조회하는 API입니다."
    )
    public ResponseEntity<List<JobHistory>> getJobHistory(@PathVariable Integer employeeid) {
        List<JobHistory> jobHistoryList = jobHistoryService.findJobHistories(employeeid);
        return ResponseEntity.ok(jobHistoryList);
    }


}
