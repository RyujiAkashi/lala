package com.anastacio.draw.controller;
import com.anastacio.draw.service.ImageFileService;
import com.anastacio.draw.service.XmlDocumentService;
import com.anastacio.drawfx.ActionCommand;
import com.anastacio.drawfx.ShapeMode;
import com.anastacio.drawfx.model.Drawing;
import com.anastacio.drawfx.service.AppService;
import lombok.Setter;
import com.anastacio.fontchooser.FontDialog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ActionController implements ActionListener {
    AppService appService;
    ImageFileService imageFileService;

    @Setter
    Component component;

    Drawing drawing;

    @Setter
    JFrame frame;

    public  ActionController(AppService appService){
        this.appService = appService;
        drawing = appService.getDrawing();
        imageFileService = new ImageFileService();
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (ActionCommand.UNDO.equals(cmd)) {
            appService.undo();
        }
        if (ActionCommand.REDO.equals(cmd)) {
            appService.redo();
        } else if (ActionCommand.LINE.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Line);
        } else if (ActionCommand.RECT.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Rectangle);
        } else if (ActionCommand.ELLIPSE.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Ellipse);
        } else if (ActionCommand.IMAGE.equals(cmd)) {
            if(drawing.getImageFilename() == null) {
                imageFileService.setImage(drawing);
            }
            appService.setShapeMode(ShapeMode.Image);
        } else if (ActionCommand.IMAGEFILE.equals(cmd)) {
            imageFileService.setImage(drawing);
        } else if (ActionCommand.COLOR.equals(cmd)) {
            Color color = JColorChooser.showDialog(frame, "Select color", appService.getColor());
            if(color != null) {
                appService.setColor(color);
                component.repaint();
            }
        } else if (ActionCommand.FONT.equals(cmd)) {
            getFont();
        } else if (ActionCommand.TEXT.equals(cmd)) {
            if(drawing.getFont() == null) {
                getFont();
            }
            appService.setShapeMode(ShapeMode.Text);
        } else if (ActionCommand.FILL.equals(cmd)) {
            Color color = JColorChooser.showDialog(frame, "Select fill color", appService.getFill());
            if(color != null) {
                Color newColor = new Color(color.getRed(),color.getGreen(), color.getBlue(), color.getAlpha() );
                appService.setFill(newColor );
                component.repaint();
            }
        } else if (ActionCommand.SAVEAS.equals(cmd)) {
            FileDialog fDialog = new FileDialog(frame, "Save", FileDialog.SAVE);
            fDialog.setFile(drawing.getFilename());
            fDialog.setVisible(true);
            String path = fDialog.getDirectory() + fDialog.getFile();
            File f = new File(path);
            drawing.setFilename(path);
            appService.save();
        } else if (ActionCommand.SELECT.equals(cmd)) {
            appService.clearSelections();
            appService.setShapeMode(ShapeMode.Select);
        } else if (ActionCommand.OPEN.equals(cmd)) {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.addChoosableFileFilter(new FileFilter() {
                public String getDescription() {
                    return "Xml Documents (*.xml)";
                }

                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        return f.getName().toLowerCase().endsWith(".xml");
                    }
                }
            });
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                drawing.setFilename(filename);
                XmlDocumentService docService = new XmlDocumentService(drawing);
                docService.open();
                frame.setTitle(filename);

            }
            component.repaint();
        } else if (ActionCommand.NEW.equals(cmd)) {
            if (!drawing.getShapes().isEmpty()) {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to continue and discard your changes?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    drawing.getShapes().clear();
                }
                component.repaint();
            }
        } else if (ActionCommand.SAVE.equals(cmd)) {
            String filename = drawing.getFilename();
            if (filename == null) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    public String getDescription() {
                        return "Xml Documents (*.xml)";
                    }

                    public boolean accept(File f) {
                        if (f.isDirectory()) {
                            return true;
                        } else {
                            return f.getName().toLowerCase().endsWith(".xml");
                        }
                    }
                });
                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    filename = fileChooser.getSelectedFile().getAbsolutePath();
                    drawing.setFilename(filename);
                    frame.setTitle(filename);
                }
            }
            XmlDocumentService docService = new XmlDocumentService(drawing);
            docService.save();
        } else if (ActionCommand.DELETE.equals(cmd)) {
            appService.delete();
            component.repaint();
        }
    }
    
    void getFont() {
        FontDialog dialog = new FontDialog((Frame) null, "Font Dialog Example", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setFont(drawing.getFont());
        dialog.setPreviewText(drawing.getText());
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        if (!dialog.isCancelSelected()) {
            Font font = dialog.getSelectedFont();
            drawing.setFont(dialog.getSelectedFont());
            drawing.setText(dialog.getPreviewText());
            System.out.println("Selected font is: " + dialog);
        }
        dialog.setVisible(false);
    }

}
