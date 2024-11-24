package com.demo.backend.service;

import com.demo.backend.entity.Employees;
import com.demo.backend.entity.JobHistory;
import com.demo.backend.repository.JobHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;

    public List<JobHistory> findJobHistories(Integer employeeId) {
        return jobHistoryRepository.findByEmployees_EmployeeIdOrderByIdStartDateDesc(employeeId);
    }
}
