package org.acme.services.persistence.impl.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.inject.Inject;
import org.acme.domain.models.Quiz;
import org.acme.services.persistence.QuizPersistenceService;
import org.bson.Document;

public class QuizMongoDBService implements QuizPersistenceService {
    @Inject
    MongoClient mongoClient;

    @Override
    public void createQuiz(Quiz quiz) {
        Document document = new Document()
                .append("quiz_owner", quiz.getQuizOwner())
                .append("title", quiz.getTitle())
                .append("description", quiz.getDescription());


        getCollection().insertOne(document);
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("quizu").getCollection("quiz");
    }
}
