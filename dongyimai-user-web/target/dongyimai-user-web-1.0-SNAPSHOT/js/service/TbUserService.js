app.service("TbUserService", function ($http) {

    this.sendCode = function (phoneNumbers) {
        return $http.post("/TbUserController/sendCode?phoneNumbers=" + phoneNumbers);
    }

    this.register = function (tbUser, code1) {
        return $http.post("/TbUserController/register?code1=" + code1, tbUser);
    }

    this.showName=function () {
        return $http.post("/TbUserController/showName");
    }
})