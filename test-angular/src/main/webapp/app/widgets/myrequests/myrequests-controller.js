myRequests.controller('MyRequestsController', function($scope, MyRequestsService) {

	MyRequestsService.getMyRequests().success(function(data, status, headers, config) {
        $scope.ndata = data;
    });

    $scope.gridOptions = {
        data: 'ndata',
        enableColumnMenu: false,
        enableSorting: false,
        enableScrollbars: false,
        columnDefs: [{
            field: 'department',
            displayName: 'Dept'
        }, {
            field: 'request',
            displayName: 'Request'
        }, {
            field: 'requestedDate',
            displayName: 'Req. Date',
            cellClass: 'text-center'
        }, {
            field: 'dueDate',
            displayName: 'Due Date',
            cellClass: 'text-center'
        }, {
            field: 'status',
            displayName: 'Status'
        }]
    };
});