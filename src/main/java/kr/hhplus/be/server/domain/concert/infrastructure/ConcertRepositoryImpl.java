package kr.hhplus.be.server.domain.concert.infrastructure;

import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.concert.domain.Concert;
import kr.hhplus.be.server.domain.concert.domain.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public Optional<Concert> findById(Long id) {
        return concertJpaRepository.findById(id);
    }

}
