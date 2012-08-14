package org.agorava.socializer;

import javax.inject.Inject;

import org.agorava.core.api.oauth.OAuthSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;

public class TwitterProfilePanel extends Panel {
	
	@Inject
	private SocialClient client;

	public TwitterProfilePanel(String id) {
		super(id);
		add(new Label("name", new LoadableDetachableModel<String>() {
			protected String load() {
				OAuthSession currentSession = client.getCurrentSession();
				if (currentSession != null && currentSession.isConnected()) {
					return currentSession.getUserProfile().getFullName();
				} else {
					return "not connected";
				}
			};
		}));
	}
}
