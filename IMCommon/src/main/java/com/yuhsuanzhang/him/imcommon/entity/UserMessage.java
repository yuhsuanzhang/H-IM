package com.yuhsuanzhang.him.imcommon.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Data
public class UserMessage {
    private Long id; // 关系ID，使用自增主键或雪花算法生成
    private Long messageId; // 消息ID
    private Long userId; // 用户ID
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Long version; //版本号
}
