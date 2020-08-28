package com.dityatkin.gradient.userInterface;

import com.dityatkin.gradient.dao.DBConnector;
import com.dityatkin.gradient.dao.impl.FilesCrudDao;
import com.dityatkin.gradient.domain.FileMovingConfiguration;
import com.dityatkin.gradient.provider.FileMoveProvider;
import com.dityatkin.gradient.provider.TableViewProvider;
import com.dityatkin.gradient.provider.impl.TransferCancelProviderImpl;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProvideWindowManipulations implements ActionListener {
    private static final String ROOT_PATH = "Root path";
    private static final String MUSIC_DIR = "Music";
    private static final String SOFT_DIR = "Soft";
    private static final String PDF_DOC_DIR = "Pdf or Doc";
    private static final String TORRENT_DIR = "Torrents";
    private static final String VIDEO_DIR = "Video";
    private static final String SORT = "Sort!";
    private static final String CANCEL = "Cancel";
    private static final String TABLE = "Transfer result table";
    private static final String ROOT_DIR = "D:\\";
    public static final String DOC_TYPE = "doc";
    public static final String DOCX_TYPE = "docx";
    public static final String TXT_TYPE = "txt";
    public static final String PDF_TYPE = "pdf";
    public static final String MP3_TYPE = "mp3";
    public static final String MP4_TYPE = "mp4";
    public static final String AVI_TYPE = "avi";
    public static final String EXE_TYPE = "exe";
    public static final String TORRENT_TYPE = "torrent";
    private static final String DOT = ".";
    public static final String CLEAR_TABLE = "Clear table";

    private final FileMoveProvider<FileMovingConfiguration> fileMoveProvider;
    private FileMovingConfiguration filesConfiguration = null;
    private final TableViewProvider tableViewProviderImpl;

    JFrame gradient;
    JButton dirFrom;
    JButton dir1;
    JButton dir2;
    JButton dir3;
    JButton dir4;
    JButton dir5;
    JButton sortButton;
    JButton cancelButton;
    JButton filesTable;
    JButton clearTable;
    JLabel dirFromTxt;
    JLabel dir1Txt;
    JLabel dir2Txt;
    JLabel dir3Txt;
    JLabel dir4Txt;
    JLabel dir5Txt;

    Path directoryFrom = null;

    public ProvideWindowManipulations(FileMoveProvider<FileMovingConfiguration> fileMoveProvider,
                                      TableViewProvider tableViewProviderImpl) {
        gradient = new JFrame("Gradient");
        gradient.setVisible(true);
        gradient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gradient.setLayout(null);
        gradient.setSize(850, 300);

        dirFrom = new JButton(ROOT_PATH);
        dirFrom.setSize(70, 10);
        dir1 = new JButton(MUSIC_DIR);
        dir2 = new JButton(SOFT_DIR);
        dir3 = new JButton(PDF_DOC_DIR);
        dir4 = new JButton(TORRENT_DIR);
        dir5 = new JButton(VIDEO_DIR);
        sortButton = new JButton(SORT);
        cancelButton = new JButton(CANCEL);
        filesTable = new JButton(TABLE);
        clearTable = new JButton(CLEAR_TABLE);

        dirFromTxt = new JLabel("Actual path");
        dir1Txt = new JLabel("Path for music");
        dir2Txt = new JLabel("Path for Soft");
        dir3Txt = new JLabel("Path for PDF or DOC");
        dir4Txt = new JLabel("Path for Torrents");
        dir5Txt = new JLabel("Path for videos");

        dirFrom.addActionListener(this);
        dir1.addActionListener(this);
        dir2.addActionListener(this);
        dir3.addActionListener(this);
        dir4.addActionListener(this);
        dir5.addActionListener(this);
        sortButton.addActionListener(this);
        cancelButton.addActionListener(this);
        filesTable.addActionListener(this);
        clearTable.addActionListener(this);

        dirFrom.setBounds(20, 95, 100, 30);
        dir1.setBounds(420, 20, 100, 20);
        dir2.setBounds(420, 60, 100, 20);
        dir3.setBounds(420, 100, 100, 20);
        dir4.setBounds(420, 140, 100, 20);
        dir5.setBounds(420, 180, 100, 20);
        sortButton.setBounds(300, 80, 100, 60);
        cancelButton.setBounds(310, 150, 80, 30);
        filesTable.setBounds(280, 220, 160, 30);
        clearTable.setBounds(440, 220, 120, 30);

        dirFromTxt.setBounds(140, 100, 120, 20);
        dir1Txt.setBounds(540, 20, 200, 20);
        dir2Txt.setBounds(540, 60, 200, 20);
        dir3Txt.setBounds(540, 100, 200, 20);
        dir4Txt.setBounds(540, 140, 200, 20);
        dir5Txt.setBounds(540, 180, 200, 20);

        gradient.add(dirFrom);
        gradient.add(dirFromTxt);
        gradient.add(dir1);
        gradient.add(dir1Txt);
        gradient.add(dir2);
        gradient.add(dir2Txt);
        gradient.add(dir3);
        gradient.add(dir3Txt);
        gradient.add(dir4);
        gradient.add(dir4Txt);
        gradient.add(dir5);
        gradient.add(dir5Txt);

        gradient.add(sortButton);
        gradient.add(filesTable);
        gradient.add(clearTable);

        this.tableViewProviderImpl = tableViewProviderImpl;
        this.fileMoveProvider = fileMoveProvider;
    }

    public void actionPerformed(ActionEvent e) {
        String actionComm = e.getActionCommand();

        switch (actionComm) {

            case ROOT_PATH:
                dirFromTxt.setText(makeFileChooseDialogWindow(actionComm, dirFromTxt.getText()));
                if (!dirFromTxt.getText().equals("Path From")) {
                    directoryFrom = Paths.get(dirFromTxt.getText());
                }
                break;

            case MUSIC_DIR:
                dir1Txt.setText(makeFileChooseDialogWindow(e.getActionCommand(), dir1Txt.getText()));
                break;

            case SOFT_DIR:
                dir2Txt.setText(makeFileChooseDialogWindow(e.getActionCommand(), dir2Txt.getText()));
                break;

            case PDF_DOC_DIR:
                dir3Txt.setText(makeFileChooseDialogWindow(e.getActionCommand(), dir3Txt.getText()));
                break;

            case TORRENT_DIR:
                dir4Txt.setText(makeFileChooseDialogWindow(e.getActionCommand(), dir4Txt.getText()));
                break;

            case VIDEO_DIR:
                dir5Txt.setText(makeFileChooseDialogWindow(e.getActionCommand(), dir4Txt.getText()));
                break;
        }

        if (e.getActionCommand().equals(SORT) && directoryFrom != null) {
            fileMoveProvider.sortFilesToDirectories(filesConfiguration);

            gradient.add(cancelButton);
        }

        if (e.getActionCommand().equals(CANCEL) && filesConfiguration != null) {
            TransferCancelProviderImpl cancel = new TransferCancelProviderImpl(filesConfiguration, fileMoveProvider);
            cancel.cancelFileTransfer();
        }

        if (e.getActionCommand().equals(TABLE)) {
            tableViewProviderImpl.createTable();
        }

        if (e.getActionCommand().equals(CLEAR_TABLE)) {
            final FilesCrudDao filesCrudDao = new FilesCrudDao(new DBConnector("database.properties"));
            filesCrudDao.deleteAll();
        }
    }

    private String makeFileChooseDialogWindow(String buttonType, String labelName) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(ROOT_DIR));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        Path directory = Paths.get("");

        if (buttonType.equals(ROOT_PATH)) {
            fileChooser.setDialogTitle("Choose unload directory");
        }

        if (!buttonType.equals(ROOT_PATH)) {
            fileChooser.setDialogTitle("Choose directory for " + buttonType);
        }

        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

            if (folderSelectCheck(fileChooser)) {
                directory = Paths.get(fileChooser.getSelectedFile().toString());
            } else directory = Paths.get("");
        }

        if (directory.toString().isEmpty()) {
            String ask = "Please, select folder.";
            JOptionPane.showMessageDialog(new JFrame(), ask, "oi yoi..", JOptionPane.ERROR_MESSAGE);
        }

        if (!directory.toString().isEmpty()) {
            String information = "Folder selected!";
            JOptionPane.showMessageDialog(new JFrame(), information, "that's good!",
                    JOptionPane.INFORMATION_MESSAGE);
            if (buttonType.equals(ROOT_PATH)) {
                filesConfiguration = createNewFileMovingConfiguration(directory);
            }
            setDirForTransfer(buttonType, directory);
        }

        if (directory.toString().isEmpty()) {
            return labelName;
        }

        return directory.toString();
    }

    private boolean folderSelectCheck(JFileChooser fileChooser) {
        return !fileChooser.getSelectedFile().toString().endsWith(DOT + EXE_TYPE) &&
                !fileChooser.getSelectedFile().toString().endsWith(DOT + MP3_TYPE) &&
                !fileChooser.getSelectedFile().toString().endsWith(DOT + MP4_TYPE) &&
                !fileChooser.getSelectedFile().toString().endsWith(DOT + DOC_TYPE) &&
                !fileChooser.getSelectedFile().toString().endsWith(DOT + DOCX_TYPE) &&
                !fileChooser.getSelectedFile().toString().endsWith(DOT + PDF_TYPE) &&
                !fileChooser.getSelectedFile().toString().endsWith(DOT + AVI_TYPE) &&
                !fileChooser.getSelectedFile().toString().endsWith(DOT + TORRENT_TYPE);
    }

    private FileMovingConfiguration createNewFileMovingConfiguration(Path path) {
        return new FileMovingConfiguration(path);
    }

    private void setDirForTransfer(String buttonType, Path dir) {
        switch (buttonType) {

            case MUSIC_DIR:
                filesConfiguration.addFileTypeToFilePath(MP3_TYPE, dir);

                break;

            case TORRENT_DIR:
                filesConfiguration.addFileTypeToFilePath(TORRENT_TYPE, dir);

                break;

            case VIDEO_DIR:
                filesConfiguration.addFileTypeToFilePath(MP4_TYPE, dir);
                filesConfiguration.addFileTypeToFilePath(AVI_TYPE, dir);

                break;

            case SOFT_DIR:
                filesConfiguration.addFileTypeToFilePath(EXE_TYPE, dir);

                break;

            case PDF_DOC_DIR:
                filesConfiguration.addFileTypeToFilePath(PDF_TYPE, dir);
                filesConfiguration.addFileTypeToFilePath(DOC_TYPE, dir);
                filesConfiguration.addFileTypeToFilePath(TXT_TYPE, dir);
                filesConfiguration.addFileTypeToFilePath(DOCX_TYPE, dir);
                break;
        }
    }
}
