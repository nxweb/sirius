var ctx;
var csrfParameter;
var csrfHeader;
var csrfToken
jQuery(function($){
	ctx = $('#ctx').val();
	
	csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	csrfHeader = $("meta[name='_csrf_header']").attr("content");
	csrfToken = $("meta[name='_csrf']").attr("content");
	
	$('#userForm #isvalid').combobox({    
	    valueField:'id',    
	    textField:'text',
	    panelHeight:50,
	    data:[{'id':1,'text':'有效'},{'id':0,'text':'无效'}]
	});
	
	var queryParams = {};
	queryParams[csrfParameter]=csrfToken;
	
	$('#userDg').datagrid({    
	    url:ctx+'/secuUser/queryPagedSecuUsers',
	    queryParams:queryParams,
	    loadMsg:'数据装载中......',
		iconCls : 'icon-mylist',
		rownumbers : true,
		singleSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20, 30],
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : true,
	    columns:[[    
	        {field:'id',title:'id',width:100,checkbox:true},    
	        {field:'username',title:'用户名',width:120},
	        {field:'mobile',title:'手机',width:120},
	        {field:'telephone',title:'电话',width:120},
	        {field:'email',title:'邮箱',width:150},
	        {field:'isvalid',title:'有效性',width:70,
	         formatter:function(value){
	        	 if(value==1){
	        		 return '有效';
	        	 }else{
	        		 return '无效';
	        	 }
	         }}
	    ]],
	    toolbar: '#userDgToolbar'
	});  
	
	openAddOrModifyUserDialog=function(operationType){
		var title;
		var height=240;
		if(operationType=='add'){
			title = '新增用户';
			height=240;
			$('#password').validatebox('enableValidation');
			$('.passwordTr').show();
			$('.isvalidTr').hide();
			$('#userForm').form('clear');
			$('#userForm input[name="'+csrfParameter+'"]').val(csrfToken);
		}else if(operationType=='edit'){
			title = '修改用户';
			height=210;
			$('#password').validatebox('disableValidation');
			$('.passwordTr').hide();
			$('.isvalidTr').show();
			var selectedRows = $('#userDg').datagrid('getSelections');
			if(!selectedRows || selectedRows.length==0){
				$.messager.alert('系统消息','请选择数据！','warning');
				return;
			}else if(selectedRows.length>1){
				$.messager.alert('系统消息','请选择一条数据！','warning');
				return;
			}else{
				$('#userForm').form('load',selectedRows[0]);
			}
		}
		
		$('#userDiv').show().dialog({    
		    title: title,    
		    width: 320,    
		    height: height,    
		    closed: false,    
		    modal: true,
		    buttons:[{
				text:'提交',
				handler:function(){
					addOrModifyUser();
                }
			},{
				text:'取消',
				handler:function(){
					$('#userDiv').dialog('close');
				}
			}]
		});    
		$('#userDiv').dialog('refresh'); 
	};
	
	var addOrModifyUser = function(){
		var isFormValid = $('#userForm').form('validate');
		if(!isFormValid){
			return;
		}
		
		var options = { 
		        beforeSubmit:  function(){
		        }, 
		        success:function(data){
		        	if(data && data.code==200){
		        	    $('#userDiv').dialog('close');
		        	    $.messager.alert('系统消息','处理成功！','info');
		        	    $('#userDg').datagrid('load');
		        	}else{
		        		$.messager.alert('系统消息','处理失败！','error');
		        	}
		        },  
		        error:function(){
		        	$.messager.alert('系统消息','处理失败了！','error');
		        },
		        url: ctx+'/secuUser/addOrModifyUser',
		        type:'post', 
		        dataType:'json',        // 'xml', 'script', or 'json' (expected server response type) 
//		        clearForm: true,        // clear all form fields after successful submit 
		        //resetForm: true        // reset the form after successful submit 
		        // $.ajax options can be used here too, for example: 
		        timeout:3000 
		}; 
		 
		$('#userForm').ajaxSubmit(options);
	};
	
	authenticateUser = function(){
		var selectedRows = $('#userDg').datagrid('getSelections');
		var currentUserId=0;
		if(!selectedRows || selectedRows.length==0){
			$.messager.alert('系统消息','请选择数据！','warning');
			return;
		}else if(selectedRows.length>1){
			$.messager.alert('系统消息','请选择一条数据！','warning');
			return;
		}else{
			currentUserId=selectedRows[0].id;
		}
		
		$('#authenticateUserDg').datagrid({    
		    url:ctx+'/secuRole/queryPagedSecuRoles',
		    queryParams:queryParams,
		    idField:'id',
		    loadMsg:'数据装载中......',
			iconCls : 'icon-mylist',
			rownumbers : true,
			pagination : true,
			pagePosition : 'bottom',
			pageNumber:1,
			pageSize : 10,
			pageList : [ 10, 20, 30],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : true,
		    columns:[[    
		  	        {field:'id',title:'id',width:100,checkbox:true},    
			        {field:'roleName',title:'角色名称',width:100},
			        {field:'roleCode',title:'角色编码',width:100},
			        {field:'roleDesc',title:'角色说明',width:200}
			    ]],
			onLoadSuccess:function(data){
				var queryRoleParam = {};
				queryRoleParam[csrfParameter]=csrfToken;
				queryRoleParam['id']=currentUserId;
				$.ajax({
					url:ctx+'/secuUser/queryUserById',
					type:'post',
					data:queryRoleParam,
					dataType:'json',
					error:function(){
						$.messager.alert('系统消息','加载角色失败了！','error');
					},
					success:function(res){
						if(res && res.code==200 && res.data){
							var currentUserRoles = res.data.roles;
							$('#authenticateUserDg').datagrid('unselectAll');
							$.each(currentUserRoles,function(i,e){
								$('#authenticateUserDg').datagrid('selectRecord',e.id);
							});
						}else{
							$.messager.alert('系统消息','加载角色失败！','error');	
						}
					}
				});
			}
		});  
		
		$('#authenticateUserDiv').show().dialog({    
		    title: '用户关联角色',    
		    width: 620,    
		    height: 360,    
		    closed: false,    
		    draggable:true,
		    modal: true,
		    buttons:[{
				text:'提交',
				handler:function(){
					var checkedUserRolesParam = [];
					var allUserRolesParam = [];
					
					var checkedUserRoles = $('#authenticateUserDg').datagrid('getChecked');
					var allUserRoles = $('#authenticateUserDg').datagrid('getRows');
					
					if(checkedUserRoles && checkedUserRoles.length>0){
						$.each(checkedUserRoles,function(i,e){
							checkedUserRolesParam.push({'userId':currentUserId,'roleId':e.id});
						});
					}
					
					if(allUserRoles && allUserRoles.length>0){
						$.each(allUserRoles,function(i,e){
							allUserRolesParam.push({'userId':currentUserId,'roleId':e.id});
						});
					}
					
					var headers = {};
					headers[csrfHeader] = csrfToken;
					$.ajax({
						url:ctx+'/secuUser/addUserRoles',
						contentType:'application/json;charset=UTF-8',
						type:'post',
						headers: headers,
						data:JSON.stringify({'checkedUserRoles':checkedUserRolesParam,'allUserRoles':allUserRolesParam}),
						dataType:'json',
						error:function(){
							$.messager.alert('系统消息','关联失败了！','error');
						},
						success:function(data){
							if(data && data.code==200){
								$.messager.alert('系统消息','关联成功！');
								$('#authenticateUserDiv').dialog('close');
							}else{
								$.messager.alert('系统消息','关联失败！','error');	
							}
						}
					});
                }
			},{
				text:'取消',
				handler:function(){
					$('#authenticateUserDiv').dialog('close');
				}
			}]
		});    
		$('#authenticateUserDiv').dialog('refresh'); 
	};
	
	clearSelected = function(){
		$('#userDg').datagrid('unselectAll');
	}
	
	clearQueryCondition = function(){
		$('#queryConditionUserForm').form('clear');
		$('#queryConditionUserForm input[name="'+csrfParameter+'"]').val(csrfToken);
	};
	
	queryUsers = function(){
		var formData={};
		var formDataArr = $('#queryConditionUserForm').serializeArray();
		$.each(formDataArr, function() {
			formData[this.name] = this.value;
		});
		$('#userDg').datagrid('load',formData);
	};
});