import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class CurrencyHistoricalEndpntTest {

    Response response;

    @Test
    public void allCurrencyResponseTest() {
        response = given().get("https://api.apilayer.com/currency_data/historical?date=2018-01-01" + "&apikey=QGqeB8MjjQhQIt90y5yPOSN599jJegWI");
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("date", equalTo("2018-01-01"));
    }

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("&currencies=EUR", "2003-09-09", "EUR"),
                Arguments.of("&currencies=GBP", "2003-04-04", "GBP"),
                Arguments.of("&currencies=JPY", "2020-09-17", "JPY")
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void eachCurrencyDifferentDatesTest(String currency, String date, String check) {
        response = given().get("https://api.apilayer.com/currency_data/historical?date=" + date + "&apikey=QGqeB8MjjQhQIt90y5yPOSN599jJegWI" + currency);
        System.out.println(response.asString());
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo(date));
        response.then().body(containsString(check));

    }
}
