/*
 * Copyright (c) 2008-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cvicse.ts.cometd.extension;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.server.BayeuxServer.Extension;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerMessage.Mutable;
import org.cometd.bayeux.server.ServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * cometd的扩展类，可以自定义消息接收和发送时的处理逻辑。例如增加一些报文内容。此处使用该机制实现消息的监控，主要逻辑如下
 * <ul>
 * <li>实现send或rcv方法（应该所有的都会有send，你测一下，只一个就可以，否则会重复），此处增加对channel的判断，只有测试channel才进行处理。</li>
 * <li>获取一个环境变量（-D参数，每个节点启动时增加一个参数例如-Dserver.name=ts-web-01),将该变量、发送的测试标识、时间戳写入一个表中</li>
 * </ul>
 * @author jiang_zhuo
 *
 */
@Component
public class LogExtension extends Extension.Adapter {
	private static final Logger logger = LoggerFactory.getLogger(LogExtension.class);

    @Override
    public boolean send(ServerSession from, ServerSession to, Mutable message) {
    	if(message.getChannel().equals("/chat/1")) {
    		logger.debug("send:" + message.getJSON());
    		Map<String,String> map = new HashMap<String,String>();
    		//如果已经有类似的-D参数，用已有的即可
    		String url = System.getProperty("server.name");
    		//下面添加写入表的操作，可以设置三个字段（测试标识，节点，时间戳）
    		//测试标识由前端发送测试的代码随机生成，可以是uuid之类的，发送到测试channel，在这里就能够取到
    		map.put("msg", "ok," + url + ",msg:" + message.getDataAsMap().get("msg"));
    	}
        return true;
    }

    @Override
    public boolean rcv(ServerSession from, ServerMessage.Mutable message) {
    	if(message.getChannel().equals("/chat/1")) {
    		logger.debug("receive:" + message.getJSON());
    	}
        return true;
    }

}
