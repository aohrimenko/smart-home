package com.smarthome.application.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodPressureRange {

    private Range normal;

    private Range low;

    private Range high;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Range {

        private Integer systolicMin;

        private Integer systolicMax;

        private Integer diastolicMin;

        private Integer diastolicMax;

    }

}