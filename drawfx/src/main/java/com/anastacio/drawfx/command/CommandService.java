package com.anastacio.drawfx.command;

import java.util.Stack;
public class CommandService {
    static Stack<Command> undoStack = new Stack<Command>();
    static Stack<Command> redoStack = new Stack<Command>();

    public static void ExecuteCommand(Command command) {
        System.out.println("EXECUTE: Executing command: " + command.getClass().getSimpleName());
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        System.out.println("EXECUTE: Command added to undo stack. Stack size: " + undoStack.size());
    }

    public static void undo() {
        System.out.println("UNDO called. Undo stack size: " + undoStack.size());
        if (undoStack.empty()) {
            System.out.println("UNDO: Stack is empty, nothing to undo");
            return;
        }
        Command command = undoStack.pop();
        System.out.println("UNDO: Executing undo on command: " + command.getClass().getSimpleName());
        command.undo();
        redoStack.push(command);
        System.out.println("UNDO: Complete. Undo stack size now: " + undoStack.size());
    }

    public static void redo() {
        System.out.println("REDO called. Redo stack size: " + redoStack.size());
        if (redoStack.empty()) {
            System.out.println("REDO: Stack is empty, nothing to redo");
            return;
        }
        Command command = redoStack.pop();
        System.out.println("REDO: Executing redo on command: " + command.getClass().getSimpleName());
        command.execute();
        undoStack.push(command);
        System.out.println("REDO: Complete. Redo stack size now: " + redoStack.size());
    }
}