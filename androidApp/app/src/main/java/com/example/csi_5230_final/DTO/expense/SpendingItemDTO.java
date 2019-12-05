package com.example.csi_5230_final.DTO.expense;

public class SpendingItemDTO {
    private String category_primary;
    private String category_secondary;
    private int count;
    private float total;

    public String getCategory_primary() {
        return category_primary;
    }

    public void setCategory_primary(String category_primary) {
        this.category_primary = category_primary;
    }

    public String getCategory_secondary() {
        return category_secondary;
    }

    public void setCategory_secondary(String category_secondary) {
        this.category_secondary = category_secondary;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
