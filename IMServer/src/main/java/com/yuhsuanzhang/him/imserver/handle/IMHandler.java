package com.yuhsuanzhang.him.imserver.handle;

import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import com.yuhsuanzhang.him.imcommon.entity.User;
import com.yuhsuanzhang.him.imcommon.enums.MessageTypeEnum;
import com.yuhsuanzhang.him.imserver.service.UserChannelService;
import com.yuhsuanzhang.him.imserver.service.UserService;
import com.yuhsuanzhang.him.imserver.util.HashUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class IMHandler extends SimpleChannelInboundHandler<IMMessageProto.IMMessage> {

    @Value("${im.server.id:zyx}")
    private String serverId;

    @Resource
    private UserService userService;

    @Resource
    private UserChannelService userChannelService;

    @Resource
    private IMMessageHandler imMessageHandler;

//    @Resource
//    private UserSessionHolder userSessionHolder;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //RMap<String, ChannelHandlerContext> clients = redissonClient.getMap("clients");
//        clients.put(ctx.channel().id().asLongText(),ctx);
//        System.out.println("Client " + ctx.channel().id().asLongText() + " connected.");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userChannelService.logout(ctx);
        super.channelInactive(ctx);
        //RMap<String, ChannelHandlerContext> clients = redissonClient.getMap("clients");
//        RMap<String, List<String>> groups = redissonClient.getMap("groups");
//        String clientId = ctx.channel().id().asLongText();
//        clients.remove(clientId);
//        System.out.println("Client " + clientId + " disconnected.");
//        for (List<String> group : groups.values()) {
//            group.remove(clientId);
//        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMMessageProto.IMMessage message) throws Exception {
        if (message == null) {
            log.info("Received message is null channel id [{}]", ctx.channel().id().asLongText());
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.LOGIN.getCode()) {
            imMessageHandler.login(ctx, message);
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.LOGOUT.getCode()) {
            imMessageHandler.logout(ctx, message);
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.PING.getCode()) {
            imMessageHandler.ping(ctx, message);
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.TEXT.getCode()) {
            imMessageHandler.text(ctx, message);
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.IMAGE.getCode()) {
            imMessageHandler.image(ctx, message);
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.VOICE.getCode()) {
            imMessageHandler.voice(ctx, message);
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.VIDEO.getCode()) {
            imMessageHandler.video(ctx, message);
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.FILE.getCode()) {
            imMessageHandler.file(ctx, message);
            return;
        }
        if (message.getMessageType() == MessageTypeEnum.SYSTEM.getCode()) {
            imMessageHandler.system(ctx, message);
            return;
        }
//        RMap<String, ChannelHandlerContext> clients = redissonClient.getMap("clients");
//        String message = mes.getContent();
//        RMap<String, List<String>> groups = redissonClient.getMap("groups");
//        System.out.println("Received message [" + message + "]");
//        String clientId = ctx.channel().id().asLongText();
//        if (message.startsWith("@")) {
//            // 私聊消息
//            int index = message.indexOf(":");
//            if (index > 0) {
//                String targetClientId = message.substring(1, index);
//                String content = message.substring(index + 1);
//                ChannelHandlerContext targetCtx = clients.remove(targetClientId);
//                if (targetCtx != null) {
//                    targetCtx.writeAndFlush("[" + clientId + "]: " + content + "\n");
//                    ctx.writeAndFlush("You whispered to [" + targetClientId + "]: " + content + "\n");
//                } else {
//                    ctx.writeAndFlush("No such client with ID " + targetClientId + "\n");
//                }
//            } else {
//                ctx.writeAndFlush("Invalid message format for private chat, should be '@clientId:message'\n");
//            }
//        } else if (message.startsWith("#")) {
//            // 群聊消息
//            int index = message.indexOf(":");
//            if (index > 0) {
//                String groupId = message.substring(1, index);
//                String content = message.substring(index + 1);
//                List<String> group = groups.get(groupId);
//                if (group != null) {
//                    for (String memberId : group) {
//                        if (!memberId.equals(clientId)) {
//                            ChannelHandlerContext memberCtx = clients.remove(memberId);
//                            if (memberCtx != null) {
//                                memberCtx.writeAndFlush("[" + clientId + "]: " + content + "\"");
//                            }
//                        }
//                        ctx.writeAndFlush("You sent a message to group " + groupId + ": " + content + "\n");
//                    }
//                } else {
//                    ctx.writeAndFlush("Invalid message format for group chat, should be '#groupId:message'\n");
//                }
//            } else {
//                // 广播消息
//                for (ChannelHandlerContext clientCtx : clients.values()) {
//                    if (clientCtx != ctx) {
//                        clientCtx.writeAndFlush("[" + clientId + "]: " + message + "\n");
//                    }
//                }
//            }
//        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("Client " + ctx.channel().id().asLongText() + " error: " + cause + "\n" + Arrays.toString(cause.getStackTrace()));
        ctx.close();
    }

}
