import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Main mewarisi JFrame (inheritance), jadi class ini langsung menjadi jendela aplikasi.
public class Main extends JFrame {

    // ArrayList: menyimpan objek tanaman dan perawatan selama aplikasi berjalan.
    private ArrayList<Tanaman> daftarTanaman = new ArrayList<>();
    private ArrayList<Perawatan> daftarPerawatan = new ArrayList<>();

    // Model tabel tanaman: isCellEditable dibuat false agar isi tabel tidak bisa diedit langsung.
    private DefaultTableModel modelTanaman = new DefaultTableModel(
            new String[]{"Nama Tanaman", "Jenis", "Saran Perawatan"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Model tabel perawatan: sama, hanya untuk menampilkan data.
    private DefaultTableModel modelPerawatan = new DefaultTableModel(
            new String[]{"Jenis Perawatan", "Nama Tanaman", "Tanggal", "Catatan"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Constructor: mengatur judul, ukuran, posisi jendela, dan menyusun tab.
    public Main() {
        setTitle("Sistem Perawatan Tanaman");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Program berhenti saat jendela ditutup.
        setLocationRelativeTo(null);                    // Jendela muncul di tengah layar.

        // JTabbedPane: membagi aplikasi menjadi dua halaman (tab).
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Data Tanaman", buatPanelTanaman());
        tabs.addTab("Jadwal Perawatan", buatPanelPerawatan());

        add(tabs);
    }

    // Halaman 1: form input dan tabel data tanaman.
    private JPanel buatPanelTanaman() {

        // BorderLayout: membagi panel menjadi atas (form) dan tengah (tabel).
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // FlowLayout: komponen form tersusun menyamping dari kiri.
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField inputNama = new JTextField(15);

        // JComboBox: pilihan jenis tanaman berupa dropdown.
        JComboBox<String> pilihanJenis = new JComboBox<>(
                new String[]{"Tanaman Hias", "Tanaman Buah"});

        JButton tombolTambah = new JButton("Tambah Tanaman");

        form.add(new JLabel("Nama Tanaman:"));
        form.add(inputNama);
        form.add(new JLabel("Jenis:"));
        form.add(pilihanJenis);
        form.add(tombolTambah);

        // JTable: menampilkan data dari modelTanaman, JScrollPane agar bisa di-scroll.
        JTable tabel = new JTable(modelTanaman);
        tabel.setRowHeight(28);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);

        // ActionListener (lambda): kode ini berjalan saat tombol diklik.
        tombolTambah.addActionListener(e -> {

            String nama = inputNama.getText().trim();

            // Validasi: nama tidak boleh kosong.
            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Nama tanaman belum diisi.");
                return;
            }

            // Polimorfisme: variabel bertipe Tanaman bisa berisi objek TanamanHias atau TanamanBuah.
            Tanaman tanaman;

            if (pilihanJenis.getSelectedItem().equals("Tanaman Hias")) {
                tanaman = new TanamanHias(nama);
            } else {
                tanaman = new TanamanBuah(nama);
            }

            daftarTanaman.add(tanaman);

            // getSaranPerawatan() menghasilkan isi berbeda sesuai jenis tanaman.
            modelTanaman.addRow(new Object[]{
                    tanaman.getNama(),
                    tanaman.getJenis(),
                    tanaman.getSaranPerawatan()
            });

            inputNama.setText(""); // Mengosongkan input setelah data masuk.

            JOptionPane.showMessageDialog(
                    this, "Data tanaman berhasil ditambahkan.");
        });

        return panel;
    }

    // Halaman 2: form input dan tabel jadwal perawatan.
    private JPanel buatPanelPerawatan() {

        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // GridLayout: form tersusun rapi dalam 4 baris dan 2 kolom (label | input).
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

        // Tombol diletakkan di kanan bawah form.
        JPanel bagianTombol = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        bagianTombol.add(tombolSimpan);
