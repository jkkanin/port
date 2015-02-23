package com.ofs.portal.dao;

import java.util.List;

import com.ofs.portal.model.Widget;

public interface UserDao {

    List<Widget> getWidgets(List<String> roles);
}
