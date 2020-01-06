package com.bae.persistence.repo;

import com.bae.persistence.domain.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringRepo extends JpaRepository<Gathering,Long> {
}
