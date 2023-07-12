package com.elai.common.utils;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author:
 * @date: 2018/1/25
 * @time: 17:19
 * @description: http请求工具类
 * 注：内网请求统一用noProxy
 * 外网请求内网http内网调试onProxy https代理会失败
 * 生产环节外网请求务必调试测试
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientUtil {

    public static String PROXY_HOST = "PROXY_HOST";
    public static String PROXY_PORT = "PROXY_PORT";

    private static Log log = LogFactory.getLog(HttpClientUtil.class);

    /**
     * 构建client builder
     */
    private static HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

    /**
     * 默认RequestConfig
     */
    private static RequestConfig defaultRequestConfig = RequestConfig.custom().build();



    /**
     * 构建client builder
     *
     * @return
     */
    public static HttpClientBuilder getHttpClientBuilder() {
        return httpClientBuilder;
    }

    /**
     * 执行get请求
     *
     * @param url       请求url
     * @param header    header 设置
     * @param character 字符集

     * @return
     * @throws Exception
     */
    private static String get(String url, Map<String, String> header, String character) throws Exception {
        String result = null;
        if (url.startsWith("https")) {
//            result = getHttps(url, header, character);
        } else {
            if (StringUtil.isEmpty(character)) {
                character = "utf-8";
            }
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url);
            //header设置
            if (header != null) {
                for (String key : header.keySet()) {
                    get.addHeader(key, header.get(key));
                }
            }


            CloseableHttpResponse response = null;
            try {
                response = client.execute(get);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(response.getEntity(), character);
                } else {
                    String error = "请求错误： [url:" + url + "] [method: get]";
                    log.error(error);
                    throw new Exception(error);
                }
            } catch (IOException e) {
                log.error("请求错误： [url:" + url + "] [method: get]");
                throw e;
            } finally {
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            }
        }
        return result;
    }


    /**
     * 执行get请求
     *
     * @param url       请求url
     * @param header    header 设置
     * @param character 字符集
     * @param proxy     代理
     * @return
     */
    public static String getOnProxy(String url, Map<String, String> header, String character, Map<String, String> proxy) throws Exception {
        return get(url, header, character);
    }

    /**
     * 执行get请求 不执行代理验证 代理后内网接口无法走通
     *
     * @param url       请求url
     * @param header    header 设置
     * @param character 字符集
     * @return
     */
    public static String getNoProxy(String url, Map<String, String> header, String character) throws Exception {
        return get(url, header, character);
    }

    /**
     * 执行get请求
     *
     * @param url    请求url
     * @param header 头部
     * @param proxy  代理
     * @return
     */
    public static String getOnProxy(String url, Map<String, String> header, Map<String, String> proxy) throws Exception {
        return getOnProxy(url, header, "utf-8", proxy);
    }

    /**
     * 执行get请求  不执行代理验证 代理后内网接口无法走通
     *
     * @param url    请求url
     * @param header header
     * @return
     */
    public static String getNoProxy(String url, Map<String, String> header) throws Exception {
        return getNoProxy(url, header, "utf-8");
    }

    /**
     * 执行get请求
     *
     * @param url       请求url
     * @param character 字符集
     * @param proxy     代理
     * @return
     * @throws Exception
     */
    public static String getOnProxy(String url, String character, Map<String, String> proxy) throws Exception {
        return getOnProxy(url, null, character, proxy);
    }

    /**
     * 执行get请求 不执行代理验证 代理后内网接口无法走通
     *
     * @param url       请求url
     * @param character 字符集
     * @return
     * @throws Exception
     */
    public static String getNoProxy(String url, String character) throws Exception {
        return getNoProxy(url, null, character);
    }

    /**
     * 执行get请求
     *
     * @param url 请求地址
     * @return 返回响应内容
     * @throws Exception
     */
    public static String getOnProxy(String url, Map<String, String> proxy) throws Exception {
        return getOnProxy(url, null, "utf-8", proxy);
    }

    /**
     * 执行get请求 不执行代理验证 代理后内网接口无法走通
     *
     * @param url 请求地址
     * @return 返回响应内容
     * @throws Exception
     */
    public static String getNoProxy(String url) throws Exception {
        return getNoProxy(url, null, "utf-8");
    }

    /**
     * 执行post请求
     *
     * @param url            请求地址
     * @param header         请求头部
     * @param param          请求参数
     * @param requestContent post请求流中的参数
     * @param character      字符集
     * @param proxy          是否验证代理
     * @return
     * @throws Exception
     */
    private static String post(String url, Map<String, String> header, Map<String, String> param,
                               String requestContent, String character, Map<String, String> proxy, boolean isProxy) throws Exception {
        String result = null;
        if (url.startsWith("https")) {
//            result = postHttps(url, header, param,
//                    requestContent, character, proxy, isProxy);
        } else {
            if (StringUtil.isEmpty(character)) {
                character = "utf-8";
            }
            CloseableHttpClient client = getHttpClientBuilder().build();
            HttpPost post = new HttpPost(url);
            //header设置
            if (header != null) {
                for (String key : header.keySet()) {
                    post.addHeader(key, header.get(key));
                }
            }else{
                post.addHeader("signKey", "123");
            }

            //请求流中的内容
            if (requestContent != null) {
                StringEntity s = new StringEntity(requestContent, character);
                s.setContentEncoding(character);
                s.setContentType("application/json");
                post.setEntity(s);
            }

            //设置参数
            if (param != null) {
                List<NameValuePair> list = new ArrayList<>();
                for (String key : param.keySet()) {
                    list.add(new BasicNameValuePair(key, param.get(key)));
                }
                //url格式编码
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, character);
                post.setEntity(uefEntity);
            }
            CloseableHttpResponse response = null;
            try {
                response = client.execute(post);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(response.getEntity(), character);
                } else {
                    String error = "请求错误： [url:" + url + "] [method: post]";
                    log.info(error);
                    throw new Exception("---------------");
                }
            } catch (IOException e) {
                log.info("请求错误： [url:" + url + "] [method: post]");
                e.printStackTrace();
                throw e;
            } finally {
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            }
        }
        return result;
    }

    /**
     * https post请求
     *
     * @param url
     * @param header
     * @param param
     * @param requestContent
     * @param character
     * @param proxy
     * @param isProxy
     * @return
     * @throws Exception
     */
