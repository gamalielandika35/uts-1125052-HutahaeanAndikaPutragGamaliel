package coffeeshopmvc.Model;

public enum EnumUkuran {
    SMALL(1.0),
    MEDIUM(1.25),
    LARGE(1.5);

    private final double pengaliHarga;

    EnumUkuran(double pengaliHarga) {
        this.pengaliHarga = pengaliHarga;
    }

    public double getPengaliHarga() {
        return pengaliHarga;
    }
}

