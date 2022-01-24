package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("collect")
@Data
public class Collect {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String user;
    private int newsId;
    private Date date;
}
