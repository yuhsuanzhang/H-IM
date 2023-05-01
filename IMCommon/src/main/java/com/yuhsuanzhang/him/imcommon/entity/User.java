package com.yuhsuanzhang.him.imcommon.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Data
public class User {
    private Long id;  // 数据库id
    private String account;  // 用户名/账号
    private String password;  // 密码
    private String phone;  // 手机
    private String email;  // 邮箱
    private String nickname;  // 昵名
    private String avatar;  // 头像
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
    private Long version;  // 版本号
}
