package com.github.alexnijjar.ad_astra.client.screens.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum ButtonType {
        LARGE(75, 20), NORMAL(71, 20), SMALL(37, 20), STEEL(71, 20);

        private int width;
        private int height;

        private ButtonType(int width, int height) {
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
