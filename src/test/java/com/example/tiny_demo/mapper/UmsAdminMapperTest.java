package com.example.tiny_demo.mapper;

import com.example.tiny_demo.modules.ums.mapper.UmsAdminRoleRMapper;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.mapper.UmsAdminMapper;
import com.example.tiny_demo.modules.ums.model.UmsAdminRoleR;
import com.example.tiny_demo.security.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UmsAdminMapperTest {

    @Value("${jwt.expiration}")
    private Long expiration;

//    @Autowired(required = false)
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UmsAdminMapper mapper;

    @Autowired
    public UmsAdminRoleRMapper adminRoleRMapper;

    @Test
    public void pickVal() {
        System.out.println(expiration);
        System.out.println(jwtTokenUtil.getExpiration());

    }

    @Test
    public void selectByKeywordTest() {
        String keyword = "%商品%";
        List<UmsAdminDO> umsAdminDOS = mapper.selectByKeyword(keyword);
        System.out.println(umsAdminDOS);
    }

    @Test
    @Transactional
    public void deleteTest() {
        adminRoleRMapper.delete(99);
    }

    @Test
//    @Transactional
    public void insertBatchTest() {
        UmsAdminRoleR adminRoleR = new UmsAdminRoleR();
        adminRoleR.setAdminId(99);
        adminRoleR.setRoleId(1);
        ArrayList<UmsAdminRoleR> list = new ArrayList<>();
        list.add(adminRoleR);
        adminRoleRMapper.insertBatch(list);
    }

        @Test
    public void selectListTest() {
        List<UmsAdminDO> umsAdminDOS = mapper.selectList(new UmsAdminDO());
        System.out.println(umsAdminDOS);
    }

    @Test
    public void insertTest() {
        UmsAdminDO umsAdminDO = new UmsAdminDO();
        umsAdminDO.setUsername("insert test");
        mapper.insert(umsAdminDO);
//        this.find_mybatis(UmsAdminMapper.class.getName() + ".insert", umsAdminDO);
    }

    @Test
    public void selectByIdBatchTest() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        List<UmsAdminDO> adminDOList = mapper.selectByIdBatch(list);
        System.out.println(adminDOList);
    }

    @Test
    public void updateByIdTest() {
        UmsAdminDO umsAdminDO = new UmsAdminDO();
        umsAdminDO.setId(20);
        umsAdminDO.setUsername("jerry");
        int c = mapper.updateByIdOrUsername(umsAdminDO);
        System.out.println(c);
    }

    // brcy
//    @Test
//    public void bcryTest() {
//        String password = "123";
//        // 加密
//        String encode = passwordEncoder.encode(password);
//        System.out.println(encode);
//        String oldPassword = "123";
//        // 校验，将原密码（未加密的密码）与加密密码进行核对，
//        boolean matches = passwordEncoder.matches(oldPassword, encode);
//        System.out.println(matches);
//    }
}
