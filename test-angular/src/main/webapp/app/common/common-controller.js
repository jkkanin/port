commonModule.controller('CommonController', function($rootScope, $scope, localStorageService) {

	$scope.getUserDetails = function() {
		$scope.userName = $rootScope.user.loginName;
		$scope.loginName = $rootScope.user.loginName;
	};

	$rootScope.$watch('user', function() {
		$scope.getUserDetails();
	});
	
	$scope.logoutUrl = 'https://cas.objectfrontier.com/logout';
});