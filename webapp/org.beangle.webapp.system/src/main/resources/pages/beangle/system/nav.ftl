[#ftl]
[@b.navmenu title="系统信息"]
	[@b.navitem title="系统信息" href="info"/]
	[@b.navitem title="系统状态" href="info!status" /]
	[@b.navitem title="系统属性" href="info!properties" /]
	[@bs.guard res="/system/property"][@b.navitem title="系统配置" href="property" /][/@]
	[@bs.guard res="/system/file"][@b.navitem title="文件浏览" href="file" /][/@]
[/@]