# cometd-demo #


## 测试地址 ##

http://localhost:8080/cometd-demo/test.jsp

## 自定义类 ##
### DynamicOortMulticastConfigServlet ###
可以通过-D参数指定不同的oort启动参数

```
# oort.url 当前节点cometd的url
# oort.multicast.groupInterfaces 当前节点用于接收oort分布式消息的ip

-Doort.url=http://192.168.31.179:8080/cometd-demo/cometd -Doort.multicast.groupInterfaces=192.168.31.179
```

### CometDJMXExporter ###

将cometd的JMX服务打开，可以通过jconsole进行访问，包括节点状态，连接客户单数量，oort连接的节点，当前节点url等信息

### LogExtension、ExtensionRegister ###

扩展cometd，记录日志，以及注册扩展