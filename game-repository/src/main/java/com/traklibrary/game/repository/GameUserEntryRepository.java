package com.traklibrary.game.repository;

import com.traklibrary.game.domain.GameUserEntry;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameUserEntryRepository extends PagingAndSortingRepository<GameUserEntry, Long>, JpaSpecificationExecutor<GameUserEntry> {
}
