(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Product_MasterDialogController', Product_MasterDialogController);

    Product_MasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Product_Master'];

    function Product_MasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Product_Master) {
        var vm = this;

        vm.product_Master = entity;
        vm.clear = clear;
        vm.save = save;
        vm.product_Masters = [];
           loadAll();
         function loadAll() {
            Product_Master.query(function(result) {
                vm.product_Masters = result;
                vm.product_Master.product_id = result.length+1;
                vm.searchQuery = null;
            });
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.product_Master.id !== null) {
                Product_Master.update(vm.product_Master, onSaveSuccess, onSaveError);
            } else {
                Product_Master.save(vm.product_Master, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('practiseApp:product_MasterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
