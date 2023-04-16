package com.yuhsuanzhang.him.imserver.handle;

import io.lettuce.core.RedisClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<String> {

    @Resource
    private RedissonClient redissonClient;
    private final RMap<String, ChannelHandlerContext> clients = redissonClient.getMap("clients");
    private final RMap<String, List<String>> groups = redissonClient.getMap("groups");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clients.put(ctx.channel().id().asLongText(),ctx);
        System.out.println("Client " + ctx.channel().id().asLongText() + " connected.");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientId = ctx.channel().id().asLongText();
        clients.remove(clientId);
        System.out.println("Client " + clientId + " disconnected.");
        for (List<String> group : groups.values()) {
            group.remove(clientId);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        System.out.println("Received message [" + message + "]");
        String clientId = ctx.channel().id().asLongText();
        if (message.startsWith("@")) {
            // 私聊消息
            int index = message.indexOf(":");
            if (index > 0) {
                String targetClientId = message.substring(1, index);
                String content = message.substring(index + 1);
                ChannelHandlerContext targetCtx = clients.get(targetClientId);
                if (targetCtx != null) {
                    targetCtx.writeAndFlush("[" + clientId + "]: " + content + "\n");
                    ctx.writeAndFlush("You whispered to [" + targetClientId + "]: " + content + "\n");
                } else {
                    ctx.writeAndFlush("No such client with ID " + targetClientId + "\n");
                }
            } else {
                ctx.writeAndFlush("Invalid message format for private chat, should be '@clientId:message'\n");
            }
        } else if (message.startsWith("#")) {
            // 群聊消息
            int index = message.indexOf(":");
            if (index > 0) {
                String groupId = message.substring(1, index);
                String content = message.substring(index + 1);
                List<String> group = groups.get(groupId);
                if (group != null) {
                    for (String memberId : group) {
                        if (!memberId.equals(clientId)) {
                            ChannelHandlerContext memberCtx = clients.get(memberId);
                            if (memberCtx != null) {
                                memberCtx.writeAndFlush("[" + clientId + "]: " + content + "\"");
                            }
                        }
                        ctx.writeAndFlush("You sent a message to group " + groupId + ": " + content + "\n");
                    }
                } else {
                    ctx.writeAndFlush("Invalid message format for group chat, should be '#groupId:message'\n");
                }
            } else {
                // 广播消息
                for (ChannelHandlerContext clientCtx : clients.values()) {
                    if (clientCtx != ctx) {
                        clientCtx.writeAndFlush("[" + clientId + "]: " + message + "\n");
                    }
                }
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("Client " + ctx.channel().id().asLongText() + " error: " + cause);
        ctx.close();
    }

}
