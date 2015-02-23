outstandingTasks.controller('OutstandingTasksController', function($scope, OutstandingTasksService) {

	OutstandingTasksService.getOutstandingTasks().success(function(data, status, headers, config) {
        $scope.ndata = data;
    });

    $scope.gridOptions = {
        data: 'ndata',
        enableColumnMenu: false,
        enableSorting: false,
        enableScrollbars: false,
        columnDefs: [{
            field: 'projectName',
            displayName: 'Project'
        }, {
            field: 'taskName',
            displayName: 'Task'
        }, {
            field: 'dueDate',
            displayName: 'Due Date',
            cellClass: 'text-center'
        }, {
            field: 'estimatedEffort',
            displayName: 'Est. Effort (hr)',
            cellClass: 'text-right'
        }, {
            field: 'actualEffort',
            displayName: 'Effort Spent (hr)',
            cellClass: 'text-right'
        }]
    };
});