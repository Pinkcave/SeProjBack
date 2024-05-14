package com.example.seprojback.service.impl;

import com.example.seprojback.domain.LoginUser;
import com.example.seprojback.entity.SysUserRole;
import com.example.seprojback.entity.User;
import com.example.seprojback.mapper.SysUserRoleMapper;
import com.example.seprojback.mapper.UserMapper;
import com.example.seprojback.model.ResponseResult;
import com.example.seprojback.service.LoginLogoutService;
import com.example.seprojback.utils.JwtUtil;
import com.example.seprojback.utils.RedisCache;
import com.example.seprojback.utils.TokenOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
public class LoginLogoutServiceImpl implements LoginLogoutService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getName(), user.getPwd());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId();
        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);

        //把完整的用户信息存入redis  userid作为key
        redisCache.setCacheObject("login:" + userId, loginUser);

        return new ResponseResult(200, "登录成功", map);
    }

    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户信息
//        UsernamePasswordAuthenticationToken authentication =
//                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        String userId = loginUser.getUser().getUserId();

        String userId = TokenOperation.getUserIdFromToken();


        //删除redis中的值
        redisCache.deleteObject("login:" + userId);

        return new ResponseResult(200, "注销成功");
    }

    @Override
    public ResponseResult register(User user) {
        Integer rendId =  10000 + new Random().nextInt(90000);
        user.setUserId(String.valueOf(rendId));
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        userMapper.insert(user);
        sysUserRoleMapper.insert(new SysUserRole(user.getUserId(), 2));

        return new ResponseResult(200, "注册成功");
    }
}
