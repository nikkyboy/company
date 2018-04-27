(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Order_DetailsDetailController', Order_DetailsDetailController);

    Order_DetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Order_Details'];

    function Order_DetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, Order_Details) {
        var vm = this;

        vm.order_Details = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('practiseApp:order_DetailsUpdate', function(event, result) {
            vm.order_Details = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
