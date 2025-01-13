package kr.hhplus.be.server.domain.concert.domain;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository {

    Optional<Concert> findById(Long id);
}
