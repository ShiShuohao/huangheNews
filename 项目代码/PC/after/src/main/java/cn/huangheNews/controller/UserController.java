package cn.huangheNews.controller;

import cn.hutool.extra.mail.MailUtil;
import cn.huangheNews.common.Result;
import cn.huangheNews.common.VerifyUtil;
import cn.huangheNews.entity.User;
import cn.huangheNews.mapper.UserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    //获取用户对象
    @PostMapping("/get")
    public Result<User> get(@RequestBody String email) {
        User user = userMapper.selectById(email);
        return Result.success(user);
    }

    //该接口接收前端传入的邮箱，判断该用户是否存在，若存在，则是登录，若不存在，则是注册
    @PostMapping("/sendEmail")
    public Result<?> sendEmail(@RequestBody String email) {
        List<String> userList = new ArrayList<>();   //所有用户的邮箱
        List<User> list = userMapper.selectList(Wrappers.<User>lambdaQuery());
        for (User user : list) {
            userList.add(user.getEmail());
        }
        if (userList.contains(email)) {  //如果该用户存在
            String verifyCode = VerifyUtil.getVerifyCode();
            MailUtil.send(email, "黄河新闻网", "您的验证码为： "+ verifyCode +"  请尽快验证！", false); //发送邮件
            Result<String> success = Result.success(verifyCode);
            success.setMsg("登录");
            return success;
        }else {
            String verifyCode = VerifyUtil.getVerifyCode();
            MailUtil.send(email, "黄河新闻网", "您的验证码为： "+ verifyCode +"  请尽快验证！", false); //发送邮件
            Result<String> success = Result.success(verifyCode);
            success.setMsg("注册");
            return success;
        }
    }

    //该接口的作用是将用户的注册信息添加到数据库
    @PostMapping("/logon")
    public Result<String> logon(@RequestBody User user) {
        user.setPetname("未设置");
        user.setSex("未设置");
        user.setBirthday(new Date());
        user.setArea("未设置");
        user.setRole(0);
        userMapper.insert(user);
        return Result.success("注册成功");
    }

    //该接口的作用是修改用户的个人信息
    @PostMapping("/editUserInfo")
    public Result<String> editUserInfo(@RequestBody User user) {
        user.setRole(0);
        userMapper.updateById(user);
        return Result.success("更新成功");
    }

    //该接口接收前端传入的邮箱，判断该用户是否存在，若存在，则返回验证码
    @PostMapping("/sendEmailForAdmin")
    public Result<?> sendEmailForAdmin(@RequestBody String email) {
        List<String> userList = new ArrayList<>();   //所有用户的邮箱
        List<User> list = userMapper.selectList(Wrappers.<User>lambdaQuery());
        for (User user : list) {
            userList.add(user.getEmail());
        }
        if (userList.contains(email)) {  //如果该用户存在
            String verifyCode = VerifyUtil.getVerifyCode();
            MailUtil.send(email, "黄河新闻网", "您的验证码为： "+ verifyCode +"  请尽快验证！", false); //发送邮件
            return Result.success(verifyCode);
        }else {
            return Result.error("1","失败");
        }
    }

    //该接口接收前端参数为用户的邮箱，返回用户的角色
    @PostMapping("/getRole")
    public Result<Integer> getRole(@RequestBody String email) {
        User user = userMapper.selectById(email);
        return Result.success(user.getRole());
    }

    //该接口的作用是返回所有用户的用户邮箱
    @PostMapping("/getAllUserEmail")
    public Result<List<String>> getAllUserEmail() {
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery());
        List<String> userEmailList = new ArrayList<>();
        for (User user : users) {
            userEmailList.add(user.getEmail());
        }
        return Result.success(userEmailList);
    }

    //该接口的作用是查询所有的普通用户
    @GetMapping("/getAllUserNotAdmin")
    public Result<?> getAllUserNotAdmin(@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<User> userPage = userMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<User>lambdaQuery().eq(User::getRole, 0).or().eq(User::getRole, 3));
        return Result.success(userPage);
    }

    //该接口的作用是删除用户
    @PostMapping("/deleteUserByEmail")
    public Result<String> deleteUserByEmail(@RequestBody String email) {
        userMapper.deleteById(email);
        return Result.success("删除成功");
    }

    //该接口的作用是查询所有的管理员
    @GetMapping("/getAllUserAdmin")
    public Result<?> getAllUserAdmin(@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<User> userPage = userMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<User>lambdaQuery().eq(User::getRole, 1));
        return Result.success(userPage);
    }

    //该接口的作用是将管理员降为用户
    @PostMapping("/downRole")
    public Result<String> downRole(@RequestBody String email) {
        User user = userMapper.selectById(email);
        user.setRole(0);
        userMapper.updateById(user);
        return Result.success("更新成功");
    }

    //该接口的作用是同意用户的管理员申请
    @PostMapping("/upRole")
    public Result<String> upRole(@RequestBody String email) {
        User user = userMapper.selectById(email);
        user.setRole(1);
        userMapper.updateById(user);
        return Result.success("更新成功");
    }

    //该接口的作用是查询所有的管理员申请
    @GetMapping("/getAllAdminRequest")
    public Result<?> getAllAdminRequest(@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<User> userPage = userMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<User>lambdaQuery().eq(User::getRole, 3));
        return Result.success(userPage);
    }

    //该接口的作用是修改用户的个人信息
    @PostMapping("/editUser")
    public Result<String> editUser(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success("更新成功");
    }

    //该接口的作用是将用户变为待审核的管理员
    @PostMapping("/changeUserToAdminForRequest")
    public Result<String> changeUserToAdminForRequest(@RequestBody String userEmail) {
        User user = userMapper.selectById(userEmail);
        user.setRole(3);
        userMapper.updateById(user);
        return Result.success("修改成功");
    }


}
