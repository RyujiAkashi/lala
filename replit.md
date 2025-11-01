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

This compiles and runs the draw module's Main class which launches the DrawingFrame window.

## Features
- Shape drawing tools (line, rectangle, ellipse)
- Text rendering with font selection
- Image insertion
- Property editing panel
- Drawing persistence (save/load)
- Undo/redo functionality
- Selection and manipulation of shapes

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
