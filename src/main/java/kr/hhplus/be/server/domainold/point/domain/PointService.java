package kr.hhplus.be.server.domainold.point.domain;

public interface PointService {

    long charge(long userId, long amount);

    long use(long userId, long amount);

    long getBalance(long userId);
}
