(function () {
    'use strict';

    angular
        .module('practiseApp')
        .controller('ReportsController', ReportsController);

    ReportsController.$inject = ['$scope', 'ReportsService','Order_Details','Product_Master','Company_Master'];

    function ReportsController($scope, ReportsService,Order_Details,Product_Master,Company_Master) {
        var vm = this;
         vm.order_Details = [];
         vm.product_Masters = [];
         vm.company_Masters = [];



            loadAll();

                 function loadAll() {
                    Order_Details.query(function(result) {
                        vm.order_Details = result;
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
    }
})();
