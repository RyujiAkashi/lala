package com.anastacio.drawfx.command;

public interface Command {
    void execute();
    void undo();
    void redo();
    
    default void unexecute() {
        undo();
    }
}
