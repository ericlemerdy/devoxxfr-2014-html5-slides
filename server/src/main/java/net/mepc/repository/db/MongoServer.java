package net.mepc.repository.db;

import com.google.inject.Inject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.gridfs.GridFS;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;
import java.text.MessageFormat;

public class MongoServer {

	private Jongo jongo;
	private DB db;

	@Inject
	public MongoServer(MongoConfiguration configuration) {
		MongoClientURI uri = configuration.getUri();
		try {
			String dbName = configuration.getDb();
			MongoClient mongoClient = new MongoClient(uri);
			db = mongoClient.getDB(dbName);
			this.jongo = new Jongo(db);
		} catch (UnknownHostException e) {
			System.err.println(MessageFormat.format("Unable to connect to mongodb host : {0}", uri));
		}
	}

	public DBCollection getDBCollection(String collectionName) {
		return db.getCollection(collectionName);
	}

	public MongoCollection getCollection(String collectionName) {
		return jongo.getCollection(collectionName);
	}

	public GridFS getGridFs() {
		return new GridFS(db);
	}
}
