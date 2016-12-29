(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('RegionDeclarationController', RegionDeclarationController);

    RegionDeclarationController.$inject = ['AlertService','Declaration','region'];

    function RegionDeclarationController (AlertService, Declaration, region) {
        var vm = this;
        vm.regionId = region.regionId;
        vm.regionName = region.regionName;
        vm.declarations = [];
        vm.loadAll = loadAll;


        vm.loadAll();


        function loadAll() {
            console.log("ddd");
            Declaration.getAllDeclarationsByRegion({
                    IdRegion:vm.regionId
                }
                , onSuccess, onError);

        }

        function onSuccess(data, headers) {
            vm.declarations = data;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }
    }
})();
