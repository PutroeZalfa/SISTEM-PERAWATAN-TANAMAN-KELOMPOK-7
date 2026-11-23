public class Pemupukan extends Perawatan {

    public Pemupukan(String namaTanaman, String tanggal, String catatan) {
        super(namaTanaman, tanggal, catatan);
    }

    // Menentukan jenis aktivitas sebagai pemupukan.
    @Override
    public String getJenisPerawatan() {
        return "Pemupukan";
    }
}
