package com.booking.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FonoApiResponse {

    private String deviceName;
    private String technology;
    private String _2g_bands;
    private String _3g_bands;
    private String _4g_bands;

}
