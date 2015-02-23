'use strict';

app.controller('LoginCtrl', function($scope, $location, LoginService, $window) {

    $scope.isAuthenticated = false;

    $scope.login = function () {

        $scope.user = {UserName: $scope.username, Password: $scope.password};
        if (!$scope.username || !$scope.password) {
            $scope.validationError = "Username and Password can not be empty";
            return;
        } else if ($scope.username && $scope.password) {

            LoginService.authenticate($scope.user)
                .success(function (data, status, headers, config) {

                    if (!data) return;

                    CashApp.User.Id = data.UserId;
                    CashApp.User.UserName = data.UserName;
                    CashApp.User.Name = data.FullName;
                    CashApp.User.Role = data.Role;
                    $window.sessionStorage.token = data.UserToken;
                    $scope.userData.Name = data.FullName;

                    $scope.isAuthenticated = true;
                    $location.path('/home');
                })
                .error(function (data, status) {
                    // Erase the token if the user fails to log in
                    delete $window.sessionStorage.token;
                    $scope.isAuthenticated = false;

                    // Handle login errors here
                    $scope.validationError = 'Error: Invalid username or password';
                })
        }
    };

    $scope.logout = function () {
        $scope.isAuthenticated = false;
        delete $window.sessionStorage.token;
    };
});
