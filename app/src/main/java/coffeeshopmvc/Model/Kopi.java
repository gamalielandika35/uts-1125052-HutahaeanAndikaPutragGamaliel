package coffeeshopmvc.Model;

import java.util.List;

public class Kopi extends MenuItem  {
    private int rum;
    private int es;
    private int gula;
    private int karamel;

    public Kopi(String id, String nama, double hargaBase, int stok) {
        super(id, nama, hargaBase, stok);
    }

    
    public boolean bisaPilihUkuran() {
        return true;
    }

    
    public void customize(List<Integer> customizations) {
        customize(konversiCustomisasi(customizations, 4));
    }

   
    public void customize(int[] customizations) {
        int[] values = validasiPanjangArray(customizations, 4);
        rum = validasiRentang(values[0], 0, 1, "Rum");
        es = validasiRentang(values[1], 0, 5, "Es");
        gula = validasiRentang(values[2], 0, 5, "Gula");
        karamel = validasiRentang(values[3], 0, 5, "Karamel");
    }

 
    public double hitungHargaTotal() {
        return getHargaBase() + (rum * 3000) + (karamel * 3000);
    }

    
    public String getOrderDetail() {
        return getNama() + " | Rum: " + rum + ", Es: " + es + ", Gula: " + gula
                + ", Karamel: " + karamel;
    }

   
    public List<String> getCustomizationPrompts() {
        return List.of("Rum (0-1)", "Es (0-5)", "Gula (0-5)", "Karamel (0-5)");
    }
}