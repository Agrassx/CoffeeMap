import org.junit.Test;
import com.agrass.coffeemap.OpenHourParser;
import static org.junit.Assert.assertEquals;

public class OpenHoursTests {

    final static int MONDAY = 2;
    final static int TUESDAY = 3;
    final static int WEDNESDAY = 4;
    final static int THURSDAY = 5;
    final static int FRIDAY = 6;
    final static int SATURDAY = 7;
    final static int SUNDAY = 1;

    OpenHourParser parser;
    String[] variantsOfRules = {
            "24/7",
            "Mo-Fr 09:00-23:00",
            "Mo-Su 08:00-23:00; Fr off; Sa off",
            "Mo-Fr 08:00-23:00; Sa,Su 10:00-22:00",
            "Mo-Fr 08:00-23:00; Sa-Su 09:00-22:00",
            "Mo-Th 09:00-23:00; Fr 09:00-23:00; Sa 11:00-23:00; Su off",
            "09:00-23:00"
    };

    @Test
    public void WhenOpenAroundTheClock_AndTodayIsMonday() {
        parser = new OpenHourParser();
        assertEquals(parser.getOpenHours("24/7", MONDAY), "Круглосуточно");
    }

    @Test
    public void testGetOpenHoursMoTh() throws Exception {
        parser = new OpenHourParser();

        for (int i = 1; i < variantsOfRules.length; i++) {

            assertEquals("MONDAY", parser.getOpenHours(variantsOfRules[i], MONDAY), "до 23:00");
            assertEquals("TUESDAY", parser.getOpenHours(variantsOfRules[i], TUESDAY), "до 23:00");
            assertEquals("WEDNESDAY", parser.getOpenHours(variantsOfRules[i], WEDNESDAY), "до 23:00");
            assertEquals("THURSDAY", parser.getOpenHours(variantsOfRules[i], THURSDAY), "до 23:00");
        }

        assertEquals("MONDAY", parser.getOpenHours(variantsOfRules[0], MONDAY), "Круглосуточно");
    }

    @Test
    public void testGetOpenHoursOff() throws Exception {
        parser = new OpenHourParser();

        assertEquals("FRIDAY", parser.getOpenHours(variantsOfRules[2], FRIDAY), "закрыто");
        assertEquals("SUNDAY", parser.getOpenHours(variantsOfRules[5], SUNDAY), "закрыто");

    }
}
