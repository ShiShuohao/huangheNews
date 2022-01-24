package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.Focus_User;
import cn.huangheNews.mapper.Focus_UserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/focus_User")
public class Focus_UserController {

    @Resource
    Focus_UserMapper focus_userMapper;

    //该接口的作用是查询某用户的全部关注
    @GetMapping("/get")
    public Result<?> get(@RequestParam String userEmail,@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<Focus_User> focus_userPage = focus_userMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Focus_User>lambdaQuery().eq(Focus_User::getUserFrom, userEmail));
        return Result.success(focus_userPage);
    }
}
