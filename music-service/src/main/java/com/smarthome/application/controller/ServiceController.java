package com.smarthome.application.controller;

import com.smarthome.application.restApiData.ApplicationResponse;
import com.smarthome.application.restApiData.MusicResponse;
import com.smarthome.application.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/service", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceController {

    private final MusicService musicService;

    @GetMapping
    @Operation(summary = "Get service configuration.", description = "Retrieves the current active service configuration.")
    public ResponseEntity<ApplicationResponse<MusicResponse>> getConfiguration() {
        return ResponseEntity.ok(ApplicationResponse.buildSuccessResponse(musicService.getCurrentConfig()));
    }

    @GetMapping(path = "/health")
    @Operation(summary = "Returns health check.", description = "Returns the health state of the service.")
    public ResponseEntity<ApplicationResponse<Void>> getHealthStatus() {
        return ResponseEntity.ok(ApplicationResponse.buildSuccessResponse());
    }

}
