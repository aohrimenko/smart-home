package com.smarthome.application.restApiData.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DoorNoiseData {

    @NotNull
    @Min(1)
    @Schema(title = "The relative people count based on the noise.")
    private Integer personCount;

    @NotNull
    @Schema(title = "The diversity of the sexes, based on the voice data.")
    private Boolean sexDiversity;

    @NotNull
    @Schema(title = "Flag, indicating the probability of elders in the group(mother, fathers, grandparents).")
    private Boolean probableElderRelations;

}
