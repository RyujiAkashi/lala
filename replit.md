# GoDraw - Java Drawing Application

## Overview
GoDraw is a Java-based drawing application built using Maven with a multi-module architecture. It follows the Model-View-Controller (MVC) pattern and provides a desktop GUI for creating and manipulating various shapes (rectangles, ellipses, lines, images, and text).

## Project Structure
This is a Maven multi-module project with the following modules:
- **draw**: Main drawing application module
- **drawfx**: Drawing framework and core functionality
- **batik**: SVG rendering support using Apache Batik
- **prop**: Property management and UI components
- **fontchooser**: Font selection dialog component

## Technologies
- Java 17 (GraalVM)
- Maven for build management
- Swing/AWT for GUI
- Apache Batik for SVG support
- Lombok for reducing boilerplate code

## Running the Application
The application is configured to run automatically through the "GoDraw Application" workflow. It will display in the VNC viewer (desktop GUI mode).

Command: `mvn -pl draw exec:java`

## Recent Changes
- **2025-11-02**: Project migrated and enhanced for Replit environment
  - Installed Java (GraalVM)
  - Successfully built all Maven modules
  - Configured VNC workflow for GUI display
  - **Critical Bug Fixes:**
    - Fixed XORMode null pointer error in all shape renderers (Rectangle, Ellipse, Line, Text) by adding null checks before setXORMode calls
    - Changed default mode from Idle/Ellipse to Select mode for better user experience
    - Centered all popup dialogs (color picker, font dialog) on parent frame
  - **Property Sheet Enhancements:**
    - Added comprehensive fields: visibility, x/y location, width/height, gradient properties (start/end colors, start/end x/y), text properties, font, line thickness
    - Fixed property update handlers to use new values correctly (was using old values)
    - All property changes now trigger immediate repaint for visual feedback
  - **Save Functionality:**
    - Implemented save-on-exit prompt to prevent data loss
    - Added proper filename handling - prompts for filename when drawing has never been saved
    - Changed window close behavior to DO_NOTHING_ON_CLOSE to enable save prompt
  - **Command Pattern Enhancements:**
    - Implemented SetPropertyCommand for all property modifications (color, fill, width, height, thickness, text, font, gradient, visibility, location)
    - Fixed undo/redo to work action-based (A to B) instead of pixel-by-pixel
    - Enhanced SetPropertyCommand to handle multiple property types via reflection (boolean, int, Color, Point, Font, String)
    - Fixed location commands to properly capture old values before mutation
    - All property changes now support undo/redo functionality
  - **Text Gradient Support:**
    - Added gradient paint rendering to TextRenderer using GradientPaint with start/end colors
    - Text shapes now properly display gradient fills
  - **Multiple Selection Support:**
    - Extended property modification commands to work on all selected shapes simultaneously
    - Users can now modify properties (color, size, position, etc.) for multiple shapes at once
    - True grouping functionality for property editing
  - Application running successfully with all interactive features working

## Key Features (Based on Requirements)
- Multiple drawing tools (Rectangle, Ellipse, Line, Image, Text)
- Property sheet for editing shape properties
- Color picker with transparency (alpha channel) support
- Gradient fills
- Undo/Redo functionality using Command Pattern
- Save/Load drawings as XML files
- MVC architecture with service layer
- Input validation
- Selection tools (single, multiple, drag-to-select)

## Development Notes
- Main class: `com.gabriel.draw.Main`
- The project uses the Command Pattern for undo/redo operations
- All file I/O operations use XML format with proper exception handling
- GUI components follow Swing best practices
