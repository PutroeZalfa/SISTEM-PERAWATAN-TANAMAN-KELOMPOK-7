// Kelas turunan dari Perawatan khusus untuk aktivitas penyiraman tanaman.
public class Penyiraman extends Perawatan {

    // Konstruktor untuk menginisialisasi data dasar penyiraman ke kelas induk (Perawatan).
    public Penyiraman(String namaTanaman, String tanggal, String catatan) {
        super(namaTanaman, tanggal, catatan);
    }

    // Menentukan jenis aktivitas perawatan.
    @Override
    public String getJenisPerawatan() {
        return "Penyiraman";
    }
}