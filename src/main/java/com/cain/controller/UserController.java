package com.cain.controller;

import com.cain.entity.User;
import com.cain.service.UserService;
import com.cain.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cain
 * @version v1.0
 * @Title: UserController
 * @Description: TODO
 * @date 2021/1/23 13:21
 */
@RestController
@CrossOrigin
@RequestMapping("user")
@Slf4j
public class UserController {

    // Generate the Base64 code of the verify code image
    @RequestMapping("getImage")
    public String getImageCode64(HttpServletRequest request) throws IOException {
        // 1.Use the utils to generate the verify code whose length is 6
        String verifyCode = VerifyCodeUtils.generateVerifyCode(6);
        // 2.The verify code will be matched later, so it should be saved into the Application scope (servletContext)
        request.getServletContext().setAttribute("verifyCode",verifyCode);
        // 3.Turn the image into base64 format
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(120,40,byteArrayOutputStream, verifyCode);
        String verifyCodeImg_base64 = Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
        // 4.Format the base64 code
        return "data:image/png;base64,"+verifyCodeImg_base64;
    }


    // Inject the userService object
    @Autowired
    private UserService userService;
    // Handle the register of the users
    @PostMapping("signup")
    public Map<String, Object> signup(@Param("username") String username,
                                      @Param("password") String password,
                                      @Param("gender") String gender,
                                      @Param("email") String email,
                                      @Param("verifyCode") String verifyCode, HttpServletRequest request){
        Map<String, Object> res = new HashMap<>();
//        System.out.println(verifyCode);
//        System.out.println((String) request.getServletContext().getAttribute("verifyCode"));
        try {
            // 1. Get the key verify code from application scope(ServletContext)
            String keyCode = (String) request.getServletContext().getAttribute("verifyCode");
            // 2. Match the verify code
            if (keyCode.equalsIgnoreCase(verifyCode)){
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setGender(gender);
                user.setEmail(email);
                System.out.println(user.toString());
                userService.register(user);
//                System.out.println(user.toString());
                res.put("state",true);
                res.put("msg", "Message: Success!");
            }else{
                throw new RuntimeException("Verify code is wrong!");
            }
        }catch (Exception e){
            res.put("state",false);
            res.put("msg", "Message: "+e.getMessage());
        }
        return res;
    }

    //Handle user's login operation
    @PostMapping("signin")
    public Map<String, Object> signin(@Param("username") String username,
                                      @Param("password") String password){
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            User userByUsername = userService.login(user);
            map.put("state",true);
            map.put("msg", "Success.");
            // Username needed to show welcome page
            map.put("username", username);
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg", "message"+e.getMessage());
        }
        return map;
    }
}
