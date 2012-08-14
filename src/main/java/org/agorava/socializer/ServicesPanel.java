package org.agorava.socializer;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServicesPanel extends Panel {

	@Inject
	private SocialClient client;

	private ModalWindow authModal;

	private Logger log = LoggerFactory.getLogger(ServicesPanel.class);

	public ServicesPanel(String id) {
		super(id);

//		authModal = new ModalWindow("authModal");
//		authModal.setInitialHeight(400);
//		authModal.setInitialWidth(400);
//		add(authModal);

		List<String> listOfServices = client.getManager().getListOfServices();
		add(new ListView<String>("services", listOfServices) {

			@Override
			protected void populateItem(final ListItem<String> item) {

				AjaxLink<String> serviceLink = new AjaxLink<String>(
						"serviceLink", item.getModel()) {

					@Override
					public void onClick(AjaxRequestTarget target) {
						log.info("YOU CLICKED ON SERVICE: {}", getModelObject());
						client.setSelectedService(getModelObject());
						String url = "http://google.com";
						try {
							url = client.serviceInit();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						throw new RedirectToUrlException(url);
//						authModal.setPageCreator(new ModalWindow.PageCreator() {
//
//							public Page createPage() {
//								String url = "http://google.com";
//								try {
//									url = client.serviceInit();
//								} catch (IOException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
//								return new MyRedirectPage(url);
//							}
//						});
//						authModal.setContent(new ClientAuthorizationPage(authModal.getContentId(), url));
//						authModal.show(target);
					}
				};
				item.add(serviceLink);

				WebMarkupContainer serviceImage = new WebMarkupContainer(
						"serviceImage") {
					@Override
					protected void onComponentTag(ComponentTag tag) {
						super.onComponentTag(tag);
						tag.put("src", "gfx/" + item.getModelObject() + ".png");
					}
				};
				serviceLink.add(serviceImage);
			}

		});
	}
}
