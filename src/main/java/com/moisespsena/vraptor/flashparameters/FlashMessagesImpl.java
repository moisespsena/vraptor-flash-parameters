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
package com.moisespsena.vraptor.flashparameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.validator.I18nMessage;

import com.moisespsena.vraptor.modularmessages.MessageLevel;
import com.moisespsena.vraptor.modularvalidator.MessageInfo;
import com.moisespsena.vraptor.modularvalidator.ModularMessagesFactory;
import com.moisespsena.vraptor.modularvalidator.SimpleMessage;
import com.moisespsena.vraptor.modularvalidator.SimpleMessageImpl;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 05/08/2011
 * 
 */
@Component
@RequestScoped
public class FlashMessagesImpl implements FlashMessages {
	private static final String FLASH_PARAMETER = FlashMessagesImpl.class
			.getName();
	private final List<SimpleMessage> messages = new ArrayList<SimpleMessage>(0);
	private final ModularMessagesFactory modularMessageFactory;

	public FlashMessagesImpl(final FlashParameters flashParameters,
			final ModularMessagesFactory modularMessageFactory) {
		this.modularMessageFactory = modularMessageFactory;

		if (flashParameters.isAfterLoadCalled()) {
			loadMessages(flashParameters);
		} else {
			flashParameters.addLoadListener(new LoadListener() {
				@Override
				public void afterLoad(final FlashParameters flashParameters) {
					loadMessages(flashParameters);
				}

				@Override
				public void beforeSave(final FlashParameters flashParameters) {
					flashParameters.setParameter(FLASH_PARAMETER, messages);
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addAllI18n(com.
	 * moisespsena.vraptor.modularmessages.MessageType, java.util.Collection)
	 */
	@Override
	public void addAllI18n(final MessageLevel messageLevel,
			final Collection<MessageInfo> messages) {
		for (final MessageInfo messageInfo : messages) {
			addI18n(messageLevel, messageInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addAllI18nDebug
	 * (java.util.Collection)
	 */
	@Override
	public void addAllI18nDebug(final Collection<MessageInfo> messages) {
		addAllI18n(MessageLevel.DEBUG, messages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addAllI18nError
	 * (java.util.Collection)
	 */
	@Override
	public void addAllI18nError(final Collection<MessageInfo> messages) {
		addAllI18n(MessageLevel.ERROR, messages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addAllI18nInfo(
	 * java.util.Collection)
	 */
	@Override
	public void addAllI18nInfo(final Collection<MessageInfo> messages) {
		addAllI18n(MessageLevel.INFO, messages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addAllI18nWarn(
	 * java.util.Collection)
	 */
	@Override
	public void addAllI18nWarn(final Collection<MessageInfo> messages) {
		addAllI18n(MessageLevel.WARN, messages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18n(java.lang
	 * .String, com.moisespsena.vraptor.modularvalidator.MessageInfo)
	 */
	@Override
	public void addI18n(final MessageLevel messageLevel,
			final MessageInfo messageInfo) {
		addI18n(messageLevel, messageInfo.getCategory(), messageInfo.getKey(),
				messageInfo.getParameters());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18n(com.moisespsena
	 * .vraptor.modularmessages.MessageType, java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public void addI18n(final MessageLevel messageLevel, final String category,
			final String reason, final Object... parameters) {
		final I18nMessage message = modularMessageFactory.createI18n(category,
				reason, parameters);
		final SimpleMessage simpleMessage = new SimpleMessageImpl();
		simpleMessage.setCategory(category);
		simpleMessage.setLevel(messageLevel.toString());
		simpleMessage.setMessage(message.getMessage());

		messages.add(simpleMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18nDebug(com
	 * .moisespsena.vraptor.modularvalidator.MessageInfo)
	 */
	@Override
	public void addI18nDebug(final MessageInfo messageInfo) {
		addI18n(MessageLevel.DEBUG, messageInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18nDebug(java
	 * .lang.String, java.lang.Object[])
	 */
	@Override
	public void addI18nDebug(final String category, final String reason,
			final Object... parameters) {
		addI18n(MessageLevel.DEBUG, category, reason, parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18nError(com
	 * .moisespsena.vraptor.modularvalidator.MessageInfo)
	 */
	@Override
	public void addI18nError(final MessageInfo messageInfo) {
		addI18n(MessageLevel.ERROR, messageInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18nError(java
	 * .lang.String, java.lang.Object[])
	 */
	@Override
	public void addI18nError(final String category, final String reason,
			final Object... parameters) {
		addI18n(MessageLevel.ERROR, category, reason, parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18nInfo(com
	 * .moisespsena.vraptor.modularvalidator.MessageInfo)
	 */
	@Override
	public void addI18nInfo(final MessageInfo messageInfo) {
		addI18n(MessageLevel.INFO, messageInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18nInfo(java
	 * .lang.String, java.lang.Object[])
	 */
	@Override
	public void addI18nInfo(final String category, final String reason,
			final Object... parameters) {
		addI18n(MessageLevel.INFO, category, reason, parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18nWarn(com
	 * .moisespsena.vraptor.modularvalidator.MessageInfo)
	 */
	@Override
	public void addI18nWarn(final MessageInfo messageInfo) {
		addI18n(MessageLevel.WARN, messageInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#addI18nWarn(java
	 * .lang.String, java.lang.Object[])
	 */
	@Override
	public void addI18nWarn(final String category, final String reason,
			final Object... parameters) {
		addI18n(MessageLevel.WARN, category, reason, parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.moisespsena.vraptor.flashparameters.FlashMessages#add(br.com.caelum
	 * .vraptor.validator.Message)
	 */
	@Override
	public void addSimpleMessage(final SimpleMessage message) {
		messages.add(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.moisespsena.vraptor.flashparameters.FlashMessages#getMessages()
	 */
	@Override
	public List<SimpleMessage> getMessages() {
		return new ArrayList<SimpleMessage>(messages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.moisespsena.vraptor.flashparameters.FlashMessages#hasMessages()
	 */
	@Override
	public boolean hasMessages() {
		return messages.size() > 0;
	}

	private void loadMessages(final FlashParameters flashParameters) {
		@SuppressWarnings("unchecked")
		final List<SimpleMessage> onFlashMessages = (List<SimpleMessage>) flashParameters
				.getParameter(FLASH_PARAMETER);

		if (onFlashMessages != null) {
			messages.addAll(onFlashMessages);
		}
	}
}
