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
 * cometd����չ�࣬�����Զ�����Ϣ���պͷ���ʱ�Ĵ����߼�����������һЩ�������ݡ��˴�ʹ�øû���ʵ����Ϣ�ļ�أ���Ҫ�߼�����
 * <ul>
 * <li>ʵ��send��rcv������Ӧ�����еĶ�����send�����һ�£�ֻһ���Ϳ��ԣ�������ظ������˴����Ӷ�channel���жϣ�ֻ�в���channel�Ž��д���</li>
 * <li>��ȡһ������������-D������ÿ���ڵ�����ʱ����һ����������-Dserver.name=ts-web-01),���ñ��������͵Ĳ��Ա�ʶ��ʱ���д��һ������</li>
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
    		//����Ѿ������Ƶ�-D�����������еļ���
    		String url = System.getProperty("server.name");
    		//�������д���Ĳ������������������ֶΣ����Ա�ʶ���ڵ㣬ʱ�����
    		//���Ա�ʶ��ǰ�˷��Ͳ��ԵĴ���������ɣ�������uuid֮��ģ����͵�����channel����������ܹ�ȡ��
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
