package kr.hhplus.be.server.common;

import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenProperties;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueOffsetRepository;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ControllerIntegrationTest {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected QueueTokenProperties queueTokenProperties;

    @Autowired
    protected QueueOffsetRepository queueOffsetRepository;

    @Autowired
    protected QueueTokenRepository queueTokenRepository;

    @Autowired
    private DataCleaner dataCleaner;

}
