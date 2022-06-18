package co.pivoterra.repository;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import co.pivoterra.entity.impl.UserEntityImpl;

public interface UserRepository extends PagingAndSortingRepository<UserEntityImpl, Long> {

  Optional<UserEntityImpl> findByName(String name);

}
