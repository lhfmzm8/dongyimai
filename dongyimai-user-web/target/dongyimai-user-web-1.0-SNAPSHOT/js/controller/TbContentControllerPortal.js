app.controller("TbContentControllerPortal", function ($controller, $scope, $location, TbContentServicePortal) {

    $controller("BaseController", {$scope: $scope});

    $scope.findByCategoryId = function (categoryId) {
        $scope.tbContents = {};
        TbContentServicePortal.findByCategoryId(categoryId).success(function (response) {
            $scope.tbContents[categoryId] = response;
        })
    }

    $scope.search = function () {
        location.href = "/html/search.html#?keywords=" + $scope.keywords;
    }

})