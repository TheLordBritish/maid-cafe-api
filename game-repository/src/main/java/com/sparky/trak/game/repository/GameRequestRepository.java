package com.sparky.trak.game.repository;

import com.sparky.trak.game.domain.GameRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameRequestRepository extends PagingAndSortingRepository<GameRequest, Long> {
}