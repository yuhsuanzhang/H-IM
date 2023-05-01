package com.yuhsuanzhang.him.imserver.controller;

import com.yuhsuanzhang.him.imcommon.entity.User;
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

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // 验证用户名和密码是否正确，这里省略具体实现
//        if (!isValid(user)) {
//            return "用户名或密码错误";
//        }

        // 创建session并将sessionId返回给客户端
        String sessionId = userSession.createSession(user.getId());
        return sessionId;
    }

    @PostMapping("/logout")
    public String logout(@RequestParam("sessionId") String sessionId) {
        // 删除session
        userSession.removeSession(sessionId);
        return "注销成功";
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
