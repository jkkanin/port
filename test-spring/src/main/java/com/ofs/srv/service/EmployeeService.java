package com.ofs.srv.service;

import java.util.List;

import com.ofs.srv.model.ApproverInfo;
import com.ofs.srv.model.LeaveBalance;

public interface EmployeeService {

    List<ApproverInfo> getEmployeeApproverInfo(String loginName);

    List<LeaveBalance> getEmployeeLeaveInfo(String loginName);

    List<String> getUserRoles(String loginName);
}
