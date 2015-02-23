app.service('AppConstants',[

    function () {

        var constants = {
            serviceURL: "http://192.168.16.125:44000/API",
            url: {

                login: "/Security/Login"
            }
        };

        return constants;
    }]);

