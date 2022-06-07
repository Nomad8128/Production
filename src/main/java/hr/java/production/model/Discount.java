package hr.java.production.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Record that has a discount value
 */
public record Discount(BigDecimal popust) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount)) return false;
        Discount discount = (Discount) o;
        return Objects.equals(popust, discount.popust);
    }

    @Override
    public int hashCode() {
        return Objects.hash(popust);
    }
}
