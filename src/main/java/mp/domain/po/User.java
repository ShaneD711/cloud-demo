package mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
// 数据库表名是：tb_user
// Java类名是：User
// 如果不加这个注解，MP 会去找 'user' 表，然后报错
@TableName("tb_user")
public class User {

    /**
     * 用户id
     */
    // type = IdType.AUTO：告诉 MP 这是一个自增主键（依赖数据库的 auto_increment）
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    // 数据库字段：user_name
    // Java 属性：name
    // @TableField("user_name") 解决名字不一致
    private String username;

    /**
     * 密码
     */
    // exist = false 代表：这个字段只在 Java 代码里用，数据库里没有，别去找它
    // @TableField(exist = false)
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 详细信息
     */
    private String info;

    /**
     * 使用状态（1正常 2冻结）
     */
    private Integer status;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
