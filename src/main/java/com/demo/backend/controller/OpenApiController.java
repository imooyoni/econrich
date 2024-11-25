package com.demo.backend.controller;

import com.demo.backend.service.OpenApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/openapi")
@RequiredArgsConstructor
@Tag(name = "openapi", description = "공공 데이터 OPEN API")
public class OpenApiController {

    private final OpenApiService openApiService;

    @GetMapping("/getNaMovementOrg_v2")
    public ResponseEntity<String> getOpenApiData(@RequestParam(defaultValue = "1") int currentPage
                                               , @RequestParam(defaultValue = "10") int perPage) throws IOException {
        return ResponseEntity.ok(openApiService.getOpenApiData(currentPage, perPage));
    }
}
