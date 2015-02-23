"use strict";
var angularLocalStorage = angular.module("LocalStorageModule", []);
angularLocalStorage.provider("localStorageService", function () {
    this.prefix = "ls", this.storageType = "localStorage", this.cookie = {
        expiry: 30,
        path: "/"
    }, this.notify = {
        setItem: !0,
        removeItem: !1
    }, this.setPrefix = function (prefix) {
        this.prefix = prefix
    }, this.setStorageType = function (storageType) {
        this.storageType = storageType
    }, this.setStorageCookie = function (exp, path) {
        this.cookie = {
            expiry: exp,
            path: path
        }
    }, this.setStorageCookieDomain = function (domain) {
        this.cookie.domain = domain
    }, this.setNotify = function (itemSet, itemRemove) {
        this.notify = {
            setItem: itemSet,
            removeItem: itemRemove
        }
    }, this.$get = ["$rootScope", "$window", "$document",
        function ($rootScope, $window, $document) {
            var prefix = this.prefix,
                cookie = this.cookie,
                notify = this.notify,
                storageType = this.storageType,
                webStorage = $window[storageType];
            $document || ($document = document), "." !== prefix.substr(-1) && (prefix = prefix ? prefix + "." : "");
            var browserSupportsLocalStorage = function () {
                    try {
                        var supported = storageType in $window && null !== $window[storageType],
                            key = prefix + "__" + Math.round(1e7 * Math.random());
                        return supported && (webStorage.setItem(key, ""), webStorage.removeItem(key)), !0
                    } catch (e) {
                        return storageType = "cookie", $rootScope.$broadcast("LocalStorageModule.notification.error", e.message), !1
                    }
                }(),
                addToLocalStorage = function (key, value) {
                    if (!browserSupportsLocalStorage)
                        return $rootScope.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), notify.setItem && $rootScope.$broadcast("LocalStorageModule.notification.setitem", {
                            key: key,
                            newvalue: value,
                            storageType: "cookie"
                        }), addToCookies(key, value);
                    "undefined" == typeof value && (value = null);
                    try {
                        (angular.isObject(value) || angular.isArray(value)) && (value = angular.toJson(value)), webStorage.setItem(prefix + key, value), notify.setItem && $rootScope.$broadcast("LocalStorageModule.notification.setitem", {
                            key: key,
                            newvalue: value,
                            storageType: this.storageType
                        })
                    } catch (e) {
                        return $rootScope.$broadcast("LocalStorageModule.notification.error", e.message), addToCookies(key, value)
                    }
                    return !0
                },
                getFromLocalStorage = function (key) {
                    if (!browserSupportsLocalStorage)
                        return $rootScope.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), getFromCookies(key);
                    var item = webStorage.getItem(prefix + key);
                    return item && "null" !== item ? "{" === item.charAt(0) || "[" === item.charAt(0) ? angular.fromJson(item) : item : null
                },
                removeFromLocalStorage = function (key) {
                    if (!browserSupportsLocalStorage)
                        return $rootScope.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), notify.removeItem && $rootScope.$broadcast("LocalStorageModule.notification.removeitem", {
                            key: key,
                            storageType: "cookie"
                        }), removeFromCookies(key);
                    try {
                        webStorage.removeItem(prefix + key), notify.removeItem && $rootScope.$broadcast("LocalStorageModule.notification.removeitem", {
                            key: key,
                            storageType: this.storageType
                        })
                    } catch (e) {
                        return $rootScope.$broadcast("LocalStorageModule.notification.error", e.message), removeFromCookies(key)
                    }
                    return !0
                },
                getKeysForLocalStorage = function () {
                    if (!browserSupportsLocalStorage)
                        return $rootScope.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), !1;
                    var prefixLength = prefix.length,
                        keys = [];
                    for (var key in webStorage)
                        if (key.substr(0, prefixLength) === prefix)
                            try {
                                keys.push(key.substr(prefixLength))
                            } catch (e) {
                                return $rootScope.$broadcast("LocalStorageModule.notification.error", e.Description), []
                            }
                    return keys
                },
                clearAllFromLocalStorage = function (regularExpression) {
                    var regularExpression = regularExpression || "",
                        tempPrefix = prefix.slice(0, -1) + ".",
                        testRegex = RegExp(tempPrefix + regularExpression);
                    if (!browserSupportsLocalStorage)
                        return $rootScope.$broadcast("LocalStorageModule.notification.warning", "LOCAL_STORAGE_NOT_SUPPORTED"), clearAllFromCookies();
                    var prefixLength = prefix.length;
                    for (var key in webStorage)
                        if (testRegex.test(key))
                            try {
                                removeFromLocalStorage(key.substr(prefixLength))
                            } catch (e) {
                                return $rootScope.$broadcast("LocalStorageModule.notification.error", e.message), clearAllFromCookies()
                            }
                    return !0
                },
                browserSupportsCookies = function () {
                    try {
                        return navigator.cookieEnabled || "cookie" in $document && ($document.cookie.length > 0 || ($document.cookie = "test").indexOf.call($document.cookie, "test") > -1)
                    } catch (e) {
                        return $rootScope.$broadcast("LocalStorageModule.notification.error", e.message), !1
                    }
                },
                addToCookies = function (key, value) {
                    if ("undefined" == typeof value)
                        return !1;
                    if (!browserSupportsCookies())
                        return $rootScope.$broadcast("LocalStorageModule.notification.error", "COOKIES_NOT_SUPPORTED"), !1;
                    try {
                        var expiry = "",
                            expiryDate = new Date,
                            cookieDomain = "";
                        if (null === value ? (expiryDate.setTime(expiryDate.getTime() + -864e5), expiry = "; expires=" + expiryDate.toGMTString(), value = "") : 0 !== cookie.expiry && (expiryDate.setTime(expiryDate.getTime() + 24 * cookie.expiry * 60 * 60 * 1e3), expiry = "; expires=" + expiryDate.toGMTString()), key) {
                            var cookiePath = "; path=" + cookie.path;
                            cookie.domain && (cookieDomain = "; domain=" + cookie.domain), $document.cookie = prefix + key + "=" + encodeURIComponent(value) + expiry + cookiePath + cookieDomain
                        }
                    } catch (e) {
                        return $rootScope.$broadcast("LocalStorageModule.notification.error", e.message), !1
                    }
                    return !0
                },
                getFromCookies = function (key) {
                    if (!browserSupportsCookies())
                        return $rootScope.$broadcast("LocalStorageModule.notification.error", "COOKIES_NOT_SUPPORTED"), !1;
                    for (var cookies = $document.cookie && $document.cookie.split(";") || [], i = 0; i < cookies.length; i++) {
                        for (var thisCookie = cookies[i];
                            " " === thisCookie.charAt(0);)
                            thisCookie = thisCookie.substring(1, thisCookie.length);
                        if (0 === thisCookie.indexOf(prefix + key + "="))
                            return decodeURIComponent(thisCookie.substring(prefix.length + key.length + 1, thisCookie.length))
                    }
                    return null
                },
                removeFromCookies = function (key) {
                    addToCookies(key, null)
                },
                clearAllFromCookies = function () {
                    for (var thisCookie = null, prefixLength = prefix.length, cookies = $document.cookie.split(";"), i = 0; i < cookies.length; i++) {
                        for (thisCookie = cookies[i];
                            " " === thisCookie.charAt(0);)
                            thisCookie = thisCookie.substring(1, thisCookie.length);
                        var key = thisCookie.substring(prefixLength, thisCookie.indexOf("="));
                        removeFromCookies(key)
                    }
                },
                getStorageType = function () {
                    return storageType
                };
            return {
                isSupported: browserSupportsLocalStorage,
                getStorageType: getStorageType,
                set: addToLocalStorage,
                add: addToLocalStorage,
                get: getFromLocalStorage,
                keys: getKeysForLocalStorage,
                remove: removeFromLocalStorage,
                clearAll: clearAllFromLocalStorage,
                cookie: {
                    set: addToCookies,
                    add: addToCookies,
                    get: getFromCookies,
                    remove: removeFromCookies,
                    clearAll: clearAllFromCookies
                }
            }
        }
    ]
});
