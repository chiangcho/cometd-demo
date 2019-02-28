package com.cvicse.ts.cometd.servlet;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;

import org.cometd.oort.Oort;
import org.cometd.oort.OortMulticastConfigServlet;
import org.cometd.oort.OortMulticastConfigurer;

public class DynamicOortMulticastConfigServlet extends OortMulticastConfigServlet {
	private static final long serialVersionUID = 1476114735995932705L;
	private OortMulticastConfigurer configurer;

	@Override
	protected String provideOortURL() {
		String oortUrl = System.getProperty(OORT_URL_PARAM);
		if (oortUrl == null) {
			return super.provideOortURL();
		} else {
			return oortUrl;
		}
	}

	@Override
	protected void configureCloud(ServletConfig config, Oort oort) throws Exception {
		configurer = new OortMulticastConfigurer(oort);

		String bindAddress = config.getInitParameter(OORT_MULTICAST_BIND_ADDRESS_PARAM);
		if (bindAddress != null)
			configurer.setBindAddress(InetAddress.getByName(bindAddress));

		String groupAddress = config.getInitParameter(OORT_MULTICAST_GROUP_ADDRESS_PARAM);
		if (groupAddress != null)
			configurer.setGroupAddress(InetAddress.getByName(groupAddress));

		String groupPort = config.getInitParameter(OORT_MULTICAST_GROUP_PORT_PARAM);
		if (groupPort != null)
			configurer.setGroupPort(Integer.parseInt(groupPort));

		String groupInterfaceList = this.provideGroupInterfaces();
		if (groupInterfaceList != null) {
			List<NetworkInterface> networkInterfaces = new ArrayList<>();
			String[] groupInterfaces = groupInterfaceList.split(",");
			for (String groupInterface : groupInterfaces) {
				groupInterface = groupInterface.trim();
				if (!groupInterface.isEmpty())
					networkInterfaces.add(NetworkInterface.getByInetAddress(InetAddress.getByName(groupInterface)));
			}
			configurer.setGroupInterfaces(networkInterfaces);
		}

		String timeToLive = config.getInitParameter(OORT_MULTICAST_TIME_TO_LIVE_PARAM);
		if (timeToLive != null)
			configurer.setTimeToLive(Integer.parseInt(timeToLive));

		String advertiseInterval = config.getInitParameter(OORT_MULTICAST_ADVERTISE_INTERVAL_PARAM);
		if (advertiseInterval != null)
			configurer.setAdvertiseInterval(Long.parseLong(advertiseInterval));

		String connectTimeout = config.getInitParameter(OORT_MULTICAST_CONNECT_TIMEOUT_PARAM);
		if (connectTimeout != null)
			configurer.setConnectTimeout(Long.parseLong(connectTimeout));

		String maxTransmissionLength = config.getInitParameter(OORT_MULTICAST_MAX_TRANSMISSION_LENGTH_PARAM);
		if (maxTransmissionLength != null)
			configurer.setMaxTransmissionLength(Integer.parseInt(maxTransmissionLength));

		configurer.start();
	}

	protected String provideGroupInterfaces() {
		String groupInterfaces = System.getProperty(OORT_MULTICAST_GROUP_INTERFACES_PARAM);
		if (groupInterfaces == null) {
			return getServletConfig().getInitParameter(OORT_MULTICAST_GROUP_INTERFACES_PARAM);
		} else {
			return groupInterfaces;
		}
	}
}
