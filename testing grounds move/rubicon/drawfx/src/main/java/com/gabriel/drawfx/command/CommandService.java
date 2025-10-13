package com.gabriel.drawfx.command;

import java.util.Stack;

public final class CommandService {
    private static final Stack<Command> undoStack = new Stack<>();
    private static final Stack<Command> redoStack = new Stack<>();

    private CommandService() {}

    public static void ExecuteCommand(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public static boolean canUndo() { return !undoStack.isEmpty(); }
    public static boolean canRedo() { return !redoStack.isEmpty(); }

    public static void undo() {
        if (!undoStack.isEmpty()) {
            Command c = undoStack.pop();
            c.undo();
            redoStack.push(c);
        }
    }

    public static void redo() {
        if (!redoStack.isEmpty()) {
            Command c = redoStack.pop();
            c.redo();
            undoStack.push(c);
        }
    }
}
