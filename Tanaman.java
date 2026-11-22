// Abstract class: cetakan dasar semua tanaman, tidak bisa dibuat objek langsung.
public abstract class Tanaman {

    // Enkapsulasi: atribut private, diakses lewat getter dan setter.
    private String nama;
    private String jenis;

    // Constructor: mengisi data awal, dipanggil class turunan lewat super().
    public Tanaman(String nama, String jenis) {
        this.nama = nama;
        this.jenis = jenis;
    }

    // Getter: membaca nama tanaman.
    public String getNama() {
        return nama;
    }

    // Setter: mengubah nama tanaman.
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter: membaca jenis tanaman.
    public String getJenis() {
        return jenis;
    }

    // Setter: mengubah jenis tanaman.
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    // Method abstrak: wajib diisi class turunan (dasar polimorfisme).
    public abstract String getSaranPerawatan();

    // Override: mengatur tampilan objek saat dicetak.
    @Override
    public String toString() {
        return nama + " (" + jenis + ")";
    }
}