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
- **2025-11-02 (Latest Session)**: Critical bug fixes and feature completions
  - **SetPropertyCommand Reflection Fix:**
    - Fixed SetPropertyCommand to properly iterate through multiple parameter types (Color, Font, Point, String, boolean, int)
    - Color objects are now correctly set via reflection instead of being converted to strings
    - All property changes now work correctly with undo/redo functionality
  - **Alpha Transparency Support:**
    - Implemented alpha property handlers for Fore Alpha, Fill Alpha, Start Alpha, and End Alpha
    - Property sheet now allows users to control transparency for all color properties
    - Alpha values are properly preserved when creating Color objects from RGB components
  - **Line Style Command Pattern Integration:**
    - Added setLineStyle/getLineStyle methods to AppService interface and implementations
    - Line Style changes now go through DrawingCommandAppService with SetPropertyCommand support
    - Line style changes are now undoable/redoable and persist to Drawing defaults for new shapes
    - Fixed PropertyEventListener to delegate line style changes to appService instead of direct mutation
  - **Text Gradient Rendering Fix:**
    - Fixed TextRenderer to use proper coordinates relative to shape location
    - Gradient paint now correctly displays on text shapes with start/end colors
    - Text gradients render at the shape's actual position instead of absolute coordinates
  - **Undo/Redo Enhancements:**
    - Added redoStack.clear() to CommandService.ExecuteCommand() method
    - Redo stack is now properly cleared when new commands are executed, preventing invalid redo operations
    - Undo/redo works correctly for all property changes including color, thickness, line style, position, size
  - **New Shape Creation:**
    - DrawingController now properly sets thickness and lineStyle when creating new shapes
    - New shapes inherit current Drawing defaults for thickness and line style
    - All shape properties (color, fill, thickness, line style, font) properly persist to new shapes
  - All fixes tested and verified working - application builds and runs successfully

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
