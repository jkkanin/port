package com.ofs.srv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ofs.srv.config.HRMDSTxManager;
import com.ofs.srv.config.IniaDSTxManager;
import com.ofs.srv.dao.hrm.LeaveDao;
import com.ofs.srv.dao.inia.EmployeeDao;
import com.ofs.srv.model.ApproverInfo;
import com.ofs.srv.model.LeaveBalance;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private LeaveDao leaveDao;

    @Override
    @IniaDSTxManager
    public List<ApproverInfo> getEmployeeApproverInfo(String loginName) {
        return employeeDao.getEmployeeApproverInfo(loginName);
    }

    @Override
    @HRMDSTxManager
    public List<LeaveBalance> getEmployeeLeaveInfo(String loginName) {
        return leaveDao.getEmployeeLeaveInfo(loginName);
    }

	@Override
	@IniaDSTxManager
    public List<String> getUserRoles(String loginName) {
		return employeeDao.getUserRoles(loginName);
    }
}
