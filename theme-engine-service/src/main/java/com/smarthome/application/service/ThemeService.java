package com.smarthome.application.service;

import com.smarthome.application.config.HomeThemeConfiguration;
import com.smarthome.application.enums.HomeTheme;
import com.smarthome.application.mapper.ThemeResponseMapper;
import com.smarthome.application.producer.ThemeDispatcherEventProducer;
import com.smarthome.application.restApiData.request.BloodPressureData;
import com.smarthome.application.restApiData.request.DoorHandleDataRequest;
import com.smarthome.application.restApiData.request.DoorNoiseData;
import com.smarthome.application.restApiData.response.ThemeDeviceSettingsResponse;
import com.smarthome.application.utility.BloodPressureHelper;
import com.smarthome.application.utility.HeartRateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeResolverService themeResolverService;

    private final ThemeDispatcherEventProducer themeDispatcherEventProducer;

    private final ThemeResponseMapper responseMapper;

    /**
     * @param request the request with the data, that was resolved by our smart futuristic door
     * @return the current configuration set that was sent toward all the associated micro-services
     */
    public ThemeDeviceSettingsResponse setTheme(final DoorHandleDataRequest request) {

        HomeTheme homeTheme = calculateTheme(request);
        HomeThemeConfiguration homeThemeConfiguration = themeResolverService.resolveThemeSettings(homeTheme);

        themeDispatcherEventProducer.sendThemeDeviceSettings(homeThemeConfiguration);

        return responseMapper.mapToResponse(homeThemeConfiguration, homeTheme);
    }

    /**
     * This method calculates the most suitable theme on point-based calculation.
     * These calculations should otherwise be done by AI for more realistic scenario
     *
     * @param request the request with the data, that was resolved by our smart futuristic door
     * @return Theme, that should resolve specific configuration for each of the services that should be involved
     */
    private HomeTheme calculateTheme(final DoorHandleDataRequest request) {
        Map<HomeTheme, Integer> mapThemePoints = new HashMap<>();

        BloodPressureData bloodPressureData = request.getBloodPressureData();
        DoorNoiseData doorNoiseData = request.getDoorNoiseData();

        switch (HeartRateHelper.classify(request.getHearthRate())) {
            case CRITICAL -> {
                return HomeTheme.CALL_HELP;
            }
            case LOW -> addThemePoints(mapThemePoints, HomeTheme.PARTY, 2);
            case HIGH -> {
                addThemePoints(mapThemePoints, HomeTheme.CHILL, 1);
                addThemePoints(mapThemePoints, HomeTheme.SOLITUDE, 1);
                addThemePoints(mapThemePoints, HomeTheme.STRESS_RELIEF, 1);
            }
            case NORMAL -> addThemePoints(mapThemePoints, HomeTheme.SOLITUDE, 1);
        }

        switch (BloodPressureHelper.classify(bloodPressureData)) {
            case HIGH -> {
                addThemePoints(mapThemePoints, HomeTheme.CHILL, 1);
                addThemePoints(mapThemePoints, HomeTheme.SOLITUDE, 1);
                addThemePoints(mapThemePoints, HomeTheme.STRESS_RELIEF, 1);
            }
            case LOW -> {
                addThemePoints(mapThemePoints, HomeTheme.PARTY, 1);
                addThemePoints(mapThemePoints, HomeTheme.STRESS_RELIEF, 1);
            }
            case NORMAL -> {
                addThemePoints(mapThemePoints, HomeTheme.PARTY, 1);
                addThemePoints(mapThemePoints, HomeTheme.CHILL, 1);
                addThemePoints(mapThemePoints, HomeTheme.SOLITUDE, 1);
            }
            case CRITICAL -> {
                return HomeTheme.CALL_HELP;
            }
        }

        if (doorNoiseData != null) {
            if (doorNoiseData.getProbableElderRelations()) {
                addThemePoints(mapThemePoints, HomeTheme.CHILL, 1);
                addThemePoints(mapThemePoints, HomeTheme.STRESS_RELIEF, 2);
            }
            if (doorNoiseData.getPersonCount() == 1) {
                addThemePoints(mapThemePoints, HomeTheme.CHILL, 2);
                addThemePoints(mapThemePoints, HomeTheme.SOLITUDE, 3);
            }
            if (doorNoiseData.getSexDiversity()) {
                addThemePoints(mapThemePoints, HomeTheme.ROMANTIC, 1);
            }
            if (doorNoiseData.getPersonCount() == 2) {
                addThemePoints(mapThemePoints, HomeTheme.ROMANTIC, 2);
            } else {
                addThemePoints(mapThemePoints, HomeTheme.PARTY, 3);
            }

        }

        if (mapThemePoints.isEmpty()) {
            return HomeTheme.SOLITUDE;
        }

        return mapThemePoints.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();

    }

    private void addThemePoints(Map<HomeTheme, Integer> points, HomeTheme theme, int amount) {
        points.merge(theme, amount, Integer::sum);
    }

}
