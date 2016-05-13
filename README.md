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

###基本样例地址
- 字典轻应用  :
http://127.0.0.1:8080/lightdemo
- ticket身份验证  :
http://127.0.0.1:8080/lightdemo/views/jsp/person.jsp
- jsbridge : 
http://127.0.0.1:8080/lightdemo/views/jsp/jsbridge.jsp

- hightchart : http://127.0.0.1:8080/lightdemo/views/hightchart/examples/pie-basic/index.htm



