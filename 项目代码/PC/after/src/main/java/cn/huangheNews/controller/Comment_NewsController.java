package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Comment_News;
import cn.huangheNews.mapper.Comment_NewsMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment_News")
public class Comment_NewsController {

    @Resource
    Comment_NewsMapper comment_newsMapper;

    //该接口的作用是查询某用户的全部评论
    @GetMapping("/get")
    public Result<?> get(@RequestParam String userEmail,@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<Comment_News> comment_newsPage = comment_newsMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Comment_News>lambdaQuery().eq(Comment_News::getAuthor, userEmail).orderByDesc(Comment_News::getDate));
        return Result.success(comment_newsPage);
    }
}
