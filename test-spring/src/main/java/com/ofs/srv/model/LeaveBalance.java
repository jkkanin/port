package com.ofs.srv.model;

public class LeaveBalance {

    private String leaveType;
    private int leavesPerYear;
    private float availableLeaves;
    private float approvedLeaves;
    private float pendingApproval;

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public int getLeavesPerYear() {
        return leavesPerYear;
    }

    public void setLeavesPerYear(int leavesPerYear) {
        this.leavesPerYear = leavesPerYear;
    }

    public float getAvailableLeaves() {
        return availableLeaves;
    }

    public void setAvailableLeaves(float availableLeaves) {
        this.availableLeaves = availableLeaves;
    }

    public float getApprovedLeaves() {
        return approvedLeaves;
    }

    public void setApprovedLeaves(float approvedLeaves) {
        this.approvedLeaves = approvedLeaves;
    }

    public float getPendingApproval() {
        return pendingApproval;
    }

    public void setPendingApproval(float pendingApproval) {
        this.pendingApproval = pendingApproval;
    }
}
