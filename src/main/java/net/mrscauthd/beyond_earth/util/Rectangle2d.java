package net.mrscauthd.beyond_earth.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Rectangle2d {
	private int x;
	private int y;
	private int width;
	private int height;

	public Rectangle2d(int p_i47637_1_, int p_i47637_2_, int p_i47637_3_, int p_i47637_4_) {
		this.x = p_i47637_1_;
		this.y = p_i47637_2_;
		this.width = p_i47637_3_;
		this.height = p_i47637_4_;
	}

	@OnlyIn(Dist.CLIENT)
	public Rectangle2d(net.minecraft.client.renderer.Rect2i vanila) {
		this.x = vanila.getX();
		this.y = vanila.getY();
		this.width = vanila.getWidth();
		this.height = vanila.getHeight();
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean contains(int p_199315_1_, int p_199315_2_) {
		return p_199315_1_ >= this.x && p_199315_1_ <= this.x + this.width && p_199315_2_ >= this.y && p_199315_2_ <= this.y + this.height;
	}

	@OnlyIn(Dist.CLIENT)
	public net.minecraft.client.renderer.Rect2i toRect2i() {
		return new net.minecraft.client.renderer.Rect2i(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
