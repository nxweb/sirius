var ctx;
jQuery(function($){
	ctx = $('#ctx').val();
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$('#menuTree').tree({
		lines:true,
		animate:false,
		url:ctx+'/secuMenu/queryAllMenus',
		queryParams:{
	    	'_csrf':token,
	    },
		onClick: function(node){
			var isLeaf = $('#menuTree').tree('isLeaf',node.target);
			var nodeAtt = node.attributes;
			if(isLeaf){
				if(nodeAtt && nodeAtt.url){
				    addTab(node.text,nodeAtt.url,'');
				}
			}else{
				$('#menuTree').tree('toggle',node.target);
			}
		},
		onLoadSuccess:function(node, data){
			$('#menuTree').tree('collapseAll');
		}
	});
	
	//右键菜单click
    $("#rightMenu").menu({
        onClick : function (item) {
        	var allTabs = $("#tabs").tabs('tabs');
            var allTabtitle = [];
            $.each(allTabs, function (i, e) {
                var opt = $(e).panel('options');
                if (opt.closable){
                    allTabtitle.push(opt.title);
                }
            });
            var curTabTitle = $(this).data("tabTitle");
            var curTabIndex = $("#tabs").tabs("getTabIndex", $("#tabs").tabs("getTab", curTabTitle));
            
        	var itemText = item.text;
        	if(itemText=='刷新'){
        		var panel = $("#tabs").tabs("getTab", curTabTitle).panel("refresh");
        	}else if(itemText=='关闭'){
        		$("#tabs").tabs("close", curTabTitle);
        	}else if(itemText=='关闭全部标签页'){
        		for(var i=0;i<allTabtitle.length;i++){
        			$("#tabs").tabs("close", allTabtitle[i]);
        		}
        	}else if(itemText=='关闭其他标签页'){
        		for(var i=0;i<allTabtitle.length;i++){
        			if(curTabTitle != allTabtitle[i]){
        			    $("#tabs").tabs("close", allTabtitle[i]);
        			}
        		}
        	}else if(itemText=='关闭右侧标签页'){
        		for(var i=curTabIndex+1;i<allTabtitle.length;i++){
        			$("#tabs").tabs("close", allTabtitle[i]);
        		}
        	}else if(itemText=='关闭左侧标签页'){
        		for(var i=0;i<curTabIndex;i++){
        			$("#tabs").tabs("close", allTabtitle[i]);
        		}
        	}
        }
    });
	
	$('#tabs').tabs({    
		pill:true,
		narrow:false,
		onContextMenu:function(e, title,index){
			e.preventDefault();
			$('#rightMenu').menu('show', {
                left: e.pageX,
                top: e.pageY
            }).data("tabTitle", title);
		}
	});  
	
	$('#tabs').tabs('bindDblclick', function(index, title){
		$('#tabs').tabs('close',title);
    });
	
	logout = function(){
		$('#loginOutForm').submit();
	}
});