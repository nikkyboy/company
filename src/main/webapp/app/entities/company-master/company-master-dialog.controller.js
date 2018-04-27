(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Company_MasterDialogController', Company_MasterDialogController);

    Company_MasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Company_Master'];

    function Company_MasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Company_Master) {
        var vm = this;

        vm.company_Master = entity;
        vm.clear = clear;
        vm.save = save;
        vm.company_Masters = [];
        loadAll();
        function loadAll() {
            Company_Master.query(function(result) {
                vm.company_Masters = result;
                vm.company_Master.company_id = result.length+1;
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
            if (vm.company_Master.id !== null) {
                Company_Master.update(vm.company_Master, onSaveSuccess, onSaveError);
            } else {
                Company_Master.save(vm.company_Master, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('practiseApp:company_MasterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
