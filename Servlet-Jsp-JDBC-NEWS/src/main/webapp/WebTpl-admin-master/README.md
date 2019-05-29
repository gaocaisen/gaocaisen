#  使用layui框架   使用gulp工具搭建的后台模板
使用layui作为主框架，扩展了datatable一下。
新增了echarts图表；注释丰富，简单易用。
新增表格工具栏，可以按需查看，复制数据，导出csv，打印数据等。
使用ajax请求json数据渲染，没使用laypage，使用的是datatable自己的分页，可以自己配置，本地搜索，排序等。增删改查的模块的基本代码已完善。

#<a target="_blank" href="https://loinver.github.io/WebTpl-admin/webTpl-admin/dev/html/index.html">预览地址</a>

# 修复bug    ----2017-2-7
重现：如果导出15位以上的数字的话，15位及后面就会变成000.这是excel的一个问题，不能输入15位以上的数字。
解决方案：将数字变成字符串，比如身份证号码等等。
目前解决方案是在渲染数据的时候，强制性在身份证号列前后各加一个空格（为了美观，所以前后都加，当然只加一个也可以）。
# 增加了部分common公共函数    ----2017-3-10
# 更新layui版本，修复bug ----2017-3-17
1、更新layui版本到1.09
2、重写侧边导航，修复layui升级版本导致切换tab和删除tab失效的bug问题

# 重构gulp版本----2017-5-16
貌似新gulp有点小bug，只能在第一次的时候生产sprite。有gulp高手可以帮忙看下
