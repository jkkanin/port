package com.ofs.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ofs.portal.config.LocalDSTxManager;
import com.ofs.portal.dao.UserDao;
import com.ofs.portal.model.Widget;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @LocalDSTxManager
    public List<Widget> getWidgets(List<String> roles) {
        return userDao.getWidgets(roles);
    }

}