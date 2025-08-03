package models;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private String title;
    private String price;
    private String quantity = "1";
    private String subTotal;

    public Product(String title, String price) {
        this.title = title;
        this.price = price;
        this.quantity = "1";
        updateSubTotal(); // tự động tính subtotal
    }

    public Product(String title, String quantity, String subTotal) {
        this.title = title;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public void updateSubTotal() {
        try {
            String numericPrice = price.replaceAll("[^\\d.]", "");
            double priceVal = Double.parseDouble(numericPrice);
            int quantityVal = Integer.parseInt(quantity);
            this.subTotal = String.format("$%.2f", priceVal * quantityVal);
        } catch (NumberFormatException e) {
            this.subTotal = "$0.00";
        }
    }

    public boolean equalsByTitleAndPrice(Product other) {
        if (other == null) return false;
        return Objects.equals(this.title, other.title) &&
                Objects.equals(this.price, other.price);
    }

    public boolean equalsByTitleQuantityAndPrice(Product other) {
        if (other == null) return false;
        return Objects.equals(this.title, other.title) &&
                Objects.equals(this.quantity, other.quantity) &&
                Objects.equals(this.price, other.price);
    }

    public boolean equalsByTitleQuantityAndSubTotal(Product other) {
        if (other == null) return false;
        return Objects.equals(this.title, other.title) &&
                Objects.equals(this.quantity, other.quantity) &&
                Objects.equals(this.subTotal, other.subTotal);
    }

    public boolean equalsByTitlePriceQuantityAndSubTotal(Product other) {
        if (other == null) return false;
        return Objects.equals(this.title, other.title) &&
                Objects.equals(this.price, other.price)&&
                Objects.equals(this.quantity, other.quantity) &&
                Objects.equals(this.subTotal, other.subTotal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (title != null ? !title.equals(product.title) : product.title != null) return false;
        return price != null ? price.equals(product.price) : product.price == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
