# cometd-demo #


## ���Ե�ַ ##

http://localhost:8080/cometd-demo/test.jsp

## �Զ����� ##
### DynamicOortMulticastConfigServlet ###
����ͨ��-D����ָ����ͬ��oort��������

```
# oort.url ��ǰ�ڵ�cometd��url
# oort.multicast.groupInterfaces ��ǰ�ڵ����ڽ���oort�ֲ�ʽ��Ϣ��ip

-Doort.url=http://192.168.31.179:8080/cometd-demo/cometd -Doort.multicast.groupInterfaces=192.168.31.179
```

### CometDJMXExporter ###

��cometd��JMX����򿪣�����ͨ��jconsole���з��ʣ������ڵ�״̬�����ӿͻ���������oort���ӵĽڵ㣬��ǰ�ڵ�url����Ϣ

### LogExtension��ExtensionRegister ###

��չcometd����¼��־���Լ�ע����չ