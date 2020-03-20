package com.tester.cases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tester.config.TestConfig;
import com.tester.model.GetUserListCase;
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
import java.util.List;

/**
 * Created by apple on 2020/3/13.
 */
public class GetUserInfoListTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取用户信息列表")
    public void getUserInfoList() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession ();
        GetUserListCase  getUserListCase = session.selectOne ("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        //发送请求获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);
        //验证
        Thread.sleep(2000);
        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);
        for(User u : userList){
            System.out.println("list获取的user:"+u.toString());
        }
        /*JSONArray userListJson = new JSONArray(userList);
        Assert.assertEquals(userListJson.length(),resultJson.length());*/

    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost httpPost = new HttpPost (TestConfig.getUserListUrl);
        JSONObject param = new JSONObject ();
        param.put ("userName",getUserListCase.getUserName ());
        param.put ("age",getUserListCase.getAge ());
        param.put ("sex",getUserListCase.getSex ());


        httpPost.setHeader ("content-type","utf-8");

        StringEntity entity = new StringEntity(param.toString (),"utf-8");
        httpPost.setEntity (entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        HttpResponse response = TestConfig.defaultHttpClient.execute (httpPost);

        String result;
        result =  EntityUtils.toString(response.getEntity(),"utf-8");

        JSONArray jsonArray = JSONArray.parseArray (result);

        System.out.println("调用接口list result:"+result);

        return jsonArray;

    }
}
