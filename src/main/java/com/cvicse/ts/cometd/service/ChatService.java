package com.cvicse.ts.cometd.service;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.server.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatService extends AbstractService {

	private static final Logger logger = LoggerFactory.getLogger(CometdChatService.class);

	public ChatService(BayeuxServer bayeux, String name) {
		super(bayeux, name);
	}

	/**************************** ÁÄÌì ***************************************/

	public void chat(String room, String msg, String formatDate) {
		logger.debug(room + "=====" + msg);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", msg);
		map.put("sendTime", formatDate);
		ServerChannel channel = getBayeux().getChannel("/chat/" + room);
		if (channel == null) {
			logger.debug("1.channelÎªnull");
			channel = getBayeux().createChannelIfAbsent("/chat/" + room).getReference();
			logger.debug("2.channelÎª" + channel);
		}
		channel.publish(getLocalSession(), map);
	}

}
