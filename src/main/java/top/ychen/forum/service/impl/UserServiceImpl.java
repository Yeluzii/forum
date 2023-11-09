package top.ychen.forum.service.impl;


import top.ychen.forum.dao.UserDao;
import top.ychen.forum.domain.entity.User;
import top.ychen.forum.service.UserService;
import top.ychen.forum.util.CommonUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

/**
 * @author moqi
 */
public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDao();

    @Override
    public int register(User user) {
        user.setPwd(CommonUtil.MD5(user.getPwd()));
        user.setImg(getRandomImg());
        user.setRole(1);
        user.setCreateTime(LocalDateTime.now());
        try {
            return userDao.save(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public User login(String phone, String pwd) {
        String md5Pwd = CommonUtil.MD5(pwd);

        // 根据手机号和密码查询用户
        User user = userDao.findByPhoneAndPwd(phone, md5Pwd);

        // 判断用户是否存在
        if (user != null) {
            // 登录成功
            return user;
        } else {
            // 登录失败
            return null;
        }
    }


    /**
     * 放在 CDN 上的随机头像
     */
    private static final String[] HEAD_IMG = {
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
    };

    private String getRandomImg() {
        int size = HEAD_IMG.length;
        Random random = new Random();
        int index = random.nextInt(size);
        return HEAD_IMG[index];
    }
}
