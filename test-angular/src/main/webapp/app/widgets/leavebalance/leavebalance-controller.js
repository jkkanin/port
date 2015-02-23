leaveBalance.controller('LeaveBalanceController', function($scope, LeaveBalanceService) {

    LeaveBalanceService.getLeaveBalance().success(function(data, status, headers, config) {
        $scope.ndata = data;
    });

    $scope.gridOptions = {
        data: 'ndata',
        enableColumnMenu: false,
        enableSorting: false,
        enableScrollbars: false,
        columnDefs: [{
            field: 'leaveType',
            displayName: 'Leave Type'
        }, {
            field: 'availableLeaves',
            displayName: 'Balance',
            cellClass: 'text-right'
        }, {
            field: 'pendingApproval',
            displayName: 'Pending Approval',
            cellClass: 'text-right'
        }]
    };
});