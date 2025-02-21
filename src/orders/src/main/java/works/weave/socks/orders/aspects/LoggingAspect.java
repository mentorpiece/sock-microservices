package works.weave.socks.orders.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import works.weave.socks.orders.entities.CustomerOrder;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* works.weave.socks.orders.repositories.CustomerOrderRepository.findByCustomerId(..)) && args(customerId)")
    public void logBefore(JoinPoint joinPoint, String customerId) {
        logger.info("Method called: {} with parameter customerId={}", joinPoint.getSignature().getName(), customerId);
    }

    @AfterReturning(value = "execution(* works.weave.socks.orders.repositories.CustomerOrderRepository.findByCustomerId(..))", returning = "orders")
    public void logAfterReturning(JoinPoint joinPoint, List<CustomerOrder> orders) {
        logger.info("Method {} returned {} orders", joinPoint.getSignature().getName(), orders.size());
        for (CustomerOrder order : orders) {
            logger.info("Order details: {}", order);
        }
    }
}
