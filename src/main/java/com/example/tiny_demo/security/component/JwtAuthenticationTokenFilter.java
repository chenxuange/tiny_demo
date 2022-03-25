package com.example.tiny_demo.security.component;

import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * jwt登录认证过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private  final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;
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
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            // 从jwtToken中取出用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            System.out.println();
            logger.info("jwt checking user: {}", username);
            // TODO 这里先前设置的authentication无效，是什么原因
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 根据用户名查询并封装
                // TODO 每一次新的请求，都封装一次用户信息和资源
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 待删，测试token ------------------------------------
/*                UmsAdminDO umsAdminDO = new UmsAdminDO();
                umsAdminDO.setUsername(userDetails.getUsername());
                List<UmsAdminDO> list = adminMapper.selectList(umsAdminDO);
                Date operatorTime = list.get(0).getOperatorTime();
                Date createTimeFromToken = jwtTokenUtil.getCreateTimeFromToken(authToken);
                LOGGER.debug("operatorTime, {}", operatorTime);
                LOGGER.debug("createTimeFromToken, {}", createTimeFromToken);*/
                // ------------------------------------
                boolean forceInvalidFlag = jwtTokenUtil.forceInValid(authToken, userDetails);
                logger.debug("是否将token强制失效: {}", forceInvalidFlag);
                // 前一个判断是比较用户名和过期时间，后一个判断是否需要将token强制失效
                if (jwtTokenUtil.validateToken(authToken, userDetails) && !forceInvalidFlag) {
                    String token = jwtTokenUtil.generateToken(userDetails);
                    logger.info("authentication pass");
                    // 设置用户凭证，保存用户信息以及用户拥有的所有权限
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // details细节信息，记录了ip地址和sessionId的值. 实际上，因为关闭了sessionManager，这里实际没啥意义，此外，RemoteIpAddress也可以通过HttpServletRequest获取
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    logger.debug("details, {}", authentication.getDetails());
                    // 安全上下文中设置登录用户凭证
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("authorities of authentication, {}", authentication.getAuthorities());
                }
            }
        }
        // 放行
        filterChain.doFilter(request, response);
    }
}
