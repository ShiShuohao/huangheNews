package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Collect_News;
import cn.huangheNews.mapper.Collect_NewsMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/collect_News")
public class Collect_NewsController {

    @Resource
    Collect_NewsMapper collect_newsMapper;

    //该接口的作用是查询某用户的全部收藏
    @GetMapping("/get")
    public Result<?> get(@RequestParam String userEmail, @RequestParam int currentPage, @RequestParam int pageSize) {
        Page<Collect_News> collect_newsPage = collect_newsMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Collect_News>lambdaQuery().eq(Collect_News::getUser, userEmail).orderByDesc(Collect_News::getDate));
        return Result.success(collect_newsPage);
    }
}
