package com.smarthome.application.restApiData.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class DeviceHealthStateResponse {

    private String dispatcher;

    private String music;

    private String climate;

    private String lights;

}
