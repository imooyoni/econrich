package com.demo.backend.controller;

import com.demo.backend.dto.SalaryRate;
import com.demo.backend.entity.Departments;
import com.demo.backend.service.DepartmentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Tag(name = "departments", description = "부서정보 API")
public class DepartmentsController {

    private final DepartmentsService departmentsService;
    
    @GetMapping("/{departmentid}")
    @Operation(
            summary = "부서 및 위치 정보 조회 API",
            description = "부서 번호를 통해 부서와 위치 정보를 조회하는 API입니다."
    )
    public ResponseEntity<Departments> getDepartmentLocation(@PathVariable(name = "departmentid") Integer departmentId) {
        Departments departments = departmentsService.findDepartmentsLocation(departmentId);
        return ResponseEntity.ok(departments);
    }

    @PutMapping("/nagotiation")
    @Operation(
            summary = "특정 부서의 급여를 특정 비율로 인상할 수 있는 API",
            description = "부서 번호를 통해 해당 부서원들의 급여를 특정 비율로 인상하는 API입니다."
    )
    public ResponseEntity<String> updateDepartmentSalary(@RequestBody SalaryRate salaryRate){
        try {
            departmentsService.updateDepartmentSalary(salaryRate);
            return ResponseEntity.ok("done");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("급여 정보 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
