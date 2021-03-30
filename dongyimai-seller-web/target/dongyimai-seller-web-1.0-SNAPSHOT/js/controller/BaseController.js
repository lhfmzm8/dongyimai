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
        let arr = JSON.parse(jsonStr);
        let str = "";
        for (let i = 0; i < arr.length; i++) {
            str += arr[i][key] + " ";
        }
        return str;
    }

    //根据属性名和对应的值在数组中查找完整对象
    //$scope.entity.goodsDesc.specificationItems
    //     [{
    //     "attributeName": "网络",
    //     "attributeValue": ["移动4G"]
    // }, {
    //     "attributeName": "机身内存",
    //     "attributeValue": ["128G", "64G", "16G"]
    // }]
    //参数：key:“attributeName”,val:"网络"
    // 返回：{"attributeName": "网络","attributeValue": ["移动4G"，"移动3G"]}
    $scope.searchObjectByKey = function (list, key, val) {
        for (var i = 0; i < list.length; i++) {
            //list[i][key]==val的意义：list[i][attributeName]="网络"
            if (list[i][key] == val) {
                return list[i];
            }
        }
        return null;
    }
})