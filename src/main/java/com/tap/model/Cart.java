package com.tap.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    
    // The HashMap stores the itemId as the Key, and the CartItem object as the Value
    private Map<Integer, CartItem> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    // 1. Add an item to the cart
    public void addItem(CartItem item) {
        int itemId = item.getItemId();
        
        // Enforce Single Restaurant Rule
        if (!items.isEmpty()) {
            // Check the restaurant ID of the items currently in the cart
            int existingRestaurantId = items.values().iterator().next().getRestaurantId();
            
            // If the new item is from a different restaurant, clear the cart completely
            if (existingRestaurantId != item.getRestaurantId()) {
                items.clear();
            }
        }

        // If the exact same food item is already in the cart, just update the quantity
        if (items.containsKey(itemId)) {
            CartItem existingItem = items.get(itemId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            // Otherwise, put the new item into the map
            items.put(itemId, item);
        }
    }

    // 2. Update the quantity of a specific item
    public void updateItem(int itemId, int quantity) {
        if (items.containsKey(itemId)) {
            if (quantity <= 0) {
                // If quantity drops to 0 or below, remove the item entirely
                items.remove(itemId);
            } else {
                items.get(itemId).setQuantity(quantity);
            }
        }
    }

    // 3. Remove a specific item from the cart
    public void removeItem(int itemId) {
        items.remove(itemId);
    }

    // 4. Retrieve all items currently in the cart
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    // 5. Empty the cart completely
    public void clear() {
        items.clear();
    }
    
    // 6. Calculate the grand total for checkout
    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getSubTotal();
        }
        return total;
    }
}