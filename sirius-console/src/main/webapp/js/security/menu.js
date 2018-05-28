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
	
	$('#menuDg').treegrid
	 (
	   {
	            url : ctx+'/secuMenu/queryAllMenusTg',
	            queryParams:queryParams,
				loadMsg:'数据装载中......',
				iconCls : 'icon-mylist',
				fit : true,
				fitColumns : true,
				border : true,
				nowrap: false,
				rownumbers: false,
				animate:true,
				collapsible:true,
				idField : 'id',
				treeField:'menuName',
				lines:true,
				columns : 
				[ [ 
				  {
					title : 'id',
					field : 'id',
					width : 10,
					hidden:true
				  },
				  {
					title : '菜单名称',
					field : 'menuName',
					width : 180
				  },
				  {
						title : '菜单排序',
						field : 'menuSeq',
						width : 40
				  },
				  {
						title : '关联资源名称',
						field : 'resourceName',
						width : 140
				  },
				  {
						title : '关联资源地址',
						field : 'resourceUrl',
						width : 260
				  },
				  {
					title : '操作',
					field : 'operation',
					width : 70,
					formatter : function(value, row, index) 
					            {
			        	  var opHtml = '<a title="修改" class="icon-myedit" style="display:inline-block;vertical-align:middle;width:16px;height:16px;" href="javascript:void(0);" onclick="openAddOrModifyMenuDialog(\'修改菜单\','+ JSON.stringify(row).replace(/"/g, '&quot;') + ');"></a>';
			        	  opHtml += '&nbsp&nbsp';
			              opHtml += '<a title="删除" class="icon-mydelete" style="display:inline-block;vertical-align:middle;width:16px;height:16px;" href="javascript:void(0);" onclick="removeMenuById(' + row.id + ');"></a>';
				          return opHtml;
					  }
				   } 
				] ],
				toolbar : 
				[ {
					text : '新增',
					iconCls : 'icon-add',
					handler : function() 
					{
						openAddOrModifyMenuDialog('新增菜单');
					}
				}, '-', {
					text : '展开',
					iconCls : 'icon-redo',
					handler : function() 
					{
						$('#menuDg').treegrid('expandAll');
					}
				}, '-', {
					text : '折叠',
					iconCls : 'icon-undo',
					handler : function() 
					{
						$('#menuDg').treegrid('collapseAll');
					}
				}, '-', {
					text : '刷新',
					iconCls : 'icon-reload',
					handler : function() 
					{
						$('#menuDg').treegrid('reload');
					}
				}
				]
	   
	      }
	   );
	
	removeMenuById = function(parentId){
		$.messager.confirm('系统确认', '确认删除吗？', function(r){
			if (r){
				var removeParams = {};
				removeParams[csrfParameter]=csrfToken;
				removeParams['parentId']=parentId;
				
				$.ajax({
					url:ctx+'/secuMenu/removeMenusByParentId',
					type:'post',
					data:removeParams,
					dataType:'json',
					error:function(){
						$.messager.alert('系统消息','删除菜单失败了！','error');
					},
					success:function(res){
						if(res && res.code==200){
							$('#menuDg').treegrid('remove',parentId);
							console.log('删除菜单成功');
						}else{
							$.messager.alert('系统消息','删除菜单失败！','error');	
						}
					}
				});
			}
		});
	};
	
	openAddOrModifyMenuDialog = function(title,currenMenu){
		if(currenMenu){
			$('#menuDialogForm').form('load',currenMenu);			
		}else{
			$('#menuDialogForm').clearForm();
		}
		
		$('#menuDiv').show().dialog({    
		    title: title,    
		    width: 400,    
		    height: 248,    
		    closed: false,    
		    modal: true,
		    buttons:[{
				text:'保存',
				handler:function(){
					addOrModifyMenu();
                }
			},{
				text:'取消',
				handler:function(){
					$('#menuDiv').dialog('close');
				}
			}]
		});    
		$('#menuDiv').dialog('refresh'); 
	};
	
	var addOrModifyMenu = function(){
		var options = { 
		        beforeSubmit:  function(){
		        }, 
		        success:function(data){
		        	if(data && data.code==200){
		        	    $('#menuDiv').dialog('close');
		        	    $.messager.alert('系统消息','处理成功！','info');
		        	    $('#menuDg').treegrid('load');
		        	}else{
		        		$.messager.alert('系统消息','处理失败！','error');
		        	}
		        },  
		        error:function(){
		        	$.messager.alert('系统消息','处理失败了！','error');
		        },
		        url: ctx+'/secuMenu/addOrModifyMenu',
		        type:'post', 
		        dataType:'json',        // 'xml', 'script', or 'json' (expected server response type) 
		        //clearForm: true        // clear all form fields after successful submit 
		        //resetForm: true        // reset the form after successful submit 
		        // $.ajax options can be used here too, for example: 
		        timeout:3000 
		}; 
		 
		$('#menuDialogForm').ajaxSubmit(options);
	};
	
	$('#parentId').combotree
	   (
	      {   
	         url: ctx+'/secuMenu/queryAllMenus',
	         queryParams:{
	    	    	'_csrf':csrfToken,
	    	 },
	         lines : true,
	         required: false,
	         cascadeCheck:false,
	         panelHeight:120
	      }
	   ); 
	
	var iconData = 
		[ 
		    {
				value : '',
				text : '默认'
			},
			{
				value : 'icon-myedit',
				text : 'icon-myedit'
			},
			{
				value : 'icon-mylist',
				text : 'icon-mylist'
			},
			{
				value : 'icon-mydelete',
				text : 'icon-mydelete'
			},
			{
				value : 'icon-myupload',
				text : 'icon-myupload'
			},
			{
				value : 'icon-myhome',
				text : 'icon-myhome'
			},
			{
				value : 'icon-myfolder',
				text : 'icon-myfolder'
			}, 
			{
				value : 'icon-mysave',
				text : 'icon-mysave'
			},
			{
				value : 'icon-myprivilege',
				text : 'icon-myprivilege'
			},
			{
				value : 'icon-myuser',
				text : 'icon-myuser'
			},
			{
				value : 'icon-myusers',
				text : 'icon-myusers'
			},
			{
				value : 'icon-myman',
				text : 'icon-myman'
			},
			{
				value : 'icon-mywoman',
				text : 'icon-mywoman'
			},
			{
				value : 'icon-mywindow',
				text : 'icon-mywindow'
			},
			{
				value : 'icon-myapp',
				text : 'icon-myapp'
			},
			{
				value : 'icon-mysetting',
				text : 'icon-mysetting'
			},
			{
				value : 'icon-myconfig',
				text : 'icon-myconfig'
			},
			{
				value : 'icon-mycomputer',
				text : 'icon-mycomputer'
			},
			{
				value : 'icon-mybag',
				text : 'icon-mybag'
			},
			{
				value : 'icon-add',
				text : 'icon-add'
			},
			{
				value : 'icon-edit',
				text : 'icon-edit'
			}, {
				value : 'icon-remove',
				text : 'icon-remove'
			}, {
				value : 'icon-save',
				text : 'icon-save'
			}, {
				value : 'icon-cut',
				text : 'icon-cut'
			}, {
				value : 'icon-ok',
				text : 'icon-ok'
			}, {
				value : 'icon-no',
				text : 'icon-no'
			}, {
				value : 'icon-cancel',
				text : 'icon-cancel'
			}, {
				value : 'icon-reload',
				text : 'icon-reload'
			}, {
				value : 'icon-search',
				text : 'icon-search'
			}, {
				value : 'icon-print',
				text : 'icon-print'
			}, {
				value : 'icon-help',
				text : 'icon-help'
			}, {
				value : 'icon-undo',
				text : 'icon-undo'
			}, {
				value : 'icon-redo',
				text : 'icon-redo'
			}, {
				value : 'icon-back',
				text : 'icon-back'
			}, {
				value : 'icon-sum',
				text : 'icon-sum'
			}, {
				value : 'icon-tip',
				text : 'icon-tip'
		    }, {
			value : 'icon-myfolder3',
			text : 'icon-myfolder3'
		    }, {
				value : 'icon-myfile',
				text : 'icon-myfile'
		    }, {
			value : 'icon-myuser2',
			text : 'icon-myuser2'
		    }, {
			value : 'icon-myfolder2',
			text : 'icon-myfolder2'
		    }, {
			value : 'icon-myfile2',
			text : 'icon-myfile2'
		    }, {
			value : 'icon-mylist2',
			text : 'icon-mylist2'
		    }, {
			value : 'icon-myapp2',
			text : 'icon-myapp2'
		    }, {
			value : 'icon-mysearch',
			text : 'icon-mysearch'
		    }, {
			value : 'icon-myinfo',
			text : 'icon-myinfo'
		    }, {
			value : 'icon-myxp',
			text : 'icon-myxp'
		    }
		    , {
		    	value : 'icon-1',
		    	text : 'icon-1'
		        }
		    , {
		    	value : 'icon-2',
		    	text : 'icon-2'
		        }
		    , {
		    	value : 'icon-3',
		    	text : 'icon-3'
		        }
		    , {
		    	value : 'icon-4',
		    	text : 'icon-4'
		        }
		    , {
		    	value : 'icon-5',
		    	text : 'icon-5'
		        }
		    , {
		    	value : 'icon-6',
		    	text : 'icon-6'
		        }
		    , {
		    	value : 'icon-7',
		    	text : 'icon-7'
		        }
		    , {
		    	value : 'icon-15',
		    	text : 'icon-15'
		        }
		    , {
		    	value : 'icon-16',
		    	text : 'icon-16'
		        }
		    , {
		    	value : 'icon-17',
		    	text : 'icon-17'
		        }
		    , {
		    	value : 'icon-18',
		    	text : 'icon-18'
		        }
		    , {
		    	value : 'icon-19',
		    	text : 'icon-19'
		        }
		    , {
		    	value : 'icon-20',
		    	text : 'icon-20'
		        }
		    , {
		    	value : 'icon-23',
		    	text : 'icon-23'
		        }
		    , {
		    	value : 'icon-24',
		    	text : 'icon-24'
		        }
		    , {
		    	value : 'icon-25',
		    	text : 'icon-25'
		        }
		    , {
		    	value : 'icon-31',
		    	text : 'icon-31'
		        }
		    , {
		    	value : 'icon-32',
		    	text : 'icon-32'
		        }
		    , {
		    	value : 'icon-33',
		    	text : 'icon-33'
		        }
		    , {
		    	value : 'icon-34',
		    	text : 'icon-34'
		        }
		    , {
		    	value : 'icon-35',
		    	text : 'icon-35'
		        }
		    , {
		    	value : 'icon-36',
		    	text : 'icon-36'
		        }
		    , {
		    	value : 'icon-37',
		    	text : 'icon-37'
		        }
		    , {
		    	value : 'icon-38',
		    	text : 'icon-38'
		        }
		    , {
		    	value : 'icon-39',
		    	text : 'icon-39'
		        }
		    
		    , {
		    	value : 'icon-40',
		    	text : 'icon-40'
		        }
		    , {
		    	value : 'icon-41',
		    	text : 'icon-41'
		        }
		    , {
		    	value : 'icon-42',
		    	text : 'icon-42'
		        }
		    , {
		    	value : 'icon-44',
		    	text : 'icon-44'
		        }
		    , {
		    	value : 'icon-45',
		    	text : 'icon-45'
		        }
		    , {
		    	value : 'icon-46',
		    	text : 'icon-46'
		        }
		    , {
		    	value : 'icon-47',
		    	text : 'icon-47'
		        }
		    , {
		    	value : 'icon-48',
		    	text : 'icon-48'
		        }
		    , {
		    	value : 'icon-49',
		    	text : 'icon-49'
		        }
		    , {
		    	value : 'icon-50',
		    	text : 'icon-50'
		        }
		    , {
		    	value : 'icon-51',
		    	text : 'icon-51'
		        }
		    , {
		    	value : 'icon-52',
		    	text : 'icon-52'
		        }
		    , {
		    	value : 'icon-53',
		    	text : 'icon-53'
		        }
		    , {
		    	value : 'icon-54',
		    	text : 'icon-54'
		        }
		    , {
		    	value : 'icon-56',
		    	text : 'icon-56'
		        }
		    , {
		    	value : 'icon-57',
		    	text : 'icon-57'
		        }
		    , {
		    	value : 'icon-58',
		    	text : 'icon-58'
		        }
			
		];
	
	function formatString(str) 
	{
		for ( var i = 0; i < arguments.length - 1; i++) 
		{
			str = str.replace("{" + i + "}", arguments[i + 1]);
		}
		return str;
	};
	
	$('#menuIcon').combobox
	   (
	        {
				data : iconData,
				panelHeight:300,
				formatter : function(v) 
				{
					return formatString('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>&nbsp;{1}', v.value, v.value);
				}
		     }
		);
	
	$('#resourceId').combogrid({    
	    panelWidth:450,  
	    mode:'remote',
	    striped:true,
	    pagination:true,
	    rownumbers:true,
	    pageSize:5,
	    pageList:[5,10,15],
	    idField:'id',    
	    textField:'resourceName',    
	    url:ctx+'/secuResource/queryPagedSecuResources', 
	    queryParams:{
	    	'_csrf':csrfToken,
	    	resourceType:'10'
	    },
	    columns:[[    
	        {field:'resourceName',title:'名称',width:200},    
	        {field:'resourceUrl',title:'路径',width:410} 
	    ]]    
	});
});

function cleanParent(){
	$('#parentId').combotree('clear');
}