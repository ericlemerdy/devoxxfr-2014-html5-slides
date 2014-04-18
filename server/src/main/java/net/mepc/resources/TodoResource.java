package net.mepc.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import com.google.inject.Inject;

import net.mepc.model.Todo;
import net.mepc.repository.db.MongoServer;

import java.util.ArrayList;
import java.util.List;

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
	
	@Consumes(APPLICATION_JSON)
	public Response create(Todo todo) {
		ObjectId id = (ObjectId) mongoCollection.insert(todo).getUpsertedId();
		return ok(id.toHexString()).build();
	}

    @PUT
    @Consumes(APPLICATION_JSON)
    public Response update(Todo todo) {
        System.out.println(todo);
        if (todo.get_id() != null && todo.get_id().length() == 0) {
            todo.set_id(null);
        }
        mongoCollection.withWriteConcern(WriteConcern.JOURNALED).save(todo);
        return ok(todo.get_id()).build();
    }

    @OPTIONS
    public Response options() {
        return ok().build();
    }
}
