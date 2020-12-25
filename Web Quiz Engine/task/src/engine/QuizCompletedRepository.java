package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletedRepository extends PagingAndSortingRepository<QuizCompletedEntity, Integer> {
    Page<QuizCompletedEntity> findByUser(UserEntity user, Pageable pageable);
}
