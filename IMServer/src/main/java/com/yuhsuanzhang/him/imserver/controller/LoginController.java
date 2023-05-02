package com.yuhsuanzhang.him.imserver.controller;

import com.yuhsuanzhang.him.imcommon.entity.User;
import com.yuhsuanzhang.him.imserver.service.UserService;
import com.yuhsuanzhang.him.imserver.service.UserSession;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@RestController
public class LoginController {

    @Resource
    private UserSession userSession;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // 验证用户名和密码是否正确，这里省略具体实现
        user = userService.getUser(user.getAccount(), user.getPassword());
        if (user == null) {
            return "用户名或密码错误";
        }
        // 创建session并将sessionId返回给客户端
        return userSession.createSession(user.getId());
    }

    @PostMapping("/logout")
    public String logout(@RequestParam("sessionId") String sessionId) {
        // 删除session
        userSession.removeSession(sessionId);
        return "注销成功";
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) {
        // 注册
        userService.insert(user);
        return "注册成功";
    }

    @GetMapping("/ping")
    public String ping(@RequestParam("sessionId") String sessionId) {
        // 验证session是否有效
        Long userId = userSession.getUserId(sessionId);
        if (userId == null) {
            return "session已过期";
        }
        return "pong";
    }
}
