package cn.ubiot.utils;

import cn.ubiot.result.ResultObject;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    private static Logger logger = Logger.getLogger(HttpClientUtils.class);
    /**
     * 执行Http请求
     * @param httpUriRequest
     * @return ResultObject
     */
    public static ResultObject executeHttpUriRequest(HttpUriRequest httpUriRequest){
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 执行http请求
            response = httpClient.execute(httpUriRequest);
            String resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            // 判断返回状态是否为200
            ResultObject ro = JSONObject.parseObject(resultString,ResultObject.class);
            if (response.getStatusLine().getStatusCode() == 200) {
                logger.info(resultString);
            }else{
                logger.error(resultString);
            }
            return ro ;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统错误！",e);
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("系统错误！",e);
            }
        }
        return new ResultObject(false,"系统错误！",null) ;
    }

    /**
     * 带参数的get请求
     * @param url 请求路径
     * @param header 头信息
     * @param param 参数
     * @return String
     */
    public static ResultObject doGet(String url, Map<String,String> header,Map<String, String> param) {
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);

            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 添加头信息
            if (header != null) {
                for (String key : header.keySet()) {
                    httpGet.addHeader(key,header.get(key));
                }
            }
            // 执行请求
            return executeHttpUriRequest(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统错误！",e);
        }
        return new ResultObject(false,"系统错误！",null);
    }

    /**
     * 不带参数的get请求
     * @param url 请求路径
     * @return String
     */
    public static ResultObject doGet(String url) {
        return doGet(url, null,null);
    }

    /**
     * 带参数的post请求
     * @param url 请求路径
     * @param header 头信息
     * @param param 参数
     * @return String
     */
    public static ResultObject doPost(String url, Map<String,String> header,Map<String, String> param) {
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 添加头信息
            if (header != null) {
                for (String key : header.keySet()) {
                    httpPost.addHeader(key,header.get(key));
                }
            }

            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            return executeHttpUriRequest(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统错误！",e);
        }
        return new ResultObject(false,"系统错误！",null) ;
    }

    /**
     * 不带参数的post请求
     * @param url 请求路径
     * @return ResultObject
     */
    public static ResultObject doPost(String url) {
        return doPost(url, null,null);
    }

    /**
     * 传送json类型的post请求
     * @param url 请求路径
     * @param json
     * @return ResultObject
     */
    public static ResultObject doPostJson(String url,Map<String,String>header, String json) {
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        // 添加头信息
        if (header != null) {
            for (String key : header.keySet()) {
                httpPost.addHeader(key,header.get(key));
            }
        }
        // 创建请求内容
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        // 执行http请求
        return executeHttpUriRequest(httpPost);
    }


}
