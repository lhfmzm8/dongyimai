//内容分类服务层
app.service("TbContentCategoryService",function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.post("/TbContentCategoryController/findAll");
	}
	//分页 
	this.findPage=function(pageNum,pageSize,tbContentCategory){
		return $http.post("/TbContentCategoryController/findPage?pageNum="+pageNum+"&pageSize="+pageSize,tbContentCategory);
	}
	//查询实体
	this.findOne=function(id){
		return $http.post("/TbContentCategoryController/findOne?id="+id);
	}
	//增加、修改
	this.save=function (tbContentCategory) {
		let url;
		if(tbContentCategory.id==null){
			url="/TbContentCategoryController/add";
		}else {
			url="/TbContentCategoryController/update"
		}
		return $http.post(url,tbContentCategory);
	}
	//删除
	this.delete=function(ids){
		return $http.post("/TbContentCategoryController/delete?ids="+ids);
	}
});