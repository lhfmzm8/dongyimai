app.controller("TbUserController", function ($scope, $controller, TbUserService) {

    $controller("BaseController", {$scope: $scope});

    $scope.entity = {};

    $scope.sendCode = function () {
        let phoneNumbers = $scope.entity.phone;
        if (phoneNumbers == null || phoneNumbers === "") {
            alert("请输入手机号")
        } else {
            TbUserService.sendCode(phoneNumbers).success(function (response) {
                alert(response.message);
            })
        }
    }

    $scope.register = function () {
        let password1 = $scope.entity.password;
        let password2 = $scope.password;
        if (password1 != null && password1 !== "") {
            if (password2 != null && password2 !== "") {
                if (password1 === password2) {
                    TbUserService.register($scope.entity, $scope.code).success(function (response) {
                        alert(response.message);
                    })
                } else {
                    alert("密码不一致");
                }
            } else {
                alert("请输入确认密码");
            }
        } else {
            alert("请输入密码");
        }
    }

})