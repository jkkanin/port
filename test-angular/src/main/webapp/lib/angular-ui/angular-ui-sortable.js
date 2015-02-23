/*
 jQuery UI Sortable plugin wrapper

 @param [ui-sortable] {object} Options to pass to $.fn.sortable() merged onto ui.config
 */
"use strict";
angular.module("ui.sortable", []).value("uiSortableConfig", {}).directive("uiSortable", ["uiSortableConfig", "$timeout", "$log",
    function(uiSortableConfig, $timeout, $log) {
        return {
            require: "?ngModel",
            link: function(scope, element, attrs, ngModel) {
                function combineCallbacks(first, second) {
                    return second && "function" == typeof second ? function(e, ui) {
                        first(e, ui), second(e, ui)
                    } : first
                }

                function hasSortingHelper(element, ui) {
                    var helperOption = element.sortable("option", "helper");
                    return "clone" === helperOption || "function" == typeof helperOption && ui.item.sortable.isCustomHelperUsed()
                }

                function isFloating(item) {
                    return /left|right/.test(item.css("float")) || /inline|table-cell/.test(item.css("display"))
                }

                function afterStop(e, ui) {
                    ui.item.sortable._destroy()
                }
                var savedNodes, opts = {}, directiveOpts = {
                        "ui-floating": undefined
                    }, callbacks = {
                        receive: null,
                        remove: null,
                        start: null,
                        stop: null,
                        update: null
                    }, wrappers = {
                        helper: null
                    };
                return angular.extend(opts, directiveOpts, uiSortableConfig, scope.$eval(attrs.uiSortable)), angular.element.fn && angular.element.fn.jquery ? (ngModel ? (scope.$watch(attrs.ngModel + ".length", function() {
                    $timeout(function() {
                        element.data("ui-sortable") && element.sortable("refresh")
                    })
                }), callbacks.start = function(e, ui) {
                    if ("auto" === opts["ui-floating"]) {
                        var siblings = ui.item.siblings();
                        angular.element(e.target).data("ui-sortable").floating = isFloating(siblings)
                    }
                    ui.item.sortable = {
                        model: ngModel.$modelValue[ui.item.index()],
                        index: ui.item.index(),
                        source: ui.item.parent(),
                        sourceModel: ngModel.$modelValue,
                        cancel: function() {
                            ui.item.sortable._isCanceled = !0
                        },
                        isCanceled: function() {
                            return ui.item.sortable._isCanceled
                        },
                        isCustomHelperUsed: function() {
                            return !!ui.item.sortable._isCustomHelperUsed
                        },
                        _isCanceled: !1,
                        _isCustomHelperUsed: ui.item.sortable._isCustomHelperUsed,
                        _destroy: function() {
                            angular.forEach(ui.item.sortable, function(value, key) {
                                ui.item.sortable[key] = undefined
                            })
                        }
                    }
                }, callbacks.activate = function() {
                    savedNodes = element.contents();
                    var placeholder = element.sortable("option", "placeholder");
                    if (placeholder && placeholder.element && "function" == typeof placeholder.element) {
                        var phElement = placeholder.element();
                        phElement = angular.element(phElement);
                        var excludes = element.find('[class="' + phElement.attr("class") + '"]');
                        savedNodes = savedNodes.not(excludes)
                    }
                }, callbacks.update = function(e, ui) {
                    if (!ui.item.sortable.received) {
                        ui.item.sortable.dropindex = ui.item.index();
                        var droptarget = ui.item.parent();
                        ui.item.sortable.droptarget = droptarget, ui.item.sortable.droptargetModel = droptarget.scope().$eval(droptarget.attr("ng-model")), element.sortable("cancel")
                    }
                    hasSortingHelper(element, ui) && !ui.item.sortable.received && "parent" === element.sortable("option", "appendTo") && (savedNodes = savedNodes.not(savedNodes.last())), savedNodes.appendTo(element), ui.item.sortable.received && (savedNodes = null), ui.item.sortable.received && !ui.item.sortable.isCanceled() && scope.$apply(function() {
                        ngModel.$modelValue.splice(ui.item.sortable.dropindex, 0, ui.item.sortable.moved)
                    })
                }, callbacks.stop = function(e, ui) {
                    !ui.item.sortable.received && "dropindex" in ui.item.sortable && !ui.item.sortable.isCanceled() ? scope.$apply(function() {
                        ngModel.$modelValue.splice(ui.item.sortable.dropindex, 0, ngModel.$modelValue.splice(ui.item.sortable.index, 1)[0])
                    }) : "dropindex" in ui.item.sortable && !ui.item.sortable.isCanceled() || hasSortingHelper(element, ui) || savedNodes.appendTo(element), savedNodes = null
                }, callbacks.receive = function(e, ui) {
                    ui.item.sortable.received = !0
                }, callbacks.remove = function(e, ui) {
                    "dropindex" in ui.item.sortable || (element.sortable("cancel"), ui.item.sortable.cancel()), ui.item.sortable.isCanceled() || scope.$apply(function() {
                        ui.item.sortable.moved = ngModel.$modelValue.splice(ui.item.sortable.index, 1)[0]
                    })
                }, wrappers.helper = function(inner) {
                    return inner && "function" == typeof inner ? function(e, item) {
                        var innerResult = inner(e, item);
                        return item.sortable._isCustomHelperUsed = item !== innerResult, innerResult
                    } : inner
                }, scope.$watch(attrs.uiSortable, function(newVal) {
                    element.data("ui-sortable") && angular.forEach(newVal, function(value, key) {
                        return key in directiveOpts ? ("ui-floating" !== key || value !== !1 && value !== !0 || (element.data("ui-sortable").floating = value), void(opts[key] = value)) : (callbacks[key] ? ("stop" === key && (value = combineCallbacks(value, function() {
                            scope.$apply()
                        }), value = combineCallbacks(value, afterStop)), value = combineCallbacks(callbacks[key], value)) : wrappers[key] && (value = wrappers[key](value)), opts[key] = value, void element.sortable("option", key, value))
                    })
                }, !0), angular.forEach(callbacks, function(value, key) {
                    opts[key] = combineCallbacks(value, opts[key]), "stop" === key && (opts[key] = combineCallbacks(opts[key], afterStop))
                })) : $log.info("ui.sortable: ngModel not provided!", element), void element.sortable(opts)) : void $log.error("ui.sortable: jQuery should be included before AngularJS!")
            }
        }
    }
]);
