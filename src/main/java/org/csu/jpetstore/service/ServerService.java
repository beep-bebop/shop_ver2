package org.csu.jpetstore.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeUserDataRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeUserDataResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.aliyuncs.ecs.model.v20140526.*;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Service
public class ServerService {
    // 待查询的实例 ID
    private static String instanceId = "i-2ze2etqbqencg0qw12x8";
    private static final String ACCESS_KEY = "LTAI4G6hnrrdFFfPBMSu1S4p";
    private static final String ACCESS_SECRET = "TTYGicjfUY2OZssFXwLPBeGVjmXsZe";
    private static final String REGION_ID = "cn-beijing";
    private static IAcsClient client = null;
    private static String startTime = "2020-06-18T02:10:00Z";
    // 获取数据的结束时间点。按照 ISO8601 标准表示，并需要使用 UTC 时间，格式为：YYYY-MM-DDThh:mm:ssZ。 如果指定的秒（ss）不是 00，则自动换算为下一分钟。
    private static String endTime = "2020-06-18T02:20:00Z";
    private TimeZone tz = TimeZone.getTimeZone("UTC");
    private static DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static {
        //创建profile
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY, ACCESS_SECRET);
        //创建client
        client = new DefaultAcsClient(profile);
    }
    /**
     * DescribeInstanceMonitorData    查询一台 ECS 实例所有相关的监控信息
     */
    private static List<DescribeInstanceMonitorDataResponse.InstanceMonitorData> DescribeInstanceMonitorData(IAcsClient client) {
        DescribeInstanceMonitorDataRequest request = new DescribeInstanceMonitorDataRequest();
        // 待查询的实例 ID
        request.setInstanceId(instanceId);
        // 获取数据的起始时间点。按照 ISO8601 标准表示，并需要使用 UTC 时间，格式为：YYYY-MM-DDThh:mm:ssZ。如果指定的秒（ss）不是 00，则自动换算为下一分钟。
        request.setStartTime(startTime);
        // 获取数据的结束时间点。按照 ISO8601 标准表示，并需要使用 UTC 时间，格式为：YYYY-MM-DDThh:mm:ssZ。 如果指定的秒（ss）不是 00，则自动换算为下一分钟。
        request.setEndTime(endTime);
        try {
            DescribeInstanceMonitorDataResponse response = client.getAcsResponse(request);
            return response.getMonitorData();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            return null;
        }
    }

    public static String describeInstanceStatus(FormatType formatType) {
        // 构造请求
        DescribeInstanceStatusRequest request = new DescribeInstanceStatusRequest();
        request.setAcceptFormat(formatType);
        try {
            // 获取返回对象
            return JSON.toJSONString(client.getAcsResponse(request));
        } catch (ServerException e) {
            System.out.println("服务器异常：" + e);
            e.printStackTrace();
            return null;
        } catch (ClientException e) {
            System.out.println("参数配置错误：" + e);
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject status() {
        startTime = OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(2).format(fm).toString();
        endTime = OffsetDateTime.now(ZoneOffset.UTC).format(fm).toString();
        JSONObject temp = JSON.parseObject(describeInstanceStatus(FormatType.JSON));
        JSONObject data = new JSONObject();
        data.put("status",temp.getJSONArray("instanceStatuses").getJSONObject(0).get("status"));
        List<DescribeInstanceMonitorDataResponse.InstanceMonitorData> monitorDataList = DescribeInstanceMonitorData(client);
        // 处理业务返回的监控信息数据。（请根据您的实际业务需求来进行处理）
        temp = (JSONObject)JSONObject.toJSON(monitorDataList.get(0));
        System.out.println(temp);
        data.put("id",temp.get("instanceId"));
        data.put("cpu",temp.get("cPU"));
        if (temp.get("InternetRX") != null)
            data.put("receive",temp.get("InternetRX"));
        else
            data.put("receive",0);
        if (temp.get("InternetTX") != null)
            data.put("send",temp.get("InternetTX"));
        else
            data.put("send",0);
        return data;
    }
}
