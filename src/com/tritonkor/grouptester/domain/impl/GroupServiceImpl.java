package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.GroupRepository;
import java.util.Optional;

public class GroupServiceImpl extends GenericService<Group> implements GroupService {

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        super(groupRepository);
        this.groupRepository = groupRepository;
    }

    @Override
    public Optional<Group> findByName(String name) {
        return groupRepository.findByName(name);
    }

}
