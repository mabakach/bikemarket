package ch.mabaka.bikemarket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminSetupIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void adminSetupAvailable() throws Exception {
        String url = "http://localhost:" + port + "/admin-setup";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        assertThat(status).isBetween(200, 299);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            content.append(line).append('\n');
        }
        in.close();
        con.disconnect();
        assertThat(content.toString()).contains("Ersteinrichtung: Administrator");
    }
}