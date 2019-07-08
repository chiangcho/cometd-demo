package com.cvicse.ts.cometd.extension;

import javax.annotation.PostConstruct;

import org.cometd.bayeux.server.BayeuxServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 将上下文中的logExtension注册到cometd中
 * @author jiang_zhuo
 *
 */
@Component
public class ExtensionRegister {
	@Autowired
	private BayeuxServer bayeuxServer = null;
	@Autowired
	private LogExtension logExtension = null;
	
	
	@PostConstruct
	public void register() {
		bayeuxServer.addExtension(logExtension);
	}


	public BayeuxServer getBayeuxServer() {
		return bayeuxServer;
	}


	public void setBayeuxServer(BayeuxServer bayeuxServer) {
		this.bayeuxServer = bayeuxServer;
	}


	public LogExtension getLogExtension() {
		return logExtension;
	}


	public void setLogExtension(LogExtension logExtension) {
		this.logExtension = logExtension;
	}
}
