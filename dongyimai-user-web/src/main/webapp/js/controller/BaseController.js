app.controller("BaseController", function ($scope, TbUserService) {
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
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            str += arr[i][key] + " ";
        }
        return str;
    }

    $scope.keyEnter = function ($event) {
        if ($event.keyCode === 13 && $scope.keywords !== undefined && $scope.keywords !== "")
            location.href = "/html/search.html#?keywords=" + $scope.keywords;
    }

    $scope.login = function () {
        // let url = window.location.href;
        // window.location.href = "http://192.168.234.128:8866/cas/login?service=http://localhost:9004";
        TbUserService.login()
    }

    $scope.showName = function () {
        TbUserService.showName().success(function (response) {
            $scope.username = response.username;
        })
    }

})