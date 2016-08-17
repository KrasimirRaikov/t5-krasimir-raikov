/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
describe('OperationsCtrl should', function () {
  var $q, scope, ctrl, deferred, accountGateway, growl;

  beforeEach(function () {
    module('bank.operations');
    inject(function ($controller, _$q_, _$rootScope_) {
      accountGateway = {};
      growl = {};
      $q = _$q_;
      scope = _$rootScope_.$new();
      ctrl = $controller('OperationsCtrl', {accountGateway: accountGateway, growl: growl});
      deferred = $q.defer();
    });
  });

  it('deposit some funds', function () {
    var response = {message: "message", currentBalance: 300};
    ctrl.balance = 100;
    accountGateway.depositFunds = jasmine.createSpy('deposit').and.returnValue(deferred.promise);
    growl.success = jasmine.createSpy("growl success");

    ctrl.deposit(ctrl.amount);

    deferred.resolve(response);
    scope.$digest();

    expect(ctrl.balance).toBe(300);
    expect(growl.success).toHaveBeenCalledWith(response.message);
  });


  it('fail to deposit funds', function () {
    var message = "failed to transfer funds";
    ctrl.balance = 100;
    accountGateway.depositFunds = jasmine.createSpy('deposit').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy("growl error");

    ctrl.deposit(ctrl.amount);

    deferred.reject(message);
    scope.$apply();

    expect(ctrl.balance).toBe(100);
    expect(growl.error).toHaveBeenCalledWith("Error: We were not able to deposit the funds. " + message);

  });

  it('withdraw some funds', function () {
    var response = {message: "message", currentBalance: 100};
    ctrl.balance = 300;
    accountGateway.withdrawFunds = jasmine.createSpy('withdraw').and.returnValue(deferred.promise);
    growl.success = jasmine.createSpy("growl success");

    ctrl.withdraw(ctrl.amount);

    deferred.resolve(response);
    scope.$digest();

    expect(ctrl.balance).toBe(100);
    expect(growl.success).toHaveBeenCalledWith(response.message);
  });

  it('fail to withdraw funds', function () {
    var message = "failed to withdraw funds";
    ctrl.balance = 100;
    accountGateway.withdrawFunds = jasmine.createSpy('withdraw').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy("growl error");

    ctrl.withdraw(ctrl.amount);

    deferred.reject(message);
    scope.$apply();

    expect(ctrl.balance).toBe(100);
    expect(growl.error).toHaveBeenCalledWith("Error: We were not able to withdraw the funds. " + message);

  });

  it('load current balance', function () {
    accountGateway.getCurrentBalance = jasmine.createSpy('get current balance').and.returnValue(deferred.promise);

    ctrl.loadCurrentBalance();

    deferred.resolve(123);
    scope.$apply();

    expect(ctrl.balance).toEqual(123);
  });

  it('could not load current balance', function () {
    accountGateway.getCurrentBalance = jasmine.createSpy('get current balance').and.returnValue(deferred.promise);

    ctrl.loadCurrentBalance();

    deferred.reject(125);
    scope.$apply();

    expect(ctrl.balance).toEqual(undefined);
  });

});
