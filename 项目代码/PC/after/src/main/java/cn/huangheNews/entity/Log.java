package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("log")
@Data
public class Log {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String version;
    private Date date;
    private String content;
}
