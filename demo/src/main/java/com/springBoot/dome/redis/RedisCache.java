package com.springBoot.dome.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisCache implements Cache {
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id; // cache instance id
    @SuppressWarnings("rawtypes")
	private RedisTemplate redisTemplate;
    
    private static final long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间

    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void putObject(Object key, Object value) {
		// TODO Auto-generated method stub
		try {
            RedisTemplate redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            opsForValue.set(key, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
            logger.debug("Put query result to redis");
        }
        catch (Throwable t) {
            logger.error("Redis put failed", t);
        }
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getObject(Object key) {
		// TODO Auto-generated method stub
		try {

            RedisTemplate redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            logger.debug("Get cached query result from redis");
            System.out.println("****"+opsForValue.get(key).toString());
            return opsForValue.get(key);
        }
        catch (Throwable t) {
            logger.error("Redis get failed, fail over to db", t);
            return null;
        }
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object removeObject(Object key) {
		// TODO Auto-generated method stub
		try {
            RedisTemplate redisTemplate = getRedisTemplate();
            redisTemplate.delete(key);
            logger.debug("Remove cached query result from redis");
        }
        catch (Throwable t) {
            logger.error("Redis remove failed", t);
        }
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return null;
        });
        logger.debug("Clear all the cached query result from redis");	
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		// TODO Auto-generated method stub
		return readWriteLock;
	}

	@SuppressWarnings("rawtypes")
	private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
