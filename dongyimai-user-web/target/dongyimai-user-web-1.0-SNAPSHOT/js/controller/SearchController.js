app.controller("SearchController", function ($scope, $location, $controller, SearchService, CartService) {

        $controller("BaseController", {$scope: $scope})

        $scope.grouping = function () {
            $scope.searchEntity = {
                "keywords": "",
                "categories": [],
                "tbBrands": [],
                "specs": {},
                "price": "",
                "pageNum": 1,
                "pageSize": 10,
                "sortValue": "",
                "sortField": ""
            };
            $scope.searchEntity.keywords = $location.search()["keywords"];
            if ($scope.searchEntity.keywords !== "" && $scope.searchEntity.keywords != null) {
                SearchService.grouping($scope.searchEntity).success(function (response) {
                    $scope.entity = response;
                    $scope.buildPageLabel();
                })
            }
        }

        $scope.keywordsIsBrand = function () {
            let tbBrandList = $scope.entity.tbBrandList;
            let keywords = $scope.searchEntity.keywords;
            for (let i = 0; i < tbBrandList.length; i++) {
                if (keywords.includes(tbBrandList[i].text)) {
                    return false;
                }
            }
            return true;
        }

        $scope.search = function () {
            SearchService.search($scope.searchEntity).success(function (response) {
                $scope.entity = response;
                $scope.buildPageLabel();
            })
        }

        $scope.addSearchOption = function (key, value) {
            if (key === "categories" || key === "tbBrands") {
                $scope.searchEntity[key].push(value);
            } else if (key === "price") {
                $scope.searchEntity.price = value;
            } else {
                $scope.searchEntity.specs[key] = value;
            }
            $scope.searchEntity.pageNum = 1;
            $scope.search();
        }

        $scope.removeSearchOption = function (key, value) {
            if (key === "categories" || key === "tbBrands") {
                $scope.searchEntity[key].splice(value, 1);
            } else if (key === "price") {
                $scope.searchEntity.price = "";
            } else {
                delete $scope.searchEntity.specs[key];
            }
            $scope.searchEntity.pageNum = 1;
            $scope.search();
        }

        $scope.buildPageLabel = function () {
            //页码
            $scope.pageLabel = [];
            let totalPages = $scope.entity.totalPages;
            let pageNum = $scope.searchEntity.pageNum
            let begin = 1;
            let end = totalPages;
            if (end > 5) {
                if (pageNum < 3) {
                    end = 5;
                } else if (pageNum < end - 2) {
                    begin = pageNum - 2;
                    end = pageNum + 2;
                } else {
                    begin = end - 4;
                }
            }
            for (let i = begin; i <= end; i++) {
                $scope.pageLabel.push(i);
            }
            $scope.prePage = (pageNum > 1) ? (pageNum - 1) : 1;
            $scope.sufPage = (pageNum < totalPages) ? (pageNum + 1) : totalPages;
        }

        $scope.queryByPage = function (page) {
            //如果不在页面数字范围则不处理
            if (page > 0 && page <= $scope.entity.totalPages) {
                $scope.searchEntity.pageNum = page;
                $scope.search();
            } else {
                alert("超出总页数");
            }
        }

        $scope.sort = function (sortField, sortValue) {
            $scope.searchEntity.sortValue = sortValue;
            $scope.searchEntity.sortField = sortField;
            $scope.searchEntity.pageNum = 1;
            $scope.search();
        }

        $scope.toItem = function (dto) {
            location.href = dto.goodsId + ".html";
        }

        $scope.addToCart = function (dto) {
            CartService.add(dto.id, 1, $scope.loginStatus, $scope.username).success(function (response) {
                alert(response.message)
            })
        }
    }
)