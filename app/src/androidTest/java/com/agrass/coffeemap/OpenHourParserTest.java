package com.agrass.coffeemap;

import junit.framework.TestCase;

import java.util.Calendar;

/**
 * Created by Agrass- on 02.12.15.
 */
public class OpenHourParserTest extends TestCase {

    OpenHourParser openHourParser;
    String[] variantsOfRules = {"24/7", "Mo-Su 09:00-23:00", "Mo-Fr 08:00-23:00; Sa,Su 10:00-23:00",
            "Mo-Th 09:00-23:00; Fr 09:00-23:00; Sa 11:00-23:00; Su 11:00-24:00",
            "Mo-Su 08:00-23:00; Fr off; Sa off", "Mo-Fr 08:00-23:00; Sa-Su 09:00-23:00"};


    public void setUp() throws Exception {
        super.setUp();
    }

    public void testGetOpenHours() throws Exception {

    }
}