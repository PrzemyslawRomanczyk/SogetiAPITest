import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.runners.Parameterized;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.Console;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Test1 {

    public static final String SOGETI_ENDPOINT = "http://api.zippopotam.us/de/bw/stuttgart";
    public static final String ZIPOPOTAM =  "http://api.zippopotam.us/";

    CloseableHttpClient client;
    CloseableHttpResponse response;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a").withLocale(Locale.GERMANY);

    @BeforeMethod
    public void setup() {
        client  = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResources() throws IOException {
        client.close();
        response.close();
    }
    @Test
    public void firstTest() throws IOException, InterruptedException {
        HttpGet get = new HttpGet(SOGETI_ENDPOINT);
        long start = System.currentTimeMillis();
        response = client.execute(get);
        long end = System.currentTimeMillis();
        //Check response time
        if ((end - start) < 1000) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }

        //Get response type
        int actualStatus = response.getStatusLine().getStatusCode();
        Assert.assertEquals(actualStatus, 200);
        //Get content type
        Header contentType = response.getEntity().getContentType();
        Assert.assertEquals(contentType.getValue(), "application/json");
        //InputStream content = response.getEntity().getContent();
        //LocalDate endDate = LocalDate.parse(getHeader(response, "Date"), formatter);

        JsonClass classToCheck = unmarshall(response, JsonClass.class);
        Assert.assertEquals(classToCheck.getCountry(), "Germany");
        Assert.assertEquals(classToCheck.getState(), "Baden-Württemberg");

    }
    @DataProvider
    private Object[][] endpoints() {
        return new Object[][]{
                {"/us/90210", "Beverly Hills"},
                {"/us/12345", "Schenectady"},
                {"/ca/B2R", "Waverley"}
        };
    }
    @Test(dataProvider = "endpoints")
    public void secondTest(String endpoint, String city) throws IOException, InterruptedException {
        HttpGet get = new HttpGet(ZIPOPOTAM + endpoint);
        long start = System.currentTimeMillis();
        response = client.execute(get);
        long end = System.currentTimeMillis();
        //Check response time
        if ((end - start) < 1000) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }

        //Get response type
        int actualStatus = response.getStatusLine().getStatusCode();
        Assert.assertEquals(actualStatus, 200);
        //Get content type
        Header contentType = response.getEntity().getContentType();
        Assert.assertEquals(contentType.getValue(), "application/json");
        //InputStream content = response.getEntity().getContent();
        //LocalDate endDate = LocalDate.parse(getHeader(response, "Date"), formatter);

        JsonClass classToCheck = unmarshall(response, JsonClass.class);
        System.out.print(classToCheck.getPlaceName());
        //Assert.assertEquals(classToCheck.getCountry(), "Germany");
        //Assert.assertEquals(classToCheck.getState(), "Baden-Württemberg");

    }

    private JsonClass unmarshall(CloseableHttpResponse response, Class<JsonClass> jsonClassClass) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity());

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(jsonBody, jsonClassClass);

    }

    private String getHeader(CloseableHttpResponse response, String headerName) {
        // Get All headers

        Header[] headers = response.getAllHeaders();
        List<Header> httpHeaders = Arrays.asList(headers);
        String returnHeader = "";

        for(Header header : httpHeaders) {
            if(headerName.equalsIgnoreCase(header.getName())) {
                returnHeader = header.getValue();
            }
        }

        if(returnHeader.isEmpty()) {
            throw new RuntimeException(("Didn't find a matching header"));
        }

        return returnHeader;
    }


}
