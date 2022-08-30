package site.easystartup.web.forum.repo;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.web.forum.domain.model.Discussion;

public interface DiscussionRepo extends CrudRepository<Discussion, Long> {
}
