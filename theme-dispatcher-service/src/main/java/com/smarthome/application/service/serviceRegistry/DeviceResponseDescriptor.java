package com.smarthome.application.service.serviceRegistry;

import com.smarthome.application.restApiData.response.ClimateDeviceResponse;
import com.smarthome.application.restApiData.response.DeviceHealthStateResponse;
import com.smarthome.application.restApiData.response.LightDeviceResponse;
import com.smarthome.application.restApiData.response.MusicDeviceResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;

@Getter
@RequiredArgsConstructor
public enum DeviceResponseDescriptor {
    MUSIC("music-service", MusicDeviceResponse.class, DeviceHealthStateResponse::setMusic),
    CLIMATE("climate-service", ClimateDeviceResponse.class, DeviceHealthStateResponse::setClimate),
    LIGHT("light-service", LightDeviceResponse.class, DeviceHealthStateResponse::setLights);

    private final String serviceName;

    private final Class<?> responseClass;

    private final BiConsumer<DeviceHealthStateResponse, String> healthSetter;
}