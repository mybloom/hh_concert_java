package kr.hhplus.be.server.domain.point.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.USER_NOT_FOUND;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointCommandRepository;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointUseService {

    private final PointQueryRepository pointQueryRepository;
    private final PointCommandRepository pointCommandRepository;

    @Transactional
    public long use(long userId, long amount) {
        Point point = pointQueryRepository.findByUserIdWithLock(userId)
            .orElseThrow(() -> new BusinessIllegalArgumentException(USER_NOT_FOUND));
        point.use(amount);

        point = pointCommandRepository.save(point);
        return point.getBalance();
    }

}
