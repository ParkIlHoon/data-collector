package io.hoon.datacollector.common.aop;

import io.hoon.datacollector.common.event.DataCollectEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * <h1>AOP Advisor</h1>
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AopAdvisor {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * PublishDataCollectEvent 어노테이션이 붙은 메서드의 AOP 처리
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(PublishDataCollectEvent)")
    public Object eventPublish(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        if (proceed != null) {
            applicationEventPublisher.publishEvent(new DataCollectEvent());
            log.info("이벤트를 발행했습니다.");
        }
        return proceed;
    }
}
