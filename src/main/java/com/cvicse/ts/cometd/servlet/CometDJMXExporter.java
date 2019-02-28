package com.cvicse.ts.cometd.servlet;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.oort.Oort;
import org.eclipse.jetty.jmx.MBeanContainer;

public class CometDJMXExporter extends GenericServlet {
	private static final long serialVersionUID = -6152962473022203590L;
	private volatile MBeanContainer mbeanContainer;
    private final List<Object> mbeans = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        try {
            mbeanContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
            BayeuxServer bayeuxServer = (BayeuxServer)getServletContext().getAttribute(BayeuxServer.ATTRIBUTE);
            Oort oort = (Oort)getServletContext().getAttribute(Oort.OORT_ATTRIBUTE);
            mbeanContainer.beanAdded(null, bayeuxServer);
            mbeans.add(bayeuxServer);
            mbeanContainer.beanAdded(null, oort);
            mbeans.add(oort);
            // Add other components
        } catch (Exception x) {
            throw new ServletException(x);
        }
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        throw new ServletException();
    }

    @Override
    public void destroy() {
    	for (int i = mbeans.size() - 1; i >= 0; --i)
            mbeanContainer.beanRemoved(null, mbeans.get(i));
    }
}