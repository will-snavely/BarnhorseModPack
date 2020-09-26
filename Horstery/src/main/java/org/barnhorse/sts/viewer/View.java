package org.barnhorse.sts.viewer;

import org.barnhorse.sts.lib.events.GameEvent;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class View {
    private JFrame frame;
    private JButton fileOpen;
    private JLabel currentLoadedRun;
    private JLabel fileNameLabel;
    private JLabel status;
    private DefaultListModel eventModel;

    public View() {
        this.frame = new JFrame("Event Viewer");
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 500);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 1));
        this.fileOpen = new JButton("Select Run");
        this.fileOpen.addActionListener(new FileOpenListener());
        this.currentLoadedRun = new JLabel("Selected Run:");
        topPanel.add(fileOpen);

        JPanel secondRow = new JPanel();
        this.fileNameLabel = new JLabel("None");
        secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
        secondRow.add(Box.createHorizontalStrut(5));
        secondRow.add(this.currentLoadedRun);
        secondRow.add(Box.createHorizontalStrut(5));
        secondRow.add(this.fileNameLabel);
        topPanel.add(secondRow);

        this.status = new JLabel();
        topPanel.add(status);

        this.eventModel = new DefaultListModel();
        JList eventList = new JList(eventModel);
        JScrollPane scrollPane = new JScrollPane(eventList);

        this.frame.add(topPanel, BorderLayout.NORTH);
        this.frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    private void onFileSelected(File file) {
        this.fileNameLabel.setText(file.getAbsolutePath());
        this.status.setText("Loading Events...");
        EventLoader loader = new EventLoader(file);
        loader.execute();
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

        private String renderEvent(GameEvent event) {
            return String.format(
                    "Key: %s, Floor: %d, TimeStamp: %d, Desc: %s",
                    event.key,
                    event.floorNumber,
                    event.timestamp,
                    event.desc);
        }

        protected void done() {
            try {
                System.out.println("Done");
                List<GameEvent> result = this.get();
                View.this.status.setText("Loaded Events!");
                View.this.eventModel.clear();
                for (GameEvent event : result) {
                    View.this.eventModel.addElement(renderEvent(event));
                }
            } catch (Exception e) {
                View.this.status.setText("Failed to load events:\n " + e.getMessage());
            }
        }
    }
}
