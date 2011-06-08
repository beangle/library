[#ftl]
[@b.grid items=sessionActivitys var="sessionActivity"]
	[@b.row]
		[@b.col width="5%" title="序号"]${sessionActivity_index+1}[/@]
		[@b.col width="20%" title="登录名(姓名)" property="name"]${sessionActivity.name?html}(${sessionActivity.fullname?html})[/@]
		[@b.col width="15%" title="登录时间"  property="loginAt"]${sessionActivity.loginAt?string("yy-MM-dd HH:mm")}[/@]
		[@b.col width="15%" title="退出时间" property="logoutAt"]${sessionActivity.logoutAt?string("yy-MM-dd HH:mm")}[/@]
		[@b.col width="10%" title="在线时间" property="onlineTime"]${(sessionActivity.onlineTime/60000)?int}分${(sessionActivity.onlineTime/1000)%60}秒[/@]
		[@b.col title="ip" property="host"/]
		[@b.col title="操作系统" property="os"/]
		[@b.col title="用户代理" property="agent"/]
		[@b.col title="备注" property="remark"/]
	[/@]
[/@]