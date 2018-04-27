(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Company_MasterDetailController', Company_MasterDetailController);

    Company_MasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Company_Master'];

    function Company_MasterDetailController($scope, $rootScope, $stateParams, previousState, entity, Company_Master) {
        var vm = this;

        vm.company_Master = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('practiseApp:company_MasterUpdate', function(event, result) {
            vm.company_Master = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
