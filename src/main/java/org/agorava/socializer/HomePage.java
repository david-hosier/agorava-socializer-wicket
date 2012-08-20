package org.agorava.socializer;

import javax.inject.Inject;

import org.agorava.core.api.oauth.OAuthSession;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	
	@Inject
	private SocialClient client;

    public HomePage(final PageParameters parameters) {
    	
    	final IModel<Boolean> connected = new AbstractReadOnlyModel<Boolean>() {

			@Override
			public Boolean getObject() {
    			OAuthSession session = client.getCurrentSession();
    			return session != null && session.isConnected();
			}
			
    	};
    	
    	add(new TwitterPanel("twitter") {
    		
    		@Override
    		protected void onConfigure() {
    			super.onConfigure();
    			setVisible(connected.getObject());
    		}
    		
    	});
    	add(new WebMarkupContainer("sorry") {
    		
    		@Override
    		protected void onConfigure() {
    			super.onConfigure();
    			setVisible(!connected.getObject());
    		}
    		
    	});
    	
    	add(new WebMarkupContainer("notConnected") {
    		@Override
    		protected void onConfigure() {
    			super.onConfigure();
    			setVisible(!connected.getObject());
    		}
    	});

    	add(new TwitterProfilePanel("profile") {
    		@Override
    		protected void onConfigure() {
    			super.onConfigure();
    			setVisible(connected.getObject());
    		}
    	});
    	
    	add(new ServicesPanel("services"));
    	
    }
}
