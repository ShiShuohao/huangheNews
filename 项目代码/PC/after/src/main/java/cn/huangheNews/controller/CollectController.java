package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Collect;
import cn.huangheNews.mapper.CollectMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    CollectMapper collectMapper;

    //该接口的作用是获取收藏信息
    @GetMapping("/getCollectInfo")
    public Result<String> fetCollectInfo(@RequestParam int newsId,@RequestParam String userEmail) {
        Collect collect = collectMapper.selectOne(Wrappers.<Collect>lambdaQuery().eq(Collect::getNewsId, newsId).eq(Collect::getUser, userEmail));
        if (collect==null) {    //未收藏
            return Result.success("未收藏");
        }else {
            return Result.success("已收藏");
        }
    }

    //该接口的作用是添加收藏
    @GetMapping("/insertCollect")
    public Result<String> insertCollect(@RequestParam int newsId,@RequestParam String userEmail) {
        Collect collect = new Collect();
        collect.setUser(userEmail);
        collect.setNewsId(newsId);
        collect.setDate(new Date());
        collectMapper.insert(collect);
        return Result.success("收藏成功");
    }

    //该接口的作用是取消收藏
    @GetMapping("/deleteCollect")
    public Result<String> deleteCollect(@RequestParam int newsId,@RequestParam String userEmail) {
        int delete = collectMapper.delete(Wrappers.<Collect>lambdaQuery().eq(Collect::getNewsId, newsId).eq(Collect::getUser, userEmail));
        return Result.success("取消成功");
    }

    //该接口的作用是取消收藏
    @PostMapping("/deleteCollectById")
    public Result<String> deleteCollectById(@RequestBody int id) {
        collectMapper.deleteById(id);
        return Result.success("取消成功");
    }

}
