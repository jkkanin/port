leaveBalance.factory('LeaveBalanceService', function($rootScope, $http, $q) {

    var service = {};
    service.getLeaveBalance = function() {
        return $http({
            method: 'GET',
            url: '/ofs-portal/services/employees/' + $rootScope.user.loginName +'/leavebalance'
        });
    };
    return service;
});