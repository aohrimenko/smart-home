package com.smarthome.application.mapper;

import com.smarthome.application.restApiData.response.ClimateDeviceResponse;
import com.smarthome.application.restApiData.response.CurrentDeviceConfigurationResponse;
import com.smarthome.application.restApiData.response.LightDeviceResponse;
import com.smarthome.application.restApiData.response.MusicDeviceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeviceResponseMapper {

    void updateWithDeviceData(@MappingTarget CurrentDeviceConfigurationResponse response, LightDeviceResponse lightDeviceResponse);

    void updateWithDeviceData(@MappingTarget CurrentDeviceConfigurationResponse response, MusicDeviceResponse musicDeviceResponse);

    void updateWithDeviceData(@MappingTarget CurrentDeviceConfigurationResponse response, ClimateDeviceResponse climateDeviceResponse);

}
