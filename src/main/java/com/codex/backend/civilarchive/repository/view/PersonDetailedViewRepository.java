package com.codex.backend.civilarchive.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.codex.backend.civilarchive.entity.view.PersonDetailedView;

@Repository
public interface PersonDetailedViewRepository extends JpaRepository<PersonDetailedView, Long>, JpaSpecificationExecutor<PersonDetailedView> {

}
