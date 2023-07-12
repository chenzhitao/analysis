package com.elai.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utils - Web
 */
public final class WebUtils {


    /**
     * PoolingHttpClientConnectionManager
     */
    private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER;

    /**
     * CloseableHttpClient
     */
    private static final CloseableHttpClient HTTP_CLIENT;

    //调用CK服务接口地址
    private static final String CK_URL ="http://183.3.221.246:8002/sn-api/";
//    private static final String CK_URL ="http://localhost:8002/sn-api/";

    static {
        HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build());
        HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
        HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
        HTTP_CLIENT = HttpClientBuilder.create().setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER).setDefaultRequestConfig(requestConfig).build();
    }

    //CK-GET返回结果
    //建议无参数用GET，有参数用POST
    public static JSONObject getCkObj(String apiName){
        String url=CK_URL+apiName;
        String retV = WebUtils.get(url,null);
        return  JSONObject.parseObject(retV);
    }

    //CK-POST返回结果
    //建议无参数用GET，有参数用POST
    public static JSONObject postCkObj(String apiName,Map<String, Object> paramMap){
        String url=CK_URL+apiName;
        String retV = null;
        try {
            retV = HttpClientUtil.postNoProxy(url,JSONObject.toJSONString(paramMap));
//            retV = WebUtils.sendPost(url, JSONObject.toJSONString(paramMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  JSONObject.parseObject(retV);
    }

    //CK-GET返回结果
    //建议无参数用GET，有参数用POST
    public static JSONObject getCkObj(String sysUrl,String apiName){
        String url=sysUrl+apiName;
        String retV = WebUtils.get(url,null);
        return  JSONObject.parseObject(retV);
    }

    //CK-POST返回结果
    //建议无参数用GET，有参数用POST
    public static JSONObject postCkObj(String sysUrl,String apiName,Map<String, Object> paramMap){
        String url=sysUrl+apiName;
        String retV = null;
        try {
//            if (sysUrl.contains("xxl-job-admin")){
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("code",200);
//                return jsonObject;
//            }
            retV = HttpClientUtil.postNoProxy(url,JSONObject.toJSONString(paramMap));
//            retV = WebUtils.sendPost(url, JSONObject.toJSONString(paramMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  JSONObject.parseObject(retV);
    }

    /**
     * GET请求
     *
     * @param url          URL
     * @param parameterMap 请求参数
     * @return 返回结果
     */
    public static String get(String url, Map<String, Object> parameterMap) {
        Assert.hasText(url, "[Assertion failed] - url must have text; it must not be null, empty, or blank");

        String result = null;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (parameterMap != null) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    String name = entry.getKey();
                    String value = ConvertUtils.convert(entry.getValue());
                    if (StringUtils.isNotEmpty(name)) {
                        nameValuePairs.add(new BasicNameValuePair(name, value));
                    }
                }
            }
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
            try (CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet)) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                    EntityUtils.consume(httpEntity);
                }
            }
//			} finally {
//				IOUtils.closeQuietly(httpResponse);
//			}
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }


    public static String sendPost(String urlString, String parm) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true); //设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
        connection.setDoInput(true); // 设置连接输入流为true
        connection.setRequestMethod("POST"); // 设置请求方式为post
        connection.setUseCaches(false); // post请求缓存设为false
        connection.setConnectTimeout(60000);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();

        // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
        DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
        dataout.writeBytes(parm);
        dataout.flush();
        dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
        // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
        BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder(); // 用来存储响应数据

        String retV = "";
        // 循环读取流,若不到结尾处
        while ((line = bf.readLine()) != null) {
            sb.append(line);
            retV+= line;
        }
        bf.close();    // 重要且易忽略步骤 (关闭流,切记!)
        connection.disconnect(); // 销毁连接
        System.out.println(sb.toString());
        return  sb.toString();

    }



}