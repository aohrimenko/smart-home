package com.smarthome.application.service;

import com.smarthome.application.config.HomeThemeConfiguration;
import com.smarthome.application.config.HomeThemeProperties;
import com.smarthome.application.enums.HomeTheme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeResolverService {

    private final HomeThemeProperties homeThemeProperties;

    /**
     * @param theme the resolved, based on the request data, theme that the devices should be configured for
     * @return configuration with specific details about how all the involved services should be configured based on the theme
     */
    public HomeThemeConfiguration resolveThemeSettings(HomeTheme theme) {
        if (theme == HomeTheme.CALL_HELP) {
            // the system should give the home owner about 20 seconds and then call for help
            return null;
        }
        return homeThemeProperties.getConfiguration(theme);
    }

}
