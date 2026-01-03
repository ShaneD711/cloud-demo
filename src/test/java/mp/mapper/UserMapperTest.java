package mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    /**
     * 测试插入用户
     */
    @Test
    void testInsert() {
        User user = new User();
        // user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    /**
     * 测试根据id查询用户
     */
    @Test
    void testSelectById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    /**
     * 测试根据id列表查询用户
     */
    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    /**
     * 测试根据id列表查询用户
     * Mybatis 在UserMapper.xml书写SQL语句
     */
    @Test
    void testQueryByIds2() {
        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }


    /**
     * 测试根据id更新用户
     */
    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    /**
     * 测试根据id删除用户
     */
    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }

    /**
     * QueryWrapper
     * <p>
     * 查询出名字中带o的 存款大于等于100元的人的id username info balance
     * SELECT id ,username ,info ,balance FROM user WHERE username LIKE ? AND balance = ?
     */
    @Test
    void testqueryWrapper() {
        // 构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>().select("id", "username", "info", "balance").like("username", "Lucy").ge("balance", 100);
        // 执行查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * QueryWrapper
     * <p>
     * 更新用户名为jack的用户余额为2000
     * UPDATE user SET balance = 2000 WHERE (username = "jack")
     */
    @Test
    void testUpdateByQueryWrapper() {
        // 创建要更新的数据对象
        User user = new User();
        user.setBalance(2000);
        // 更新的条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", "jack");
        // 执行更新
        userMapper.update(user, wrapper);
    }


    /**
     * UpdateWrapper
     * <p>
     * 更新id为1,2,4的用户的余额,扣200
     * UPDATE user SET balance = balance -200 WHERE id in (1,2,4)
     */
    @Test
    void testUpdateWrapper() {
        List<Long> ids = List.of(1L, 2L, 3L, 4L);
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>().setSql("balance = balance - 100").in("id", ids);
        userMapper.update(null, wrapper);
    }

    /**
     * LambdaQueryWrapper
     * 查询出名字中带o的 存款大于等于100元的人的id username info balance
     * SELECT id ,username ,info ,balance FROM user WHERE username LIKE ? AND balance = ?
     */
    @Test
    void testLambdaQueryWrapper() {
        // 构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>().select(User::getId, User::getUsername, User::getInfo, User::getBalance).like(User::getUsername, "o").ge(User::getBalance, 100);
        // 执行查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * MP 自定义SQL
     * MP 自定义 SQL = 自己手写的 SELECT 语句 + MP 自动生成的 WHERE 子句
     * 步骤:
     * 1.基于Wrapper构建where条件 调用mapper中的方法
     * 2.在mapper中的方法 参数用Param注解声明wrapper变量名称,必须为we
     * 3.在方法上方或者XML中自定义SQL 并使用Wrapper条件
     *
     * <p>
     * 更新id为1,2,4的用户的余额,扣200
     * UPDATE user SET balance = balance -200 WHERE id in (1,2,4)
     */
    @Test
    void testCustomSqlUpdate() {
        // 更新条件
        List<Long> ids = List.of(1L, 2L, 3L, 4L);
        int amount = 200;
        // 定义条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .in("id", ids);
        // 调用自定义方法
        userMapper.updateBalanceByIds(wrapper, amount);
    }


}