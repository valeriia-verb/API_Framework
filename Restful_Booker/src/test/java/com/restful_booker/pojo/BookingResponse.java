package com.restful_booker.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = "bookingid", allowSetters = true)
public class BookingResponse {
    private int bookingid;
    private Booking booking;

}

