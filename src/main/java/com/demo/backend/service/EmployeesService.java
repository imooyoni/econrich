package com.demo.backend.service;

import com.demo.backend.dto.Employee;
import com.demo.backend.entity.Departments;
import com.demo.backend.entity.Employees;
import com.demo.backend.repository.DepartmentRepository;
import com.demo.backend.repository.EmployeesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployeesRepository employeesRepository;
    private final DepartmentRepository departmentRepository;

    public Employees findEmployee(Integer employeeId) {
        Employees employees = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
        return employees;
    }

    public Employees updateEmployeeInfo(Employee employee) {
        int employeeId = employee.getEmployeeId();
        Employees originEmployee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        employee.updateEntity(originEmployee);

        // 매니저 설정
        if (employee.getManagerId() != null) {
            Employees manager = employeesRepository.findById(employee.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("매니저를 찾을 수 없습니다. ID: " + employee.getManagerId()));
            originEmployee.setManager(manager);
        }

        // 부서 설정
        if (employee.getDepartmentId() != null) {
            Departments department = departmentRepository.findById(employee.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("부서를 찾을 수 없습니다. ID: " + employee.getDepartmentId()));
            originEmployee.setDepartment(department);
        }

        employeesRepository.save(originEmployee);

        return originEmployee;

    }
}
