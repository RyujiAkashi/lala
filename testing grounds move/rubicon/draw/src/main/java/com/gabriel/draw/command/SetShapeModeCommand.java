package com.gabriel.draw.command;

import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;

public class SetShapeModeCommand implements Command {
    private final AppService appService;
    private final ShapeMode newMode;
    private ShapeMode oldMode;

    public SetShapeModeCommand(AppService appService, ShapeMode newMode) {
        this.appService = appService;
        this.newMode = newMode;
    }

    @Override
    public void execute() {
        oldMode = appService.getShapeMode();
        appService.setShapeMode(newMode);
    }

    @Override
    public void undo() {
        appService.setShapeMode(oldMode);
    }

    @Override
    public void redo() {
        appService.setShapeMode(newMode);
    }
}
