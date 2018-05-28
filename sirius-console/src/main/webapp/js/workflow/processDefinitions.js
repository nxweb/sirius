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
	
	$('#processDefinitionDataGrid').datagrid({    
	    url:ctx+'/processDefinition/quereyPagedProcessDefinitions',
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
	        {field:'name',title:'名称',width:160},
	        {field:'key',title:'key',width:160},
	        {field:'version',title:'版本',width:100},
	        {field:'deployTime',title:'部署时间',width:160,formatter: formatDateTimebox},
	        {field:'isLastVersion',title:'版本状态',width:100,
	         formatter: function(value,row,index){
	        	 if(value && value==1){
	        		 return '<span style="color:green;">最新版本</span>';
	        	 }else{
	        		 return '';
	        	 }
	         }
	        },
	    ]],
	    toolbar: '#processDefinitionDataGridToolbar'
	}); 
	
	deployProcessDefinitionDialog = function(){
		$('#processDefinitionDiv').dialog({    
		    title: '部署流程',    
		    width: 400,    
		    height: 200,    
		    closed: false,    
		    cache: false,    
		    modal: true,
		    buttons:[{
				text:'提交',
				handler:function(){
					deployProcess();
                }
			},{
				text:'取消',
				handler:function(){
					$('#processDefinitionDiv').dialog('close');
				}
			}]
		});    
		$('#processDefinitionDiv').dialog('refresh'); 
	};
	
	deployProcess = function(){
		var options = { 
		        beforeSubmit:  function(){
		        }, 
		        success:function(data){
		        	if(data && data.code==200){
		        		$('#processDefinitionDiv').dialog('close');
		        	    $.messager.alert('系统消息','处理成功！','info');
		        	    $('#processDefinitionDataGrid').datagrid('reload');
		        	}else{
		        		$.messager.alert('系统消息','处理失败！','error');
		        	}
		        },  
		        error:function(){
		        	$.messager.alert('系统消息','处理失败了！','error');
		        },
		        url: ctx+'/processDefinition/deploy?'+csrfParameter+'='+csrfToken,
		        type:'post', 
		        dataType:'json',        // 'xml', 'script', or 'json' (expected server response type) 
                //clearForm: true,        // clear all form fields after successful submit 
		        //resetForm: true        // reset the form after successful submit 
		        // $.ajax options can be used here too, for example: 
		        timeout:3000 
		};
		 
		$('#processDefinitionForm').ajaxSubmit(options);
	};
	
	viewDiagramImage = function(deploymentId,resourceName){
		parent.addTab('流程图','/processDefinition/viewDiagramImage?deploymentId='+deploymentId+'&resourceName='+resourceName);
	};
	
	generateDiagramImageProcessDefinition = function(){
		var selectedRows = $('#processDefinitionDataGrid').datagrid('getSelections');
		if(!selectedRows || selectedRows.length==0){
			$.messager.alert('系统消息','请选择数据！','warning');
			return;
		}else if(selectedRows.length>1){
			$.messager.alert('系统消息','请选择一条数据！','warning');
			return;
		}else{
			var row = selectedRows[0];
			
			parent.addTab('流程图','/processDefinition/viewDiagramImageProcessDefinitionPage?processDefinitionId='+row.id);
		}
	};
	
	clearSelected = function(){
		$('#processDefinitionDataGrid').datagrid('unselectAll');
	};
	
	clearProcessDefinitionCondition = function(){
		$('#queryProcessDefinitionForm').clearForm();
	};
	
	queryProcessDefinition = function(){
		var formData={};
		var formDataArr = $('#queryProcessDefinitionForm').serializeArray();
		$.each(formDataArr, function() {
			formData[this.name] = this.value;
		});
		$('#processDefinitionDataGrid').datagrid('load',formData);
	};
});