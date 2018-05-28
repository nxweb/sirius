var ctx;
var csrfParameter;
var csrfHeader;
var csrfToken
jQuery(function($){
	ctx = $('#ctx').val();
	
	csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	csrfHeader = $("meta[name='_csrf_header']").attr("content");
	csrfToken = $("meta[name='_csrf']").attr("content");
	
	var queryParams = {};
	queryParams[csrfParameter]=csrfToken;
	
	$('#roleDg').datagrid({    
	    url:ctx+'/secuRole/queryPagedSecuRoles',
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
	        {field:'roleName',title:'角色名称',width:100},
	        {field:'roleCode',title:'角色编码',width:100},
	        {field:'roleDesc',title:'角色说明',width:200}
	    ]],
	    toolbar: '#roleDgToolbar'
	});  
	
	addOrModifyRoleDialog=function(operationType){
		var title;
		if(operationType=='add'){
			title = '新增角色';
			$('#roleForm').form('clear');
			$('#roleForm input[name="'+csrfParameter+'"]').val(csrfToken);
		}else if(operationType=='edit'){
			title = '修改角色';
			var selectedRows = $('#roleDg').datagrid('getSelections');
			if(!selectedRows || selectedRows.length==0){
				$.messager.alert('系统消息','请选择数据！','warning');
				return;
			}else if(selectedRows.length>1){
				$.messager.alert('系统消息','请选择一条数据！','warning');
				return;
			}else{
				$('#roleForm').form('load',selectedRows[0]);
			}
		}
		
		$('#roleDiv').show().dialog({    
		    title: title,    
		    width: 320,    
		    height: 220,    
		    closed: false,    
		    modal: true,
		    buttons:[{
				text:'提交',
				handler:function(){
					addOrModifyRole();
                }
			},{
				text:'取消',
				handler:function(){
					$('#roleDiv').dialog('close');
				}
			}]
		});    
		$('#roleDiv').dialog('refresh'); 
	}
	
	removeRole = function(){
		var selectedRows = $('#roleDg').datagrid('getSelections');
		var roleId=0;
		if(!selectedRows || selectedRows.length==0){
			$.messager.alert('系统消息','请选择数据！','warning');
			return;
		}else if(selectedRows.length>1){
			$.messager.alert('系统消息','请选择一条数据！','warning');
			return;
		}else{
			roleId=selectedRows[0].id;
		}
		
		$.messager.confirm('系统确认', '确认删除吗？', function(r){
			if (r){
				var removeParams = {};
				removeParams[csrfParameter]=csrfToken;
				removeParams['roleId']=roleId;
				
				$.ajax({
					url:ctx+'/secuRole/removeRoleById',
					type:'post',
					data:removeParams,
					dataType:'json',
					error:function(){
						$.messager.alert('系统消息','删除角色失败了！','error');
					},
					success:function(res){
						if(res && res.code==200){
							$('#resourceDg').datagrid('reload');
							console.log('删除角色成功');
						}else{
							$.messager.alert('系统消息','删除角色失败了！','error');	
						}
					}
				});
			}
		});
	};
	
	authenticateRole = function(){
		var selectedRows = $('#roleDg').datagrid('getSelections');
		var roleId=0;
		if(!selectedRows || selectedRows.length==0){
			$.messager.alert('系统消息','请选择数据！','warning');
			return;
		}else if(selectedRows.length>1){
			$.messager.alert('系统消息','请选择一条数据！','warning');
			return;
		}else{
			roleId=selectedRows[0].id;
		}
		
		$('#authenticateRoleDg').datagrid({    
		    url:ctx+'/secuResource/queryPagedSecuResources',
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
//			fit : true,
//			fitColumns : false,
			nowrap : false,
			border : true,
		    columns:[[    
		  	        {field:'id',title:'id',width:100,checkbox:true},    
			        {field:'resourceName',title:'名称',width:160},
			        {field:'resourceType',title:'类型',width:80,
			         formatter:function(value){
			        	 if(value==10){
			        		 return '菜单';
			        	 }else if(value==20){
			        		 return '操作';
			        	 }
			         }
			        },
			        {field:'resourceUrl',title:'路径',width:240},
			        {field:'resourceDesc',title:'说明',width:160}
			    ]],
			onLoadSuccess:function(data){
				var queryRoleParam = {};
				queryRoleParam[csrfParameter]=csrfToken;
				queryRoleParam['id']=roleId;
				$.ajax({
					url:ctx+'/secuRole/queryRoleById',
					type:'post',
					data:queryRoleParam,
					dataType:'json',
					error:function(){
						$.messager.alert('系统消息','加载角色失败了！','error');
					},
					success:function(res){
						if(res && res.code==200 && res.data){
							var currentRoleResources = res.data.resources;
							$('#authenticateRoleDg').datagrid('unselectAll');
							$.each(currentRoleResources,function(i,e){
								$('#authenticateRoleDg').datagrid('selectRecord',e.id);
							});
						}else{
							$.messager.alert('系统消息','加载角色失败！','error');	
						}
					}
				});
			}
		});  
		
		$('#authenticateRoleDiv').show().dialog({    
		    title: '角色关联资源',    
		    width: 720,    
		    height: 430,    
		    closed: false,    
		    draggable:true,
		    modal: true,
		    buttons:[{
				text:'确定',
				handler:function(){
					$.messager.progress(); 
					var allRoleResources = [];
					var checkedRoleResources = [];
					var allResources = $('#authenticateRoleDg').datagrid('getRows');
					var checkedResources = $('#authenticateRoleDg').datagrid('getChecked');
					
					if(allResources && allResources.length>0){
						$.each(allResources,function(i,e){
							allRoleResources.push({'roleId':roleId,'resourceId':e.id});
						});
					}
					if(checkedResources && checkedResources.length>0){
						$.each(checkedResources,function(i,e){
							checkedRoleResources.push({'roleId':roleId,'resourceId':e.id});
						});
					}
					
					var headers = {};
					headers[csrfHeader] = csrfToken;
					$.ajax({
						url:ctx+'/secuRole/addRoleResources',
						contentType:'application/json;charset=UTF-8',
						type:'post',
						headers: headers,
						data:JSON.stringify({'checkedRoleResources':checkedRoleResources,
							                 'allRoleResources':allRoleResources}),
						dataType:'json',
						error:function(){
							$.messager.progress('close');
							$.messager.alert('系统消息','关联失败了！','error');
						},
						success:function(data){
							$.messager.progress('close');
							if(data && data.code==200){
								$.messager.alert('系统消息','关联成功！');
								$('#authenticateRoleDiv').dialog('close');
							}else{
								$.messager.alert('系统消息','关联失败！','error');	
							}
						}
					});
                }
			},{
				text:'取消',
				handler:function(){
					$('#authenticateRoleDiv').dialog('close');
				}
			}]
		});    
		$('#authenticateRoleDiv').dialog('refresh'); 
	};
	
	var addOrModifyRole = function(){
		var options = { 
		        beforeSubmit:  function(){
		        }, 
		        success:function(data){
		        	if(data && data.code==200){
		        	    $('#roleDiv').dialog('close');
		        	    $.messager.alert('系统消息','处理成功！','info');
		        	    $('#roleDg').datagrid('reload');
		        	}else{
		        		$.messager.alert('系统消息','处理失败！','error');
		        	}
		        },  
		        error:function(){
		        	$.messager.alert('系统消息','处理失败了！','error');
		        },
		        url: ctx+'/secuRole/addOrModifyRole',
		        type:'post', 
		        dataType:'json',        // 'xml', 'script', or 'json' (expected server response type) 
		        clearForm: true,        // clear all form fields after successful submit 
		        resetForm: true,        // reset the form after successful submit 
		        // $.ajax options can be used here too, for example: 
		        timeout:3000 
		}; 
		 
		$('#roleForm').ajaxSubmit(options);
	};
	
	clearSelected = function(){
		$('#roleDg').datagrid('unselectAll');
	}
	
	clearQueryCondition = function(){
		$('#queryConditionRoleForm').form('clear');
		$('#queryConditionRoleForm input[name="'+csrfParameter+'"]').val(csrfToken);
	};
	
	queryRoles = function(){
		var formData={};
		var formDataArr = $('#queryConditionRoleForm').serializeArray();
		$.each(formDataArr, function() {
			formData[this.name] = this.value;
		});
		$('#roleDg').datagrid('load',formData);
	};
	
	queryPagedSecuResources = function(){
		var formData={};
		var formDataArr = $('#authenticateRoleForm').serializeArray();
		$.each(formDataArr, function() {
			formData[this.name] = this.value;
		});
		$('#authenticateRoleDg').datagrid('load',formData);
	};
});