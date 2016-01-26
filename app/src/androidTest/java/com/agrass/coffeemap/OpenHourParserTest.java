package com.agrass.coffeemap;

import junit.framework.TestCase;

public class OpenHourParserTest extends TestCase {

    final static int MONDAY = 2;
    final static int TUESDAY = 3;
    final static int WEDNESDAY = 4;
    final static int THURSDAY = 5;
    final static int FRIDAY = 6;
    final static int SATURDAY = 7;
    final static int SUNDAY = 1;

    OpenHourParser openHourParser;
    String[] variantsOfRules = {
            "24/7",
            "Mo-Fr 09:00-23:00",
            "Mo-Su 08:00-23:00; Fr off; Sa off",
            "Mo-Fr 08:00-23:00; Sa,Su 10:00-22:00",
            "Mo-Fr 08:00-23:00; Sa-Su 09:00-22:00",
            "Mo-Th 09:00-23:00; Fr 09:00-23:00; Sa 11:00-23:00; Su off",
            "09:00-23:00"
    };

    public void setUp() throws Exception {
        super.setUp();
        openHourParser = new OpenHourParser();
    }

    public void testGetOpenHoursMoTh() throws Exception {

        for (int i = 1; i < variantsOfRules.length; i++) {

            assertEquals("MONDAY", openHourParser.getOpenHours(variantsOfRules[i], MONDAY), "до 23:00");
            assertEquals("TUESDAY", openHourParser.getOpenHours(variantsOfRules[i], TUESDAY), "до 23:00");
            assertEquals("WEDNESDAY", openHourParser.getOpenHours(variantsOfRules[i], WEDNESDAY), "до 23:00");
            assertEquals("THURSDAY", openHourParser.getOpenHours(variantsOfRules[i], THURSDAY), "до 23:00");
        }

        assertEquals("MONDAY", openHourParser.getOpenHours(variantsOfRules[0], MONDAY), "Круглосуточно");
    }

    public void testGetOpenHoursOff() throws Exception {

        assertEquals("FRIDAY", openHourParser.getOpenHours(variantsOfRules[2], FRIDAY), "закрыто");

        assertEquals("SUNDAY", openHourParser.getOpenHours(variantsOfRules[5], SUNDAY), "закрыто");

    }
}