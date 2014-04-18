package net.mepc.model;

import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.Objects;

import static com.google.common.base.Objects.*;

public class Todo {
	private String title;
	private boolean completed;
	@ObjectId
	private String _id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("title", title).add("completed", completed).add("_id", _id).toString();
	}
}
