dashboardModule.controller('DashboardController', function($scope, localStorageService) {

    var name = 'Dashboard';
    var model = localStorageService.get(name);

    if (!model) {
        // set default model
        model = {
            title: "Dashboard",
            structure: "6-6/6-3-3",
            rows: [{
                columns: [{
                    styleClass: "col-md-6",
                    widgets: [{
                        type: "leavebalance"
                    }]
                }, {
                    styleClass: "col-md-6",
                    widgets: [{
                        type: "outstandingtasks",
                    }]
                }, {
                    styleClass: "col-md-6",
                    widgets: [{
                        type: "myrequests",
                    }]
                }, {
                    styleClass: "col-md-3",
                    widgets: [{
                        type: "quicklinks",
                    }]
                }]
            }]
        };
    }

    $scope.name = name;
    $scope.model = model;
    $scope.collapsible = false;

    $scope.$on('adfDashboardChanged', function(event, name, model) {
        localStorageService.set(name, model);
    });
});