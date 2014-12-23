var Ctrl = function($scope) {
    $scope.skills = [];

    $scope.skill = '';
    $scope.submit = function() {
        if (!!$scope.skill) {
            $scope.skills.push($scope.skill);
        }
        $scope.skill = '';
    };
};

var MySelectCtrl = function($scope)
{
  $scope.Model = [
    {
      id: 10001,
      MainCategory: '男',
      ProductName: '水洗T恤',
      ProductColor: '白'
    },
    {
      id: 10002,
      MainCategory: '女',
      ProductName: '圓領短袖',
      ProductColor: '黃'
    },
    {
      id: 10002,
      MainCategory: '女',
      ProductName: '圓領短袖',
      ProductColor: '黃'
    }];
}