//    private static String postHttps(String url, Map<String, String> header, Map<String, String> param,
//                                    String requestContent, String character, Map<String, String> proxy, boolean isProxy) throws Exception {
//        String result = null;
//        if (StringUtil.isEmpty(character)) {
//            character = "utf-8";
//        }
//        HttpClient client = new SSLClient();
//        HttpPost post = new HttpPost(url);
//        //header设置
//        if (header != null) {
//            for (String key : header.keySet()) {
//                post.addHeader(key, header.get(key));
//            }
//        }
//
//        //请求流中的内容
//        if (requestContent != null) {
//            StringEntity s = new StringEntity(requestContent, character);
//            s.setContentEncoding(character);
//            s.setContentType("application/json");
//            post.setEntity(s);
//        }
//
//        //设置参数
//        if (param != null) {
//            List<NameValuePair> list = new ArrayList<>();
//            for (String key : param.keySet()) {
//                list.add(new BasicNameValuePair(key, param.get(key)));
//            }
//            //url格式编码
//            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, character);
//            post.setEntity(uefEntity);
//        }
//        HttpResponse response = null;
//        try {
//            response = client.execute(post);
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                result = EntityUtils.toString(response.getEntity(), character);
//            } else {
//                String error = "请求错误： [url:" + url + "] [method: post]";
//                log.info(error);
//                throw new Exception("---------------");
//            }
//        } catch (IOException e) {
//            log.info("请求错误： [url:" + url + "] [method: post]");
//            e.printStackTrace();
//            throw e;
//        }
//        return result;
//    }

    /**
     * 执行post请求
     *
     * @param url            请求地址
     * @param header         请求头部
     * @param param          请求参数
     * @param requestContent post请求流中的参数
     * @param character      请求编码
     * @param proxy          代理
     * @return
     * @throws Exception
     */
    public static String postOnProxy(String url, Map<String, String> header, Map<String, String> param,
                                     String requestContent, String character, Map<String, String> proxy) throws Exception {
        return post(url, header, param, requestContent, character, proxy, true);
    }

    /**
     * 执行post请求 不进行代理验证 代理后内网接口访问失败，内网专用
     *
     * @param url            请求地址
     * @param header         请求头部
     * @param param          请求参数
     * @param requestContent post请求流中的参数
     * @param character      字符集
     * @return
     * @throws Exception
     */
    public static String postNoProxy(String url, Map<String, String> header, Map<String, String> param,
                                     String requestContent, String character) throws Exception {
        return post(url, header, param, requestContent, character, null, false);
    }

    /**
     * post请求
     *
     * @param url       请求地址
     * @param header    header
     * @param param     参数
     * @param character 字符集
     * @param proxy     代理
     * @return
     * @throws Exception
     */
    public static String postOnProxy(String url, Map<String, String> header,
                                     Map<String, String> param, String character, Map<String, String> proxy) throws Exception {
        return postOnProxy(url, header, param, null, character, proxy);
    }

    /**
     * post请求 不进行代理验证 代理后内网接口访问失败，内网专用
     *
     * @param url       请求地址
     * @param header    header
     * @param param     参数
     * @param character 字符集
     * @return
     * @throws Exception
     */
    public static String postNoProxy(String url, Map<String, String> header,
                                     Map<String, String> param, String character) throws Exception {
        return postNoProxy(url, header, param, null, character);
    }

    /**
     * post请求
     *
     * @param url            请求地址
     * @param header         header
     * @param requestContent 请求数据流
     * @param character      字符集
     * @param proxy          代理
     * @return
     * @throws Exception
     */
    public static String postOnProxy(String url, Map<String, String> header,
                                     String requestContent, String character, Map<String, String> proxy) throws Exception {
        return postOnProxy(url, header, null, requestContent, character, proxy);
    }

    /**
     * post请求 不进行代理验证 代理后内网接口访问失败，内网专用
     *
     * @param url            请求地址
     * @param header         header
     * @param requestContent 请求数据流
     * @param character      字符集
     * @return
     * @throws Exception
     */
    public static String postNoProxy(String url, Map<String, String> header,
                                     String requestContent, String character) throws Exception {
        return postNoProxy(url, header, null, requestContent, character);
    }

    /**
     * post请求
     *
     * @param url       请求地址
     * @param param     参数
     * @param character 字符集
     * @param proxy     代理
     * @return
     * @throws Exception
     */
    public static String postOnProxy(String url, Map<String, String> param, String character, Map<String, String> proxy) throws Exception {
        return postOnProxy(url, null, param, character, proxy);
    }

    /**
     * post请求 不进行代理验证 代理后内网接口访问失败，内网专用
     *
     * @param url       请求地址
     * @param param     参数
     * @param character 字符集
     * @return
     * @throws Exception
     */
    public static String postNoProxy(String url, Map<String, String> param, String character) throws Exception {
        return postNoProxy(url, null, param, character);
    }

    /**
     * post请求
     *
     * @param url            请求地址
     * @param requestContent 参数流
     * @param character      字符集
     * @param proxy          代理
     * @return
     * @throws Exception
     */
    public static String postOnProxy(String url, String requestContent, String character, Map<String, String> proxy) throws Exception {
        return postOnProxy(url, null, requestContent, character, proxy);
    }

    /**
     * post请求 不进行代理验证 代理后内网接口访问失败，内网专用
     *
     * @param url            请求地址
     * @param requestContent 参数流
     * @param character      字符集
     * @return
     * @throws Exception
     */
    public static String postNoProxy(String url, String requestContent, String character) throws Exception {
        return postNoProxy(url, null, requestContent, character);
    }

    /**
     * post请求
     *
     * @param url   请求地址
     * @param param 请求参数
     * @param proxy 代理
     * @return
     * @throws Exception
     */
    public static String postOnProxy(String url, Map<String, String> param, Map<String, String> proxy) throws Exception {
        return postOnProxy(url, param, "utf-8", proxy);
    }

    /**
     * post请求 不进行代理验证 代理后内网接口访问失败，内网专用
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return
     * @throws Exception
     */
    public static String postNoProxy(String url, Map<String, String> param) throws Exception {
        return postNoProxy(url, param, "utf-8");
    }

    /**
     * post请求
     *
     * @param url            请求地址
     * @param requestContent 请求参数流
     * @param proxy          代理
     * @return
     * @throws Exception
     */
    public static String postOnProxy(String url, String requestContent, Map<String, String> proxy) throws Exception {
        return postOnProxy(url, requestContent, "utf-8", proxy);
    }

    /**
     * post请求 不进行代理验证 代理后内网接口访问失败，内网专用
     *
     * @param url            请求地址
     * @param requestContent 请求参数流
     * @return
     * @throws Exception
     */
    public static String postNoProxy(String url, String requestContent) throws Exception {
        return postNoProxy(url, requestContent, "utf-8");
    }

    /**
     * post请求
     *
     * @param url   请求地址
     * @param proxy 代理
     * @return
     * @throws Exception
     */
    public static String postOnProxy(String url, Map<String, String> proxy) throws Exception {
        return postOnProxy(url, (Map<String, String>) null, "utf-8", proxy);
    }

    /**
     * post请求 不进行代理验证 代理后内网接口访问失败，内网专用
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static String postNoProxy(String url) throws Exception {
        return postNoProxy(url, (Map<String, String>) null, "utf-8");
    }


    /**
     * 获取文件类型
     * 获取response header中Content-Type
     * * @param response
     * @return
     */
    public static String getContentType(HttpResponse response) {
        Header header = response.getFirstHeader("Content-Type");
        String fileType = null;
        if (header != null){
            fileType = header.getValue();
        }
        return fileType;
    }

    /**
     * rest请求
     * @param url
     * @param content
     * @param header
     * @return
     */
    public static String postContentOnProxy(String url,String content,Map<String,String> header) throws Exception {
        String result = post(url,header,null,content,"utf-8",null,true);
        return result;
    }

    /**
     * 读取request流
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String readRequest(HttpServletRequest request) throws IOException {
        Log log = LogFactory.getLog(HttpClientUtil.class);
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        log.info("request data : " + sb.toString());
        return sb.toString();
    }

    /**
     * 发送 http put 请求，参数以原生字符串进行提交
     * @param url
     * @param encode
     * @return
     */
    public static String putRaw(String url,String stringJson,Map<String,String> header, String encode) throws Exception{

        String result = null;
        if(StringUtil.isEmpty(encode)){
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);

        //设置header
        httpput.setHeader("Content-type", "application/json");
        //header设置
        if (header != null) {
            for (String key : header.keySet()) {
                httpput.addHeader(key, header.get(key));
            }
        }
        //组织请求参数
        if (stringJson != null) {
            StringEntity s = new StringEntity(stringJson, encode);
            s.setContentEncoding(encode);
            s.setContentType("application/json");
            httpput.setEntity(s);
        }
        CloseableHttpResponse  response = null;
        try {
            //响应信息
            response = client.execute(httpput);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), encode);
            } else {
                String error = "请求错误： [url:" + url + "] [method: post]";
                log.info(error);
                throw new Exception("---------------");
            }
        } catch (Exception e) {
            log.info("请求错误： [url:" + url + "] [method: post]");
            e.printStackTrace();
            throw e;
        }finally{
            if (client != null) {
                client.close();
            }
            if (response != null) {
                response.close();
            }
        }

        return result;
    }

    /**
     * 发送http delete请求
     */
    public static String delete(String url,Map<String,String> header,String encode) throws Exception{
        String result = null;
        if(StringUtil.isEmpty(encode)){
            encode = "utf-8";
        }
        String content = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpDelete httpdelete = new HttpDelete(url);
        //设置header
        httpdelete.setHeader("Content-type", "application/json");
        //header设置
        if (header != null) {
            for (String key : header.keySet()) {
                httpdelete.addHeader(key, header.get(key));
            }
        }
        CloseableHttpResponse response = null;
        try {
            //响应信息
            response = client.execute(httpdelete);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), encode);
            } else {
                String error = "请求错误： [url:" + url + "] [method: post]";
                log.info(error);
                throw new Exception("---------------");
            }

        } catch (Exception e) {
            log.info("请求错误： [url:" + url + "] [method: post]");
            e.printStackTrace();
            throw e;
        }finally{
            if (client != null) {
                client.close();
            }
            if (response != null) {
                response.close();
            }
        }

        return result;
    }
}
