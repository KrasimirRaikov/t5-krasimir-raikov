/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
describe('HistoryCtrl should', function () {

  var ctrl, growl, historyGateway, deffered, scope;


  beforeEach(function () {
    module('bank.history');

    inject(function ($controller, $q, $rootScope) {

      growl = {};
      historyGateway = {};
      scope = $rootScope.$new();
      ctrl = $controller('HistoryCtrl', {growl: growl, historyGateway: historyGateway});
      deffered = $q.defer();
    });
  });

  it('initialize first page content', function () {
    var content = ['1', '2', '3'];
    historyGateway.getPage = jasmine.createSpy('getPage').and.returnValue(deffered.promise);

    ctrl.initializeContent();

    deffered.resolve(content);
    scope.$digest();

    expect(historyGateway.getPage).toHaveBeenCalledWith(1);
    expect(ctrl.tracks).toEqual(content);
  });

  it('get next page', function () {
    var content = ['4', '5', '6'];
    historyGateway.getPage = jasmine.createSpy('getPage').and.returnValue(deffered.promise);
    ctrl.tracks = ['1', '2', '3'];
    ctrl.pageSize = 2;

    ctrl.next();

    deffered.resolve(content);
    scope.$digest();

    expect(historyGateway.getPage).toHaveBeenCalledWith(2);
    expect(ctrl.tracks).toEqual(content);
  });

  it('get previous page', function () {
    var content = ['1', '2', '3'];
    historyGateway.getPage = jasmine.createSpy('getPage').and.returnValue(deffered.promise);
    ctrl.tracks = ['4', '5', '6'];
    ctrl.pageSize = 2;
    ctrl.currentPage = 2;

    ctrl.previous();

    deffered.resolve(content);
    scope.$digest();

    expect(historyGateway.getPage).toHaveBeenCalledWith(1);
    expect(ctrl.tracks).toEqual(content);
  });
});