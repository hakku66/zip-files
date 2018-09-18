package com.patwa.view.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Lazy
@Component
@Scope("prototype")
public class TextFieldAutoComplete {
	private final SortedSet<String> entries;
	private ContextMenu entriesPopup;

	public TextFieldAutoComplete() {
		this.entries = new TreeSet<>();
		this.entriesPopup = new ContextMenu();
	}

	private SortedSet<String> getEntries() {
		return this.entries;
	}

	private void addAutoCompleteOptions(String[] options) {
		for (String str : options) {
			getEntries().add(str);
		}
	}

	private void addAutoCompleteOptions(Collection<String> options) {
		for (String str : options) {
			getEntries().add(str);
		}
	}

	private ChangeListener<String> getAutoCompleteTextPropertyListener(TextField node) {
		return new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
				if (node.getText() != null && node.getText().length() == 0) {
					entriesPopup.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					// searchResult.addAll(entries.subSet(getText(), getText() +
					// Character.MAX_VALUE));
					final List<String> filteredEntries = entries.stream()
							.filter(e -> e.toLowerCase().contains(node.getText().toLowerCase()))
							.collect(Collectors.toList());
					searchResult.addAll(filteredEntries);
					if (entries.size() > 0) {
						populatePopup(searchResult, node);
						if (!entriesPopup.isShowing()) {
							entriesPopup.show(node, Side.BOTTOM, 0, 0);
						}
					} else {
						entriesPopup.hide();
					}
				}
			}
		};
	}

	private ChangeListener<Boolean> getAutoCompleteFocusPropertyListener() {
		return new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
					Boolean aBoolean2) {
				entriesPopup.hide();
			}
		};
	}

	private void populatePopup(List<String> searchResult, TextField node) {
		List<CustomMenuItem> menuItems = new LinkedList<>();
		// If you'd like more entries, modify this line.
		int maxEntries = 10;
		int count = Math.min(searchResult.size(), maxEntries);
		for (int i = 0; i < count; i++) {
			final String result = searchResult.get(i);
			Label entryLabel = new Label(result);
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					node.setText(result);
					entriesPopup.hide();
				}
			});
			menuItems.add(item);
		}
		entriesPopup.getItems().clear();

		entriesPopup.getItems().addAll(menuItems);
	}

	public void addAutoCompleteOptions(TextField node, String[] options) {
		addAutoCompleteOptions(options);
		node.textProperty().addListener(getAutoCompleteTextPropertyListener(node));
		node.focusedProperty().addListener(getAutoCompleteFocusPropertyListener());
	}

	public void addAutoCompleteOptions(TextField node, Collection<String> options) {
		addAutoCompleteOptions(options);
		node.textProperty().addListener(getAutoCompleteTextPropertyListener(node));
		node.focusedProperty().addListener(getAutoCompleteFocusPropertyListener());
	}
}