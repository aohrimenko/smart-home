package com.smarthome.application.config;

import com.smarthome.application.enums.HomeTheme;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties()
@Builder
public class HomeThemeProperties {

    @NotNull
    private Map<HomeTheme, HomeThemeConfiguration> homeThemeProperties;

    public HomeThemeConfiguration getConfiguration(final HomeTheme homeTheme) {
        return homeThemeProperties.get(homeTheme);
    }

}
