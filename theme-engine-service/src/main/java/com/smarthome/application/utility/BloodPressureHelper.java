package com.smarthome.application.utility;

import com.smarthome.application.enums.BloodPressureLevel;
import com.smarthome.application.restApiData.request.BloodPressureData;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class BloodPressureHelper {

    public static BloodPressureLevel classify(BloodPressureData data) {

        if (data == null || data.getLowNumber() == null || data.getHighNumber() == null) {
            return BloodPressureLevel.UNKNOWN;
        }

        int diastolic = data.getLowNumber();
        int systolic = data.getHighNumber();

        BloodPressureLevel systolicLevel = classifyValue(
                systolic, 90, 120, 121, 180
        );

        BloodPressureLevel diastolicLevel = classifyValue(
                diastolic, 60, 80, 81, 120
        );

        return maxLevel(systolicLevel, diastolicLevel);
    }

    private static BloodPressureLevel classifyValue(int value, int normalMin, int normalMax, int highMin, int highMax) {
        if (value >= normalMin && value <= normalMax) return BloodPressureLevel.NORMAL;
        if (value < normalMin && value >= 30) return BloodPressureLevel.LOW;
        if (value > normalMax && value <= highMax) return BloodPressureLevel.HIGH;
        return BloodPressureLevel.CRITICAL;
    }

    private static BloodPressureLevel maxLevel(BloodPressureLevel a, BloodPressureLevel b) {

        List<BloodPressureLevel> levels = List.of(
                BloodPressureLevel.LOW,
                BloodPressureLevel.NORMAL,
                BloodPressureLevel.HIGH,
                BloodPressureLevel.CRITICAL
        );

        return levels.get(Math.max(levels.indexOf(a), levels.indexOf(b)));
    }

}
