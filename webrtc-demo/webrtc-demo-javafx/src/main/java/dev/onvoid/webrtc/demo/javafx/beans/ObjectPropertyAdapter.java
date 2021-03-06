/*
 * Copyright 2019 Alex Andres
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.onvoid.webrtc.demo.javafx.beans;

import static java.util.Objects.nonNull;

import dev.onvoid.webrtc.demo.beans.ChangeListener;
import dev.onvoid.webrtc.demo.beans.ObjectProperty;

import javafx.application.Platform;
import javafx.beans.property.ObjectPropertyBase;

public class ObjectPropertyAdapter<T> extends ObjectPropertyBase<T> {

	private final ObjectProperty<T> wrappedProperty;

	private final ChangeListener<T> changeListener;


	public ObjectPropertyAdapter(ObjectProperty<T> property) {
		wrappedProperty = property;

		changeListener = (observable, oldValue, newValue) -> {
			if (!Platform.isFxApplicationThread()) {
				Platform.runLater(() -> {
					invalidated();
					fireValueChangedEvent();
				});
			}
		};

		wrappedProperty.addListener(changeListener);
	}

	@Override
	public Object getBean() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public T get() {
		return wrappedProperty.get();
	}

	@Override
	public void set(T value) {
		wrappedProperty.set(value);
	}

	@Override
	public void unbind() {
		super.unbind();

		if (nonNull(wrappedProperty)) {
			wrappedProperty.removeListener(changeListener);
		}
	}

}
