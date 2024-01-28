package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.GroupAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import java.util.Optional;

public interface GroupService extends Service<Group> {

    Group findByName(String name);

    Group add(GroupAddDto groupAddDto);
}
