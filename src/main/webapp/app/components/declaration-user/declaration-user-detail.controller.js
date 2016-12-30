/**
 * Created by admin on 30/12/2016.
 */


(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('DeclarationUserDetailController', DeclarationUserDetailController);

    DeclarationUserDetailController.$inject = ['declaration','$uibModalInstance'];

    function DeclarationUserDetailController (declaration, $uibModalInstance) {
        var vm = this;

        vm.clear = clear;

        console.log(declaration);

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
