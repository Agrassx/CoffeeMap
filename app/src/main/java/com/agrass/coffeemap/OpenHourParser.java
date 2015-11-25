package com.agrass.coffeemap;

import android.util.Log;

import java.util.Objects;

public class OpenHourParser {
    private String strOpenHours;
    private String openingHours;
    final private String[] daysStr = {"Mo","Tu","We","Th","Fr","Sa","Su"};

    public OpenHourParser(String openHours, int numOfDay) {
        if (!Objects.equals(openHours, "24/7")) {
            strOpenHours = openHours;
            try {
                openingHours = "Работает до "+ getEndTimeWork(openHours, numOfDay - 1);
            } catch (Exception e){
                Log.e("OH",e.toString());
            }

        } else {
            openingHours = "круглосуточно";
        }
    }

    private String getEndTimeWork(String StrOpenHour, int numOfDay) {
        String maintime = "";
        if (StrOpenHour.contains(";")) {
            String[] timeParts = StrOpenHour.split(";");
            for (int k = 0; k < 1; k++) {
                if (!maintime.equals("")) {
                    break;
                }
                    String parts[] = timeParts[k].split(" ");

                    String time = parts[1];
                    String days = parts[0];

                    if(days.contains("-")) {
                        String lastDay[] = days.split("-");
                        for (int i = 0; i < daysStr.length - 1; i++) {
                            if(lastDay[1].equals(daysStr[i]) ) {
                                if(numOfDay<=i){
                                    maintime = time.split("-")[1];
                                    break;
                                }
                            }
                        }

                    }
                    if(days.contains(",")){
                        String lastDay[] = days.split("-");
                        for (int i = 0; i <= daysStr.length - 1; i++) {
                            for (int j = 0; j <= lastDay.length; j++) {
                                if(lastDay[j].equals(daysStr[i]) ) {
                                    if(numOfDay<=i){
//                                        result21 = time.split("-");
                                        maintime = time.split("-")[1];
                                        break;
                                    }
                                }
                            }
                        }
                    }
            }
        } else {
            String[] timeParts = StrOpenHour.split(" ");
            String days = timeParts[0];
            String time = timeParts[1];
            if(days.contains("-")) {
                String lastDay[] = days.split("-");
                for (int i = 0; i < daysStr.length; i++) {
                    if(lastDay[1].equals(daysStr[i]) ) {
                        if(numOfDay<=i){
                            maintime = time.split("-")[1];
                            break;
                        }
                    }
                }

            }
            if(days.contains(",")){
                    String lastDay[] = days.split("-");
                    for (int i = 0; i <= daysStr.length - 1; i++) {
                        for (int j = 0; j <= lastDay.length; j++) {
                            if(lastDay[j].equals(daysStr[i]) ) {
                                if(numOfDay<=i){
//                                        result21 = time.split("-");
                                    maintime = time.split("-")[1];
                                    break;
                                }
                            }
                        }
                    }
                }

        }
        maintime = (maintime != null ? maintime : "null");
        return maintime;
    }

    public String getOpenHours() {
        return openingHours;
    }

}
