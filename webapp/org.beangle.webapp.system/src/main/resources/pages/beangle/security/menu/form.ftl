[#ftl]
[@b.head/]
<script  type="text/javascript" src="${base}/static/scripts/validator.js"></script>
[@b.toolbar title="info.moduleUpdate"]bar.addBack();[/@]
[@b.form name="moduleForm" action="!save" target="menulist"]
<table width="90%" class="formTable" align="center">
	<tr class="thead">
	[#assign userMsg]${b.text("menu")}[/#assign]
	<td  colspan="2"> ${b.text("ui.editForm")}</td>
	</tr>
	<tr>
	<td class="title">菜单配置<font color="red">*</font></td>
	<td><select  name="menu.profile.id" style="width:150px;" >
		[#list profiles as profile]
		<option value="${profile.id}" [#if ((menu.profile.id)!0) ==profile.id]selected="selected"[/#if]>${profile.name}</option>
		[/#list]
		</select>
	</td>
	</tr>
	<tr>
	<td class="title" width="25%" id="f_id">&nbsp;${b.text("common.id")}<font color="red">*</font>:</td>
	<td >
		 <input id="menu_code" type="text" name="menu.code" value="${menu.code!}" style="width:200px;" onKeyUp="value=validCode(value);"/> 数字
	</td>
	</tr>
	<tr>
	<td class="title" width="25%" id="f_title">&nbsp;标题<font color="red">*</font>:</td>
	<td >
	 <input type="text" name="menu.title" value="${menu.title!}" style="width:200px;" />
	</td>
	</tr>
	<tr>
	<td class="title" width="25%" id="f_engTitle">英文标题<font color="red">*</font>:</td>
	<td >
	 <input type="text" name="menu.engTitle" value="${menu.engTitle!}" style="width:200px;" />
	</td>
	</tr>
	<tr>
		 <td class="title" width="25%" id="f_name">&nbsp;${b.text("common.status")}:</td>
		 <td><select  name="menu.enabled" style="width:100px;" >
				<option value="true" [#if menu.enabled]selected="selected"[/#if]>${b.text("action.activate")}</option>
				<option value="false" [#if !menu.enabled]selected="selected"[/#if]>${b.text("action.freeze")}</option>
	 </select>
	</td>
	</tr>
	<tr>
	<td class="title" width="25%" id="f_entry">&nbsp;${b.text("menu.entry")}:</td>
	<td><input type="text" name="menu.entry" value="${menu.entry!}"/></td>
	</tr>

	<tr>
	<td class="title" width="25%" id="f_resources">使用资源:</td>
	<td >
	<table>
	<tr>
		<td>
		<select name="Resources" multiple="multiple" size="10" style="width:200px" onDblClick="JavaScript:bg.select.moveSelected(this.form['Resources'], this.form['SelectedResource'])" >
			[#list resources?sort_by("name") as resource]
					<option value="${resource.id}" title="${resource.title!}">
						${resource.name!}(${resource.title!})
					</option>
				[/#list]
		</select>
		</td>
		<td  valign="middle">
		<br/><br/>
		<input onclick="JavaScript:bg.select.moveSelected(this.form['Resources'], this.form['SelectedResource'])" type="button" value="&gt;"/>
		<br/><br/>
		<input onclick="JavaScript:bg.select.moveSelected(this.form['SelectedResource'], this.form['Resources'])" type="button" value="&lt;"/>
		<br/>
		</td>
		<td  class="normalTextStyle">
		<select name="SelectedResource" multiple="multiple" size="10" style="width:200px;" onDblClick="JavaScript:bg.select.moveSelected(this.form['SelectedResource'], this.form['Resources'])">
			[#list menu.resources?sort_by("name") as resource]
					<option value="${resource.id}" title="${resource.title!}" [#if (menu.resources?seq_contains(resource))]selected="selected"[/#if]>
						${resource.name!}(${resource.title!})
					</option>
				[/#list]
		</select>
		</td>
	</tr>
	</table>
	</td>
	</tr>
	<tr>
		<td class="title" width="25%" id="f_description">&nbsp;${b.text("common.description")}:</td>
		<td ><textarea cols="35" rows="2" name="menu.description">${menu.description!}</textarea></td>
	</tr>
	<tr class="tfoot">
		<td colspan="6"  >
		[@b.submit value="action.submit"  onsubmit="validateMenu"/]&nbsp;
		<input type="reset"  name="reset1" value="${b.text("action.reset")}" class="buttonStyle" />
		[@b.redirectParams/]
		<input type="hidden" name="menu.id" value="${menu.id!}" />
		<input type="hidden" name="resourceIds" />
	</td>
	</tr>
</table>
[/@]
<script  type="text/javascript">
	function validCode(codeValue){
		return codeValue.replace(/[^\d]/g,'')
	}
	function validateMenu(form){
		form.resourceIds.value = bg.select.getValues(form.SelectedResource);
		var a_fields = {
			'menu.code':{'l':'${b.text("common.id")}', 'r':true,'t':'f_id'},
			'menu.title':{'l':'标题', 'r':true, 't':'f_title'},
			'menu.engTitle':{'l':'英文标题', 'r':true, 't':'f_engTitle'}
		};
	
		var codeValue=document.getElementById("menu_code").value;
		 var le = codeValue.length;
		if(le%2!=0){
			 alert("代码必须为双数位!");
			 return;
		}
		var v = new validator(form, a_fields, null);
		return v.exec();
	}
</script>

[@b.foot/]
