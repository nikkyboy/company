(function() {
    'use strict';
    angular
        .module('practiseApp')
        .factory('Order_Details', Order_Details);

    Order_Details.$inject = ['$resource', 'DateUtils'];

    function Order_Details ($resource, DateUtils) {
        var resourceUrl =  'api/order-details/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.order_Date = DateUtils.convertLocalDateFromServer(data.order_Date);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.order_Date = DateUtils.convertLocalDateToServer(copy.order_Date);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.order_Date = DateUtils.convertLocalDateToServer(copy.order_Date);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
