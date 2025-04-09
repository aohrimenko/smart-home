package com.smarthome.application.restApiData.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smarthome.application.data.ThemeDeviceSettings;
import com.smarthome.application.enums.HomeTheme;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class ThemeDeviceSettingsResponse extends ThemeDeviceSettings {

    @Schema(title = "The resolved theme name settings.")
    private HomeTheme theme;

}
