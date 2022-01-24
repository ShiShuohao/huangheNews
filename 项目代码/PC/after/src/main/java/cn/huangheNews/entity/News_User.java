package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("news_user")
@Data
public class News_User {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String headline;
    private String author;
    private String content;
    private Date date;
    private String type;
    private int click;
    private int comment;
    private int first;
    private String petname;
    private String sex;
}
