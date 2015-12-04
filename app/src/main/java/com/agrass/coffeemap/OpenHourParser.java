package com.agrass.coffeemap;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OpenHourParser {
//    final private String[] daysStr = {"Mo","Tu","We","Th","Fr","Sa","Su"};
    private Map<String, Integer> weekDays = new HashMap<>();

    public OpenHourParser() {
//        Integer s = weekDays.get("Mo");
        weekDays.put("Mo", 1);
        weekDays.put("Tu", 2);
        weekDays.put("We", 3);
        weekDays.put("Th", 4);
        weekDays.put("Fr", 5);
        weekDays.put("Sa", 6);
        weekDays.put("Su", 7);

        String[] variantsOfRules = {
                "24/7",
                "Mo-Su 09:00-23:00",
                "Mo-Su 08:00-23:00; Fr off; Sa off",
                "Mo-Fr 08:00-23:00; Sa,Su 10:00-23:00",
                "Mo-Fr 08:00-23:00; Sa-Su 09:00-23:00",
                "Mo-Th 09:00-23:00; Fr 09:00-23:00; Sa 11:00-23:00; Su 11:00-24:00"
        };
    }

    public String getOpenHours(String openHours, int dayNumber) {
        if (!Objects.equals(openHours, "24/7")) {
            try {
                return parseOpenHours(openHours, dayNumber - 1);
            } catch (Exception e){
                Log.e("Open Hour",e.toString());
            }
        } else {
            return "Работает круглосуточно";
        }
        return "";
    }

    private String parseOpenHours(String StrOpenHour, int dayNumber) {
        if (StrOpenHour.contains(";")) {
            String[] timeParts = StrOpenHour.split(";");
            for (int k = 0; k < timeParts.length; k++) {
                if (k != 0) {
                    timeParts[k] = new StringBuilder(timeParts[k]).deleteCharAt(0).toString();
                }
                String days = timeParts[k].split(" ")[0];
                String time = timeParts[k].split(" ")[1];
                if (isInInterval(days, dayNumber)){
                    return getEndTimeWork(time);
                }
            }
        } else {
            String days = StrOpenHour.split(" ")[0];
            String time = StrOpenHour.split(" ")[1];
            if (isInInterval(days, dayNumber)){
                return getEndTimeWork(time);
            }
        }
        if (StrOpenHour.equals("")) {
            return "Нет данных";
        }
        return "Сегодня закрыто";
    }

    private Boolean isInInterval(String interval, int dayNumber){
        if(interval.contains("-")) {
            Integer leftLimit = weekDays.get(interval.split("-")[0]);
            Integer rightLimit = weekDays.get(interval.split("-")[1]);
            if (leftLimit <= dayNumber && dayNumber <= rightLimit) {
                return true;
            }
        } else if(interval.contains(",")) {
            String[] splitedInt = interval.split(",");
            for (int i = 0; i < splitedInt.length; i++) {
                if (dayNumber == weekDays.get(splitedInt[i])) {
                    return true;
                }
            }
        } else {
            if(dayNumber == weekDays.get(interval)) {
                return true;
            }
        }
        return false;
    }

    private String getEndTimeWork(String time) {
        if (time.contains("-")) {
            return String.format("Работает до %s", time.split("-")[1]);
        } else if(time.contains("off")) {
            return "Сегодня закрыто";
        } else {
            return "";
        }
    }
}
