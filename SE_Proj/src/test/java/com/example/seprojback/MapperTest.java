package com.example.seprojback;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.seprojback.entity.User;
import com.example.seprojback.mapper.PermsMenuMapper;
import com.example.seprojback.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermsMenuMapper permsMenuMapper;

    @Test
    public void testPermsMenuMapper() {
        System.out.println(permsMenuMapper.selectPermsByUserId("123"));
    }

    @Test
    public void TestBCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("1234");
//        String encode2 = bCryptPasswordEncoder.encode("1234");
//        System.out.println(encode);
//        System.out.println(encode2);
//        $2a$10$orqwzQShNGRFUmYyjoucNOQ98EUBm0V6WWaHaSWjYgYu8D9ItpAqm

        System.out.println(bCryptPasswordEncoder.matches("1234", "$2a$10$orqwzQShNGRFUmYyjoucNOQ98EUBm0V6WWaHaSWjYgYu8D9ItpAqm"));
    }

    @Test
    public void testUserMapper() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, "tester");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }
}
