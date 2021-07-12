package com.atjingbo.pojo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// 购物车对象
public class Cart {
    // private Integer totalCount;
    // private BigDecimal totalPrice;


    // key 是商品编号。value 是商品信息
    private Map<Integer, CartItem> items = new HashMap<>();

    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem){
        // 先查看购物车中是否已经添加过此商品，如果已经添加过，则数量累加，总金额更新；如果没有添加过，那么直接放到集合中即可
        CartItem item = items.get(cartItem.getId());

        if(item == null){
            // 之前没添加过该商品
            items.put(cartItem.getId(), cartItem);
        }else {
            // 之前已经添加过该商品
            item.setCount(item.getCount() + 1);// 数量累加
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));// 更新总金额
        }
    }

    /**
     * 删除商品项
     * @param id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }

    /**
     * 清空商品项
     */
    public void clear(){
        items.clear();
    }

    /**
     * 修改商品数量
     * @param id
     * @param count
     */
    public void update(Integer id, Integer count){
        // 先查看购物车中是否已经添加过此商品，如果已经添加过，则数量累加，更新数量
        CartItem item = items.get(id);

        if(item != null){
            // 之前已经添加过该商品
            item.setCount(count);// 修改商品数量
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));// 更新总金额
        }
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for(Map.Entry<Integer, CartItem> entry : items.entrySet()){
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }


    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for(Map.Entry<Integer, CartItem> entry : items.entrySet()){
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }


    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
