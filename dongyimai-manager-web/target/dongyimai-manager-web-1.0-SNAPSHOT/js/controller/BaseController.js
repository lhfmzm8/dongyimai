app.controller("BaseController", function ($scope) {
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30],
        onChange: function () {
            $scope.findPage();
        }
    };
    $scope.keys = [];
    $scope.updateKeys = function ($event, key) {
        if ($event.target.checked) {
            $scope.keys.push(key);
        } else {
            let index = $scope.keys.indexOf(key);
            $scope.keys.splice(index, 1);
        }
    }
    //添加选项
    $scope.addOption = function () {
        if ($scope.entity.optionList === undefined || $scope.entity.optionList === null) {
            $scope.entity.optionList = [];
        }
        $scope.entity.optionList.push({});
    }

    //删除选项
    $scope.deleteOption = function (index) {
        $scope.entity.optionList.splice(index, 1)
    }

    $scope.jsonToString = function (jsonStr, key) {
        var arr = JSON.parse(jsonStr);
        var str="";
        for (var i = 0; i < arr.length; i++) {
            str += arr[i][key] + " ";
        }
        return str;
    }
})