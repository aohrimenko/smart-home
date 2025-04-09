package com.smarthome.application.mapper;

import com.smarthome.application.config.HomeThemeConfiguration;
import com.smarthome.application.enums.HomeTheme;
import com.smarthome.application.restApiData.response.ThemeDeviceSettingsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeResponseMapper {

    ThemeDeviceSettingsResponse mapToResponse(HomeThemeConfiguration themeConfiguration, HomeTheme theme);

}
