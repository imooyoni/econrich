package com.demo.backend.service;

import com.demo.backend.dto.SalaryRate;
import com.demo.backend.entity.Departments;
import com.demo.backend.entity.Employees;
import com.demo.backend.repository.DepartmentRepository;
import com.demo.backend.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentsService {

    private final DepartmentRepository departmentRepository;
    private final EmployeesRepository employeesRepository;

    public Departments findDepartmentsLocation(Integer departmentId) {
        Departments departmentLocation = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
        return departmentLocation;
    }

    public void updateDepartmentSalary(SalaryRate salaryRate) {
        int departmentId = salaryRate.getDepartmentId();
        float increaseRate = salaryRate.getIncreaseRate();

        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        long employeeCount = employeesRepository.countByDepartmentDepartmentId(departmentId);
        if (employeeCount == 0) {
            throw new RuntimeException("No employees found in department with id: " + departmentId);
        }
        // 해당 부서의 모든 직원 조회
        List<Employees> employeesList = employeesRepository.findByDepartmentDepartmentId(departmentId);

        // 급여 인상 처리
        for (Employees employee : employeesList) {
            BigDecimal currentSalary = employee.getSalary();
            if (currentSalary != null) {
                BigDecimal rate = BigDecimal.valueOf(1 + (increaseRate / 100));
                BigDecimal newSalary = currentSalary.multiply(rate)
                        .setScale(2, RoundingMode.HALF_UP);
                employee.setSalary(newSalary);
            }
        }

        employeesRepository.saveAll(employeesList);
    }
}
