## lightdemo说明 ##
### 导入配置 ###
- JDK : 1.7</br>
- 发布容器：tomcat(如果使用tomcat可能需要导入log4j包)/jetty
- 开发工具：sts/eclipse
- 使用技术：spring
- classpath: lib、serverruntime

**注意：**
所有的配置在resources下，公共号等相关信息放在common.properties下进行配置。 通过下面代码方法获取相关的配置信息，数据库配置文件是jdbc.properties</b>
`Common cm = PropertiesUtil.loadCommonProperties("common.properties");`

### 1.组织人员同步相关接口 ###
操作类是 `com.lightdemo.sync.PersonSyncApiTest`
### 2.发送公共号消息，修改消息待办状态 ###
操作类是`com.lightdemo.postmsg.MsgSend`
### 3.微博相关接口oAuth验证调用
- 微博这边的接口使用的是OAuth1.0 协议。 请参考下面地址说明进行添加key和secret等，才能进行相关接口调用 http://erpcloud.kingdee.com/developer/?p=8
- 本例中也同样遵循该协议，请详细研读代码。如果发现有什么问题请联系作者。调用方式主要写在WeiboHttp.java中
### 4.推送公共号消息、代办消息、改代办状态接口
- MsgSend.java类有具体demo

###基本样例地址
- 字典轻应用  :
http://127.0.0.1:8080/lightdemo
- ticket身份验证  :
http://127.0.0.1:8080/lightdemo/views/jsp/person.jsp
- jsbridge : 
http://127.0.0.1:8080/lightdemo/views/jsp/jsbridge.jsp

- hightchart : http://127.0.0.1:8080/lightdemo/views/hightchart/examples/pie-basic/index.htm

- 新闻轻应用 : http://127.0.0.1:8080/lightdemo/news/shownew(集成基本的接口调用方式， 当前登录人是否是部门负责人，如果是部门负责人
可以广播新闻，广播是调用公共号发送消息，如果不是部门负责人只能分享到会话中，每天晚上通过调度来获取部门负责人和新闻保存到数据库中，数据库表对应sql文件)




