package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.domain.dto.GroupAddDto;
import com.tritonkor.grouptester.domain.exception.EntityNotFoundException;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.GroupRepository;
import java.util.Set;

public class GroupServiceImpl extends GenericService<Group> implements GroupService {

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        super(groupRepository);
        this.groupRepository = groupRepository;
    }

    @Override
    public Set<Group> getAll() {
        return getAll(g -> true);
    }

    @Override
    public Group findByName(String name) {
        return groupRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("This group does not exist."));
    }

    @Override
    public Group findByUser(User user) {
        return groupRepository.findByUser(user).orElse(null);
    }

    @Override
    public Group add(GroupAddDto groupAddDto) {
        try {
            var group = Group.builder().id(groupAddDto.getId()).name(groupAddDto.getName())
                    .users(groupAddDto.getUsers()).createdAt(groupAddDto.getCreatedAt()).build();
            groupRepository.add(group);
            return group;
        } catch (RuntimeException e) {
            throw new SignUpException("Error when saving group: %s"
                    .formatted(e.getMessage()));
        }
    }

}
