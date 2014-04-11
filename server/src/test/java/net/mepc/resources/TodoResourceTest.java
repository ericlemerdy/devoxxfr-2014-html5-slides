package net.mepc.resources;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.jongo.Find;
import org.jongo.MongoCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mongodb.WriteResult;

import net.mepc.model.Todo;
import net.mepc.repository.db.MongoServer;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TodoResourceTest {
	@Mock
	private MongoServer mongoServer;
	@Mock
	private MongoCollection collection;
	@Mock
	private Find find;
	@Mock
	private WriteResult writeResult;

	@Test
	public void with_1_entry_in_db_when_listing_todos_should_return_1_todo() {
		when(mongoServer.getCollection("todo")).thenReturn(collection);
		when(collection.find()).thenReturn(find);
		Todo todo = new Todo();
		when(find.as(Todo.class)).thenReturn(newArrayList(todo));
		TodoResource resource = new TodoResource(mongoServer);

		Response response = resource.list();

		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(200);
		List<Todo> todos = (List<Todo>) response.getEntity();
		assertThat(todos).hasSize(1).startsWith(todo);
	}

	@Test
	public void with_no_entry_in_db_when_listing_todos_should_return_204() {
		when(mongoServer.getCollection("todo")).thenReturn(collection);
		when(collection.find()).thenReturn(find);
		when(find.as(Todo.class)).thenReturn(new ArrayList<Todo>());
		TodoResource resource = new TodoResource(mongoServer);

		Response response = resource.list();

		assertThat(response.getStatus()).isEqualTo(204);
	}

	@Test
	public void when_puting_todo_should_add_todo_in_db() {
		Todo todo = new Todo();
		when(mongoServer.getCollection("todo")).thenReturn(collection);
		when(collection.insert(todo)).thenReturn(writeResult);
		ObjectId fakeId = new ObjectId();
		when(writeResult.getUpsertedId()).thenReturn(fakeId);
		TodoResource resource = new TodoResource(mongoServer);

		Response response = resource.create(todo);

		assertThat(response.getStatus()).isEqualTo(200);
		verify(collection).insert(todo);
		assertThat(response.getEntity()).isEqualTo(fakeId.toHexString());
	}

	@Test
	public void when_posting_todo_should_add_todo_in_db() {
		Todo todo = new Todo();
		when(mongoServer.getCollection("todo")).thenReturn(collection);
		when(collection.insert(todo)).thenReturn(writeResult);
		ObjectId fakeId = new ObjectId();
		when(writeResult.getUpsertedId()).thenReturn(fakeId);
		TodoResource resource = new TodoResource(mongoServer);

		Response response = resource.update(todo);

		assertThat(response.getStatus()).isEqualTo(204);
		verify(collection).save(todo);
		assertThat(response.getEntity()).isNull();
	}
}
