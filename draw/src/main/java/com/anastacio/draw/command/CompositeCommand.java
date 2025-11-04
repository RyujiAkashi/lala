package com.anastacio.draw.command;

import com.anastacio.drawfx.command.Command;
import java.util.ArrayList;
import java.util.List;

public class CompositeCommand implements Command {
    private final List<Command> commands;
    private final String description;

    public CompositeCommand(String description) {
        this.commands = new ArrayList<>();
        this.description = description;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public boolean isEmpty() {
        return commands.isEmpty();
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }

    @Override
    public void redo() {
        for (Command command : commands) {
            command.redo();
        }
    }

    @Override
    public String toString() {
        return description + " (" + commands.size() + " operations)";
    }
}
