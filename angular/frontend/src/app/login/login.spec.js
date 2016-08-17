describe('LoginCtrl tests', function () {

  var deferred, loginGateway, growl, cookies, currentUserProvider, state, scope;

  beforeEach(function () {
    module('bank.login');
    inject(function ($controller, $q, $rootScope) {
      httpRequest = {};
      deferred = $q.defer();
      loginGateway = {};
      growl = {};
      cookies = {};
      currentUserProvider = {};
      scope = $rootScope.$new();
      state = {};
      loginCtrl = $controller('LoginCtrl', {
        loginGateway: loginGateway,
        growl: growl,
        currentUserProvider: currentUserProvider,
        $state: state,
        $cookies: cookies
      });
    });
  });

  it('login, and get the balance', function () {
    loginGateway.login = jasmine.createSpy('login spy').and.returnValue(deferred.promise);
    cookies.put = jasmine.createSpy('store session spy');
    currentUserProvider.setCurrentUser = jasmine.createSpy('setCurrentUser');
    growl.success = jasmine.createSpy('growl success');
    location.path = jasmine.createSpy('location path');
    state.go = jasmine.createSpy('state go');

    var username = 'Krasimir';
    var password = '123456';

    var sessionid = 1;

    loginCtrl.login(username, password);

    deferred.resolve(sessionid);
    scope.$digest();

    expect(cookies.put).toHaveBeenCalledWith('sessionId', sessionid);
    expect(currentUserProvider.setCurrentUser).toHaveBeenCalledWith(username);
    expect(growl.success).toHaveBeenCalledWith("you have logged in successfully");
    expect(state.go).toHaveBeenCalledWith("home");
  });

  it('login, unsuccessfuly', function () {
    loginGateway.login = jasmine.createSpy('login spy').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('growl error');

    var username = 'Krasimir';
    var password = '123456';

    var message = 'no such user';

    loginCtrl.login(username, password);

    deferred.reject(message);
    scope.$digest();


    expect(growl.error).toHaveBeenCalledWith(message);
  });
});
