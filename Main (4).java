import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

// Main mewarisi JFrame sehingga class ini menjadi jendela utama aplikasi.
public class Main extends JFrame {

    // ArrayList untuk menyimpan objek tanaman dan perawatan selama aplikasi berjalan.
    private final ArrayList<Tanaman> daftarTanaman = new ArrayList<>();
    private final ArrayList<Perawatan> daftarPerawatan = new ArrayList<>();

    // Model tabel tanaman dibuat tidak dapat diedit langsung.
    private final DefaultTableModel modelTanaman = new DefaultTableModel(
            new String[]{"Nama Tanaman", "Jenis", "Saran Perawatan"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Model tabel jadwal perawatan dibuat tidak dapat diedit langsung.
    private final DefaultTableModel modelPerawatan = new DefaultTableModel(
            new String[]{"Jenis Perawatan", "Nama Tanaman", "Tanggal", "Catatan"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Konstruktor untuk mengatur jendela dan tab aplikasi.
    public Main() {
        setTitle("Sistem Perawatan Tanaman");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Data Tanaman", buatPanelTanaman());
        tabs.addTab("Jadwal Perawatan", buatPanelPerawatan());
        add(tabs);
    }

    // Halaman pertama: form input dan tabel data tanaman.
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

        tombolTambah.addActionListener(e -> {
            String nama = inputNama.getText().trim();

            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama tanaman belum diisi.");
                inputNama.requestFocus();
                return;
            }

            // Polimorfisme: objek dapat berupa TanamanHias atau TanamanBuah.
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
            JOptionPane.showMessageDialog(this, "Data tanaman berhasil ditambahkan.");
        });

        return panel;
    }

    // Halaman kedua: form input dan tabel jadwal perawatan.
    private JPanel buatPanelPerawatan() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        JPanel bagianTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bagianTombol.add(tombolSimpan);

        // Form dan tombol digabung di bagian atas halaman.
        JPanel bagianAtas = new JPanel(new BorderLayout(5, 10));
        bagianAtas.add(form, BorderLayout.CENTER);
        bagianAtas.add(bagianTombol, BorderLayout.SOUTH);

        JTable tabel = new JTable(modelPerawatan);
        tabel.setRowHeight(28);

        panel.add(bagianAtas, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);

        // Menyimpan jadwal ketika tombol diklik.
        tombolSimpan.addActionListener(e -> {
            String nama = inputNama.getText().trim();
            String tanggal = inputTanggal.getText().trim();
            String catatan = inputCatatan.getText().trim();

            if (nama.isEmpty() || tanggal.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Nama tanaman dan tanggal harus diisi.");
                return;
            }

            // Validasi tanggal agar sesuai format dd-MM-yyyy dan merupakan tanggal yang benar.
            DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-uuuu");
            try {
                LocalDate.parse(tanggal, formatTanggal);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(
                        this, "Format tanggal tidak valid. Gunakan dd-mm-yyyy, contoh 25-11-2026.");
                inputTanggal.requestFocus();
                return;
            }

            // Polimorfisme: Perawatan dapat berisi objek Penyiraman atau Pemupukan.
            Perawatan perawatan;
            if ("Penyiraman".equals(pilihanPerawatan.getSelectedItem())) {
                perawatan = new Penyiraman(nama, tanggal, catatan);
            } else {
                perawatan = new Pemupukan(nama, tanggal, catatan);
            }

            daftarPerawatan.add(perawatan);
            modelPerawatan.addRow(new Object[]{
                    perawatan.getJenisPerawatan(),
                    perawatan.getNamaTanaman(),
                    perawatan.getTanggal(),
                    perawatan.getCatatan()
            });

            inputNama.setText("");
            inputTanggal.setText("");
            inputCatatan.setText("");
            inputNama.requestFocus();
            JOptionPane.showMessageDialog(this, "Jadwal berhasil disimpan.");
        });

        return panel;
    }

    // Titik awal program.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
