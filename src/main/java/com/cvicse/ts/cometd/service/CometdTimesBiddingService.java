package com.cvicse.ts.cometd.service;

import java.util.Map;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CometdTimesBiddingService extends AbstractService {

	public CometdTimesBiddingService(BayeuxServer bayeux, String name) {
		super(bayeux, name);
		// адлЛ
		addService("/service/chat", "chat");

		addService("/service/test", "test");

	}

	private static final Logger logger = LoggerFactory.getLogger(CometdTimesBiddingService.class);

	// адлЛ
	public void chat(ServerSession remote, ServerMessage message) {
		logger.debug(message.toString());
		try {
			Map<String, Object> map = message.getDataAsMap();
			String projectId = (String) map.get("projectId");
			String mark = (String) map.get("mark");
			String msg = (String) map.get("msg");
			String toMark = (String) map.get("toMark");

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void test(ServerSession remote,ServerMessage message){
		logger.debug(message.toString());
		try {
			Map<String,Object> map = message.getDataAsMap();
			String room = (String) map.get("room");
			ServerChannel channel = getBayeux().getChannel("/chat/" + room);
			channel.publish(getLocalSession(), message.getDataAsMap());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
