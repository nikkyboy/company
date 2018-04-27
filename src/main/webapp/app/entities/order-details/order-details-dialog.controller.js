(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Order_DetailsDialogController', Order_DetailsDialogController);

    Order_DetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Order_Details','Product_Master','Company_Master'];

    function Order_DetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Order_Details,Product_Master,Company_Master) {
        var vm = this;

        vm.order_Details = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.product_Masters = [];
        vm.company_Masters = [];
        vm.order_Details.vat = 10;
                loadAll();

                function loadAll() {
                    Order_Details.query(function(result) {
                        vm.order_Details.order_id = result.length+1;
                        vm.searchQuery = null;
                    });
                    Product_Master.query(function(result) {
                                    vm.product_Masters = result;
                                    vm.searchQuery = null;
                                });
                     Company_Master.query(function(result) {
                                    vm.company_Masters = result;
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
            vm.order_Details.product_id = $scope.product.product_id;
            vm.order_Details.total = vm.order_Details.quantity*$scope.product.unit_Price;
            vm.order_Details.order_value = (vm.order_Details.quantity*$scope.product.unit_Price)+((vm.order_Details.vat/100)*(vm.order_Details.quantity*$scope.product.unit_Price));
            vm.isSaving = true;
            if (vm.order_Details.id !== null) {
                Order_Details.update(vm.order_Details, onSaveSuccess, onSaveError);
            } else {
                Order_Details.save(vm.order_Details, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('practiseApp:order_DetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.order_Date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
