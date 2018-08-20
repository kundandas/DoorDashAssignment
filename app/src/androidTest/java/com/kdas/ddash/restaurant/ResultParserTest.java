package com.kdas.ddash.restaurant;

import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

@RunWith(AndroidJUnit4.class)
public class ResultParserTest {

    ResultParser resultParser;
    static final String MOCK_ID = "mock_id";
    static final String MOCK_NAME = "mock_name";
    static final String MOCK_DESCRIPTION = "mock_description";
    static final String MOCK_STATUS = "mock_status";
    static final String MOCK_COVER_IMAGE_URL = "mock_coverImageUrl";

    @Before
    public void setup() throws Exception {
        resultParser = new ResultParser();
    }

    @Test
    public void testSuccessfulParsing() throws Exception {
        List<Restaurant> result = resultParser.parseResult(getMockSuccessData());
        Assert.assertTrue(result.size() == 1);
        Assert.assertTrue(result.get(0).getId().equals(MOCK_ID));
    }

    @Test
    public void testNullReturnedForEmptyResponse() throws Exception {
        List<Restaurant> result = resultParser.parseResult("");
        Assert.assertTrue(result == null);
    }

    @Test
    public void testNullReturnedForInvalidJSONResponse() throws Exception {
        List<Restaurant> result = resultParser.parseResult("This is not a json format data");
        Assert.assertTrue(result == null);
    }

    private String getMockSuccessData() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", MOCK_ID);
        jsonObject.put("name", MOCK_NAME);
        jsonObject.put("description", MOCK_DESCRIPTION);
        jsonObject.put("status", MOCK_STATUS);
        jsonObject.put("cover_img_url", MOCK_COVER_IMAGE_URL);
        JSONArray array = new JSONArray();
        array.put(jsonObject);
        return array.toString();
    }

}
