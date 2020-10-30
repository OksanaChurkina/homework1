package ru.digitalhabbits.homework1.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);

        HttpGet request = new HttpGet(uri);

        try(CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                        .build();
            CloseableHttpResponse response = httpClient.execute(request)){
            HttpEntity httpEntity = response.getEntity();

                Gson gson = new Gson();
                JsonObject json = gson.fromJson(EntityUtils.toString(httpEntity), JsonObject.class)
                        .getAsJsonObject("query")
                        .getAsJsonObject("pages");

                List<String> keySet = new ArrayList<>(json.keySet());
                String[] keys = new String[keySet.size()];
                keySet.toArray(keys);

                return json.getAsJsonObject(keys[0])
                        .getAsJsonPrimitive("extract")
                        .getAsString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
