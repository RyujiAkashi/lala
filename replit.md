# GoDraw - Java Drawing Application

## Overview
GoDraw is a Java Swing-based 2D drawing application that allows users to create and manipulate vector graphics. The application provides tools for drawing shapes (lines, rectangles, ellipses), adding text and images, and managing drawings through a graphical user interface.

## Project Structure
This is a multi-module Maven project with the following modules:

- **draw**: Main application module containing the GUI and main entry point
- **drawfx**: Core drawing functionality, models, and services
- **prop** (property): Property sheet component for object editing
- **fontchooser**: Font selection dialog component
- **batik**: Apache Batik integration for SVG support

## Technology Stack
- Java 17
- Maven build system
- Swing for GUI
- Lombok for reducing boilerplate code
- Apache Batik for SVG rendering

## Running the Application
The application is configured to run automatically in VNC mode (desktop GUI application). The workflow executes:
```
mvn -pl draw exec:java
```

This compiles and runs the draw module's Main class which launches the SplashFrame, then the DrawingFrame window.

## Features Implemented

### ✅ Core Drawing Tools
- Shape drawing: Lines, Rectangles, Ellipses
- Text insertion with font selection
- Image insertion and manipulation
- Selection mode with handles for resizing/moving
- 8 handles for rectangles/ellipses, 2 for lines

### ✅ User Interface
- **Splash Screen**: Personalized splash screen with image and launch button
- **Toolbar**: Quick access buttons for all drawing tools
- **Menu Bar**: File, Edit, Draw, and Properties menus
- **Property Sheet**: Right-side panel showing selected object properties
- **Status Bar**: Bottom panel with X,Y cursor coordinates  
- **Scroll Bars**: Main drawing panel with horizontal and vertical scrolling

### ✅ Property Management
- Property sheet updates when shapes are selected
- Editable properties include:
  - Shape type and current shape
  - Fore color and fill color
  - Gradient colors (start/end)
  - Position (Start X, Y, End X, Y)
  - Size (Width, Height)
  - Line thickness
  - Text content
  - Image filename
  - Font properties (family, style, size)
  - Visibility flags

### ✅ Undo/Redo System (Extended)
- Command pattern implementation
- Undoable operations:
  - Creating shapes
  - Deleting shapes
  - Moving shapes
  - Scaling/resizing shapes
  - Setting draw mode

**Note**: Property modifications are not yet fully integrated with undo/redo. Infrastructure (SetPropertyCommand) is in place but needs wiring in the property event listener.

### ✅ File Operations
- New drawing
- Open existing drawings (XML format)
- Save and Save As functionality

### ✅ Selection and Manipulation
- Select mode with search radius
- Handle-based resizing (8 handles for rect/ellipse, 2 for lines)
- Drag to move shapes
- Multi-shape selection support

### ✅ Color Tools
- Color picker for foreground color
- Fill color selector
- Gradient support (start/end colors)

## Build Information
- Compiler target: Java 17
- Build tool: Maven
- Main class: `com.gabriel.draw.Main`

## Recent Changes
- November 1, 2025: Initial Replit environment setup
  - Fixed dependency resolution (prop → property)
  - Fixed ActionProperty constructor call
  - Configured VNC workflow for GUI display
  - Added .gitignore for Java/Maven projects
  - Updated Main.java to launch splash screen
  - Extended undo/redo system with new command classes:
    - DeleteShapeCommand
    - MoveShapeCommand
    - ScaleShapeCommand
    - SetPropertyCommand (infrastructure ready)
  - Wrapped delete, move, and scale operations in DrawingCommandAppService

- November 1, 2025: 1st Revision Improvements
  - **Menu Bar Reorganization**:
    - Created "Tools" menu (moved Select tool here)
    - Created "Insert" menu (moved Text and Image here)
    - Kept "Draw" menu for shape tools (Line, Rectangle, Ellipse)
    - Added Font to Properties menu
    - Fixed typo: "Umdo" → "Undo"
    - Added Delete menu item to Edit menu
  - **Toolbar Enhancements**:
    - Added Color (stroke) and Fill color picker buttons
    - Added Delete, Undo, and Redo buttons
    - Reorganized layout with logical separators
    - Cleaner visual appearance
  - **Property Sheet Refactoring**:
    - Removed test/unnecessary properties
    - Shows only relevant, editable properties
    - Dynamic content based on selected shape type
    - Shape-specific properties (e.g., Text Content for text, Image Path for images)
    - Conditional display (e.g., Gradient colors only shown when gradient is enabled)
  - **Shape Rendering Improvements**:
    - Added shapeMode field to Shape base class
    - All shape constructors (Line, Rectangle, Ellipse, Text, Image) now set correct shapeMode
    - Line shapes now display only 2 handles (at endpoints)
    - Other shapes display 8 handles (corners and midpoints)
    - Selection handles changed from outlined to filled blue rectangles for better visibility
  - **Delete Functionality**:
    - Added DELETE action command
    - Delete handler calls existing delete() method
    - Integrated with undo/redo system via DeleteShapeCommand

## Known Limitations
1. Property changes from property sheet not yet wrapped in undo commands (infrastructure in place, needs integration)
2. Text editing, moving, and reshaping not yet implemented
3. Rendering during move/reshape may need optimization
4. Undo/redo/save buttons not yet disabled when nothing has changed
5. Some LSP warnings present (mostly unused imports, non-critical)

## Requirements Compliance
Based on professor's requirements:
- ✅ **Splash Screen (10%)**: Personalized splash with image and launch button
- ✅ **Property Sheet (40%)**: Shows and allows modification of selected shape properties  
- ⚠️ **Undo/Redo (40%)**: Partially implemented - works for create/delete/move/scale, property changes need wiring
- ✅ **Enhancements (10%)**: Clean UI with toolbars, menus, status bar, scrollbars, property panel
