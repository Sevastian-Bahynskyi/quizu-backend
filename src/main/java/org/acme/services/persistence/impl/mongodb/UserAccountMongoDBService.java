package org.acme.services.persistence.impl.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.models.UserAccount;
import org.acme.services.persistence.UserAccountPersistenceService;
import org.bson.Document;
import org.jboss.resteasy.reactive.common.NotImplementedYet;

import java.util.Objects;

@ApplicationScoped
public class UserAccountMongoDBService implements UserAccountPersistenceService {
    @Inject
    MongoClient mongoClient;

    @Override
    public UserAccount getUserAccount(String email) throws Exception {
        Document query = new Document("email", email);
        if(getCollection().find(query).first() == null) return null;

        var json = Objects.requireNonNull(getCollection().find(query)
                .first()).toJson();

        return UserAccount.fromJson(json);
    }

    @Override
    public void createUserAccount(UserAccount userAccount) {
        Document document = new Document()
                .append("email", userAccount.getEmail()) // TODO: encrypt
                .append("password", userAccount.getPassword()); // TODO: encrypt
        getCollection().insertOne(document);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        throw new NotImplementedYet();
    }

    @Override
    public void deleteUserAccount(String email) {
        throw new NotImplementedYet();
    }

    @Override
    public UserAccount updateUserAccountEmail(String oldEmail, String newEmail) {
        throw new NotImplementedYet();
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("quizu").getCollection("user");
    }
}
