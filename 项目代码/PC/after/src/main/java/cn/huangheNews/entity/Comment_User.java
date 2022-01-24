package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("comment_user")
@Data
public class Comment_User {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private int newsId;
    private String author;
    private String content;
    private Date date;
    private String petname;
    private String sex;
}
