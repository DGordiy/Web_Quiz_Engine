package engine;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class QuizController {
    //final static Logger logger = LoggerFactory.getLogger(Quiz.class);

    @Autowired
    QuizRepository quizRepo;
    @Autowired
    QuizService quizService;
    @Autowired
    QuizCompletedRepository quizCompletedRepository;
    @Autowired
    QuizCompletedService quizCompletedService;
    @Autowired
    UserRepository userRepository;

    // Solve a quiz - none or more answers to a question.
    @PostMapping(value = "/api/quizzes/{id}/solve",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnswerResponse handleAnswer(@PathVariable @Min(1) int id, @RequestBody @NotNull TheAnswer theAnswer)
            throws ResponseStatusException {

        QuizEntity quiz = quizRepo.findById(id).orElse(null);
        if (quiz == null) {
            //logger.info(
            //        String.format("The id %d for the question for answer %s was not found.",
            //                id, theAnswer.getAnswer().toString()));
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        if (!quiz.getAnswer().toString().equals(theAnswer.getAnswer().toString())) {
            //logger.warn(String.format("The expected answer is %s. But the given answer was %s",
            //        quiz.getAnswer().toString(), theAnswer.getAnswer().toString()));
            return new AnswerResponse(false,
                    "Wrong answer! Please, try again.");
        }

        quizCompletedRepository.save(new QuizCompletedEntity(getCurrentUser(), quiz));
        return new AnswerResponse(true, "Congratulations, you're right!");
    }

    // Create a new quiz!
    @PostMapping("/api/quizzes")
    public QuizEntity addNewQuiz(@RequestBody @Valid QuizEntity newQuiz) {
        UserEntity userEntity = getCurrentUser();
        newQuiz.setUser(userEntity);
        quizRepo.save(newQuiz);
        //logger.info(
//                String.format("A new quiz, named '%s', with id '%d' for user '%s', with id '%d' was saved!", newQuiz.getTitle(), newQuiz.getId(), newQuiz.getUser().getEmail(), newQuiz.getUser().getId())
//        );

        return newQuiz.clone();
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizEntity theQuizByID(@PathVariable int id) throws ResponseStatusException {
//        logger.info("param id:" + id);

            return quizRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity " + id + " not found"
            ));
    }

    @DeleteMapping("/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTheQuizByID(@PathVariable int id) throws ResponseStatusException {
//        logger.info("param TheQuiz id:" + id);
        QuizEntity quizEntity = quizRepo.findById(id).orElse(null);
        if (quizEntity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity " + id + " not found"
            );
        }

        UserEntity userEntity = getCurrentUser();
        UserEntity quizUser = quizEntity.getUser();
        if (quizUser.getId() != userEntity.getId()) {
//            logger.info("TheQuiz " + " with id:" + id + "does not belong to the user " + theUser.getEmail());
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "TheQuiz does not belong to the current user!"
            );
        }
        quizRepo.delete(quizEntity);
    }

    @GetMapping("/api/quizzes")
    public Page<QuizEntity> allQuizzes(@RequestParam(defaultValue = "0") int page) {
        return quizService.findAll(page, 10, "id");
    }

    @GetMapping("/api/quizzes/completed")
    public Page<QuizCompletedEntity> completedQuizzes(@RequestParam(defaultValue = "0") int page) {
        System.out.println("Completed " + page);
        return quizCompletedService.findAllByUser(getCurrentUser(), page, 10);
    }

    private UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Getting user by email: " + email);
        return userRepository.findByEmail(email);
    }
}