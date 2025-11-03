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

    public ActionController(AppService appService) {
        this.appService = appService;
        drawing = appService.getDrawing();
        imageFileService = new ImageFileService();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (ActionCommand.UNDO.equals(cmd)) {
            appService.undo();
            component.repaint();
        } else if (ActionCommand.REDO.equals(cmd)) {
            appService.redo();
            component.repaint();
        } else if (ActionCommand.LINE.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Line);
        } else if (ActionCommand.RECT.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Rectangle);
        } else if (ActionCommand.ELLIPSE.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Ellipse);
        } else if (ActionCommand.IMAGE.equals(cmd)) {
            if (drawing.getImageFilename() == null) {
                imageFileService.setImage(drawing);
            }
            appService.setShapeMode(ShapeMode.Image);
        } else if (ActionCommand.IMAGEFILE.equals(cmd)) {
            imageFileService.setImage(drawing);
        } else if (ActionCommand.COLOR.equals(cmd)) {
            Color color = JColorChooser.showDialog(frame, "Select color", appService.getColor());
            if (color != null) {
                appService.setColor(color);
                component.repaint();
            }
        } else if (ActionCommand.FONT.equals(cmd)) {
            getFont();
        } else if (ActionCommand.TEXT.equals(cmd)) {
            if (drawing.getFont() == null) {
                getFont();
            }
            appService.setShapeMode(ShapeMode.Text);
        } else if (ActionCommand.FILL.equals(cmd)) {
            Color color = JColorChooser.showDialog(frame, "Select fill color", appService.getFill());
            if (color != null) {
                Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
                appService.setFill(newColor);
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
                    drawing.setColor(Color.BLACK);
                    drawing.setFill(null);
                    drawing.setFont(new Font("Arial", Font.PLAIN, 12));
                    drawing.setText("");
                    drawing.setThickness(1);
                    drawing.setGradient(false);
                    drawing.setStartColor(Color.WHITE);
                    drawing.setEndColor(Color.BLACK);
                    drawing.setVisible(true);
                    drawing.setFilename(null);
                    frame.setTitle("GoDraw - Untitled");
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
        } else if (ActionCommand.PIN.equals(cmd)) {
            if (drawing.getSelectedShape() != null) {
                boolean newPinnedState = !drawing.getSelectedShape().isPinned();
                for (com.anastacio.drawfx.model.Shape shape : drawing.getShapes()) {
                    if (shape.isSelected()) {
                        shape.setPinned(newPinnedState);
                    }
                }
                String message = newPinnedState ? "Selected shapes pinned" : "Selected shapes unpinned";
                JOptionPane.showMessageDialog(frame, message, "Pin Status", JOptionPane.INFORMATION_MESSAGE);
                component.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "No shapes selected", "Pin Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if (ActionCommand.LINE_STYLE.equals(cmd)) {
            String[] styles = {"Solid", "Dashed", "Dotted", "Dash-Dot"};
            String currentStyle = "Solid";
            if (drawing.getSelectedShape() != null) {
                currentStyle = drawing.getSelectedShape().getLineStyle();
            }
            String selectedStyle = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select line style:",
                    "Line Style",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    styles,
                    currentStyle
            );
            if (selectedStyle != null) {
                if (drawing.getSelectedShape() != null) {
                    for (com.anastacio.drawfx.model.Shape shape : drawing.getShapes()) {
                        if (shape.isSelected()) {
                            shape.setLineStyle(selectedStyle);
                        }
                    }
                } else {
                    //  Update default line style when no shape is selected
                    drawing.setLineStyle(selectedStyle);
                }
                component.repaint();
            }
        } else if (ActionCommand.LINE_WIDTH.equals(cmd)) {
            int currentWidth = 1;
            if (drawing.getSelectedShape() != null) {
                currentWidth = drawing.getSelectedShape().getThickness();
            }
            String input = JOptionPane.showInputDialog(
                    frame,
                    "Enter line width (1-20):",
                    String.valueOf(currentWidth)
            );
            if (input != null) {
                try {
                    int width = Integer.parseInt(input);
                    if (width >= 1 && width <= 20) {
                        if (drawing.getSelectedShape() != null) {
                            for (com.anastacio.drawfx.model.Shape shape : drawing.getShapes()) {
                                if (shape.isSelected()) {
                                    shape.setThickness(width);
                                }
                            }
                        } else {
                            // FIX: Update default thickness when no shape is selected
                            drawing.setThickness(width);
                        }
                        component.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Width must be between 1 and 20", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
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
