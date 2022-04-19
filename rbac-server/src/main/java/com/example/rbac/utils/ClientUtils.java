package com.example.rbac.utils;

import com.alibaba.fastjson.JSON;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import eu.bitwalker.useragentutils.UserAgent;
import jdk.nashorn.internal.parser.JSONParser;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * @Author suj
 * @create 2022/3/14
 */
public class ClientUtils {
    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取浏览器
     * @param request
     * @return
     */
    public static String getBrowserType(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String browserType = userAgent.getBrowser().toString() + " " + userAgent.getBrowserVersion().getVersion();
        return browserType;
    }

    /**
     * 获取操作系统
     * @param request
     * @return
     */
    public static String getOs(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getOperatingSystem().toString();
    }

    /**
     * 调用淘宝的接口获取ip地址对应的城市
     * @param request
     * @return
     */
    public static String getAddressByApi(HttpServletRequest request){
        String ip = getIpAddress(request);
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder("");
        try {
            URL url = new URL("https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc");
            URLConnection connection = url.openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str = "";
            while ((str = reader.readLine()) != null) {
                result.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != reader) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        StringBuilder address = new StringBuilder("");
        Map map = (Map) JSON.parse(String.valueOf(result));
        Map data = (Map) map.get("data");
        address.append(data.get("country")+" ").append(data.get("region")+" ").append(data.get("city"));
        return String.valueOf(address);
    }

    /**
     * 根据ip地址获取对应的城市
     * @param request
     * @return
     */
    public static String getAddress(HttpServletRequest request) {
        String data = getAddress1(getIpAddress(request));
        Map<String, String> map = (Map<String, String>)JSON.parse(data);
        String address = "";
        System.out.println(data);
        if(null != map) {
            if (null != map.get("country")) {
                address += map.get("country");
            }
            if (null != map.get("region")) {
                address += map.get("region");
            }
            if (null != map.get("city")) {
                address += map.get("city");
            }
            if("".equals(address)) {
                address = "未知地点";
            }
        }
        return address;
    }

    public static String getAddress1(String ip) {
        if("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "localhost";
        }

        String path = "https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc";
        //请求的url
        URL url = null;
        //请求的输入流
        BufferedReader in = null;
        //输入流的缓冲
        StringBuffer sb = new StringBuffer();
        try{
            url = new URL(path);
            in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8") );
            String str = null;
            //一行一行进行读入
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ex) {

        } finally{
            try{
                if(in!=null) {
                    in.close(); //关闭流
                }
            }catch(IOException ex) {

            }
        }
        String result =sb.toString();
        return result;
    }
}
