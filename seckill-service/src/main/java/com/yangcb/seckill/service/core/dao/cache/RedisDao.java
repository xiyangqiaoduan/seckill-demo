package com.yangcb.seckill.service.core.dao.cache;

import com.yangcb.seckill.model.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

	private JedisPool jedisPool;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public RedisDao(String ip, int port) {

		jedisPool = new JedisPool(ip, port);

	}

	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

	public Seckill getSeckill(long seckillId) {

		// redis 操作逻辑 内部没有序列化操作
		// get->byte[] ->反序列化->object[Seckill]

		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				// 反序列化 获取对象信息
				// prostuff pojo
				byte[] bytes = jedis.get(key.getBytes());
				// 缓存重新获取
				if (bytes != null && bytes.length > 0) {
					// 空对象
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}

			} finally {
				jedis.close();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}
	//存储秒杀对象
	public String putSeckill(Seckill seckill) {
		// set -》字节数组-》redis
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				// 超时缓存
				int timeout = 60 * 60;
				String result=jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} finally {
				jedis.close();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
