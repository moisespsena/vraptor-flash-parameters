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

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 21/09/2011
 */
@Component
@RequestScoped
public class FlashParametersImpl implements FlashParameters {
	public static final String SESSION_ATTRIBUTE = FlashParameters.class
			.getName();
	private boolean afterLoadCalled = false;

	private boolean beforeSaveCalled = false;

	private final Set<LoadListener> listeners = new HashSet<LoadListener>(0);
	private Map<String, Serializable> parameters = new HashMap<String, Serializable>();

	public FlashParametersImpl() {

	}

	@Override
	public void addLoadListener(final LoadListener listener) {
		listeners.add(listener);

		dispatch(listener);
	}

	private void dispatch(final LoadListener listener) {
		if (beforeSaveCalled) {
			listener.beforeSave(this);
		}
		if (afterLoadCalled) {
			listener.afterLoad(this);
		}
	}

	@Override
	public void dispatchAfterLoad() {
		if (!afterLoadCalled) {
			afterLoadCalled = true;
			for (final LoadListener listener : listeners) {
				dispatch(listener);
			}
		}
	}

	@Override
	public void dispatchBeforeSave() {
		if (!beforeSaveCalled) {
			beforeSaveCalled = true;
			for (final LoadListener listener : listeners) {
				dispatch(listener);
			}
		}
	}

	@Override
	public Object getParameter(final String name) {
		return parameters.get(name);
	}

	@Override
	public Map<String, Serializable> getParameters() {
		return parameters;
	}

	@Override
	public boolean isAfterLoadCalled() {
		return afterLoadCalled;
	}

	@Override
	public void setParameter(final String name, final Object value) {
		parameters.put(name, (Serializable) value);
	}

	@Override
	public void setParameters(final Map<String, Serializable> parameters) {
		this.parameters = parameters;
	}
}
