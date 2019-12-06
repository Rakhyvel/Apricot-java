package com.josephs_projects;

import java.awt.*;

public interface Element {
    void tick();
    void render(Graphics g);
    void input();
}
