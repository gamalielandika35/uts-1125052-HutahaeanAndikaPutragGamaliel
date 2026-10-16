package CoffeeShop.Model;

import java.util.List;

public interface Customizable {
    List<String> getCustomizations();
    void customize(int[] customizations);
}
