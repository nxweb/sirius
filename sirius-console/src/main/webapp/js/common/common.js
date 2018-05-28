/**
 * 给easyui tabs绑定双击事件
 */
$.extend($.fn.tabs.methods, {
    /**
     * 绑定双击事件
     * @param {Object} jq
     * @param {Object} caller 绑定的事件处理程序
     */
    bindDblclick: function(jq, caller){
        return jq.each(function(){
            var that = this;
            $(this).children("div.tabs-header").find("ul.tabs").undelegate('li', 'dblclick.tabs').delegate('li', 'dblclick.tabs', function(e){
                if (caller && typeof(caller) == 'function') {
                    var title = $(this).text();
                    var index = $(that).tabs('getTabIndex', $(that).tabs('getTab', title));
                    caller(index, title);
                }
            });
        });
    },
    /**
     * 解除绑定双击事件
     * @param {Object} jq
     */
    unbindDblclick: function(jq){
        return jq.each(function(){
            $(this).children("div.tabs-header").find("ul.tabs").undelegate('li', 'dblclick.tabs');
        });
    }
});

var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};   

function isCardID(sId){   
    var iSum=0 ;  
    var info="" ;  
    if(!(/^\d{17}(\d|x)$/i.test(sId) || /^\d{15}$/i.test(sId))){
    	return "你输入的身份证长度或格式错误";   
    }
    sId=sId.replace(/x$/i,"a");   
    if(aCity[parseInt(sId.substr(0,2))]==null) return "你的身份证地区非法"; 
    if(sId.length==18){
        sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
    }else if(sId.length==15){
    	sBirthday="19"+sId.substr(6,2)+"-"+Number(sId.substr(8,2))+"-"+Number(sId.substr(10,2));
    }
    var d=new Date(sBirthday.replace(/-/g,"/")) ;  
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法";   
    if(sId.length==18){
        for(var i = 17;i>=0;i --) {
        	iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
        }
        if(iSum%11!=1) return "你输入的身份证号非法";
    }
    return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女")   
}   

$.extend($.fn.validatebox.defaults.rules, {    
	idCode: {
		validator: function(value,param){    
			var flag= isCardID(value);  
            return flag==true?true:false;    
        },     
        message: '请输入正确的身份证号' 
    },
    mobile: {
		validator: function(value,element){    
			var length = value.length;
			var mobileReg = /^1[3|4|5|7|8][0-9]\d{8}$/;
			return (length == 11 && mobileReg.test(value));  
        },     
        message: '请输入正确的手机号码' 
    },
    charOrNum: {
		validator: function(value,element){    
			var test = /^[a-zA-Z0-9]*$/g;
			return isCharOrNum(value);  
        },     
        message: '请输入正确的密码' 
    },
    fixPhoneNum:{//固定电话号码校验 
    	validator: function (value) {
    		return /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(value);
    	},
    	message: '请输入正确的固定电话号码'
    },
    equals: {    
        validator: function(value,param){    
            return value == $(param[0]).val();    
        },    
        message: '密码和确认密码不同'   
    }
});

/**
 * 新增标签页
 * @param title
 * @param url
 * @param icon
 */
function addTab(title,url,icon)
{
	if($('#tabs').tabs('exists',title))
	{
		//已经存在，先删除
		$('#tabs').tabs('close',title);
	}
	
	$('#tabs').tabs('add',
	{
		title:title,
		content:createFrame(url),
		icon:icon,
		closable:true,
		cache:false
	}
    );
}

function createFrame(url)
{
	var frameHtml = '';
	if(url.indexOf('http') <= -1)
	{
		//url = baseUrl + url;
		if (url.indexOf('?') == -1)
		{
			url = ctx + url + '?timestamp=' + (new Date()).getTime();
		}
		else
		{
			url = ctx + url + '&timestamp=' + (new Date()).getTime();
		}
	}
	frameHtml = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return frameHtml;
}

Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1, // month  
        "d+": this.getDate(), // day  
        "h+": this.getHours(), // hour  
        "m+": this.getMinutes(), // minute  
        "s+": this.getSeconds(), // second  
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S": this.getMilliseconds()  
        // millisecond  
    }  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
            .substr(4 - RegExp.$1.length));  
    for (var k in o)  
        if (new RegExp("(" + k + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
    return format;  
}  
function formatDatebox(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {  
        dt = new Date(value);  
    }  
  
    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)  
}  

function formatDateTimebox(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {  
        dt = new Date(value);  
    }  
  
    return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)  
}

/**
 * 判断字符串是否为数字和字母组合
 * @param nubmer
 * @returns {Boolean}
 */
function isCharOrNum(str){  
     var re =/^[0-9a-zA-Z]*$/g;    
     if (re.test(str)){  
        return true;  
     } 
     return false;  
}  

/**
 * 加载数据字典
 * @param name
 * @returns
 */
function loadDataDictionary(dicName) {
	var dataDictionaryList = [];
	var dicParams = {};
	dicParams[csrfParameter]=csrfToken;
	dicParams['dicName']=dicName;
	$.ajax({
		'url' : ctx + '/sysDataDictionary/getSysDataDictionaryByName',
		'type' : 'post',
		'async' : false,
		'data':dicParams,
		'dataType':'json',
		'error' : function() {
		 },
		'success' : function(res) {
			if(res && res.code==200){
				dataDictionaryList = res.data;
			}
		}
	});
	return dataDictionaryList;
}

/**
 * 加载系统参数
 * @param name
 * @returns
 */
function loadSysParam(paramKey) {
	// 数据字典url
	var sysParamValue = '';
	var searchParams = {};
	searchParams[csrfParameter]=csrfToken;
	searchParams['paramKey']=paramKey;
	$.ajax({
		'url' : ctx + '/sysParam/getSysParamByKey',
		'type' : 'post',
		'async' : false,
		'data':searchParams,
		'dataType':'json',
		'error' : function() {
		 },
		'success' : function(res) {
			if(res && res.code==200){
				sysParamValue = res.data;
			}
		}
	});
	return sysParamValue;
}