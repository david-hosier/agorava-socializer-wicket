package org.agorava.socializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.agorava.twitter.TwitterTimelineService;
import org.agorava.twitter.model.Tweet;
import org.joda.time.DateTime;

@SessionScoped
public class Timelines implements Serializable {

	@Inject
	private transient TwitterTimelineService twitter;
	
	/*
	 * Caching tweets for now to avoid accidentally bludgeoning the Twitter API
	 */
	private Map<TwitterTimelineMode,TweetList> tweetsByMode = 
			new HashMap<TwitterTimelineMode,TweetList>();
	private Map<Long,Tweet> tweetsById = new HashMap<Long,Tweet>();

	public List<Tweet> getTweets(TwitterTimelineMode mode) {
		TweetList list = tweetsByMode.get(mode);
		if (list == null) {
			list = new TweetList(mode);
			tweetsByMode.put(mode, list);
		}
		if (!list.isInitialized()) {
			list.init();
		}
		return list.getTweets();
	}
	
	public DateTime getLastUpdate(TwitterTimelineMode mode) {
		TweetList list = tweetsByMode.get(mode);
		if (list != null) {
			return list.getLastUpdated();
		}
		return null;
	}
	
	public Tweet getTweet(long id) {
		return tweetsById.get(id);
	}
	
	private void cacheTweet(Tweet t) {
		tweetsById.put(t.getId(), t);
	}
	
	private class TweetList {
		private TwitterTimelineMode mode;
		private boolean initialized = false;
		private DateTime lastUpdated;
		private List<Tweet> tweets = new ArrayList<Tweet>();
		
		private TweetList(TwitterTimelineMode mode) {
			this.mode = mode;
		}
		
		private DateTime getLastUpdated() { return lastUpdated; }
		private List<Tweet> getTweets() { return tweets; }
		private boolean isInitialized() { return initialized; }
		private void init() {
			if (!isInitialized()) {
				List<Tweet> timeline = new ArrayList<Tweet>();
				switch (mode) {
				case FAVORITES:
					timeline = twitter.getFavorites(1, 5);
					break;
				case MENTIONS:
					timeline = twitter.getMentions(1, 5);
					break;
				default:
					timeline = twitter.getHomeTimeline(1, 5);
					break;
				}

				for (Tweet t : timeline) {
					if (getTweet(t.getId()) != null) {
						tweets.add(getTweet(t.getId()));
					} else {
						tweets.add(t);
						cacheTweet(t);
					}
				}
			}
			initialized = true;
			lastUpdated = new DateTime();
		}
	}
}
