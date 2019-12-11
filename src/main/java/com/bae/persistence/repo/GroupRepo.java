package com.bae.persistence.repo;

import com.bae.persistence.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group,Long> {
}
