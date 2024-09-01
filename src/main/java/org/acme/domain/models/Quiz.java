package org.acme.domain.models;

import org.acme.domain.exceptions.QuestionAlreadyExistsException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Quiz implements Serializable {
    private UserAccount quizOwner;
    private String name;
    private String description;

    private Set<Question> questions;

    public Quiz(UserAccount quizOwner, String name, String description, List<Question> questions) {
        this.quizOwner = quizOwner;
        this.name = name;
        this.description = description;
        this.questions = new HashSet<>(questions);
    }

    public Quiz(UserAccount quizOwner, String name, String description) {
        this.quizOwner = quizOwner;
        this.name = name;
        this.description = description;
        this.questions = new HashSet<>();
    }

    public Quiz(UserAccount quizOwner, String name) {
        this.quizOwner = quizOwner;
        this.name = name;
        this.questions = new HashSet<>();
    }

    public UserAccount getQuizOwner() {
        return quizOwner;
    }

    public void setQuizOwner(UserAccount quizOwner) {
        this.quizOwner = quizOwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addQuestion(Question question) throws QuestionAlreadyExistsException {
        if(questions.stream().anyMatch(q -> q.getTitle().equals(question.getTitle()))){
            throw new QuestionAlreadyExistsException(question.getTitle());
        }
        this.questions.add(question);
    }
}
