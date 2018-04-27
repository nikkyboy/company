(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Product_MasterDeleteController',Product_MasterDeleteController);

    Product_MasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Product_Master'];

    function Product_MasterDeleteController($uibModalInstance, entity, Product_Master) {
        var vm = this;

        vm.product_Master = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Product_Master.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
