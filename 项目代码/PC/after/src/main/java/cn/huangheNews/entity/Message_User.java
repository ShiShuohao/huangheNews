package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@TableName("message_user")
@Data
public class Message_User {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String userFrom;
    private String userTo;
    private String headline;
    private String content;
    private Date date;
    private int state;
    private String petname;
}
