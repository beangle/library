[#ftl]
[#include "/template/head.ftl"/]
 <link href="${base}/static/css/tab.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/validator.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/tabpane.js"></script> 
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/TabPane.js"></script>
 <script>
    function save(){
       var form=document.restrictionForm;
       if(confirm("确定设置?")){
          form.submit();
       }
    }
 </script> 
 <body>
 <table id="restrictionBar"></table>
  <form name="restrictionForm" method="post" action="restriction.action?method=save">
    <input type="hidden" name="restriction.id" value="${restriction.id!}"/>
	<input type="hidden" name="restrictionType" value="${Parameters['restrictionType']}"/>
	<input type="hidden" name="restriction.paramGroup.id" value="${restriction.paramGroup.id}"/>
	<input type="hidden" name="restriction.holder.id" value="${restriction.holder.id}"/>
    <input type="hidden" name="params" value="&restrictionType=${Parameters['restrictionType']}&restriction.holder.id=${Parameters['restriction.holder.id']}"/>
 <div>是否启用:
  <input type="radio" [#if (restriction.enabled)!(true)]checked[/#if] value="1" name="restriction.enabled"]启用
  <input type="radio" [#if !(restriction.enabled)!(true)]checked[/#if] value="0" name="restriction.enabled"]禁用
 </div>
 <div class="dynamic-tab-pane-control tab-pane" id="tabPane1" >
   <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
    [#list restriction.paramGroup.params?sort_by("description") as param]
     <div style="display: block;" class="tab-page" id="tabPage${param_index}">
      <h2 class="tab"><a href="#" style="font-size:12px"> ${param.description}</a></h2>
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage${param_index}" ) );</script>
       [#if ignoreParams?seq_contains(param)]
       <div>
       	<input name="ignoreParam${param.id}" type="radio" value="1" [#if holderIgnoreParams?seq_contains(param)]checked[/#if] id="ignoreParam${param.id}_1"]<label for="ignoreParam${param.id}_1"]使用通配符*</label>
       	<input name="ignoreParam${param.id}" type="radio" value="0" [#if !holderIgnoreParams?seq_contains(param)]checked[/#if] id="ignoreParam${param.id}_2"]<label for="ignoreParam${param.id}_2"]选择或填写具体值</label>
       </div>
		[/#if]
       [#if param.editor??]
       [@table.table width="100%"]
         [@table.thead]
		     [@table.selectAllTd id="${param.name}"/]
		     [@table.td text="可选值"/]
	     [/@]
	     [@table.tbody datas=mngParams[param.name];value]
            <td width="10%"><input type="checkbox" name="${param.name}" [#if aoParams[param.name]!?seq_contains(value)]checked[/#if] value="${value["${param.editor.idProperty}"]}"]</td>
            <td>${value["${param.editor.properties}"]}</td>
         [/@]
      [/@]
       [#else]
       <table class="grid" width="100%">
          <tr><td colspan="2"><input type="text" name="${param.name}" value="${aoParams[param.name]!}"/>[#if param.multiValue]多个值请用,格开[/#if]</td></tr>
       </table>
       [/#if]
       </div>
    [/#list]
    </div>
    </form>
    <script type="text/javascript">setupAllTabs();</script>
<script> 
   var bar = new ToolBar('restrictionBar','数据权限',null,true,true);
   bar.setMessage('[@getMessage/]');
   bar.addItem("[@text name="action.save"/]",save,'save.gif');
   bar.addBack();
 </script>
 </body>
[#include "/template/foot.ftl"/] 

