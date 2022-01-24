package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Comment;
import cn.huangheNews.mapper.CommentMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentMapper commentMapper;

    //该接口的作用是新增评论
    @PostMapping("/insertComment")
    public Result<String> insertComment(@RequestBody Comment comment) {
        comment.setDate(new Date());
        commentMapper.insert(comment);
        return Result.success("新增成功");
    }

    //该接口的作用是删除评论
    @PostMapping("/deleteCommentById")
    public Result<String> deleteCommentById(@RequestBody int id) {
        commentMapper.deleteById(id);
        return Result.success("删除成功");
    }

}
