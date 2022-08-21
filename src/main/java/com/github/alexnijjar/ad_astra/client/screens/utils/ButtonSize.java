package com.github.alexnijjar.beyond_earth.client.screens.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum ButtonSize {
        LARGE(75, 20), NORMAL(71, 20), SMALL(37, 20);

        private int width;
        private int height;

        private ButtonSize(int width, int height) {
                this.width = width;
                this.height = height;
        }

        public int getWidth() {
                return this.width;
        }

        public int getHeight() {
                return this.height;
        }
}
