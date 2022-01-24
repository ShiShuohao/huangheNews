package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Comment_User;
import cn.huangheNews.mapper.Comment_UserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/comment_User")
public class Comment_UserController {

    @Resource
    Comment_UserMapper comment_userMapper;

    //该接口的作用是查询某新闻的全部评论
    @GetMapping("/getAllCommentByNewsId")
    public Result<?> getAllCommentByNewsId(@RequestParam int newsId,@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<Comment_User> comment_userPage = comment_userMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Comment_User>lambdaQuery().eq(Comment_User::getNewsId, newsId).orderByDesc(Comment_User::getDate));
        return Result.success(comment_userPage);
    }
}
