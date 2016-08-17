describe('HomeCtr should', function () {
  var ctrl, deferred, growl, homeGateway, scope;
  beforeEach(function () {
    module('bank.home');

    inject(function ($controller, $q, $rootScope) {
      growl = {};
      homeGateway = {};
      deferred = $q.defer();
      ctrl = $controller('HomeCtrl', {homeGateway: homeGateway, growl: growl});
      scope = $rootScope.$new();
    });
  });

  it('load the number of online users', function () {
    var onlineUsers = 10;
    homeGateway.countOnlineUsers = jasmine.createSpy('count online users').and.returnValue(deferred.promise);

    ctrl.loadNumberOfOnlineUsers();

    deferred.resolve(onlineUsers);
    scope.$digest();

    expect(homeGateway.countOnlineUsers).toHaveBeenCalled();
    expect(ctrl.onlineUsers).toEqual(onlineUsers);
  });

  it('unable to count the online users', function () {
    var errorMessage = 'unable to count online users';
    homeGateway.countOnlineUsers = jasmine.createSpy('count online users').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('growl error').and.returnValue(deferred.promise);

    ctrl.loadNumberOfOnlineUsers();

    deferred.reject(errorMessage);
    scope.$digest();

    expect(homeGateway.countOnlineUsers).toHaveBeenCalled();
    expect(growl.error).toHaveBeenCalledWith(errorMessage);
  });
});

