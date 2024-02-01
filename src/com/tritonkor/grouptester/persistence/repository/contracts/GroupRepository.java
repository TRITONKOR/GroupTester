package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;

/**
 * The {@code GroupRepository} interface provides methods for interacting with the repository of
 * {@link Group} entities.
 */
public interface GroupRepository extends Repository<Group> {

    /**
     * Finds a group by its name.
     *
     * @param name The name of the group to find.
     * @return An optional containing the group if found, or empty if not found.
     */
    Optional<Group> findByName(String name);

    /**
     * Finds a group associated with a specific user.
     *
     * @param user The user for which to find the associated group.
     * @return An optional containing the group if associated, or empty if not associated.
     */
    Optional<Group> findByUser(User user);
}
