package net.mepc.model;

import org.jongo.marshall.jackson.oid.ObjectId;

public class Todo {
	private String todoMessage;
	@ObjectId
	private String _id;

	public String getTodoMessage() {
		return todoMessage;
	}

	public void setTodoMessage(String todoMessage) {
		this.todoMessage = todoMessage;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
}
