package org.agorava.socializer;

import javax.enterprise.inject.spi.BeanManager;

import net.ftlines.wicket.cdi.CdiConfiguration;

import org.apache.wicket.protocol.http.WebApplication;
import org.jboss.weld.environment.servlet.Listener;
import org.scribe.builder.ServiceBuilder;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see org.agorava.socializer.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();

		BeanManager manager = (BeanManager) getServletContext().getAttribute(
				Listener.BEAN_MANAGER_ATTRIBUTE_NAME);
		new CdiConfiguration(manager).configure(this);
		
		mountPage("/home", HomePage.class);
		mountPage("/auth", AuthorizationCallbackPage.class);
	}
}
