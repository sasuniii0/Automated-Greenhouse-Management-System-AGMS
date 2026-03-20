package com.example.Zone.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZoneRequest {
    @NotNull
    private String name;

    @NotNull
    private Double minTemp;

    @NotNull
    private Double maxTemp;
}
