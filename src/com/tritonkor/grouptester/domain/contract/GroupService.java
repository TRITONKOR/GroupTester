package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.GroupAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.User;

/**
 * The GroupService interface defines the contract for group-related operations. It provides methods
 * for finding groups by name or user, adding new groups, and other group-related functionalities.
 */
public interface GroupService extends Service<Group> {

    /**
     * Finds a group by its name.
     *
     * @param name The name of the group to be found.
     * @return The Group object representing the found group, or null if no group is found with the
     * given name.
     */
    Group findByName(String name);

    /**
     * Finds a group associated with the specified user.
     *
     * @param user The User object for which to find the associated group.
     * @return The Group object representing the group associated with the given user, or null if no
     * associated group is found.
     */
    Group findByUser(User user);

    /**
     * Adds a new group based on the provided GroupAddDto.
     *
     * @param groupAddDto The GroupAddDto containing the details for creating a new group.
     * @return The Group object representing the newly added group.
     */
    Group add(GroupAddDto groupAddDto);
}
