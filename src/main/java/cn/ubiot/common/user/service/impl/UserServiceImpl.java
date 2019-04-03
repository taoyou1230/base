package cn.ubiot.common.user.service.impl;

import cn.ubiot.common.user.entity.User;
import cn.ubiot.common.user.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    /**
     * 根据token获取User信息
     * @param token
     * @return User
     */
    public User getUser(String token){
        try {
            User user = new User();
            DecodedJWT jwt = JWT.decode(token);
            user.setUserId(jwt.getClaim("userId").asInt());
            user.setCompanyName(jwt.getClaim("companyName").asString());
            user.setIat(jwt.getClaim("iat").asLong());
            user.setExp(jwt.getClaim("exp").asLong());
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
