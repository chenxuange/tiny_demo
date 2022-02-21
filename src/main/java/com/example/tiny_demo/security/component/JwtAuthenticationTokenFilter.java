package com.example.tiny_demo.security.component;

import com.example.tiny_demo.modules.ums.service.UmsAdminService;
import com.example.tiny_demo.security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证过滤器
 */
@Configuration
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断是否有认证请求头
        String authHeader = request.getHeader(tokenHeader);
        if(authHeader != null && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            // 从jwtToken中取出用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            LOGGER.info("checking username:{}", username);
            // TODO 这里先前设置的authentication无效，是什么原因
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                LOGGER.debug("authentication, {}", (Object) null);
                // 根据用户名查询并封装
                UserDetails userDetails = adminService.loadUserByUsername(username);
                if(jwtTokenUtil.validateToken(authToken, userDetails)) {
                    String token = jwtTokenUtil.generateToken(userDetails);
                    LOGGER.info("authenticated user:{}", username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // TODO 这里怎么回事
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 安全上下文中设置登录用户凭证
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    LOGGER.debug("authentication, {}", authentication);
                }
            }
        }
        // 放行, 若放行后
        filterChain.doFilter(request, response);
    }
}
