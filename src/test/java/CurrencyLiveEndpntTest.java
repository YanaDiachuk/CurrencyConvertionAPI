import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class CurrencyLiveEndpntTest {

    Response response;

    @Test
    public void allCurrencyResponseTest() {
        response = given().get(Consts.URL + Consts.LIVE_ENDP + Consts.KEY + Consts.CURRENCY_PARAM + "EUR%2CGBP%2CJPY");
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EUR", "GBP", "JPY"})
    public void EachCurrencyResponseTest(String code) {
        response = given().get(Consts.URL + Consts.LIVE_ENDP + Consts.KEY + Consts.CURRENCY_PARAM + code);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
    }

    @Test
    public void invalidCurrencyCodeResponseTest() {
        response = given().get(Consts.URL + Consts.LIVE_ENDP + Consts.KEY + Consts.CURRENCY_PARAM + "EU");
        System.out.println(response.asString());
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", equalTo("You have provided one or more invalid Currency Codes. [Required format: currencies=EUR,USD,GBP,...]"));
    }

    @Test
    public void performanceTest() {
        response = given().get(Consts.URL + Consts.LIVE_ENDP + Consts.KEY + Consts.CURRENCY_PARAM + "EUR%2CGBP%2CJPY");
        response.then().time(lessThan(2000l));
    }


}
