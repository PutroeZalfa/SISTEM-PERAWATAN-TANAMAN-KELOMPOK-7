public class TanamanHias extends Tanaman {

    public TanamanHias(String nama) {
        super(nama, "Tanaman Hias");
    }

    // Override untuk memberikan saran khusus tanaman hias.
    @Override
    public String getSaranPerawatan() {
        return "Periksa kelembapan tanah dan sesuaikan penyiraman dengan kondisi tanaman hias.";
    }
}
