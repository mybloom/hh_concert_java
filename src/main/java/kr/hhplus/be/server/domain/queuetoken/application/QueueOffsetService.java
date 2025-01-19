package kr.hhplus.be.server.domain.queuetoken.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.QUEUE_OFFSET_NOT_FOUND;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.queuetoken.model.QueueOffset;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueOffsetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class QueueOffsetService {

    private static final long FIRST_OFFSET_ID = 1L;
    private final QueueOffsetRepository queueOffsetRepository;

    public QueueOffset retrieveQueueOffset() {
        QueueOffset queueOffset = queueOffsetRepository.findById(FIRST_OFFSET_ID)
            .orElseThrow(() -> new BusinessIllegalArgumentException(QUEUE_OFFSET_NOT_FOUND));
        return queueOffset;
    }

    public void updateLastActiveOffset(long lastActiveOffset) {
        QueueOffset queueOffset = retrieveQueueOffset();
        queueOffset.setLastActiveOffset(lastActiveOffset);

        queueOffsetRepository.save(queueOffset);
    }

}
