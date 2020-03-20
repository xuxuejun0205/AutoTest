package com.tester.cases;

import com.alibaba.fastjson.JSONObject;
import com.tester.config.TestConfig;
import com.tester.model.InterfaceName;
import com.tester.model.LoginCase;
import com.tester.utils.ConfigFile;
import com.tester.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by apple on 2020/3/13.
 */
public class LoginTest {
    @BeforeTest(groups = "loginTrue",description = "测试准备工作,获取HttpClient对象")
    public void beforeTest(){
        TestConfig.getUserInfoUrl = ConfigFile.getUrl (InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl (InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl (InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl (InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl (InterfaceName.UPDATEUSERINFO);
        TestConfig.defaultHttpClient =new DefaultHttpClient ();
    }
    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession ();
        LoginCase loginCase =session.selectOne ("loginCase",1);
        System.out.println ( loginCase.toString ());
        System.out.println (TestConfig.loginUrl );
        //第一步发送请求
        String result = getResult(loginCase);
        //验证结果
        Assert.assertEquals (loginCase.getExpected (),result );

    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost httpPost = new HttpPost (TestConfig.loginUrl);
        JSONObject param = new JSONObject ();
        param.put ("userName",loginCase.getUserName ());
        param.put ("password",loginCase.getPassword ());

        httpPost.setHeader ("content-type","utf-8");

        StringEntity entity = new StringEntity (param.toString (),"utf-8");
        httpPost.setEntity (entity);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute (httpPost);
        result= EntityUtils.toString (response.getEntity (),"utf-8");

        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore ();

        return result;

    }
}
