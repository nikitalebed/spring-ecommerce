package lebed.ecommerce.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lebed.ecommerce.model.cart.CartItem;
import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Data
public class RedisCache implements Cache {

    private ObjectMapper objectMapper;
    private Jedis jedis;

    public RedisCache(ObjectMapper objectMapper, Jedis jedis) {
        this.objectMapper = objectMapper;
        this.jedis = jedis;
    }

    @Override
    public Object getItem(String key, Class type) {
        String jsonObject = jedis.get(key);
        try {
            return objectMapper.readValue(jsonObject, type);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Object setItem(String key, Object item) {
        try {
            String jsonItem = objectMapper.writeValueAsString(item);
            String out = jedis.set(key, jsonItem);
            return objectMapper.readValue(out, Object.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void removeItem(String key) {
        jedis.del(key);
    }

    @Override
    public List<CartItem> getList(String key) {
        return getListFromRedis(key);
    }

    @Override
    public List<CartItem> addItemToList(String key, Object item) {
        try {
            String jsonItem = objectMapper.writeValueAsString(item);
            jedis.sadd(key, jsonItem);
            return this.getListFromRedis(key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<CartItem> removeItemFromList(String key, Object item) {
        getListFromRedis(key).forEach(row -> {
            if (row.equals(item)) {
                try {
                    String jsonItem = objectMapper.writeValueAsString(row);
                    jedis.srem(key, jsonItem);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Can't find object in Redis list.");
            }
        });

        return getListFromRedis(key);
    }

    private List<CartItem> getListFromRedis(String key) {
        List<CartItem> list = new ArrayList<>();
        jedis.smembers(key).forEach(jsonItem -> {
            try {
                list.add((CartItem) objectMapper.readValue(jsonItem, CartItem.class));
            } catch (Exception e) {
                e.getMessage();
            }
        });
        return list;
    }
}
