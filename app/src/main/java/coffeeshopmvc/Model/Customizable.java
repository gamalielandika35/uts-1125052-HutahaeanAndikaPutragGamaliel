package coffeeshopmvc.Model;

import java.util.List;

public interface Customizable {
    void customize(List<Integer> customizations);
    void customize(int[] customizations);
}
