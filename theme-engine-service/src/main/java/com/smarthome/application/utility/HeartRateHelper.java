package com.smarthome.application.utility;

import com.smarthome.application.enums.HeartRateLevel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HeartRateHelper {

    public static HeartRateLevel classify(Integer heartRate) {
        if (heartRate == null || heartRate <= 0) return HeartRateLevel.UNKNOWN;

        if (heartRate < 50 && heartRate > 30) return HeartRateLevel.LOW;
        if (heartRate >= 50 && heartRate <= 90) return HeartRateLevel.NORMAL;
        if (heartRate > 90 && heartRate <= 140) return HeartRateLevel.HIGH;
        return HeartRateLevel.CRITICAL;

    }

}
