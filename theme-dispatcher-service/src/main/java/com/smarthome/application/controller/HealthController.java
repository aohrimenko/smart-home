package com.smarthome.application.controller;

import com.smarthome.application.restApiData.response.ApplicationResponse;
import com.smarthome.application.restApiData.response.DeviceHealthStateResponse;
import com.smarthome.application.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthController {

    private final DeviceService deviceService;

    @GetMapping
    @Operation(summary = "Returns health check.", description = "Returns the health state of the service.")
    public ResponseEntity<ApplicationResponse<DeviceHealthStateResponse>> getHealthStatus() {
        return ResponseEntity.ok(deviceService.getHealthState());
    }

}
