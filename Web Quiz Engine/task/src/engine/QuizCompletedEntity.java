package engine;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.catalina.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.bind.annotation.Mapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizCompletedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuizEntity quiz;

    private LocalDateTime completedAt;

    QuizCompletedEntity() {}

    QuizCompletedEntity(UserEntity user, QuizEntity quiz) {
        this.user = user;
        this.quiz = quiz;
        this.completedAt = LocalDateTime.now();
    }

    public long getId() {
        return quiz.getId();
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
