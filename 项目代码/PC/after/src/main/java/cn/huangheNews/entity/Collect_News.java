package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("collect_news")
@Data
public class Collect_News {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String user;
    private int newsId;
    private Date date;
    private String headline;
}
