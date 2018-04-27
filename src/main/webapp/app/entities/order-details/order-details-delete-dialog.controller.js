(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Order_DetailsDeleteController',Order_DetailsDeleteController);

    Order_DetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Order_Details'];

    function Order_DetailsDeleteController($uibModalInstance, entity, Order_Details) {
        var vm = this;

        vm.order_Details = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Order_Details.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
