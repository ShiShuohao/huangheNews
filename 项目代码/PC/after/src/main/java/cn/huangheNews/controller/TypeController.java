package cn.huangheNews.controller;

import cn.huangheNews.common.Result;
import cn.huangheNews.entity.News;
import cn.huangheNews.entity.Type;
import cn.huangheNews.mapper.NewsMapper;
import cn.huangheNews.mapper.TypeMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    TypeMapper typeMapper;
    @Resource
    NewsMapper newsMapper;

    //查询所有固定板块
    @GetMapping("/getFixedType")
    public Result<?> getFixedType(@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<Type> typePage = typeMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Type>lambdaQuery().eq(Type::getState, 0));
        return Result.success(typePage);
    }

    //查询所有可变板块
    @GetMapping("/getFreeType")
    public Result<?> getFreeType(@RequestParam int currentPage,@RequestParam int pageSize) {
        Page<Type> typePage = typeMapper.selectPage(new Page<>(currentPage, pageSize), Wrappers.<Type>lambdaQuery().eq(Type::getState, 1));
        return Result.success(typePage);
    }

    //新增板块接口
    @PostMapping("/add")
    public Result<?> add(@RequestBody Type type) {
        List<Type> types = typeMapper.selectList(Wrappers.<Type>lambdaQuery());
        List<String> typeList = new ArrayList<>();
        for (Type t : types) {
            typeList.add(t.getName());
        }
        if (typeList.contains(type.getName())) {
            return Result.error("1","失败");
        }else {
            typeMapper.insert(type);
            return Result.success("新增成功");
        }
    }

    //修改板块名接口
    @GetMapping("/edit")
    public Result<?> edit(@RequestParam String oldName,@RequestParam String newName,@RequestParam int newsNumber) {
        List<Type> types = typeMapper.selectList(Wrappers.<Type>lambdaQuery());
        List<String> typeList = new ArrayList<>();
        for (Type type : types) {
            typeList.add(type.getName());
        }
        if (typeList.contains(newName)) {                 //新名字已经出现过了
            return Result.error("1","失败");
        }else if (newsNumber==0){         //该板块没有帖子
            Type type = typeMapper.selectById(oldName);
            type.setName(newName);
            typeMapper.deleteById(oldName);
            typeMapper.insert(type);
            return Result.success("修改成功");
        }else {
            Type type = typeMapper.selectById(oldName);
            type.setName(newName);
            typeMapper.insert(type);
            List<News> news = newsMapper.selectList(Wrappers.<News>lambdaQuery().eq(News::getType, oldName));
            for (News n : news) {
                n.setType(newName);
                newsMapper.updateById(n);
            }
            typeMapper.deleteById(oldName);
            return Result.success("修改成功");
        }
    }

    //该接口的作用是删除板块，没有帖子时才能删除
    @PostMapping("/delete")
    public Result<?> delete(@RequestBody Type type) {
        if (type.getNewsNumber()==0) {
            typeMapper.deleteById(type);
            return Result.success("删除成功");
        }else {
            return Result.error("1","失败");
        }
    }

    //该接口的作用是返回所有板块
    @PostMapping("/getAllType")
    public Result<?> getAllType() {
        List<Type> types = typeMapper.selectList(Wrappers.<Type>lambdaQuery().orderByAsc(Type::getState).orderByDesc(Type::getNewsNumber));
        return Result.success(types);
    }


}
