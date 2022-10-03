import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class SecurityTest {

    Response response;

    @Test
    public void liveEndpValidAPIAccessTest() {
        response = given().get(Consts.URL + Consts.LIVE_ENDP + Consts.KEY);
        //System.out.println(response.asString());
        response.then().statusCode(200);
    }

    @Test
    public void liveEndpInvalidAPIAccessTest() {
        response = given().get(Consts.URL + Consts.LIVE_ENDP + "noKey");
        //System.out.println(response.asString());
        response.then().statusCode(401);
    }

    @Test
    public void historicalEndpValidAPIAccessTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDP + Consts.KEY);
        System.out.println(response.asString());
        response.then().statusCode(200);
    }

    @Test
    public void historicalEndpInvalidAPIAccessTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDP + "noKey");
        System.out.println(response.asString());
        response.then().body("message", equalTo("No API key found in request"));
    }


}
