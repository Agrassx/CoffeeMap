package com.agrass.coffeemap.model.parsers;

public class OSMHoursParser{

    static public String OpenTill(String workHours, int dayNumber) {
         return new OpenHourParser().getOpenHours(workHours, dayNumber);
    }



}
