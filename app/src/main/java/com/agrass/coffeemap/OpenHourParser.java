package com.agrass.coffeemap;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class OpenHourParser implements MarkerColors {
    private Map<String, Integer> weekDays = new HashMap<>();
    private Calendar mCalendar = Calendar.getInstance();
    private int numOfTimePart;
    private String closed= "закрыто";
    private String noData = "нет данных";
    private String always = "Круглосуточно";
    private String workingTo = "до %s";
    private String dayOff = "off";

    public OpenHourParser() {
        weekDays.put("Mo", 1);
        weekDays.put("Tu", 2);
        weekDays.put("We", 3);
        weekDays.put("Th", 4);
        weekDays.put("Fr", 5);
        weekDays.put("Sa", 6);
        weekDays.put("Su", 7);
    }

    public String getOpenHours(String openHours, int dayNumber) {
        --dayNumber;
        if (dayNumber == 0) {
            dayNumber = 7;
        }
        if (!Objects.equals(openHours, "24/7")) {
            try {
                Integer cafeHours = Integer.valueOf(parseOpenHours(openHours, dayNumber).split(" ")[1].split(":")[0]);
                if (getCurrentHour() <= cafeHours && !parseOpenHours(openHours, dayNumber).equals(closed)) {
                    return parseOpenHours(openHours, dayNumber);
                } else {
                    return closed;
                }
            } catch (Exception e){
                Log.e("Open Hour",e.toString());
            }
        } else {
            return always;
        }
        return "";
    }
    /*
     * TODO: Add 2 rules - "09:00-23:00" and "Su-Mo 08:00-23:00"
     */
    private String parseOpenHours(String StrOpenHour, int dayNumber) {
        if (StrOpenHour.contains(";")) { //for rules where many rules as the "Mo-Th 09:00-23:00; Fr 09:00-23:00; Sa 11:00-23:00; Su off"
            String[] timeParts = StrOpenHour.split(";");
            if (checkDay(timeParts, dayNumber)) { //for exceptions in large intervals as the "Mo-Su 08:00-23:00; Fr off"
                if (numOfTimePart != 0) {
                    timeParts[numOfTimePart] = new StringBuilder(timeParts[numOfTimePart]).deleteCharAt(0).toString();
                }
                String time = timeParts[numOfTimePart].split(" ")[1];
                return getEndTimeWork(time);
            }
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
        } else { // for rules small where small interval "Mo-Fr 09:00-23:00"
            String days = StrOpenHour.split(" ")[0];
            String time = StrOpenHour.split(" ")[1];
            if (isInInterval(days, dayNumber)){
                return getEndTimeWork(time);
            }
        }
        if (StrOpenHour.equals("")) {
            return noData;
        }
        return closed;
    }

    private boolean isInInterval(String interval, int dayNumber){
        if(interval.contains("-")) {
            Integer leftLimit = weekDays.get(interval.split("-")[0]);
            Integer rightLimit = weekDays.get(interval.split("-")[1]);
            if (leftLimit <= dayNumber && dayNumber <= rightLimit) {
                return true;
            }
        } else if(interval.contains(",")) {
            String[] splitedInt = interval.split(",");
            for (String aSplitedInt : splitedInt) {
                if (dayNumber == weekDays.get(aSplitedInt)) {
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
            return String.format(workingTo, time.split("-")[1]);
        } else if(time.contains(dayOff)) {
            return closed;
        } else {
            return "";
        }
    }

    private boolean checkDay(String[] timeParts, int dayNumber) {
        String strDay = (String) getKeyFromValue(weekDays, dayNumber);
        for (int i = 0; i < timeParts.length; i++) {
            if (timeParts[i].contains(strDay)) {
                numOfTimePart = i;
                return true;
            }
        }
        return false;
    }

    private static Object getKeyFromValue(Map map, Object value) {
        for (Object obj : map.keySet()) {
            if (map.get(obj).equals(value)) {
                return obj;
            }
        }
        return null;
    }

    public int getMarkerColor(String openHours, int dayNumber) {
        if (openHours.equals("24/7")) {
            return MARKER_COLOR_GREEN;
        } else if (!getOpenHours(openHours, dayNumber).equals(closed)) {
            return MARKER_COLOR_GREEN;
        } else if (getOpenHours(openHours, dayNumber).equals(closed)) {
            return MARKER_COLOR_RED;
        }
        return MARKER_COLOR_BLUE;
    }

    private int getCurrentHour() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH", Locale.US);
        String currentHours = simpleDateFormat.format(mCalendar.getTime());
        if (currentHours.contains("0")){
            return Integer.valueOf(currentHours.replace("0",""));
        } else {
            return Integer.valueOf(currentHours);
        }
    }
}
