package com.ofs.srv.dao.hrm;

import java.util.List;

import com.ofs.srv.model.LeaveBalance;

public interface LeaveDao {

    List<LeaveBalance> getEmployeeLeaveInfo(String loginName);
}
