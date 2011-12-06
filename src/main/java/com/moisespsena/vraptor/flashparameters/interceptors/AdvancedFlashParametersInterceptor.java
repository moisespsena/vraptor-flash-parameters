/***
 * Copyright (c) 2011 Moises P. Sena - www.moisespsena.com
 * All rights reserved.
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
/**
 * 
 */
package com.moisespsena.vraptor.flashparameters.interceptors;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Lazy;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.http.MutableResponse;
import br.com.caelum.vraptor.http.MutableResponse.RedirectListener;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

import com.moisespsena.vraptor.flashparameters.FlashParameters;
import com.moisespsena.vraptor.flashparameters.FlashParametersImpl;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 05/08/2011
 * 
 */
@Lazy
@Intercepts
public class AdvancedFlashParametersInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(AdvancedFlashParametersInterceptor.class);
	private final FlashParameters flashParameters;

	private final MutableResponse response;
	private final HttpSession session;

	/**
	 * 
	 */
	public AdvancedFlashParametersInterceptor(final HttpSession session,
			final MutableResponse response,
			final FlashParameters flashParameters) {
		this.session = session;
		this.response = response;
		this.flashParameters = flashParameters;
	}

	@Override
	public boolean accepts(final ResourceMethod method) {
		return true;
	}

	@Override
	public void intercept(final InterceptorStack stack,
			final ResourceMethod method, final Object resourceInstance)
			throws InterceptionException {
		@SuppressWarnings("unchecked")
		final Map<String, Serializable> parameters = (Map<String, Serializable>) session
				.getAttribute(FlashParametersImpl.SESSION_ATTRIBUTE);

		if (parameters != null) {
			session.removeAttribute(FlashParametersImpl.SESSION_ATTRIBUTE);

			flashParameters.getParameters().putAll(parameters);

			flashParameters.dispatchAfterLoad();
		}

		response.addRedirectListener(new RedirectListener() {
			@Override
			public void beforeRedirect() {
				flashParameters.dispatchBeforeSave();

				final Map<String, Serializable> parameters = flashParameters
						.getParameters();
				if (parameters.size() > 0) {
					try {
						session.setAttribute(
								FlashParametersImpl.SESSION_ATTRIBUTE,
								parameters);
					} catch (final IllegalStateException e) {
						logger.info("HTTP Session was invalidated. It is not possible to set "
								+ "FLASH Parameters on Session Scope");
					}
				}
			}
		});

		stack.next(method, resourceInstance);
	}
}
