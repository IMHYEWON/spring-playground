package com.hyewon.springplayground.redis.service;

import com.hyewon.springplayground.redis.dto.DummyDto;
import com.hyewon.springplayground.redis.domain.Stock;
import com.hyewon.springplayground.redis.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createStock(Long id, Long quantity) {
        Stock stock = new Stock(id, quantity);
        stock.increase(quantity);
        stockRepository.saveAndFlush(stock);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity) {
         Stock stock = stockRepository.findByProductId(id);
         stock.decrease(quantity);
         stockRepository.saveAndFlush(stock);
    }

    /**
     * save : JPA에서 엔티티를 영속성 컨텍스트에 등록하고, 트랜잭션이 커밋될 때까지 기다리는 역할,
     *        save 함수를 호출해도 실제로 DB에 저장되는 시점은 트랜잭션이 커밋될 때
     *        트랜잭션 범위 내에서 동작하며, 많은 데이터를 한번에 등록할 때 성능상 이점
     *
     * saveAndFlush : save와 동일하게 동작하나, 즉시 DB에 반영,
     *                바로 DB에 저장되기 때문에 영속성 컨텍스트에서 해당 엔티티를 제거하지 않고 계속 추적
     *                이후에 트랜잭션 커밋이나 flush 메서드가 호출되면 영속성 컨텍스트의 변경된 내용을 DB에 반영
     *                특정 목적에 의해 즉시 데이터베이스와 동기화할 필요가 있을 때 사용
     *
     * **/

    public void createException() {
        throw new RuntimeException("예외 발생");
    }

    public void createException(DummyDto dummyDto) {
        throw new RuntimeException("예외 발생");
    }
}
