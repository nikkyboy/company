(function() {
    'use strict';
    angular
        .module('practiseApp')
        .factory('Product_Master', Product_Master);

    Product_Master.$inject = ['$resource'];

    function Product_Master ($resource) {
        var resourceUrl =  'api/product-masters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
