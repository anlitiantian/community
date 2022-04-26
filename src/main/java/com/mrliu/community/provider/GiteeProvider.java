package com.mrliu.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mrliu.community.dto.AccessTokenDTO;
import com.mrliu.community.dto.GiteeUser;
import com.mrliu.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-26 14:23
 **/
@Component
public class GiteeProvider {
    @Value("${gitee.accessToken.uri}")
    private String accessTokenUri;
    @Value("${gitee.user.info.uri}")
    private String userInfoUri;

    //获取access_token
    public String getAccessToken(AccessTokenDTO accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto), mediaType);
        Request request = new Request.Builder()
                .url(accessTokenUri)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            JSONObject jsonObject = JSONObject.parseObject(string);
            String access_token = (String) jsonObject.get("access_token");

            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取用户信息
    public GiteeUser getUser(String token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(userInfoUri + token)
                .header("Authorization", "token " + token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);

            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
