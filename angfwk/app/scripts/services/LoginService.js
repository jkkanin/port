'use strict';

app
    .factory('LoginService', function($http, AppConstants) {

        var service = {};
        service.authenticate = function(loginParams) {

            //return $http.post(AppConstants.serviceURL + AppConstants.url.login, loginParams);

            return $http ({
             method: 'POST',
             url: 'scripts/data/authenticate.json', //AppConstants.serviceURL + AppConstants.url.login,
             data: loginParams
            });
        };

        return service;
    });
