package lebed.ecommerce.service.cache;

import lebed.ecommerce.model.cart.CartItem;

import java.util.List;

public interface Cache {

    void removeItem(String key);

    List<CartItem> getList(String key);

    List<CartItem> addItemToList(String key, Object item);

    List<CartItem> removeItemFromList(String key, Object item);

    Object getItem(String key, Class type);

    Object setItem(String key, Object item);
}
