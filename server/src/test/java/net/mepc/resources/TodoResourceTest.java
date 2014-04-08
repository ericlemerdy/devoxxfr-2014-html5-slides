package net.mepc.resources;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import net.mepc.model.Todo;
import net.mepc.repository.db.MongoServer;

import org.jongo.Find;
import org.jongo.MongoCollection;
import org.junit.Test;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;



public class TodoResourceTest {
	@Test
	public void with_1_entry_in_db_when_listing_todos_should_return_1_todo() {
		MongoServer mongoServer = mock(MongoServer.class);
		MongoCollection collection = mock(MongoCollection.class);
		when(mongoServer.getCollection("todo")).thenReturn(collection);
		Find find = mock(Find.class);
		when(collection.find()).thenReturn(find);
		Todo todo = new Todo();
		when(find.as(Todo.class)).thenReturn(newArrayList(todo));
		TodoResource resource = new TodoResource(mongoServer);
		
		Response response = resource.list();
		
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(200);
		Object entity = response.getEntity();
		assertThat(entity).isNotNull();
		assertThat(entity).isInstanceOf(List.class);
		List<Todo> todos = (List<Todo>) entity;
		assertThat(todos).hasSize(1);
		assertThat(todos.get(0)).isEqualTo(todo);
	}
	
	@Test
	public void with_no_entry_in_db_when_listing_todos_should_return_204() {
		MongoServer mongoServer = mock(MongoServer.class);
		MongoCollection collection = mock(MongoCollection.class);
		when(mongoServer.getCollection("todo")).thenReturn(collection);
		Find find = mock(Find.class);
		when(collection.find()).thenReturn(find);
		when(find.as(Todo.class)).thenReturn(new ArrayList<Todo>());
		TodoResource resource = new TodoResource(mongoServer);
		
		Response response = resource.list();

		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(204);
	}
}
