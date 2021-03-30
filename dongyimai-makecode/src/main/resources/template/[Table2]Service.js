//[comment]服务层
app.service("[Table2]Service",function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.post("/[Table2]Controller/findAll");
	}
	//分页 
	this.findPage=function(pageNum,pageSize,[table2]){
		return $http.post("/[Table2]Controller/findPage?pageNum="+pageNum+"&pageSize="+pageSize,[table2]);
	}
	//查询实体
	this.findOne=function([PrimaryKeyName]){
		return $http.post("/[Table2]Controller/findOne?[PrimaryKeyName]="+[PrimaryKeyName]);
	}
	//增加、修改
	this.save=function ([table2]) {
		return $http.post("/[Table2]Controller/save",[table2]);
	}
	//删除
	this.delete=function([PrimaryKeyName]s){
		return $http.post("/[Table2]Controller/delete?[PrimaryKeyName]s="+[PrimaryKeyName]s);
	}
});