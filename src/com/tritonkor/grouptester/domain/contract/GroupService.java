package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.GroupAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.util.Optional;

public interface GroupService extends Service<Group> {

    Group findByName(String name);

    Group findByUser(User user);

    Group add(GroupAddDto groupAddDto);

}
