<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" uri="http://www.qifu.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>qifu</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="../common-f-inc.jsp"></jsp:include>


<!-- #################### codemirror #################### -->
<script src="<%=basePath%>/codemirror/lib/codemirror.js" type="text/javascript"></script>
<script src="<%=basePath%>/codemirror/addon/edit/matchbrackets.js"></script>
<script src="<%=basePath%>/codemirror/addon/hint/show-hint.js"></script>	
<script src="<%=basePath%>/codemirror/clike.js"></script>	
<link rel="stylesheet" type="text/css" href="<%=basePath%>/codemirror/doc/docs.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/codemirror/lib/codemirror.css" />		
<link rel="stylesheet" href="<%=basePath%>/codemirror/addon/hint/show-hint.css">	
		
<script src="<%=basePath%>/codemirror/mode/xml/xml.js"></script>
<script src="<%=basePath%>/codemirror/mode/javascript/javascript.js"></script>
<script src="<%=basePath%>/codemirror/mode/css/css.js"></script>
<script src="<%=basePath%>/codemirror/mode/htmlmixed/htmlmixed.js"></script>
<script src="<%=basePath%>/codemirror/addon/mode/multiplex.js"></script>
<script src="<%=basePath%>/codemirror/mode/htmlembedded/htmlembedded.js"></script>
<!-- #################### codemirror #################### -->


<style type="text/css">


</style>


<script type="text/javascript">

var javaEditor = null;

$( document ).ready(function() {
	
	javaEditor = CodeMirror.fromTextArea(document.getElementById("content"), {
		lineNumbers: true,
		matchBrackets: true,
		mode: 'text/x-java'
	});	
	
});

var msgFields = new Object();
msgFields['exprId'] 		= 'exprId';
msgFields['name'] 			= 'name';
msgFields['type'] 			= 'type';
msgFields['content']		= 'content';

var formGroups = new Object();
formGroups['exprId'] 		= 'form-group1';
formGroups['name'] 			= 'form-group1';
formGroups['type'] 			= 'form-group1';
formGroups['content']		= 'form-group3';

function updateSuccess(data) {
	clearWarningMessageField(formGroups, msgFields);
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		setWarningMessageField(formGroups, msgFields, data.checkFields);
		return;
	}
	parent.toastrInfo( data.message );
	clearUpdate();
}

function clearUpdate() {
	window.location=parent.getProgUrlForOid('CORE_PROG003D0002E', '${sysExpression.oid}');
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG003D0002E_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG003D0002E', '${sysExpression.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnUpdate();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0002E');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="exprId" id="exprId" value="sysExpression.exprId" maxlength="20" requiredFlag="Y" label="Id" placeholder="Enter Id" readonly="Y"></q:textbox>
		</div>
	</div>	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			
			<q:textbox name="name" id="name" value="sysExpression.name" maxlength="100" requiredFlag="Y" label="Name" placeholder="Enter name"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="typeMap" name="type" id="type" value="sysExpression.type" label="Type" requiredFlag="Y"></q:select>
		</div>
	</div>		
</div>
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textarea name="description" value="sysExpression.description" id="description" label="Description" rows="3" placeholder="Enter descripnion"></q:textarea>
		</div>
	</div>
</div>
<div class="form-group" id="form-group3">
	<div class="row">
		<div class="col-xs-11 col-md-11 col-lg-11">
			<q:textarea name="content" id="content" value="sysExpression.content" label="Expression content" requiredFlag="Y" escapeHtml="N" escapeJavaScript="N"></q:textarea>
		</div>
	</div>
</div>

<br>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnUpdate" label="Save"
			xhrUrl="./core.sysExpressionUpdateJson.do"
			xhrParameter="	
			{
				'oid'				:	'${sysExpression.oid}',
				'exprId'			:	$('#exprId').val(),
				'name'				:	$('#name').val(),
				'type'				:	$('#type').val(),
				'description'		:	$('#description').val(),
				'content'			:	javaEditor.getValue()
			}
			"
			onclick="btnUpdate();"
			loadFunction="updateSuccess(data);"
			errorFunction="clearUpdate();">
		</q:button>
		<q:button id="btnClear" label="Clear" onclick="clearUpdate();"></q:button>
	</div>
</div>

</body>
</html>