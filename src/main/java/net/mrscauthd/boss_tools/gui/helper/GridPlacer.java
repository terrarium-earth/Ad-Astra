package net.mrscauthd.boss_tools.gui.helper;

import net.minecraft.client.renderer.Rectangle2d;

public class GridPlacer {

	private int cellWidth;
	private int cellHeight;
	private int offset;

	public GridPlacer() {
		this.setCellWidth(16);
		this.setCellHeight(16);
		this.setOffset(2);
	}

	public Rectangle2d placeRight(int index, int left, int top, int rows) {
		int xi = index / rows;
		int yi = index % rows;
		int cellWidth = this.getCellWidth();
		int cellHeight = this.getCellHeight();
		int offset = this.getOffset();
		return new Rectangle2d(left + xi * (cellWidth + offset), top + yi * (cellHeight + offset), cellWidth, cellHeight);
	}

	public Rectangle2d placeBottom(int index, int left, int top, int columns) {
		int xi = index % columns;
		int yi = index / columns;
		int cellWidth = this.getCellWidth();
		int cellHeight = this.getCellHeight();
		int offset = this.getOffset();
		return new Rectangle2d(left + xi * (cellWidth + offset), top + yi * (cellHeight + offset), cellWidth, cellHeight);
	}

	public int getCellWidth() {
		return this.cellWidth;
	}

	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}

	public int getCellHeight() {
		return this.cellHeight;
	}

	public void setCellHeight(int cellHeight) {
		this.cellHeight = cellHeight;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
