package com.smarthome.application.controller;

import com.smarthome.application.restApiData.request.DoorHandleDataRequest;
import com.smarthome.application.restApiData.response.ApplicationResponse;
import com.smarthome.application.restApiData.response.CurrentConfigurationResponse;
import com.smarthome.application.restApiData.response.DeviceHealthStateResponse;
import com.smarthome.application.restApiData.response.ThemeDeviceSettingsResponse;
import com.smarthome.application.service.DeviceService;
import com.smarthome.application.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/service", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    private final ThemeService themeService;

    private final DeviceService deviceService;

    @PostMapping
    @Operation(summary = "Sets a theme based on the door data",
            description = "Controls various devices based on the fictional data, passed by our super smart futuristic door.")
    public ResponseEntity<ApplicationResponse<ThemeDeviceSettingsResponse>> doorHandleData(@Valid @RequestBody DoorHandleDataRequest doorHandleDataRequest) {
        return ResponseEntity.ok(ApplicationResponse.buildSuccessResponse(themeService.setTheme(doorHandleDataRequest)));
    }

    @GetMapping
    @Operation(summary = "Gets the current device settings",
            description = "Retrieves the current active configurations for all the devices in the system")
    public ResponseEntity<ApplicationResponse<CurrentConfigurationResponse>> getDeviceData() {
        return ResponseEntity.ok(deviceService.getCurrentDeviceConfiguration());
    }

    @GetMapping(path = "/health")
    @Operation(summary = "Returns health check.", description = "Returns the health state of all the known services.")
    public ResponseEntity<ApplicationResponse<DeviceHealthStateResponse>> getHealthStatus() {
        return ResponseEntity.ok(deviceService.getDeviceHealth());
    }

}
