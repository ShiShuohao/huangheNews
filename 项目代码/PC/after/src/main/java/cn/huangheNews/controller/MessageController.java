package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Message;
import cn.huangheNews.mapper.MessageMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    MessageMapper messageMapper;

    //该接口的作用是实现管理员给每个用户发送私信
    @PostMapping("/sendMessageToAllUser")
    public Result<String> sendMessageToAllUser(@RequestBody Message message) {
        messageMapper.insert(message);
        return Result.success("发送成功");
    }

    //该接口的作用是发送私信
    @PostMapping("/sendMessage")
    public Result<String> sendMessage(@RequestBody Message message) {
        message.setDate(new Date());
        messageMapper.insert(message);
        return Result.success("发送成功");
    }

    //该接口的作用是将私信标记为已读
    @PostMapping("/setMessageReaded")
    public Result<String> setMessageReaded(@RequestBody int id) {
        Message message = messageMapper.selectById(id);
        message.setState(1);
        messageMapper.updateById(message);
        return Result.success("更新成功");
    }

    //该接口的作用是删除私信
    @PostMapping("/deleteMessage")
    public Result<String> deleteMessage(@RequestBody int id) {
        messageMapper.deleteById(id);
        return Result.success("删除成功");
    }
}
