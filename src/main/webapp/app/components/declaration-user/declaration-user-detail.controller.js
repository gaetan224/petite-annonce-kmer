/**
 * Created by admin on 30/12/2016.
 */


(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('DeclarationUserDetailController', DeclarationUserDetailController);

    DeclarationUserDetailController.$inject = ['declaration','$uibModalInstance','DataUtils'];

    function DeclarationUserDetailController (declaration, $uibModalInstance,DataUtils) {
        var vm = this;

        vm.clear = clear;
        vm.declaration = declaration;

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        vm.noWrapSlides = false;
        vm.isCollapsed = true;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
