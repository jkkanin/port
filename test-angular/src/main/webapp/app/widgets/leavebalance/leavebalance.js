'use strict';

var leaveBalance = angular.module('LeaveBalance', ['adf.provider']);

leaveBalance.config(function(dashboardProvider) {
    dashboardProvider
        .widget('leavebalance', {
            title: 'Leave Balance',
            description: 'Display leave balances of the employee',
            templateUrl: 'app/widgets/leavebalance/leavebalance.html',
            controller: 'LeaveBalanceController'
        });
});