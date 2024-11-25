package com.demo.backend.controller;

import com.demo.backend.dto.Employee;
import com.demo.backend.entity.Employees;
import com.demo.backend.service.EmployeesService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "employees", description = "사원정보 API")
public class EmployeesController {

    private final EmployeesService employeesService;

    @Hidden
    @GetMapping("/test")
    public String testApi(){
        return "test";
    }

    @GetMapping("/{employeeid}")
    @Operation(
            summary = "특정 사원의 현재 정보 조회 가능한 API",
            description = "사번을 통해 특정 사원의 현재 정보를 조회하는 API입니다."
    )
    public ResponseEntity<Employees> getEmployee(@PathVariable Integer employeeid) {
        Employees employee = employeesService.findEmployee(employeeid);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/modification")
    @Operation(
            summary = "특정 사원의 정보를 업데이트 할 수 있는 API",
            description = "사번을 통해 특정 사원의 정보를 수정하는 API입니다."
    )
    public ResponseEntity<String> updateEmployeeInfo(@RequestBody Employee employee) {
        try {
            employeesService.updateEmployeeInfo(employee);
            return ResponseEntity.ok("직원 정보가 성공적으로 업데이트되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("직원 정보 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

}
