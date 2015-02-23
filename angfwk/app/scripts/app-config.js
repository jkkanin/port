'use strict';

app.config([
    '$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
        //Enable cross domain calls
        /*$httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];*/

        $routeProvider
            .when('/', {
                templateUrl: 'partials/login.html'
            })
            .when('/login', {
                controller: 'LoginCtrl',
                templateUrl: 'partials/login.html'
            })
            .when('/home', {
                controller: 'MainCtrl',
                templateUrl: 'partials/main.html'
            })
            .otherwise({
                redirectTo: 'partials/login.html'
            });

        /*Register error provider that shows message on failed requests or redirects to login page on
         * unauthenticated requests*/
        $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
                return {
                    'responseError': function (rejection) {
                        var status = rejection.status;
                        var config = rejection.config;
                        var method = config.method;
                        var url = config.url;

                        if (status == 401) {
                            $location.path("/login");
                        } else {
                            $rootScope.error = method + " on " + url + " failed with status " + status;
                        }

                        return $q.reject(rejection);
                    }
                };
            }
        );

        /*Registers auth token interceptor, auth token is either passed by header or by query parameter
         * as soon as there is an authenticated user*/
        $httpProvider.interceptors.push(function ($q, $rootScope, $location, $window) {
            return {
                'request': function (config) {

                    config.headers = config.headers || {};
                    if ($window.sessionStorage.SessionKey)
                        config.headers.Authorization = 'ViewScheme' + $window.sessionStorage.SessionKey; //localStorageService.get('sessionKey');

                    return config || $q.when(config);
                }
            };
        });
    }
]).run(function ($rootScope, $location, $window) {

    delete $window.sessionStorage.SessionKey;

    $rootScope.$on( '$routeChangeStart', function(){
        // possible to have session key  ex. login to cash app, open new cash app tab
        if(!$window.sessionStorage.SessionKey || (!CashApp && !CashApp.User && !CashApp.User.Id)) {
            $location.path('/login');
        }
    });
});

//TODO
app.factory('apiHost', function() {
    var host = 'http://192.168.16.125:44000/API';
    console.log(location.href);
    if(location.href.indexOf('localhost:9001') > -1) {
        host = '//localhost:8080';
    }
    return host;
});