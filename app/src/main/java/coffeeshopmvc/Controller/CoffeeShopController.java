package coffeeshopmvc.Controller;

import coffeeshopmvc.Exception.InputTidakValidException;
import coffeeshopmvc.Exception.MenuTidakDitemukanException;
import coffeeshopmvc.Exception.StokTIdakCukupException;
import coffeeshopmvc.Model.EnumUkuran;
import coffeeshopmvc.Model.Kopi;
import coffeeshopmvc.Model.MenuItem;
import coffeeshopmvc.Model.Roti;
import coffeeshopmvc.Model.Teh;
import coffeeshopmvc.View.CoffeeShopView;
import coffeeshopmvc.View.InputUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoffeeShopController {
    private final Scanner scanner = new Scanner(System.in);
    private final CoffeeShopView view = new CoffeeShopView();
    private final List<MenuItem> menuItems = new ArrayList<>();

    public CoffeeShopController() {
        menuItems.add(new Kopi("M-01", "Kopi Susu", 18000, 5));
        menuItems.add(new Teh("M-02", "Teh Tarik", 12000, 10));
        menuItems.add(new Roti("M-03", "Roti Cokelat", 10000, 8));
    }

    public void jalankan() {
        view.tampilkanMenu(menuItems);
        boolean berjalan = true;
        while (berjalan) {
            view.tampilkanMenuAwal();
            try {
                int pilihan = InputUtil.getInt(scanner);
                switch (pilihan) {
                    case 1:
                        view.tampilkanMenu(menuItems);
                        break;
                    case 2:
                        prosesPesanan();
                        break;
                    case 0:
                        berjalan = false;
                        view.tampilkanPesan("Terima kasih sudah berkunjung!");
                        break;
                    default:
                        view.tampilkanPesan("Pilihan menu tidak tersedia.");
                }
            } catch (NumberFormatException e) {
                view.tampilkanPesan("Input menu harus berupa angka.");
            }
        }
    }

    private void prosesPesanan() {
        MenuItem menuTerpilih = pilihMenu();
        if (menuTerpilih == null) {
            return;
        }

        int jumlah = inputJumlahValid(menuTerpilih);
        if (jumlah <= 0) {
            return;
        }

        if (!inputKustomisasiValid(menuTerpilih)) {
            return;
        }

        double total = hitungTotal(menuTerpilih, jumlah);
        if (total < 0) {
            return;
        }

        menuTerpilih.kurangiStok(jumlah);
        view.tampilkanStruk(menuTerpilih, jumlah, total);
    }

    private MenuItem pilihMenu() {
        while (true) {
            String id = view.inputIdMenu(scanner);
            try {
                return cariMenu(id);
            } catch (MenuTidakDitemukanException e) {
                view.tampilkanPesan(e.getMessage() + " Silakan masukkan ID lagi.");
            }
        }
    }

    private MenuItem cariMenu(String id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        throw new MenuTidakDitemukanException("Menu dengan ID " + id + " tidak ditemukan.");
    }

    private int inputJumlahValid(MenuItem item) {
        while (true) {
            try {
                int jumlah = view.inputJumlah(scanner);
                item.validasiJumlahPesanan(jumlah);
                return jumlah;
            } catch (NumberFormatException e) {
                view.tampilkanPesan("Jumlah harus berupa angka.");
            } catch (InputTidakValidException | StokTidakCukupException e) {
                view.tampilkanPesan(e.getMessage() + " Silakan masukkan jumlah lagi.");
            }
        }
    }

    private boolean inputKustomisasiValid(MenuItem item) {
        while (true) {
            List<Integer> customizations = new ArrayList<>();
            try {
                for (String namaKustomisasi : item.getCustomizationPrompts()) {
                    customizations.add(view.inputKustomisasi(scanner, namaKustomisasi));
                }
                item.customize(customizations);
                return true;
            } catch (NumberFormatException e) {
                view.tampilkanPesan("Kustomisasi harus berupa angka. Silakan masukkan ulang semua kustomisasi.");
            } catch (InputTidakValidException e) {
                view.tampilkanPesan(e.getMessage() + " Silakan masukkan ulang semua kustomisasi.");
            }
        }
    }

    private double hitungTotal(MenuItem item, int jumlah) {
        while (true) {
            view.tampilkanMetodeHarga(item);
            int pilihan;
            try {
                pilihan = InputUtil.getInt(scanner);
            } catch (NumberFormatException e) {
                view.tampilkanPesan("Pilihan harus berupa angka.");
                continue;
            }

            try {
                switch (pilihan) {
                    case 1:
                        return item.hitungHargaTotal(jumlah);
                    case 2:
                        String kodePromo = view.inputKodePromo(scanner);
                        if (!kodePromo.equalsIgnoreCase("HEMAT10")) {
                            view.tampilkanPesan("Kode promo tidak valid. Harga normal digunakan.");
                            return item.hitungHargaTotal(jumlah);
                        }
                        return item.hitungHargaTotal(jumlah, kodePromo);
                    case 3:
                        if (!item.bisaPilihUkuran()) {
                            view.tampilkanPesan("Ukuran hanya tersedia untuk minuman.");
                            break;
                        }
                        String pilihanUkuran = view.inputUkuran(scanner);
                        EnumUkuran ukuran = EnumUkuran.valueOf(pilihanUkuran.toUpperCase());
                        return item.hitungHargaTotal(jumlah, ukuran);
                    default:
                        view.tampilkanPesan("Pilihan perhitungan tidak tersedia.");
                }
            } catch (IllegalArgumentException e) {
                view.tampilkanPesan("Ukuran tidak valid. Pilih SMALL, MEDIUM, atau LARGE.");
            } catch (InputTidakValidException | StokTidakCukupException e) {
                view.tampilkanPesan(e.getMessage());
                return -1;
            }
        }
    }
}
// OK
