package net.mrscauthd.beyond_earth.client.screens.planet_selection;

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
