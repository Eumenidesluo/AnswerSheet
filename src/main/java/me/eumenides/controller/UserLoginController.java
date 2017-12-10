package me.eumenides.controller;

import me.eumenides.entity.User;
import me.eumenides.interceptor.Auth;
import me.eumenides.interceptor.Constants;
import me.eumenides.interceptor.RedisUtils;
import me.eumenides.interceptor.SessionData;
import me.eumenides.service.UserService;
import me.eumenides.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;

import static me.eumenides.interceptor.Constants.SESSION_KEY;
import static me.eumenides.interceptor.Constants.SESSION_KEY_PREFIX;

/**
 * Created by Eumenides on 2017/11/30.
 */
@Controller
public class UserLoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/loginPage")
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping("/registerPage")
    public String getRegisterPage() {
        return "register";
    }
    @RequestMapping("/")
    public String index(){
        return "forward:load";
    }
    @RequestMapping("/login")
    @ResponseBody
    public R login(HttpSession session, String username, String password) {
        R result = new R();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        List<User> users = userService.findByConditions(user);
        if (users.isEmpty() || users.size() > 1) {
            return R.error();
        }
        user = users.get(0);
        session.setAttribute(SESSION_KEY, user.getUserId());
        return result;
    }

    @RequestMapping("/register")
    @ResponseBody
    public R register(String username, String password) {
        if (userService.findByUsername(username) != null) {
           return R.error(5001, "用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (userService.insertNewUser(user) == 1)
            return R.ok();
        return R.error("未知错误");
    }
}
