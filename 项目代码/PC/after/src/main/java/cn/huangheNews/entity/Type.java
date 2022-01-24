package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("type")
@Data
public class Type {
    @TableId(value = "name")
    private String name;
    private int newsNumber;
    private int state;
}
