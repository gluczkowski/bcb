package com.BCB.bcb.domain.dbo.clientperson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientPersonRepository extends JpaRepository<ClientPerson, Integer> {
    Page<ClientPerson> findAll(Pageable pageable);
}
