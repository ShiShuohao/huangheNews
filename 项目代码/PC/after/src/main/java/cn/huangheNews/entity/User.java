package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("user")
@Data
public class User {
    @TableId(value = "email")
    private String email;
    private String petname;
    private String sex;
    private Date birthday;
    private String area;
    private int role;
}
