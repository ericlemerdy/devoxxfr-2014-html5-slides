package net.mepc.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.mepc.model.Todo;
import net.mepc.repository.db.MongoServer;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

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
	@Produces(APPLICATION_JSON)
	public Response list() {
		List<Todo> todos = new ArrayList<Todo>();
		Iterable<Todo> todosIterable = mongoCollection.find().as(Todo.class);
		for (Todo todo : todosIterable) {
			todos.add(todo);
		}
		ResponseBuilder response;
		if (todos.isEmpty()) {
			response = noContent();
		} else {
			response = ok(todos);
		}
		return response.build();
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	public Response create(Todo todo) {
		ObjectId id = (ObjectId) mongoCollection.insert(todo).getUpsertedId();
		return ok(id.toHexString()).build();
	}
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response update(Todo todo) {
		mongoCollection.save(todo);
		return noContent().build();
	}
}
