package coffeeshopmvc.Model;

import coffeeshopmvc.Exception.InputTidakValidException;
import coffeeshopmvc.Exception.StokTIdakCukupException;
import java.util.List;

public abstract class MenuItem implements Customizable {
    private final String id;
    private final String nama;
    private final double hargaBase;
    private int stok;
    private final EnumKategori kategori;

    public MenuItem(String id, String nama, double hargaBase, int stok, EnumKategori kategori) {
        if (id == null || id.isBlank()) {
            throw new InputTidakValidException("ID menu tidak boleh kosong.");
        }
        if (nama == null || nama.isBlank()) {
            throw new InputTidakValidException("Nama menu tidak boleh kosong.");
        }
        if (hargaBase <= 0) {
            throw new InputTidakValidException("Harga harus lebih dari 0.");
        }
        if (stok < 0) {
            throw new InputTidakValidException("Stok tidak boleh negatif.");
        }
        this.id = id;
        this.nama = nama;
        this.hargaBase = hargaBase;
        this.stok = stok;
        this.kategori = kategori;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaBase() {
        return hargaBase;
    }

    public int getStok() {
        return stok;
    }

    public EnumKategori getKategori() {
        return kategori;
    }

    public void validasiJumlahPesanan(int jumlah) {
        if (jumlah <= 0) {
            throw new InputTidakValidException("Jumlah pesanan harus lebih dari 0.");
        }
        if (jumlah > stok) {
            throw new StokTIdakCukupException("Stok " + nama + " tersisa " + stok + ".");
        }
    }

    public void kurangiStok(int jumlah) {
        validasiJumlahPesanan(jumlah);
        stok -= jumlah;
    }

    public double hitungHargaTotal(int jumlah) {
        validasiJumlahPesanan(jumlah);
        return hitungHargaTotal() * jumlah;
    }

    public double hitungHargaTotal(int jumlah, String kodePromo) {
        double total = hitungHargaTotal(jumlah);
        if (kodePromo != null && kodePromo.trim().equalsIgnoreCase("HEMAT10")) {
            return total * 0.90;
        }
        return total;
    }

    public double hitungHargaTotal(int jumlah, EnumUkuran ukuran) {
        validasiJumlahPesanan(jumlah);
        if (ukuran == null) {
            throw new InputTidakValidException("Ukuran harus dipilih.");
        }
        return hitungHargaTotal() * ukuran.getPengaliHarga() * jumlah;
    }

    protected int[] konversiCustomisasi(List<Integer> customizations, int jumlahDiharapkan) {
        if (customizations == null || customizations.size() != jumlahDiharapkan) {
            throw new InputTidakValidException("Jumlah kustomisasi tidak sesuai.");
        }

        int[] values = new int[jumlahDiharapkan];
        int index = 0;
        for (Integer value : customizations) {
            if (value == null) {
                throw new InputTidakValidException("Kustomisasi harus berupa angka.");
            }
            values[index] = value;
            index++;
        }
        return values;
    }

    protected int[] validasiPanjangArray(int[] customizations, int jumlahDiharapkan) {
        if (customizations == null || customizations.length != jumlahDiharapkan) {
            throw new InputTidakValidException("Jumlah kustomisasi tidak sesuai.");
        }
        return customizations;
    }

    protected int validasiRentang(int jumlah, int minimum, int maksimum, String namaKustomisasi) {
        if (jumlah < minimum || jumlah > maksimum) {
            throw new InputTidakValidException(namaKustomisasi + " harus antara " + minimum + " dan " + maksimum + ".");
        }
        return jumlah;
    }

    public abstract double hitungHargaTotal();
    public abstract String getOrderDetail();
    public abstract List<String> getCustomizationPrompts();
}



