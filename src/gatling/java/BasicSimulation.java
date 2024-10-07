import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class BasicSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .inferHtmlResources()
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .contentTypeHeader("application/x-www-form-urlencoded")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0");

    ScenarioBuilder scn = scenario("LoginScenario")
            .exec(
                    http("Get Login Page")
                            .get("/login")
                            .check(css("input[name=_csrf]", "value").saveAs("csrfToken"))
            )
            .pause(1)
            .exec(
                    http("Login Request")
                            .post("/login")
                            .formParam("username", "Sublimee")
                            .formParam("password", "123456")
                            .formParam("_csrf", "#{csrfToken}")
                            .check(status().is(200))
            )
            .pause(1)
            .exec(http("request_cities")
                    .get("/cities")
                    .check(status().is(200))
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(100))
        ).protocols(httpProtocol);
    }
}