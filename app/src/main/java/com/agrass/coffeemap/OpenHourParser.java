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
    private String closed = "закрыто";
    private String noData = "нет данных";
    private String always = "Круглосуточно";
    private String workingTo = "до %s";
    private String workingFrom = "с %s";
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

        if (openHours.equals("")) {
            return noData;
        }

        if (Objects.equals(openHours, "24/7")) {
            return always;
        }

        try {
            if (isOpen(parseOpenHours(openHours, dayNumber))) {
                return getEndTimeWork(parseOpenHours(openHours, dayNumber));
            } else {
                return closed;
            }
        } catch (Exception e){
            Log.e("OpenHour.getOpenHours()", e.toString());
        }

        return noData;
    }

    /*
     * TODO: Add rule: "Su-Mo 08:00-23:00"
     */
    private String parseOpenHours(String StrOpenHour, int dayNumber) {

        if (StrOpenHour.contains(";")) { //for rules where many rules as the "Mo-Th 09:00-23:00; Fr 09:00-23:00; Sa 11:00-23:00; Su off"
            String[] timeParts = StrOpenHour.split(";");

            if (checkDay(timeParts, dayNumber)) { //for exceptions in large intervals as the "Mo-Su 08:00-23:00; Fr off"
                if (numOfTimePart != 0) {
                    timeParts[numOfTimePart] = new StringBuilder(timeParts[numOfTimePart]).deleteCharAt(0).toString();
                }
                return timeParts[numOfTimePart].split(" ")[1];
            }

            for (int k = 0; k < timeParts.length; k++) {
                if (k != 0) {
                    timeParts[k] = new StringBuilder(timeParts[k]).deleteCharAt(0).toString();
                }

                String days = timeParts[k].split(" ")[0];
                String time = timeParts[k].split(" ")[1];

                if (isInInterval(days, dayNumber)){
                    return time;
                }
            }
        } else if (isConsistDays(StrOpenHour)) { // for rules where small interval "Mo-Fr 09:00-23:00"
            String days = StrOpenHour.split(" ")[0];
            String time = StrOpenHour.split(" ")[1];
            if (isInInterval(days, dayNumber)){
                return time;
            }
        }

        if (!isConsistDays(StrOpenHour)) { // for rules where "08:00-20:00" (without days)
            return StrOpenHour;
        }

        if (StrOpenHour.equals("")) { // if empty
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
            return noData;
        }
    }

    private boolean isConsistDays(String openHours) {
        return openHours.contains(" ");
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

        if (getOpenHours(openHours, dayNumber).equals(noData)) {
            return MARKER_COLOR_GREY;
        }

        if (openHours.equals("24/7")) {
            return MARKER_COLOR_GREEN;
        } else if (!getOpenHours(openHours, dayNumber).equals(closed)) {
            return MARKER_COLOR_GREEN;
        } else if (getOpenHours(openHours, dayNumber).equals(closed)) {
            return MARKER_COLOR_RED;
        }

        return MARKER_COLOR_GREY;
    }

    private int getCurrentHour() {
        //kk - Hour in day (1-24)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk", Locale.US);
        String currentHours = simpleDateFormat.format(mCalendar.getTime());
        currentHours = currentHours.startsWith("0") ? currentHours.substring(1) : currentHours;
        return Integer.valueOf(currentHours);

    }

    private int getCurrentMin() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm", Locale.US);
        String currentHours = simpleDateFormat.format(mCalendar.getTime());
        currentHours = currentHours.startsWith("0") ? currentHours.substring(1) : currentHours;
        return Integer.valueOf(currentHours);
    }

    private int getCafeCloseHours(String cafeHoursOfOpen) {
        cafeHoursOfOpen = cafeHoursOfOpen.split("-")[1]; // 08:00-20:00 => 20:00
        String hours = cafeHoursOfOpen.split(":")[0]; // 20:00 => 20
        if (hours.equals("00")) {
            return 24;
        } else {
            hours = hours.startsWith("0") ? hours.substring(1) : hours; //see getCafeOpenedHours()
            return Integer.valueOf(hours);
        }
    }

    private int getCafeOpenHours(String cafeHoursOfOpen) {
        cafeHoursOfOpen = cafeHoursOfOpen.split("-")[0]; // 08:00-20:00 => 08:00
        String hours = cafeHoursOfOpen.split(":")[0]; // 08:00 => 08

        if (hours.equals("00")) {
            return 24;
        } else {
            hours = hours.startsWith("0") ? hours.substring(1) : hours; // 08 => 8
            return Integer.valueOf(hours);
        }
    }

    private int getCafeOpenMins(String cafeHoursOfOpen) {
        cafeHoursOfOpen = cafeHoursOfOpen.split("-")[0]; // 08:30-20:00 => 08:30
        String mins = cafeHoursOfOpen.split(":")[1]; // 08:30 => 30

        if (mins.equals("00")) { // 08:00 => 00 => not valid int value => 0
            return 0;
        } else {
            mins = mins.startsWith("0") ? mins.substring(1) : mins; // 08 => 8
            return Integer.valueOf(mins);
        }
    }

    private int getCafeCloseMins(String cafeHoursOfOpen) {
        cafeHoursOfOpen = cafeHoursOfOpen.split("-")[1]; // 08:30-20:00 => 08:30
        String mins = cafeHoursOfOpen.split(":")[1]; // 08:30 => 30

        if (mins.equals("00")) { // 08:00 => 00 => not valid int value => 0
            return 0;
        } else {
            mins = mins.startsWith("0") ? mins.substring(1) : mins; // 08 => 8
            return Integer.valueOf(mins);
        }
    }

    private boolean isOpen(String cafeHoursOfOpen) {

        if (cafeHoursOfOpen.contains(dayOff)) {
            return false;
        }

        if (cafeHoursOfOpen.equals(noData)) {
            return false;
        }

        if (getCurrentHour() < getCafeCloseHours(cafeHoursOfOpen) && getCurrentHour() > getCafeOpenHours(cafeHoursOfOpen)) {
            return true; // interval 08:00-20:00, current 10:00
        } else if (getCurrentHour() == getCafeCloseHours(cafeHoursOfOpen) && getCurrentMin() < getCafeCloseMins(cafeHoursOfOpen)) {
            return true; // interval 08:00-20:00, current 20:30
        }  else if (getCurrentHour() == getCafeOpenHours(cafeHoursOfOpen) && getCurrentMin() > getCafeOpenMins(cafeHoursOfOpen)) {
            return true; // interval 08:00-20:00, current 08:30
        } else {
            return false;
        }
    }
}
