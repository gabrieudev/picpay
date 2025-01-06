package br.com.gabrieudev.picpay.infrastructure.persistence.redis;

import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.picpay.infrastructure.persistence.models.WalletModel;

@Repository
public class WalletRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    private String KEY_PREFIX = "wallet:";

    public WalletRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(WalletModel wallet) {
        redisTemplate.opsForValue().set(KEY_PREFIX + wallet.getId(), wallet);
    }

    public Object findById(UUID id) {
        return redisTemplate.opsForValue().get(KEY_PREFIX + id);
    }

    public void delete(UUID id) {
        redisTemplate.delete(KEY_PREFIX + id);
    }
}
