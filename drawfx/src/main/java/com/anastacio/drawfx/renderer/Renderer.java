package com.anastacio.drawfx.renderer;

import com.anastacio.drawfx.model.Shape;

import java.awt.*;

public interface Renderer {
        void render(Graphics g, Shape shape, boolean xor);
}
