package cn.huangheNews.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("focus")
@Data
public class Focus {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String userFrom;
    private String userTo;
}
