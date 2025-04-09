package com.smarthome.application.service.serviceRegistry;

import com.smarthome.application.mapper.DeviceResponseMapper;
import com.smarthome.application.restApiData.response.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class DeviceUpdaterRegistry {

    private final DeviceResponseMapper deviceResponseMapper;

    private final Map<DeviceResponseDescriptor, BiConsumer<CurrentDeviceConfigurationResponse, Object>> updateRegistry = new EnumMap<>(DeviceResponseDescriptor.class);

    private final Map<DeviceResponseDescriptor, BiConsumer<DeviceHealthStateResponse, Object>> healthRegistry = new EnumMap<>(DeviceResponseDescriptor.class);

    @PostConstruct
    public void init() {
        updateRegistry.put(DeviceResponseDescriptor.MUSIC, (resp, data) -> deviceResponseMapper.updateWithDeviceData(resp, (MusicDeviceResponse) data));
        updateRegistry.put(DeviceResponseDescriptor.CLIMATE, (resp, data) -> deviceResponseMapper.updateWithDeviceData(resp, (ClimateDeviceResponse) data));
        updateRegistry.put(DeviceResponseDescriptor.LIGHT, (resp, data) -> deviceResponseMapper.updateWithDeviceData(resp, (LightDeviceResponse) data));
    }

    public BiConsumer<CurrentDeviceConfigurationResponse, Object> getUpdater(final DeviceResponseDescriptor type) {
        return updateRegistry.get(type);
    }

}