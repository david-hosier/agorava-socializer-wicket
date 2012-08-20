package org.agorava.socializer;

import java.util.Iterator;

import javax.inject.Inject;

import org.agorava.twitter.model.Tweet;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class TwitterTimelinePanel extends Panel {

	@Inject
	private Timelines timeline;

	public TwitterTimelinePanel(String id, final TwitterTimelineMode mode) {
		super(id);

		IDataProvider<Tweet> provider = new IDataProvider<Tweet>() {

			public void detach() {
				// TODO Auto-generated method stub

			}

			public Iterator<? extends Tweet> iterator(int first, int count) {
				return timeline.getTweets(mode).iterator();
			}

			public int size() {
				return timeline.getTweets(mode).size();
			}
			
			public IModel<Tweet> model(Tweet object) {
				return new LoadableDetachableModel<Tweet>(object) {
					private long id;

					@Override
					protected Tweet load() {
						return timeline.getTweet(id);
					}

					@Override
					protected void onDetach() {
						super.onDetach();
						id = getObject().getId();
					}

				};
			}
		};

		DataView view = new DataView<Tweet>("tweets", provider) {

			@Override
			protected void populateItem(final Item<Tweet> item) {
				item.add(new TweetWidget("tweet", item.getModel()));
			}
		};
		add(view);
	}
}
