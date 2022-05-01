import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;

import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;


public class MessageSenderTests {
    @ParameterizedTest
    @ValueSource(strings = { "172.123.123.123", "96.123.123.123", "" })
    void test_check_ip_to_lang_mockito (String ip) {
        String expectedMessage = "Welcome";
        Country expectedCountry = Country.USA;

        if (ip.startsWith("172.")) {
            expectedMessage = "Добро пожаловать";
            expectedCountry = Country.RUSSIA;
        }
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        String res = messageSender.send(Map.of(IP_ADDRESS_HEADER, ip));

        if (!ip.isEmpty()) {
            Mockito.verify(geoService, Mockito.times(1)).byIp(ip);
        }
        Mockito.verify(localizationService, Mockito.times(!ip.isEmpty() ? 2 : 1)).locale(expectedCountry);
        Assertions.assertEquals(expectedMessage, res);
    }
}
