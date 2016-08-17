/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

describe('RegisterCtrl should', function () {
  var ctrl, registerGateway, growl, deferred, scope, state;

  beforeEach(function () {
    module('bank.register');
    inject(function ($controller, $q, $rootScope) {
      registerGateway = {};
      growl = {};
      scope = $rootScope.$new();
      state = {};
      ctrl = $controller('RegisterCtrl', {registerGateway: registerGateway, growl: growl, $state:state});
      deferred = $q.defer();
    });
  });

  it('register a user', function () {
    var message = 'successful registration';
    registerGateway.registerUser = jasmine.createSpy('registerUser').and.returnValue(deferred.promise);
    growl.success = jasmine.createSpy('growl success message');
    state.go = jasmine.createSpy('go');

    ctrl.register('Krasimir', '123456', '123456');

    deferred.resolve(message);
    scope.$digest();

    expect(registerGateway.registerUser).toHaveBeenCalledWith('Krasimir', '123456', '123456');
    expect(growl.success).toHaveBeenCalledWith(message);
    expect(state.go).toHaveBeenCalledWith('login');
  });

  it('show message on taken username in registration', function () {
    var message = 'unable to register, username is taken';
    registerGateway.registerUser = jasmine.createSpy('registerUser').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('growl error message');

    ctrl.register('Krasimir', '123456', '123456');

    deferred.reject(message);
    scope.$digest();

    expect(growl.error).toHaveBeenCalledWith(message);
  });

  it('show message on passwords not matching during registration', function () {
    var message = 'passwords do not match';
    registerGateway.registerUser = jasmine.createSpy('registerUser').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('growl error message');

    ctrl.register('Krasimir', '123456', '654321');

    deferred.reject();
    scope.$digest();

    expect(growl.error).toHaveBeenCalledWith(message);
  });

});