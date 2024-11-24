package com.demo.backend.service;

import com.demo.backend.entity.Employees;
import com.demo.backend.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    public Employees findEmployee(Integer employeeId) {
        Employees employees = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
        return employees;
    }
}
