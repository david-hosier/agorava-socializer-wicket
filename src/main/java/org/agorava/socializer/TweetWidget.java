package org.agorava.socializer;

import java.util.Date;

import org.agorava.twitter.model.Tweet;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;


public class TweetWidget extends GenericPanel<Tweet> {

	public TweetWidget(String id, IModel<Tweet> tweet) {
		super(id, tweet);
		add(new TwitterAvatar("avatar", tweet));
		add(new TweetProfileLink("profileLink", tweet));
		add(new DateLabel("created",
				new PropertyModel<Date>(tweet, "createdAt"),
				new PatternDateConverter("MMMM dd, yyyy", true)));
		add(new Label("via", tweet.getObject().getSource())
				.setEscapeModelStrings(false));
		add(new Label("text", tweet.getObject().getText())
				.setEscapeModelStrings(false));
	}


	private class TwitterAvatar extends WebComponent {

		public TwitterAvatar(String id, IModel<Tweet> tweet) {
			super(id, tweet);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			super.onComponentTag(tag);
			checkComponentTag(tag, "img");
			Tweet tweet = (Tweet)getDefaultModelObject();
			tag.put("src", tweet.getProfileImageUrl());
			tag.put("title", tweet.getFromUser());
		}
		
	}

	private class TweetProfileLink extends Fragment {

		public TweetProfileLink (String id, IModel<Tweet> tweet) {
			super(id, "profileLinkFragment", TweetWidget.this);
			String user = tweet.getObject().getFromUser();
			ExternalLink link = new ExternalLink("profileLink", "https://twitter.com/#!/#" + user);
			add(link);
			link.add(new Label("user", user));
		}
		
	}
}
