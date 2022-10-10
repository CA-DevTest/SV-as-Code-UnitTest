package com.ca.devtest.sv.devtools.v3;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class HttpUtils {
    public static final String URL_FORMAT = "%s://%s:%s/%s";
    public static final String VS_DETAILS_URL = "%s://%s:%s/lisa-virtualize-invoke/api/v3/vses/%s/services/%s";
    public static final String VS_SPECIFICS_URL = "%s://%s:%s/lisa-virtualize-invoke/api/v3/vses/%s/services/%s/specifics";
    public static ResponseParser GET(String protocol, String url, String server, String port, String operation){
        //HttpClient client = HttpClients.createDefault();
        CloseableHttpClient client = HttpClients.
                custom()
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        HttpGet get = new HttpGet(String.format(protocol, url, server, port, operation));
        get.addHeader("Accept", "application/json");
        Properties props = System.getProperties();
        props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

        HttpResponse response = null;
        try {
            response = client.execute(get);
            return getParser(response);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ResponseParser POST(String protocol, String url, String server, String port, String operation){
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(String.format(protocol, url, server, port, operation));
        post.addHeader("User-Agent", "SV-as-Code-Parser");
        post.addHeader("Accept","application/json");
        post.addHeader("Content-Type","application/json");
        HttpResponse response = null;
        try {
            response = client.execute(post);
            return getParser(response);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ResponseParser GET_VS_SPECIFICS(String protocol, String server, String port, String vse, String service){
        return GET_VS_INFO(VS_SPECIFICS_URL,protocol, server, port, vse, service);
    }
    public static ResponseParser GET_VS_DETAILS(String protocol, String server, String port, String vse, String service) {
        return GET_VS_INFO(VS_DETAILS_URL,protocol, server, port, vse, service);
    }

    private static ResponseParser GET_VS_INFO(String url, String protocol, String server, String port, String vse, String service){
        int timeout = 300;
        RequestConfig config = RequestConfig.custom().
                setConnectTimeout(timeout * 1000).
                setConnectionRequestTimeout(timeout * 1000).
                setSocketTimeout(timeout * 1000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(config).build();
        try {
            if(protocol.equals("https")) {
                SSLContextBuilder SSLBuilder = SSLContexts.custom();
                File file = new File("/Applications/CA/DevTest/webserver.ks");
                SSLBuilder = SSLBuilder.loadTrustMaterial(file, "changeit".toCharArray());
                SSLContext sslContext = SSLBuilder.build();
                SSLConnectionSocketFactory sslConSocFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
                HttpClientBuilder clientbuilder = HttpClients.custom();
                clientbuilder = clientbuilder.setSSLSocketFactory(sslConSocFactory);

                httpClient =  clientbuilder.setDefaultRequestConfig(config).build();
            }

            HttpGet get = new HttpGet(String.format(url, protocol, server, port, vse, service));
            get.setConfig(config);
            get.setHeader("Authorization", String.format("Basic %s",
                    new String(Base64.encodeBase64(("admin" + ":" + "admin").getBytes()))));
            get.addHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(get);
            return getParser(response);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ResponseParser getParser(HttpResponse response){
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            return null;
        }
        String contentType = response.getEntity().getContentType().getValue();
        HttpEntity entity = response.getEntity();
        if(contentType.contains(ContentType.APPLICATION_JSON.getMimeType())){
            return new JsonResponseParser(entity);
        }else if(contentType.contains(ContentType.APPLICATION_XML.getMimeType())){
            return new XMLResponseParser(entity);
        }else{
            throw new RuntimeException("Content-Type is not supported "+response.getEntity().getContentType());
        }
    }
}
