function add() {
	$.ligerDialog.open({
		height:600,
		width: 800,
		title : '新增会员表 ',
		url: __ctxPath + '/admin/pai/member/memberUser/edit.do', 
		showMax: true,
		showToggle: true,
		showMin: true,
		isResize: true,
		slide: false                
	}).max();
}
		
function edit(item)
{
	var selected = grid.getSelected();
	if(selected){
		editDialog(selected.id);
	}else{
		$.ligerDialog.tip({  title: '操作提示',content:'请选择要操作的记录'});
	}		
}

function editDialog(id) {
	$.ligerDialog.open({
		height:600,
		width: 800,
		title : '修改会员表 ',
		url: __ctxPath + '/admin/pai/member/memberUser/edit.do?id=' + id, 
		showMax: true,
		showToggle: true,
		showMin: true,
		isResize: true,
		slide: false                
	}).max();
}
		
function deleteRow()
{
	var selected = grid.getSelected();
	if(selected){
		$.ligerDialog.confirm('请确认是否删除该记录', function (yes)
				{
	            	if(yes){
	            		var url = __ctxPath + "/admin/pai/member/memberUser/delete.do";
	            		var params = "id="+selected.id;
	            		$.post(url,params,deleteResponse);            		            		
	            	}             
				});        	
	}else{
		$.ligerDialog.tip({  title: '操作提示',content:'请选择要删除的记录'});
	}                    
}		
        
function deleteResponse(responseText){        
	var result = JSON.parse(responseText);
	if(result.success){	
		var msg = getMsg(result.msgCode);				
		$.ligerDialog.success(msg);   
		grid.deleteSelectedRow(); 	 	
	} else {
		var msg = getMsg(result.msgCode);				
		$.ligerDialog.error(msg);
	}       	
}