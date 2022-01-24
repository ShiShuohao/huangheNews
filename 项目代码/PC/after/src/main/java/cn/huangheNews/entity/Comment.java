package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("comment")
@Data
public class Comment {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private int newsId;
    private String author;
    private String content;
    private Date date;
}
