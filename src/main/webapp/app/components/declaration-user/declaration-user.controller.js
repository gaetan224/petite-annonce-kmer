/**
 * Created by admin on 12/12/2016.
 * controler for the user creation of a new declration
 */



(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('DeclarationUserController', DeclarationUserController);

    DeclarationUserController.$inject = ['$scope', '$uibModalInstance', 'entity', 'DeclarationUser', 'User','Country','Region', 'Auth', 'LoginService', 'Principal'];

    function DeclarationUserController ($scope, $uibModalInstance, entity, DeclarationUser, User,Country,Region, Auth, LoginService,Principal) {
        var vm = this;

        vm.declaration = entity;
        vm.clear = clear;
        vm.onSaveSuccess = onSaveSuccess;
        vm.onSaveError = onSaveError;
        vm.generalSave = generalSave;


        // register field
        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;

        vm.registerAccount = {};
        vm.success = null;
        vm.isSave = false;;
        vm.currentAccount = null;
        vm.loginExist = false;


        vm.isAuthenticated = Principal.isAuthenticated();
       /*if(vm.isAuthenticated){
           Principal.identity().then(function(account) {
               vm.currentAccount = account;
           });
       }else {
           // verify that the typed login exist
           $scope.$watch('vm.registerAccount.login', function (typedLogin) {
               if (typedLogin) {
                   vm.loginExists(typedLogin);
               }
           });
       }
        function register () {
        console.log("SAVE register")
        if(!vm.isAuthenticated || !vm.loginExist) {
        if (vm.registerAccount.password !== vm.confirmPassword) {
        vm.doNotMatch = 'ERROR';
        }
        else {
        vm.registerAccount.langKey = $translate.use();
        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.errorEmailExists = null;

        Auth.createAccount(vm.registerAccount).then(function () {
        vm.success = 'OK';

        DeclarationUser.saveDeclarationUser(
        vm.declaration,
        vm.localisation,
        vm.registerAccount,
        [vm.images.principal, vm.images.image2, vm.images.image3]
        ).then(onSaveSuccess)
        .catch(onSaveError)

        }).catch(function (response) {
        vm.success = null;
        if (response.status === 400 && response.data === 'login already in use') {
        vm.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.data === 'e-mail address already in use') {
        vm.errorEmailExists = 'ERROR';
        } else {
        vm.error = 'ERROR';
        }
        });
        }
        }
        }
       */

        /**
         * verify if login exists
          * @param login
         */
       function loginExists(login) {

           User.loginExist({
               login:login
           },function (data) {
               vm.loginExist = true;
           },function (error) {
               vm.loginExist = false;
           });
       }





        vm.select1 = select1;
        vm.select2 = select2;
        vm.select3 = select3;
        vm.loadRegion = loadRegion;

         Country.query(function (data) {
             vm.countries = data;
             vm.localisation.country =vm.countries[0];
             vm.loadRegion();
        });
        vm.countryRegion = {};
        vm.localisation = {};




        vm.images = {};

        vm.images.principal = {}
        vm.images.image2 = {}
        vm.images.image3 = {}
        vm.hideUpload1 = false;
        vm.hideUpload2 = false;
        vm.hideUpload3 = false;


        function select1(file) {
            console.log(file);
            console.log(vm.images.principal);
            vm.hideUpload1 = true;
        }
        function select2(file) {
            console.log(file);
            vm.hideUpload2 = true;
        }

        function select3(file) {
            console.log(file);
            vm.hideUpload3 = true;
        }

     // cameroun cities by google places options
        vm.citiesOptions = {
            country: 'cm',
            types: '(cities)'
        };
        vm.citiesOptionsDetail ='';



        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


        function onSaveSuccess (data) {
            vm.isSave = true;
        }

        function onSaveError () {
            console.log("saved Error");
            vm.isSave = false;
        }

        function loadRegion() {
            if(vm.localisation.country) {
                vm.countryRegion = Region.getByCountry(
                    {countryId: vm.localisation.country.id}
                )
            }
        }



        function generalSave() {

            vm.isAuthenticated = Principal.isAuthenticated();
            if(vm.isAuthenticated){
                console.log("generalSave");
                DeclarationUser.saveDeclarationUser(
                    vm.declaration,
                    vm.localisation,
                    [vm.images.principal,vm.images.image2,vm.images.image3]
                ).then(onSaveSuccess)
                    .catch(onSaveError)

            }else{
                LoginService.open(true);
            }
        }

        /* if login success save the declaration ie onLoginSuccessSaveStartedDeclarationCreation*/
        $scope.$on("onLoginSuccessSaveStartedDeclarationCreation",function() {
            console.log("DDDD generalSave");
            vm.generalSave();
        });

    }
})();
