import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.annotations.Test;
import ru.netology.entity.Country;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeoServiceTests {
    static GeoService sut;

    @BeforeAll
    public static void started() {
        System.out.println("GeoService tests started");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("GeoService tests completed");
    }

    @BeforeEach
    public void initSuite() {
        System.out.println("GS test started");
        sut = new GeoServiceImpl();
    }

    @AfterEach
    public void finished() {
        System.out.println("GS test completed");
    }

    @Test
    public static void testGetLocaleByCoordinatesThrowsRuntimeException() {
        // arrange
        double latitude = 12.34;
        double longitude = 21.01;

        // assert
        assertThrows(RuntimeException.class, () -> sut.byCoordinates(latitude, longitude));
    }

    @ParameterizedTest
    @ValueSource(strings = { "172.123.123.123", "96.123.123.123" })
    public void testGetLocaleByIp(String ip) {
        // arrange
        Country expectedCountry = Country.USA;
        if (ip.startsWith("172.")) {
            expectedCountry = Country.RUSSIA;
        }

        // act
        Country result = sut.byIp(ip).getCountry();

        // assert
        Assertions.assertEquals(expectedCountry, result);
    }
}
