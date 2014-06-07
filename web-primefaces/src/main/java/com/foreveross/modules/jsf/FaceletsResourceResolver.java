package com.foreveross.modules.jsf;

import java.io.File;
import java.net.URL;

import javax.faces.view.facelets.ResourceResolver;

import com.foreveross.infra.dsl.engine.DSL;
import com.foreveross.infra.util.JsonHelper;

public class FaceletsResourceResolver extends ResourceResolver {

	private ResourceResolver parent;

	public FaceletsResourceResolver(ResourceResolver parent) {
		this.parent = parent;
	}

	@Override
	public URL resolveUrl(String path) {
		//URL url = parent.resolveUrl(path); // Resolves from WAR.
		URL url=null;

		if (url == null) {
			try {
				url = new File("G:/bak/groovy", path).toURI().toURL();
				DSL.dsl("bean:v1:Hello?hello", new Object[0]);
				DSL.dsl("bean:v1:ComponentSelector?init", new Object[0]);
				System.out.println(JsonHelper.toJson(DSL.dsl("bean:v1:Document?create", new Object[]{"a","b","c"})));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return url;
	}

}