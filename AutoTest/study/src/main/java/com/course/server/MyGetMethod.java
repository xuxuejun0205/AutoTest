package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Created by apple on 2020/3/6.
 */
@RestController
@Api(value = "/",description = "这是我全部的get方法")
public class MyGetMethod {

    @RequestMapping(value="/getCookie",method = RequestMethod.GET)
    @ApiOperation (value = "通过这个方法可以获取到Cookies",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        //HttpServletRequest 装请求信息的类
        //HttpServletResponse 装响应信息的类
        Cookie cookie = new Cookie ("login","true");
        response.addCookie (cookie);
        return "恭喜你获得cookies信息成功！";
    }

    /**
     * 要求客户端携带cookies访问
     */
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation (value = "要求客户端携带cookies访问",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies ();
        if (Objects.isNull (cookies)){
            return "你必须携带cookies信息来";
        }
        for(Cookie cookie:cookies){
            if (cookie.getName ().equals ("login")&&
                    cookie.getValue ().equals ("true")){
                return "这是一个需要携带cookies信息才能访问的get请求";
            }

        }

        return "你必须携带cookies信息来！";

    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式url：key=value&key=value
     * 我们老模拟获取商品列表
     */
    @RequestMapping (value = "get/with/param/{}",method = RequestMethod.GET)
    @ApiOperation (value = "需要携带参数才能访问的get请求方法",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,@RequestParam Integer end){
        Map<String,Integer> mylist = new HashMap<> ();
        mylist.put ("鞋",400);
        mylist.put ("干脆面",1);
        mylist.put ("衬衫",300);
        return mylist;

    }


}
