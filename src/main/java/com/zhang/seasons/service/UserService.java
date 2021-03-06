package com.zhang.seasons.service;

import com.zhang.seasons.bean.User;
import com.zhang.seasons.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    // user 部分

    @Transactional
    public boolean insertUser(User user) {
        return userMapper.insert(user) == 1 && userRoleMapper.insert(user.getUid(), 1) == 1;
    }

    public boolean deleteUser(int uid) {
        return userMapper.delete(uid) == 1;
    }

    public boolean updateUserInfo(int uid, String name, String phone) {
        return userMapper.updateInfo(uid, name, phone) == 1;
    }

    public boolean updateUserPassword(int uid, String password, String salt) {
        return userMapper.updatePassword(uid, password, salt) == 1;
    }

    public boolean updateUserCoin(int uid, float addition) {
        return userMapper.updateCoin(uid, addition) == 1;
    }

    public boolean updateUserActive(int uid, boolean active) {
        return userMapper.updateActive(uid, active) == 1;
    }

    public void updateUserLoginTime(int uid, Timestamp loginTime) {
        userMapper.updateLoginTime(uid, loginTime);
    }

    public float selectUserCoin(int uid) {
        return userMapper.selectCoin(uid);
    }

    public boolean isNameExist(String name) {
        return userMapper.isNameExist(name) == 1;
    }

    public boolean isPhoneExist(String phone) {
        return userMapper.isPhoneExist(phone) == 1;
    }

    public boolean hasPhone(int uid) {
        return userMapper.selectPhone(uid) != null;
    }

    /**
     * 通过UID查找用户
     * @param uid UID
     * @param eraseLevel 擦除等级：1擦除密码等私密信息，2擦除密码信息和余额信息
     * @return 用户信息
     */
    public User selectUserByUid(int uid, int eraseLevel) {
        User user = userMapper.selectByUid(uid);
        if (user != null) {
            if (eraseLevel == 1) user.erasePassword();
            else if (eraseLevel == 2) user.eraseInfo();
        }
        return user;
    }

    /**
     * 通过用户名查找用户
     * @param name 用户名
     * @param eraseLevel 擦除等级：1擦除密码等私密信息，2擦除密码信息和余额信息
     * @return 用户信息
     */
    public User selectUserByName(String name, int eraseLevel) {
        User user = userMapper.selectByName(name);
        if (user != null) {
            if (eraseLevel == 1) user.erasePassword();
            else if (eraseLevel == 2) user.eraseInfo();
        }
        return user;
    }

    public User selectUserForLogin(String credential) {
        if (credential.length() < 11) {
            return userMapper.selectByName(credential);
        } else {
            return userMapper.selectByPhone(credential);
        }
    }

    public List<User> selectUserByActive(boolean active) {
        return userMapper.selectByActive(active);
    }

    public List<User> selectAllUser() {
        return userMapper.selectAll();
    }

    public List<Integer> selectUidByLoginTime(Timestamp start, Timestamp end) {
        return userMapper.selectUidByLoginTime(start, end);
    }

    // user_role 部分

    public boolean insertUserRole(int uid, int rid) {
        return userRoleMapper.insert(uid, rid) == 1;
    }

    public boolean deleteUserRole(int uid, int rid) {
        return userRoleMapper.delete(uid, rid) == 1;
    }

    public boolean deleteUserRoleByUid(int uid) {
        return userRoleMapper.deleteByUid(uid) > 0;
    }

    public List<Integer> selectRoleByUid(int uid) {
        return userRoleMapper.selectByUid(uid);
    }
}
