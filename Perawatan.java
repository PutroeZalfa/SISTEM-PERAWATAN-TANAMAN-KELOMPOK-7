// Abstract class: cetakan dasar semua jenis perawatan, tidak bisa dibuat objek langsung.
public abstract class Perawatan {

    // Enkapsulasi: atribut private, hanya bisa dibaca lewat getter.
    private String namaTanaman;
    private String tanggal;
    private String catatan;

    // Constructor: mengisi data awal, dipanggil class turunan lewat super().
    public Perawatan(String namaTanaman, String tanggal, String catatan) {
        this.namaTanaman = namaTanaman;
        this.tanggal = tanggal;
        this.catatan = catatan;
    }

    // Getter: membaca nama tanaman yang dirawat.
    public String getNamaTanaman() {
        return namaTanaman;
    }

    // Getter: membaca tanggal perawatan.
    public String getTanggal() {
        return tanggal;
    }

    // Getter: membaca catatan perawatan.
    public String getCatatan() {
        return catatan;
    }

    // Method abstrak: wajib diisi class turunan untuk menentukan jenis perawatan (dasar polimorfisme).
    public abstract String getJenisPerawatan();

    // Override: mengatur tampilan data perawatan saat dicetak, memanggil getJenisPerawatan() dari class turunan.
    @Override
    public String toString() {
        return getJenisPerawatan() + " | " + namaTanaman
                + " | " + tanggal + " | " + catatan;
    }
}