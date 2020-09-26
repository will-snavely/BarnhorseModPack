package org.barnhorse.sts.viewer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.barnhorse.sts.lib.events.GameEvent;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class View {
    private JLabel fileNameLabel;
    private JLabel status;
    private GameEventTable eventTable;
    private ObjectMapper objectMapper = new ObjectMapper();

    public View() {
        JFrame frame = new JFrame("Event Viewer");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 1));
        JButton fileOpen = new JButton("Select Run");
        fileOpen.addActionListener(new FileOpenListener());
        JLabel currentLoadedRun = new JLabel("Selected Run:");
        topPanel.add(fileOpen);

        JPanel secondRow = new JPanel();
        this.fileNameLabel = new JLabel("None");
        secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
        secondRow.add(Box.createHorizontalStrut(5));
        secondRow.add(currentLoadedRun);
        secondRow.add(Box.createHorizontalStrut(5));
        secondRow.add(this.fileNameLabel);
        topPanel.add(secondRow);

        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new BoxLayout(thirdRow, BoxLayout.X_AXIS));
        this.status = new JLabel();
        thirdRow.add(Box.createHorizontalStrut(5));
        thirdRow.add(status);
        topPanel.add(thirdRow);

        this.eventTable = new GameEventTable();
        JTable eventList = new JTable(this.eventTable);
        JScrollPane scrollPane = new JScrollPane(eventList);
        eventList.addMouseListener(new TableClickListener());

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void onFileSelected(File file) {
        this.fileNameLabel.setText(file.getAbsolutePath());
        this.status.setText("Loading Events...");
        EventLoader loader = new EventLoader(file);
        loader.execute();
    }

    private void onRowDoubleClick(int row) {
        String message;
        GameEvent event = this.eventTable.getEventOnRow(row);
        int panelType = JOptionPane.INFORMATION_MESSAGE;

        try {
            message = View.this.objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(event);
        } catch (Exception e) {
            message = "Failed to render event: " + e.getMessage();
            panelType = JOptionPane.ERROR_MESSAGE;
        }

        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Game Event Detail",
                panelType);
    }

    private class TableClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            Point point = mouseEvent.getPoint();
            int row = table.rowAtPoint(point);
            if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                View.this.onRowDoubleClick(row);
            }
        }
    }

    private class FileOpenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userDirLocation = System.getProperty("user.dir");
            File userDir = new File(userDirLocation);
            JFileChooser chooser = new JFileChooser(userDir);
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                View.this.onFileSelected(chooser.getSelectedFile());
            }
        }
    }

    private class EventLoader extends SwingWorker<List<GameEvent>, List<GameEvent>> {
        private File target;

        public EventLoader(File file) {
            this.target = file;
        }

        private List<GameEvent> readEvents(File file) {
            Nitrite db = null;
            ObjectRepository<GameEvent> eventStore = null;
            try {
                db = Nitrite.builder().filePath(file).openOrCreate();
                eventStore = db.getRepository(GameEvent.class);
                return eventStore.find().toList();
            } finally {
                if (eventStore != null)
                    eventStore.close();
                if (db != null)
                    db.close();
            }
        }

        protected List<GameEvent> doInBackground() throws Exception {
            return readEvents(this.target);
        }

        protected void done() {
            try {
                System.out.println("Done");
                List<GameEvent> result = this.get();
                View.this.status.setText("Loaded Events! Double click on an event to see details.");
                View.this.eventTable.clear();
                View.this.eventTable.addEvents(result);
            } catch (Exception e) {
                View.this.status.setText("Failed to load events:\n " + e.getMessage());
            }
        }
    }

    private static class EventColumn {
        public String header;
        public Function<GameEvent, String> getValue;

        public EventColumn(String header, Function<GameEvent, String> f) {
            this.header = header;
            this.getValue = f;
        }
    }

    private static class GameEventTable extends AbstractTableModel {
        private List<GameEvent> events;
        private static EventColumn[] columns;

        static {
            columns = new EventColumn[]{
                    new EventColumn("Key", event -> event.key),
                    new EventColumn("Act", event -> String.valueOf(event.actNumber)),
                    new EventColumn("Floor", event -> String.valueOf(event.floorNumber)),
                    new EventColumn("Timestamp", event -> new Date(event.timestamp).toString()),
                    new EventColumn("Description", event -> event.desc),
            };
        }

        public void clear() {
            this.events.clear();
        }

        public GameEventTable() {
            this.events = new ArrayList<>();
        }

        public GameEvent getEventOnRow(int row) {
            return this.events.get(row);
        }

        public void addEvents(List<GameEvent> event) {
            this.events.addAll(event);
            this.fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return events.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column].header;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return columns[columnIndex].getValue.apply(events.get(rowIndex));
        }
    }
}
