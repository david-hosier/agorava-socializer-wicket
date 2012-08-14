package org.agorava.socializer;

import javax.inject.Inject;

import org.agorava.core.api.oauth.OAuthSession;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	
	@Inject
	private SocialClient client;

    public HomePage(final PageParameters parameters) {
    	add(new WebMarkupContainer("notConnected") {
    		@Override
    		protected void onConfigure() {
    			super.onConfigure();
    			
    			OAuthSession session = client.getCurrentSession();
    			setVisible(session == null || !session.isConnected());
    		}
    	});

    	add(new TwitterProfilePanel("profile") {
    		@Override
    		protected void onConfigure() {
    			super.onConfigure();
    			
    			OAuthSession session = client.getCurrentSession();
    			setVisible(session != null && session.isConnected());
    		}
    	});
    	
    	add(new ServicesPanel("services"));
    	
    }
}
