package com.ofs.portal.service;

import java.util.List;

import com.ofs.portal.model.Widget;

public interface UserService {

    List<Widget> getWidgets(List<String> roles);
}