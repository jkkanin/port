package com.ofs.srv.dao.inia;

import java.util.List;

import com.ofs.srv.model.ApproverInfo;

public interface EmployeeDao {

    List<ApproverInfo> getEmployeeApproverInfo(String loginName);

    List<String> getUserRoles(String loginName);
}
