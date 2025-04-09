package com.smarthome.application.service;

import com.smarthome.application.config.HomeThemeConfiguration;
import com.smarthome.application.enums.HomeTheme;
import com.smarthome.application.mapper.ThemeResponseMapper;
import com.smarthome.application.producer.ThemeDispatcherEventProducer;
import com.smarthome.application.restApiData.request.BloodPressureData;
import com.smarthome.application.restApiData.request.DoorHandleDataRequest;
import com.smarthome.application.restApiData.request.DoorNoiseData;
import com.smarthome.application.restApiData.response.ThemeDeviceSettingsResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThemeServiceTest {

    @Captor
    ArgumentCaptor<HomeTheme> themeArgumentCaptor;

    @Mock
    private ThemeResolverService themeResolverService;

    @Mock
    private ThemeDispatcherEventProducer themeDispatcherEventProducer;

    @Mock
    private ThemeResponseMapper responseMapper;

    @InjectMocks
    private ThemeService themeService;

    @ParameterizedTest
    @MethodSource("provideThemeRequests")
    void testSetTheme(DoorHandleDataRequest request, HomeTheme expectedTheme) {
        HomeThemeConfiguration mockedConfig = new HomeThemeConfiguration();
        ThemeDeviceSettingsResponse mockedResponse = new ThemeDeviceSettingsResponse();

        when(themeResolverService.resolveThemeSettings(any())).thenReturn(mockedConfig);
        when(responseMapper.mapToResponse(any(), themeArgumentCaptor.capture())).thenReturn(mockedResponse);

        themeService.setTheme(request);

        assertEquals(expectedTheme, themeArgumentCaptor.getValue());
    }

    static Stream<Arguments> provideThemeRequests() {
        return Stream.of(
                Arguments.of(
                        buildRequest(25, buildNormalBP(), null),
                        HomeTheme.CALL_HELP
                ),
                Arguments.of(
                        buildRequest(80, buildNormalBP(), null),
                        HomeTheme.SOLITUDE
                ),
                org.junit.jupiter.params.provider.Arguments.of(
                        buildRequest(110, buildHighBP(), null),
                        HomeTheme.STRESS_RELIEF
                ),
                Arguments.of(
                        buildRequest(80, buildNormalBP(), buildRomanticNoise()),
                        HomeTheme.ROMANTIC
                ),
                Arguments.of(
                        buildRequest(80, buildNormalBP(), buildPartyNoise()),
                        HomeTheme.PARTY
                )
        );
    }

    private static DoorHandleDataRequest buildRequest(int hr, BloodPressureData bp, DoorNoiseData noise) {
        return DoorHandleDataRequest.builder()
                .hearthRate(hr)
                .bloodPressureData(bp)
                .doorNoiseData(noise)
                .build();
    }

    private static BloodPressureData buildNormalBP() {
        return new BloodPressureData(80, 120);
    }

    private static BloodPressureData buildHighBP() {
        return new BloodPressureData(100, 150);
    }

    private static DoorNoiseData buildRomanticNoise() {
        return DoorNoiseData.builder()
                .personCount(2)
                .sexDiversity(true)
                .probableElderRelations(false)
                .build();
    }

    private static DoorNoiseData buildPartyNoise() {
        return DoorNoiseData.builder()
                .personCount(5)
                .probableElderRelations(false)
                .sexDiversity(false)
                .build();
    }
}
