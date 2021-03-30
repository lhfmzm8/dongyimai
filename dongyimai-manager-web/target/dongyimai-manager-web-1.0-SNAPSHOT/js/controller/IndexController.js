//控制层
app.controller("IndexController",function ($scope,$controller,IndexService) {
    //继承
    $controller("BaseController",{$scope:$scope});

    //搜索
    $scope.showName=function () {
        IndexService.showName().success(function (response) {
            $scope.loginName=response.loginName;
        })
    }
})