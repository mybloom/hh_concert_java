package kr.hhplus.be.server.domainold.concert.domain;

import java.util.Optional;

public interface ConcertRepository {

    Optional<Concert> findById(Long id);
}
