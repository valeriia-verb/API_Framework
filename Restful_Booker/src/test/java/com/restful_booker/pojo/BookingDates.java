package com.restful_booker.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDates {
    private String checkin;
    private String checkout;
}

