
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Main mewarisi JFrame (inheritance), jadi class ini langsung menjadi jendela aplikasi.
public class Main extends JFrame {

    // ArrayList untuk menyimpan objek tanaman dan perawatan.
    private ArrayList<Tanaman> daftarTanaman = new ArrayList<>();
    private ArrayList<Perawatan> daftarPerawatan = new ArrayList<>();

    // Model tabel tanaman.
    private DefaultTableModel modelTanaman = new DefaultTableModel(
            new String[]{"Nama Tanaman", "Jenis", "Saran Perawatan"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Model tabel perawatan.
    private DefaultTableModel modelPerawatan = new DefaultTableModel(
            new String[]{"Jenis Perawatan", "Nama Tanaman", "Tanggal", "Catatan"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Constructor untuk mengatur jendela aplikasi.
    public Main() {
        setTitle("Sistem Perawatan Tanaman");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Membuat dua tab halaman aplikasi.
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Data Tanaman", buatPanelTanaman());
        tabs.addTab("Jadwal Perawatan", buatPanelPerawatan());

        add(tabs);
    }

    // Halaman pertama: form dan tabel data tanaman.
    private JPanel buatPanelTanaman() {

        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField inputNama = new JTextField(15);

        JComboBox<String> pilihanJenis = new JComboBox<>(
                new String[]{"Tanaman Hias", "Tanaman Buah"});

        JButton tombolTambah = new JButton("Tambah Tanaman");

        form.add(new JLabel("Nama Tanaman:"));
        form.add(inputNama);
        form.add(new JLabel("Jenis:"));
        form.add(pilihanJenis);
        form.add(tombolTambah);

        JTable tabel = new JTable(modelTanaman);
        tabel.setRowHeight(28);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);

        // Berjalan saat tombol Tambah Tanaman diklik.
        tombolTambah.addActionListener(e -> {

            String nama = inputNama.getText().trim();

            // Validasi nama tanaman.
            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Nama tanaman belum diisi.");
                inputNama.requestFocus();
                return;
            }

            // Polimorfisme: Tanaman dapat berupa TanamanHias atau TanamanBuah.
            Tanaman tanaman;

            if ("Tanaman Hias".equals(pilihanJenis.getSelectedItem())) {
                tanaman = new TanamanHias(nama);
            } else {
                tanaman = new TanamanBuah(nama);
            }

            daftarTanaman.add(tanaman);

            modelTanaman.addRow(new Object[]{
                    tanaman.getNama(),
                    tanaman.getJenis(),
                    tanaman.getSaranPerawatan()
            });

            inputNama.setText("");
            inputNama.requestFocus();

            JOptionPane.showMessageDialog(
                    this, "Data tanaman berhasil ditambahkan.");
        });

        return panel;
    }

    // Halaman kedua: form dan tabel jadwal perawatan.
    private JPanel buatPanelPerawatan() {

        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Form dengan 4 baris dan 2 kolom.
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField inputNama = new JTextField();
        JTextField inputTanggal = new JTextField();
        JTextField inputCatatan = new JTextField();

        JComboBox<String> pilihanPerawatan = new JComboBox<>(
                new String[]{"Penyiraman", "Pemupukan"});

        form.add(new JLabel("Nama Tanaman:"));
        form.add(inputNama);

        form.add(new JLabel("Jenis Perawatan:"));
        form.add(pilihanPerawatan);

        form.add(new JLabel("Tanggal (dd-mm-yyyy):"));
        form.add(inputTanggal);

        form.add(new JLabel("Catatan:"));
        form.add(inputCatatan);

        JButton tombolSimpan = new JButton("Simpan Jadwal");

        // Panel tombol.
        JPanel bagianTombol = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        bagianTombol.add(tombolSimpan);

        // Panel gabungan form dan tombol.
        JPanel bagianAtas = new JPanel(
                new BorderLayout(5, 10));

        bagianAtas.add(form, BorderLayout.CENTER);
        bagianAtas.add(bagianTombol, BorderLayout.SOUTH);

        JTable tabel = new JTable(modelPerawatan);
        tabel.setRowHeight(28);

        panel.add(bagianAtas, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);

        // Berjalan saat tombol Simpan Jadwal diklik.
        tombolSimpan.addActionListener(e -> {

            String nama = inputNama.getText().trim();
            String tanggal = inputTanggal.getText().trim();
            String catatan = inputCatatan.getText().trim();

            // Validasi nama dan tanggal.
            if (nama.isEmpty() || tanggal.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Nama tanaman dan tanggal harus diisi.");
                return;
            }

            // Validasi format tanggal dd-mm-yyyy.
            SimpleDateFormat formatTanggal =
                    new SimpleDateFormat("dd-MM-yyyy");

            formatTanggal.setLenient(false);

            try {
                Date tanggalValid = formatTanggal.parse(tanggal);

                if (!formatTanggal.format(tanggalValid).equals(tanggal)) {
                    throw new ParseException(
                            "Format tanggal tidak sesuai", 0);
                }

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Format tanggal salah. Gunakan dd-mm-yyyy, contoh 25-11-2026.");
                inputTanggal.requestFocus();
                return;
            }

            // Polimorfisme: Perawatan dapat berupa Penyiraman atau Pemupukan.
            Perawatan perawatan;

            if ("Penyiraman".equals(pilihanPerawatan.getSelectedItem())) {
                perawatan = new Penyiraman(nama, tanggal, catatan);
            } else {
                perawatan = new Pemupukan(nama, tanggal, catatan);
            }

            daftarPerawatan.add(perawatan);

            // Memasukkan data ke tabel.
            modelPerawatan.addRow(new Object[]{
                    perawatan.getJenisPerawatan(),
                    perawatan.getNamaTanaman(),
                    perawatan.getTanggal(),
                    perawatan.getCatatan()
            });

            // Mengosongkan form.
            inputNama.setText("");
            inputTanggal.setText("");
            inputCatatan.setText("");

            inputNama.requestFocus();

            JOptionPane.showMessageDialog(
                    this, "Jadwal berhasil disimpan.");
        });

        return panel;
    }

    // Main: titik awal program.
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
