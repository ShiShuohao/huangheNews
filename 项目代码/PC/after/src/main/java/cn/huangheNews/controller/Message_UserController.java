package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Message_User;
import cn.huangheNews.mapper.Message_UserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/message_User")
public class Message_UserController {

    @Resource
    Message_UserMapper message_userMapper;

    //查询某用户的所有未读私信
    @GetMapping("/getAllNotRead")
    public Result<?> getAllNotRead(@RequestParam String userEmail,@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<Message_User> message_userPage = message_userMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Message_User>lambdaQuery().eq(Message_User::getUserTo, userEmail).eq(Message_User::getState, 0).orderByDesc(Message_User::getDate));
        return Result.success(message_userPage);
    }

    //查询某用户的所有已读私信
    @GetMapping("/getAllReaded")
    public Result<?> getAllReaded(@RequestParam String userEmail,@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<Message_User> message_userPage = message_userMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Message_User>lambdaQuery().eq(Message_User::getUserTo, userEmail).eq(Message_User::getState, 1).orderByDesc(Message_User::getDate));
        return Result.success(message_userPage);
    }
}
