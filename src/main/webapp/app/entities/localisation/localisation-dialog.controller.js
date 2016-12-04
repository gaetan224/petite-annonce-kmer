(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('LocalisationDialogController', LocalisationDialogController);

    LocalisationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Localisation', 'User'];

    function LocalisationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Localisation, User) {
        var vm = this;

        vm.localisation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.localisation.id !== null) {
                Localisation.update(vm.localisation, onSaveSuccess, onSaveError);
            } else {
                Localisation.save(vm.localisation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('petiteAnnonceKmerApp:localisationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
