package com.smarthome.application.mapper;

import com.smarthome.application.eventData.ChangeClimateEvent;
import com.smarthome.application.eventData.ChangeLightEvent;
import com.smarthome.application.eventData.ChangeMusicEvent;
import com.smarthome.application.eventData.HomeThemeEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceEventDataMapper {

    ChangeMusicEvent mapToMusicEvent(HomeThemeEvent event);

    ChangeClimateEvent mapToClimateEvent(HomeThemeEvent event);

    ChangeLightEvent mapToLightEvent(HomeThemeEvent event);

}
