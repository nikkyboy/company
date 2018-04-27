(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Product_MasterDetailController', Product_MasterDetailController);

    Product_MasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product_Master'];

    function Product_MasterDetailController($scope, $rootScope, $stateParams, previousState, entity, Product_Master) {
        var vm = this;

        vm.product_Master = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('practiseApp:product_MasterUpdate', function(event, result) {
            vm.product_Master = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
