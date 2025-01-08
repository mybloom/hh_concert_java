package kr.hhplus.be.server.domain.queuetoken.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class QueueTokenTestSample {


    @DisplayName("대기열 토큰 존재 확인")
    @Nested
    class TokenExistenceTests {

        @DisplayName("토큰 상태를 WAITING 으로 설정해 반환한다")
        @Test
        void returnWaitingToken() {

        }


        @DisplayName("토큰 상태를 RUNNING 으로 설정해 반환한다")
        @Test
        void returnRunningToken() {

        }

        @DisplayName("사용자 대기 토큰이 존재할 경우, 토큰이 WAITING이면 해당 토큰을 반환한다")
        @Test
        void returnWaitingTokenIfExists() {

        }

        @DisplayName("사용자 대기 토큰이 존재할 경우, 토큰이 WAITING이면 해당 토큰을 반환한다")
        @Test
        void returnRunningTokenIfExists() {

        }
    }


}