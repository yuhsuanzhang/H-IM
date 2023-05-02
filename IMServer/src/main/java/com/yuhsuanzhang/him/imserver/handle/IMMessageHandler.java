package com.yuhsuanzhang.him.imserver.handle;

import com.yuhsuanzhang.him.imcommon.entity.IMMessage;
import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import com.yuhsuanzhang.him.imcommon.entity.User;
import com.yuhsuanzhang.him.imcommon.enums.MessageTypeEnum;
import com.yuhsuanzhang.him.imserver.service.UserChannelService;
import com.yuhsuanzhang.him.imserver.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Slf4j
@Component
public class IMMessageHandler {
    @Resource
    private UserService userService;
    @Resource
    private UserChannelService userChannelService;

    // 构造方法注入userService和userChannelService

    public void login(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        if (StringUtils.isEmpty(message.getContent())) {
            log.info("Received message content is null while login channel id [{}]", ctx.channel().id().asLongText());
            return;
        }
        if (message.getContent().contains("/")) {
            //content为[账号/密码]，因此账号不能有特殊字符"/"
            String[] content = message.getContent().split("/", 2);
            String account = content[0];
            String password = content[1];
            User user = userService.getUser(account, password);
            if (user == null) {
                log.info("Received message user is null while login account [{}]", account);
                return;
            }
            userChannelService.login(ctx, message.getDeviceType(), user.getId());
            log.info("Received message user login account [{}]", account);
        } else {
            log.info("Received message user login account/passport error, content [{}]", message.getContent());
        }
        return;
    }

    public void logout(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        // 处理注销消息的逻辑
        if (message.getMessageType() == MessageTypeEnum.LOGOUT.getCode()) {
            userChannelService.logout(ctx);
            log.info("Received message user logout channel id [{}]", ctx.channel().id().asLongText());
            return;
        }

    }

    public void ping(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        // 处理注销消息的逻辑
        if (message.getMessageType() == MessageTypeEnum.PING.getCode()) {
            // Do nothing, just respond with a PONG message
            log.info("Received message PING, responded with a PONG message");
            return;
        }

    }

    public void text(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        // 处理注销消息的逻辑
        if (message.getMessageType() == MessageTypeEnum.TEXT.getCode()) {
            if (StringUtils.isEmpty(message.getContent())) {
                log.info("Received message content is null while sending text message channel id [{}]", ctx.channel().id().asLongText());
                return;
            }
            // Process the text message
            // ...
            return;
        }

    }

    public void image(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        // 处理注销消息的逻辑
        if (message.getMessageType() == MessageTypeEnum.IMAGE.getCode()) {
            if (StringUtils.isEmpty(message.getContent())) {
                log.info("Received message content is null while sending image message channel id [{}]", ctx.channel().id().asLongText());
                return;
            }
            // Process the image message
            // ...
            return;
        }

    }

    public void voice(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        // 处理注销消息的逻辑
        if (message.getMessageType() == MessageTypeEnum.VOICE.getCode()) {
            if (StringUtils.isEmpty(message.getContent())) {
                log.info("Received message content is null while sending voice message channel id [{}]", ctx.channel().id().asLongText());
                return;
            }
            // Process the voice message
            // ...
            return;
        }

    }

    public void video(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        // 处理注销消息的逻辑
        if (message.getMessageType() == MessageTypeEnum.VIDEO.getCode()) {
            if (StringUtils.isEmpty(message.getContent())) {
                log.info("Received message content is null while sending video message channel id [{}]", ctx.channel().id().asLongText());
                return;
            }
            // Process the video message
            // ...
            return;
        }

    }

    public void file(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        // 处理注销消息的逻辑
        if (message.getMessageType() == MessageTypeEnum.FILE.getCode()) {
            if (StringUtils.isEmpty(message.getContent())) {
                log.info("Received message content is null while sending file message channel id [{}]", ctx.channel().id().asLongText());
                return;
            }
            // Process the file message
            // ...
            return;
        }

    }

    public void system(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) {
        // 处理注销消息的逻辑
        if (message.getMessageType() == MessageTypeEnum.SYSTEM.getCode()) {
            if (StringUtils.isEmpty(message.getContent())) {
                log.info("Received message content is null while sending system message channel id [{}]", ctx.channel().id().asLongText());
                return;
            }
            // Process the system message
            // ...
            return;
        }

    }

}

