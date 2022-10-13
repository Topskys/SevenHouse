package com.house.xxl.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.house.xxl.common.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        if (token==null){
            Result resultVO = new Result("请先登录");
            doResponse(response, resultVO);
        }else {
            try {
                //验证token
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("zzh123456");  //解析token的signingkey必须和生成token时设置密码一致
                //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);

                return true;
            }catch (Exception e){
                Result resultVO = new Result("登录已经过期，请重新登录");
                doResponse(response, resultVO);
            }
        }

        return false;
    }

    private void doResponse(HttpServletResponse response, Result resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        writer.println(s);
        writer.flush();
        writer.close();
    }

}
