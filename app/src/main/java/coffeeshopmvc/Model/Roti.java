package coffeeshopmvc.Model;

import java.util.List;

public class Roti extends MenuItem implements Customizable {
    private int gula;
    private int meses;
    private int karamel;

    public Roti(String id, String nama, double hargaBase, int stok) {
        super(id, nama, hargaBase, stok);
    }

  
    public void customize(List<Integer> customizations) {
        customize(konversiCustomisasi(customizations, 3));
    }

  
    public void customize(int[] customizations) {
        int[] values = validasiPanjangArray(customizations, 3);
        gula = validasiRentang(values[0], 0, 5, "Gula");
        meses = validasiRentang(values[1], 0, 5, "Meses");
        karamel = validasiRentang(values[2], 0, 5, "Karamel");
    }

    
    public double hitungHargaTotal() {
        return getHargaBase() + (meses * 500) + (karamel * 3000);
    }

    
    public String getOrderDetail() {
        return getNama() + " | Gula: " + gula + ", Meses: " + meses + ", Karamel: " + karamel;
    }

  
    public List<String> getCustomizationPrompts() {
        return List.of("Gula (0-5)", "Meses (0-5)", "Karamel (0-5)");
    }
}
