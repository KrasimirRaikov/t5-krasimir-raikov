/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

angular.module('bank.operations', [
    'ui.router',
    'plusOne',
    'bank.http'
])


    .config(function config($stateProvider) {
        $stateProvider.state('operations', {
            url: '/operations',
            views: {
                "main": {
                    controller: 'OperationsCtrl',
                    templateUrl: 'operations/operations.tpl.html'
                }
            },
            data: {pageTitle: 'Operations'}
        });
    })


    .controller('OperationsCtrl', function OperationsController($scope, httpRequest) {
        $scope.amount = 10;
        $scope.message = '';
        $scope.deposit = function () {
            httpRequest.post('depositcontroller', {amount:$scope.amount, operation:'Deposit'}).then(function (data) {
                $scope.message = data;
            }, function(data){
                $scope.message = 'Error: We were not able to deposit the funds. '+data;
            });
        };
    });
