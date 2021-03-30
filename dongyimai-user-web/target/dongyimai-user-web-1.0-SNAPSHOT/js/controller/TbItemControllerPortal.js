app.controller("TbItemControllerPortal", function ($scope, $controller, CartService) {

        $controller("BaseController", {$scope: $scope});

        $scope.specificationItems = {}

        $scope.addNum = function (num) {
            $scope.num = $scope.num + parseInt(num);
            if ($scope.num < 1) {
                $scope.num = 1;
            }
        }

        //用户选择规格
        $scope.selectSpecification = function (name, value) {
            $scope.specificationItems[name] = value;
            $scope.searchSUK();
        }

        //判断规格选项是否被选中
        $scope.isSelected = function (name, value) {
            return ($scope.specificationItems[name] === value) ? "selected" : "";
        }

        //加载SKU
        $scope.loadSKU = function () {
            $scope.sku = skuList[0];
            $scope.specificationItems = JSON.parse(JSON.stringify($scope.sku.spec));//深层拷贝
        }

        //选择规格更新SKU
        $scope.matchObject = function (map1, map2) {
            //假设
            //map1:2,3,4,5
            //map2:2,3,4,5,6
            for (let k in map1) {
                if (map1[k] !== map2[k]) {
                    return false;
                }
            }
            for (let k in map2) {
                if (map1[k] !== map2[k]) {
                    return false;
                }
            }
            return true;
        }

        $scope.searchSKU = function () {
            for (let i = 0; i < skuList.length; i++) {
                if ($scope.matchObject(skuList[i].spec, $scope.specificationItems)) {
                    $scope.sku = skuList[i];
                }
            }
        }

        $scope.addToCart = function () {
            CartService.add($scope.sku.id, $scope.num, $scope.loginStatus, $scope.username).success(function (response) {
                alert(response.message)
            })
        }
    }
)