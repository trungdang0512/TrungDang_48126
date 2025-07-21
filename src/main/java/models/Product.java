package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private String title;
    private String price;
    private String quantity;
    private String subTotal;

    public Product(String title, String price) {
        this.title = title;
        this.price = price;
    }

    public Product(String title, String quantity, String subTotal) {
        this.title = title;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }
}
