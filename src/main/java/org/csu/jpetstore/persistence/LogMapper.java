package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.UserLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogMapper {
    List<UserLog> getLog();
}
