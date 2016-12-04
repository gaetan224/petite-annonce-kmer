(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        vm.initMap = function() {
            // Prepare demo data
            var data = [
                {
                    "hc-key": "cm-es",
                    "value": 0
                },
                {
                    "hc-key": "cm-ad",
                    "value": 1
                },
                {
                    "hc-key": "cm-nw",
                    "value": 2
                },
                {
                    "hc-key": "cm-no",
                    "value": 3
                },
                {
                    "hc-key": "cm-ce",
                    "value": 20
                },
                {
                    "hc-key": "cm-ou",
                    "value": 5
                },
                {
                    "hc-key": "cm-en",
                    "value": 6
                },
                {
                    "hc-key": "cm-sw",
                    "value": 7
                },
                {
                    "hc-key": "cm-lt",
                    "value": 50
                },
                {
                    "hc-key": "cm-su",
                    "value": 9
                }
            ];



            // Initiate the chart
            $('#camerounmap').highcharts('Map', {

                title: {
                    text: 'Choisissez votre region'
                },

                subtitle: {
                    text: 'pour trouvez un objet'
                },

                chart:{
                        backgroundColor:'#f5f5f5'
                },

                plotOptions: {
                    series: {
                        point: {
                            events: {
                                click: function (e) {
                                    console.log(e.point.name)

                                }
                            }
                        }
                    }
                },


                colorAxis: {
                    min: 0,
                    max: 60,
                    maxColor: "#081c2a"
                },

                series: [{
                    data: data,
                    mapData: Highcharts.maps['countries/cm/cm-all'],
                    joinBy: 'hc-key',
                    name: "Nombre d'objet publier",
                    allowPointSelect: true,
                    states: {
                        hover: {
                            color: '#BADA55',
                            borderColor: 'black'
                        },
                        select: {
                            color: '#BADA55',
                            borderColor: 'black'
                        }
                    },
                    dataLabels: {
                        enabled: true,
                        format: '{point.name}'
                    }
                }, {
                    type: 'mappoint',
                    id: 'clicks',
                    data: []
                }],
                legend: {
                    enabled: false
                },
                exporting: {
                    enabled: false
                },
                credits: {
                enabled:false
                }


            });
        };


        vm.initMap();

    }
})();
