package net.mepc.resources;

import static javax.ws.rs.core.Response.ok;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.mepc.model.Todo;
import net.mepc.repository.db.MongoServer;

import org.jongo.MongoCollection;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

@Path("/")
public class TodoResource extends AbstractResource {
	private static final String COLLECTION = "todo";
	private MongoCollection mongoCollection;
	
	@Inject
	public TodoResource(MongoServer mongoServer) {
		this.mongoCollection = mongoServer.getCollection(COLLECTION);
	}
	
	@GET
	public Response list() {
		List<Todo> todos = new ArrayList<Todo>();
		Iterable<Todo> todosIterable = mongoCollection.find().as(Todo.class);
		for (Todo todo : todosIterable) {
			todos.add(todo);
		}
		ResponseBuilder response;
		if (todos.isEmpty()) {
			response = Response.noContent();
		} else {
			response = ok(todos);
		}
		return response.build();
	}
}
