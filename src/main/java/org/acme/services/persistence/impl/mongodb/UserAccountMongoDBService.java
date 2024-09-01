package org.acme.services.persistence.impl.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.exceptions.UserNotFoundException;
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
    public UserAccount getUserAccount(String email) throws UserNotFoundException {
        Document query = new Document("email", email);
        if(getCollection().find(query).first() == null) throw new UserNotFoundException();

        var json = Objects.requireNonNull(
                getCollection().find(query).first()
        ).toJson();

        return UserAccount.fromJson(json);
    }

    @Override
    public void createUserAccount(UserAccount userAccount) {
        Document document = new Document()
                .append("email", userAccount.getEmail())
                .append("password", userAccount.getPassword());

        if(userAccount.hasUsername()){
            document.append("username", userAccount.getUsername());
        }

        getCollection().insertOne(document);
    }

    @Override
    public UserAccount updateUserAccount(String currentEmail, UserAccount userAccountUpdateTo) throws UserNotFoundException {
        Document existingUser = findUserByEmail(currentEmail);

        if (existingUser == null)
            throw new UserNotFoundException();


        getCollection().updateOne(
                Filters.eq("email", currentEmail),
                Updates.combine(
                        Updates.set("email", userAccountUpdateTo.getEmail()),
                        Updates.set("username", userAccountUpdateTo.getUsername()),
                        Updates.set("password", userAccountUpdateTo.getPassword())
                )
        );

        return getUserAccount(userAccountUpdateTo.getEmail());
    }

    @Override
    public void deleteUserAccount(String email) throws UserNotFoundException {

        Document existingUser = findUserByEmail(email);
        if(existingUser == null) {
            throw new UserNotFoundException("Can't delete user that doesn't exist!");
        }

        getCollection().deleteOne(existingUser);
    }

    @Override
    public boolean userExists(String email)
    {
        return getCollection().find(Filters.eq("email", email)).first() != null;
    }

    public Document findUserByEmail(String email) {
        return getCollection().find(Filters.eq("email", email)).first();
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("quizu").getCollection("user");
    }
}
