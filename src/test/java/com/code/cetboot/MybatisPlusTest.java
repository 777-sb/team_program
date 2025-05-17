package com.code.cetboot;

import com.code.cetboot.entity.User;
import com.code.cetboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : IMG
 * @create : 2025/5/16
 */
@SpringBootTest
public class MybatisPlusTest {

    @Resource
    private UserService userService;

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void saveBatchTest() {
        List<User> collect = Stream.iterate(1, i -> i + 1)
                .limit(3)
                .map(i -> {
                    User user = new User();
                    user.setUserName("test" + i);
                    return user;
                })
                .collect(Collectors.toList());

        collect.forEach(user -> {
            System.out.println("user{\n" +
                    "userId=" + user.getUserId() + "\n" +
                    "userName='" + user.getUserName() + "'\n"
            );
        });
        userService.saveBatch(collect);
        System.out.println("插入成功: ");
        collect.forEach(user -> {
            System.out.println("user{\n" +
                    "userId=" + user.getUserId() + "\n" +
                    "userName='" + user.getUserName() + "'\n"
            );
        });
        throw new RuntimeException("测试异常");
    }
}
