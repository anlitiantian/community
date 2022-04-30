package com.mrliu.community.controller;

import com.mrliu.community.dto.AccessTokenDTO;
import com.mrliu.community.dto.GiteeUser;
import com.mrliu.community.dto.GithubUser;
import com.mrliu.community.mapper.UserMapper;
import com.mrliu.community.model.User;
import com.mrliu.community.provider.GiteeProvider;
import com.mrliu.community.provider.GithubProvider;
import com.mrliu.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-25 20:14
 **/
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private GiteeProvider giteeProvider;

    @Value("${gitee.client.id}")
    private String clientId;

    @Value("${gitee.client.secret}")
    private String clientSecret;

    @Value("${gitee.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        //1.获取access_token
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        //2.通过access_token获取用户信息
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);

        if (giteeUser != null && giteeUser.getId() != null) {
            User user = User.builder().token(UUID.randomUUID().toString())
                    .accountId(giteeUser.getId())
                    .name(giteeUser.getLogin())
                    .avatarUrl(giteeUser.getAvatarUrl())
                    .gmtCreate(System.currentTimeMillis()).build();
            user.setGmtModified(user.getGmtCreate());

            //数据库中可能有记录，有的话更新，没有直接插入
            userService.createOrUpdate(user);
            //向response保留一份cookie
            response.addCookie(new Cookie("qiyu_token", user.getToken()));
            // 登录成功，写cookie和session

            return "redirect:/";
        } else {
            // 登录失败，重新登录
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("qiyu_token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
