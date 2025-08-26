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
    private int quantity = 1;
    private String subTotal;

    public Product(String title, String price) {
        this.title = title;
        this.price = price;
        this.quantity = 1;
        updateSubTotal(); // Auto-calculate subtotal
    }

    public Product(String title, int quantity, String subTotal) {
        this.title = title;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public void updateSubTotal() {
        try {
            String numericPrice = price.replaceAll("[^\\d.]", "");
            double priceVal = Double.parseDouble(numericPrice);
            this.subTotal = String.format("$%.2f", priceVal * quantity);
        } catch (NumberFormatException | NullPointerException e) {
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
                this.quantity == other.quantity &&
                Objects.equals(this.price, other.price);
    }

    public boolean equalsByTitleQuantityAndSubTotal(Product other) {
        if (other == null) return false;
        return Objects.equals(this.title, other.title) &&
                this.quantity == other.quantity &&
                Objects.equals(this.subTotal, other.subTotal);
    }

    public boolean equalsByTitlePriceQuantityAndSubTotal(Product other) {
        if (other == null) return false;
        return Objects.equals(this.title, other.title) &&
                Objects.equals(this.price, other.price) &&
                this.quantity == other.quantity &&
                Objects.equals(this.subTotal, other.subTotal);
    }

    public String getProductInfo() {
        return String.format(
                "Product [Title='%s', Price='%s', Quantity=%d, SubTotal='%s']",
                title != null ? title : "N/A",
                price != null ? price : "N/A",
                quantity,
                subTotal != null ? subTotal : "N/A"
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (!Objects.equals(title, product.title)) return false;
        return Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
