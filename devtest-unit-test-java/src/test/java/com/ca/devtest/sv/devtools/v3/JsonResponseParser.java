package com.ca.devtest.sv.devtools.v3;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

public class JsonResponseParser implements ResponseParser {
    ReadContext context;

    public JsonResponseParser(HttpEntity httpEntity){
        parse(httpEntity);
    }

    @Override
    public void parse(HttpEntity entity) {
        try {
            String responseString = EntityUtils.toString(entity, "UTF-8");
            context = JsonPath.parse(responseString);
        }catch (Exception e){

        }
    }

    @Override
    public String getValue(String path) {
        if(context==null){
            return null;
        }
        try {
             return context.read(path).toString();
        }catch (Exception e){
            return context.json();
        }
    }
}
