package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class SetFillCommand implements Command {
    private final AppService appService;
    private final Color newFill;
    private Color oldFill;

    public SetFillCommand(AppService appService, Color newFill) {
        this.appService = appService;
        this.newFill = newFill;
    }

    @Override
    public void execute() {
        oldFill = appService.getFill();
        appService.setFill(newFill);
        appService.repaint();
    }

    @Override
    public void undo() {
        appService.setFill(oldFill);
        appService.repaint();
    }

    @Override
    public void redo() {
        appService.setFill(newFill);
        appService.repaint();
    }
}
