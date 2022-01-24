package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Focus;
import cn.huangheNews.mapper.FocusMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/focus")
public class FocusController {

    @Resource
    FocusMapper focusMapper;

    //该接口的作用是判断是否关注过，若没有，则关注
    @GetMapping("/focusUser")
    public Result<String> focusUser(@RequestParam String userFrom,@RequestParam String userTo) {
        Focus focus = focusMapper.selectOne(Wrappers.<Focus>lambdaQuery().eq(Focus::getUserFrom, userFrom).eq(Focus::getUserTo, userTo));
        if (focus==null) {                  //没有关注过
            Focus newFocus = new Focus();
            newFocus.setUserFrom(userFrom);
            newFocus.setUserTo(userTo);
            focusMapper.insert(newFocus);
            return Result.success("关注成功");
        }else {
            Result result = Result.error("1", "失败");
            result.setData("关注失败");
            return result;
        }
    }

    //该接口的作用是取消关注
    @PostMapping("/deleteFocusById")
    public Result<String> deleteFocusById(@RequestBody int id) {
        focusMapper.deleteById(id);
        return Result.success("取消成功");
    }

}
