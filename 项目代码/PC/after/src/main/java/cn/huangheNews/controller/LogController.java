package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Log;
import cn.huangheNews.mapper.LogMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    LogMapper logMapper;

    //该接口的作用发布日志
    @PostMapping("/publishLog")
    public Result<String> publishLog(@RequestBody Log log) {
        logMapper.insert(log);
        return Result.success("发布成功");
    }

    //该接口的作用是查询所有日志
    @GetMapping("/getAllLog")
    public Result<?> getAllLog(@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<Log> logPage = logMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Log>lambdaQuery().orderByDesc(Log::getDate));
        return Result.success(logPage);
    }

    //该接口的作用是修改日志
    @PostMapping("/editLog")
    public Result<String> editLog(@RequestBody Log log) {
        logMapper.updateById(log);
        return Result.success("修改成功");
    }

    //该接口的作用是删除日志
    @PostMapping("/deleteLog")
    public Result<String> deleteLog(@RequestBody int id) {
        logMapper.deleteById(id);
        return Result.success("删除成功");
    }

}
