package org.csu.jpetstore.service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import java.util.*;
import com.aliyuncs.ecs.model.v20140526.*;

public class ServerService {
    private static final String ACCESS_KEY = "LTAI4G6hnrrdFFfPBMSu1S4p";
    private static final String ACCESS_SECRET = "TTYGicjfUY2OZssFXwLPBeGVjmXsZe";
    private static final String REGION_ID = "cn-beijing";
    private static IAcsClient client = null;

    static {
        //创建profile
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY, ACCESS_SECRET);
        //创建client
        client = new DefaultAcsClient(profile);
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

    public static void main(String[] args) {
        System.out.println(describeInstanceStatus(FormatType.JSON));
    }
}
