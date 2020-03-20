package com.tester.cases;

import com.alibaba.fastjson.JSONObject;
import com.tester.config.TestConfig;
import com.tester.model.AddUserCase;
import com.tester.model.User;
import com.tester.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by apple on 2020/3/13.
 */
public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口")
    public void addUser() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession ();
        AddUserCase addUserCase =session.selectOne ("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);
        //发请求
        String result = getResult(addUserCase);

        Thread.sleep(2000);
        //验证返回结果
        User user = session.selectOne ("addUser",addUserCase);
        System.out.println (user.toString () );
        Assert.assertEquals (addUserCase.getExpected (),result );

    }
    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost (TestConfig.addUserUrl);
        JSONObject param = new JSONObject ();
        param.put ("userName",addUserCase.getUserName ());
        param.put ("password",addUserCase.getPassword ());
        param.put ("sex",addUserCase.getSex ());
        param.put ("age",addUserCase.getAge ());
        param.put ("permission",addUserCase.getPermission ());
        param.put ("isDelete",addUserCase.getIsDelete ());
        //设置头信息
        post.setHeader ("content-type","application/json");
        StringEntity entity =new StringEntity (param.toString (),"utf-8");
        post.setEntity (entity);

        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore (TestConfig.store);
        String  result;//存放返回结果
        HttpResponse response = TestConfig.defaultHttpClient.execute (post);
        result = EntityUtils.toString (response.getEntity (),"utf-8");
        System.out.println (result );
        return result;

    }
}
