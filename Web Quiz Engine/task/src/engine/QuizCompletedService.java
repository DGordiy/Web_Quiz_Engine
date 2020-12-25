package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizCompletedService {

    @Autowired
    QuizCompletedRepository quizCompletedRepository;

    public Page<QuizCompletedEntity> findAllByUser(UserEntity user, int page, int pageSize) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("completedAt")));

        Page<QuizCompletedEntity> pagedResult = quizCompletedRepository.findByUser(user, paging);

        if (pagedResult.hasContent()) {
            return pagedResult;
        } else {
            return Page.empty();
        }
    }
}
