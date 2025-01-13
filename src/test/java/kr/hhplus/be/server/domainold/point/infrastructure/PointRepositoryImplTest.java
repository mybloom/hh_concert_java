package kr.hhplus.be.server.domainold.point.infrastructure;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import kr.hhplus.be.server.domainold.point.domain.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import(PointRepositoryImpl.class) //@DataJpaTest는 기본적으로 JPA 관련 빈만 로드. PointRepositoryImpl이 별도의 빈이라면 @Import를 사용하여 명시적으로 추가
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:data.sql"})
class PointRepositoryImplTest {

    @Autowired
    private PointRepositoryImpl pointRepository;

    @DisplayName("사용자의 포인트를 조회한다.")
    @Test
    void retrieveUserPoint() {
        //when
        Point point = pointRepository.findByUserId(1L)
            .orElse(null);

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(point).isNotNull();
            softAssertions.assertThat(point.getUserId()).isEqualTo(1L);
            softAssertions.assertThat(point.getBalance()).isEqualTo(   0L);
        });

    }

}