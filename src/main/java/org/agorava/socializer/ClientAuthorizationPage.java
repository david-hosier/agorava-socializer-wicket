package org.agorava.socializer;


import java.io.IOException;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientAuthorizationPage extends WebPage {

	private Logger log = LoggerFactory.getLogger(ServicesPanel.class);
	@Inject
	private SocialClient client;
	
	public ClientAuthorizationPage()
	{
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();

		String url = "http://google.com";
		try {
			url = client.serviceInit();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		log.info("REDIRECTING TO AUTH URL: " + url);
		throw new RedirectToUrlException(url);
	}

	@Override
	protected void onRender() {
		super.onRender();
	}

	@Override
	public boolean isVersioned()
	{
		return false;
	}
}
