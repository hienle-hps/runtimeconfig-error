/**
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.runtimeconfig.v1beta1.CloudRuntimeConfig;
import com.google.api.services.runtimeconfig.v1beta1.CloudRuntimeConfigScopes;
import com.google.api.services.runtimeconfig.v1beta1.model.Variable;
import com.google.api.services.runtimeconfig.v1beta1.CloudRuntimeConfig.Projects.Configs.Variables;
import com.google.appengine.api.utils.SystemProperty;

// [START example]
@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		final String PROJECT = SystemProperty.Environment.applicationId.get();
		final String CONFIG = "my_config";
		final String VARIABLE = "my_var";
		
		System.out.println("Project: " + PROJECT);
		
		PrintWriter out = resp.getWriter();
		
		try {
			CloudRuntimeConfig runtimeConfig = createClient();
			Variables variables = runtimeConfig.projects().configs().variables();
			String varName = String.format("projects/%s/configs/%s/variables/%s", PROJECT, CONFIG, VARIABLE);
			out.println(varName);
			Variable var = variables.get(varName).execute();
		    out.println(var.getText());
		} catch (Exception e) {
			out.println(e.getMessage());
		}
	}

	private CloudRuntimeConfig createClient() {
		HttpTransport transport = new UrlFetchTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		AppIdentityCredential credential = new AppIdentityCredential(
				Arrays.asList(CloudRuntimeConfigScopes.CLOUDRUNTIMECONFIG));
		CloudRuntimeConfig runtimeConfig = new CloudRuntimeConfig.Builder(transport, jsonFactory, credential).build();
		return runtimeConfig;
	}
}
// [END example]
