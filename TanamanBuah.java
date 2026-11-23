public class TanamanBuah extends Tanaman {

    public TanamanBuah(String nama) {
        super(nama, "Tanaman Buah");
    }

    // Saran perawatan khusus untuk tanaman buah.
    @Override
    public String getSaranPerawatan() {
        return "Perhatikan kebutuhan air dan pemupukan agar tanaman dapat tumbuh dan berbuah.";
    }
}
