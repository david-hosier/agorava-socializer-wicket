package org.agorava.socializer;

import javax.inject.Inject;

import org.agorava.core.api.SocialMediaApiHub;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizationCallbackPage extends WebPage {

	@Inject
	private SocialClient client;

	private Logger log = LoggerFactory.getLogger(AuthorizationCallbackPage.class);

	public AuthorizationCallbackPage(PageParameters params) {
		SocialMediaApiHub currentHub = client.getCurrentHub();
		
		org.apache.wicket.util.string.StringValue result = params.get(currentHub.getVerifierParamName());
		log.info("Param Name: " + currentHub.getVerifierParamName());
		log.info("Param Value: " + result.toString("N/A"));
		if (!result.isEmpty()) {
			client.getCurrentSession().setVerifier(result.toString());
			log.info("VERIFIER VALUE: " + client.getCurrentSession().getVerifier());
			client.connectCurrentService();
			setResponsePage(HomePage.class);
		}
	}
}
