package engine;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import java.io.Serializable;
import java.util.List;

@Entity
public class QuizEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserEntity user;


    @NotBlank(message = "Field 'title' cannot be blank!")
    private String title;

    @NotBlank(message = "Field 'test' cannot be blank!")
    private String text;

    @NotNull
    @Size(min = 2, message = "Field 'options' should contain at least 2 items!")
    // @ElementCollection() tells JPA to automatically create a table with options etc.
    @ElementCollection()
    private List<String> options;

    // Hide the answer from the produced JSON object but accept the value
    // when creating a new quiz to save it in the database.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // @ElementCollection() tells JPA to automatically create a table with options etc.
    @ElementCollection()
    private List<Integer> answer;

    public QuizEntity() {
    }

    //copy constructor to be returned to the client
    public QuizEntity(int id, String title, String text, List<String> options) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public QuizEntity clone() {
        // The copy-constructor
        return new QuizEntity(this.getId(), this.getTitle(), this.getText(), this.getOptions());
    }
}