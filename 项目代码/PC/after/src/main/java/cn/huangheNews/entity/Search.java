package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("search")
@Data
public class Search {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String user;
    private String content;
}
