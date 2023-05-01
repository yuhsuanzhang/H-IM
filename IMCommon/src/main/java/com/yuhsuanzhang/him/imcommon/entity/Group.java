package com.yuhsuanzhang.him.imcommon.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Data
public class Group {
    private Long id; // 群组ID，使用自增主键或雪花算法生成
    private String groupName; // 群组名称
    private String groupAvatar; // 群组头像
    private String creator; //创建人
    private String owner; //群主
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Long version; //版本号
}
