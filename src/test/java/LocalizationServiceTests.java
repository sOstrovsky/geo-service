import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiceTests {
    static LocalizationService sut;

    @BeforeAll
    public static void started() {
        System.out.println("LocalizationService tests started");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("LocalizationService tests completed");
    }

    @BeforeEach
    public void initSuite() {
        System.out.println("LS test started");
        sut = new LocalizationServiceImpl();
    }

    @AfterEach
    public void finished() {
        System.out.println("LS test completed");
    }

    @ParameterizedTest
    @EnumSource(value = Country.class, names = { "RUSSIA", "USA" })
    public void testGetMessageByLocale(Country cnt) {
        // arrange
        Country expectedCountry = Country.USA;
        String expectedMessage = "Welcome";
        if (cnt == Country.RUSSIA) {
            expectedCountry = Country.RUSSIA;
            expectedMessage = "Добро пожаловать";
        }

        // act
        String result = sut.locale(expectedCountry);

        // assert
        Assertions.assertEquals(expectedMessage, result);
    }
}
