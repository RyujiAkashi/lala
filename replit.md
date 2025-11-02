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
- **2025-11-02**: Project imported to Replit environment
  - Installed Java (GraalVM)
  - Successfully built all Maven modules
  - Configured VNC workflow for GUI display
  - Application running successfully

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
