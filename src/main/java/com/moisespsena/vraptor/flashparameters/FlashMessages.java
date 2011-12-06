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

import java.util.Collection;
import java.util.List;

import com.moisespsena.vraptor.modularmessages.MessageLevel;
import com.moisespsena.vraptor.modularvalidator.MessageInfo;
import com.moisespsena.vraptor.modularvalidator.SimpleMessage;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 05/08/2011
 * 
 */
public interface FlashMessages {

	void addAllI18n(MessageLevel messageLevel, Collection<MessageInfo> messages);

	void addAllI18nDebug(Collection<MessageInfo> messages);

	void addAllI18nError(Collection<MessageInfo> messages);

	void addAllI18nInfo(Collection<MessageInfo> messages);

	void addAllI18nWarn(Collection<MessageInfo> messages);

	void addI18n(MessageLevel messageLevel, MessageInfo messageInfo);

	void addI18n(MessageLevel messageLevel, String category, String reason,
			Object... parameters);

	void addI18nDebug(MessageInfo messageInfo);

	void addI18nDebug(String category, String reason, Object... parameters);

	void addI18nError(MessageInfo messageInfo);

	void addI18nError(String category, String reason, Object... parameters);

	void addI18nInfo(MessageInfo messageInfo);

	void addI18nInfo(String category, String reason, Object... parameters);

	void addI18nWarn(MessageInfo messageInfo);

	void addI18nWarn(String category, String reason, Object... parameters);

	void addSimpleMessage(SimpleMessage simpleMessage);

	List<SimpleMessage> getMessages();

	boolean hasMessages();
}
