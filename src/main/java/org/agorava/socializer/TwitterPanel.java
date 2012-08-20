package org.agorava.socializer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.agorava.twitter.TwitterTimelineService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class TwitterPanel extends Panel {

	@Inject
	private SocialClient client;
	@Inject
	private transient TwitterTimelineService twitter;
	
	public TwitterPanel(String id) {
		super(id);
		
		Form<Void> form = new Form<Void>("form") {
			
			@Override
			protected void onSubmit() {
				super.onSubmit();
				twitter.updateStatus(client.getStatus());
			}
			
		};
		add(form);
		
		final TextArea<String> status = new TextArea<String>("status", new PropertyModel<String>(client, "status"));
		form.add(status);

		final Label count = new Label("count", "140");
		count.setOutputMarkupId(true);
		form.add(count);
		
		OnChangeAjaxBehavior countBehavior = new OnChangeAjaxBehavior() {
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				int length = status.getDefaultModelObjectAsString().length();
				count.setDefaultModelObject(140 - length);
				target.add(count);
			}
		};
		status.add(countBehavior);

		
		List<AbstractTab> tabs = new ArrayList<AbstractTab>();
		tabs.add(new AbstractTab(Model.of("Timeline")) {
			
			@Override
			public WebMarkupContainer getPanel(String id) {
				return new TwitterTimelinePanel(id, TwitterTimelineMode.HOME);
			}
			
		});
		tabs.add(new AbstractTab(Model.of("Mentions")) {
			
			@Override
			public WebMarkupContainer getPanel(String id) {
				return new TwitterTimelinePanel(id, TwitterTimelineMode.MENTIONS);
			}
			
		});
		tabs.add(new AbstractTab(Model.of("Favorites")) {
			
			@Override
			public WebMarkupContainer getPanel(String id) {
				return new TwitterTimelinePanel(id, TwitterTimelineMode.FAVORITES);
			}
			
		});
		add(new TabbedPanel("tabs", tabs) {
			
			@Override
			protected String getTabContainerCssClass() {
				return "nav nav-tabs";
			}
			
		});
	}
}
