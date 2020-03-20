package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.UpdateUserInfoCase;
import com.tester.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by apple on 2020/3/13.
 */
public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession ( );
        UpdateUserInfoCase updateUserInfoCase = session.selectOne ("updateUserInfoCase", 1);
        System.out.println (updateUserInfoCase.toString ( ));
        System.out.println (TestConfig.updateUserInfoUrl);
    }
}
