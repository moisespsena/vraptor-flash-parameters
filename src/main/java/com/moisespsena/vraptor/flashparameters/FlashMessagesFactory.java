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
package com.moisespsena.vraptor.flashparameters;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.SuperMethod;

import com.moisespsena.vraptor.modularvalidator.UnimplementedBeanException;

@Component
@ApplicationScoped
public class FlashMessagesFactory implements ComponentFactory<FlashMessages> {

	private static final Logger logger = LoggerFactory
			.getLogger(FlashMessagesFactory.class);
	private FlashMessages flashMessages;
	private final Proxifier proxifier;

	public FlashMessagesFactory(final Proxifier proxifier) {
		this.proxifier = proxifier;
	}

	@Override
	public FlashMessages getInstance() {
		if (flashMessages == null) {
			flashMessages = proxifier.proxify(FlashMessages.class,
					new MethodInvocation<FlashMessages>() {
						@Override
						public Object intercept(final FlashMessages proxy,
								final Method method, final Object[] args,
								final SuperMethod superMethod) {
							throw new UnimplementedBeanException(
									FlashMessages.class);
						}
					});
		}

		return flashMessages;
	}

	@PostConstruct
	public void initialize() {
		if (logger.isDebugEnabled()) {
			logger.debug("Initializing default FlashMessagesFactory");
		}
	}

}