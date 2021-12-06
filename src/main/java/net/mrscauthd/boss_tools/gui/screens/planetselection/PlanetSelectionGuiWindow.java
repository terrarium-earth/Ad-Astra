package net.mrscauthd.boss_tools.gui.screens.planetselection;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.gui.helper.ImageButtonPlacer;
import net.mrscauthd.boss_tools.util.Rectangle2d;

@OnlyIn(Dist.CLIENT)
public class PlanetSelectionGuiWindow extends AbstractContainerScreen<PlanetSelectionGui.GuiContainer> {

	private static ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId,"textures/screens/planet_selection_gui.png");

	private static ResourceLocation defaultButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/red_button.png");
	private static ResourceLocation gbButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/green_button.png");
	private static ResourceLocation gb2ButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/green_button_2.png");
	private static ResourceLocation rbButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/red_button.png");
	private static ResourceLocation rb2ButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/red_button_2.png");

	private static ResourceLocation dbbButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/dark_blue_button.png");
	private static ResourceLocation dbb2ButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/dark_blue_button_2.png");

	private static ResourceLocation bbButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/blue_button.png");
	private static ResourceLocation bb2ButtonTex = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/blue_button_2.png");

	private static ResourceLocation sbbButtonTex = new ResourceLocation(BossToolsMod.ModId, "textures/buttons/small_blue_button.png");
	private static ResourceLocation sbb2ButtonTex = new ResourceLocation(BossToolsMod.ModId, "textures/buttons/small_blue_button_2.png");

	private static ResourceLocation bgbButtonTex = new ResourceLocation(BossToolsMod.ModId, "textures/buttons/big_green_button.png");
	private static ResourceLocation bgb2ButtonTex = new ResourceLocation(BossToolsMod.ModId, "textures/buttons/big_green_button_2.png");

	private static ResourceLocation brbButtonTex = new ResourceLocation(BossToolsMod.ModId, "textures/buttons/big_red_button.png");
	private static ResourceLocation brb2ButtonTex = new ResourceLocation(BossToolsMod.ModId, "textures/buttons/big_red_button_2.png");

	public int Category = 0;

	public float rotationMars = 0;
	public float rotationEarth = 90;
	public float rotationVenus = 180;
	public float rotationMercury = 270;

	//CATEGORY BUTTONS (0)
	public ImageButtonPlacer earthCategoryButton;
	public ImageButtonPlacer marsCategoryButton;
	public ImageButtonPlacer mercuryCategoryButton;
	public ImageButtonPlacer venusCategoryButton;

	//TELEPORT BUTTONS
	public ImageButtonPlacer earthButton;
	public ImageButtonPlacer moonButton;
	public ImageButtonPlacer marsButton;
	public ImageButtonPlacer mercuryButton;
	public ImageButtonPlacer venusButton;

	//BACK BUTTON
	public ImageButtonPlacer backButton;

	//ORBIT BUTTONS
	public ImageButtonPlacer earthOrbitButton;
	public ImageButtonPlacer moonOrbitButton;
	public ImageButtonPlacer marsOrbitButton;
	public ImageButtonPlacer mercuryOrbitButton;
	public ImageButtonPlacer venusOrbitButton;

	//SPACE STATION BUTTONS
	public ImageButtonPlacer earthSpaceStationButton;
	public ImageButtonPlacer moonSpaceStationButton;
	public ImageButtonPlacer marsSpaceStationButton;
	public ImageButtonPlacer mercurySpaceStationButton;
	public ImageButtonPlacer venusSpaceStationButton;

	public PlanetSelectionGuiWindow(PlanetSelectionGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 512;
		this.imageHeight = 512;
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);

		rotationMars = (rotationMars + partialTicks * 0.6f) % 360;
		rotationEarth = (rotationEarth + partialTicks * 1.2f) % 360;
		rotationVenus = (rotationVenus + partialTicks * 1.1f) % 360;
		rotationMercury = (rotationMercury + partialTicks * 0.9f) % 360;

		String rocketType = menu.rocket;

		//CATEGORY 0 BUTTON
		if (Category == 0) {
			earthCategoryButton.visible = true;
			marsCategoryButton.visible = true;
			mercuryCategoryButton.visible = true;
			venusCategoryButton.visible = true;

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) - 48 / 2, 70, 20, ms, earthCategoryButton, "Earth", "Tier 1 Rocket", 1);

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) - 4 / 2, 70, 20, ms, marsCategoryButton, "Mars", "Tier 2 Rocket", 2);

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) + 40 / 2, 70, 20, ms, mercuryCategoryButton, "Mercury", "Tier 3 Rocket", 3);

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) + 84 / 2, 70, 20, ms, venusCategoryButton, "Venus", "Tier 3 Rocket", 3);
		} else {
			earthCategoryButton.visible = false;
			marsCategoryButton.visible = false;
			mercuryCategoryButton.visible = false;
			venusCategoryButton.visible = false;
		}

		//BACK BUTTON
		if (Category >= 1) {
			backButton.visible = true;

			//back
			this.backButtonManager(dbbButtonTex, dbb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 48 / 2, 70, 20, backButton);
		} else {
			backButton.visible = false;
		}

		//CATEGORY 1 BUTTON
		if (Category == 1) {
			earthButton.visible = true;
			earthOrbitButton.visible = true;
			earthSpaceStationButton.visible = true;
			moonButton.visible = true;
			moonOrbitButton.visible = true;
			moonSpaceStationButton.visible = true;

			//Planets
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 4 / 2, 70, 20, ms, earthButton, "Planet", "9.807 m/s", "a" + "true" , "a" + "14");
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) + 40 / 2, 70, 20, ms, moonButton, "Moon", "1.62 m/s", "c" + "false" , "c" + "-160");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 4 / 2, 37, 20, ms, earthOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) + 40 / 2, 37, 20, ms, moonOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 4 / 2, 75, 20, ms, earthSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) + 40 / 2, 75, 20, ms, moonSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

		} else {
			earthButton.visible = false;
			earthOrbitButton.visible = false;
			earthSpaceStationButton.visible = false;
			moonButton.visible = false;
			moonOrbitButton.visible = false;
			moonSpaceStationButton.visible = false;
		}

		//CATEGORY 2 BUTTON
		if (Category == 2) {
			marsButton.visible = true;
			marsOrbitButton.visible = true;
			marsSpaceStationButton.visible = true;

			//Planets
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 4 / 2, 70, 20, ms, marsButton, "Planet", "3.721 m/s", "a" + "true" , "a" + "-63");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 4 / 2, 37, 20, ms, marsOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 4 / 2, 75, 20, ms, marsSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
		} else {
			marsButton.visible = false;
			marsOrbitButton.visible = false;
			marsSpaceStationButton.visible = false;
		}

		//CATEGORY 3 BUTTON
		if (Category == 3) {
			mercuryButton.visible = true;
			mercuryOrbitButton.visible = true;
			mercurySpaceStationButton.visible = true;

			//Planets
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 4 / 2, 70, 20, ms, mercuryButton, "Planet", "3.721 m/s", "a" + "true" , "a" + "-63");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 4 / 2, 37, 20, ms, mercuryOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 4 / 2, 75, 20, ms, mercurySpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
		} else {
			mercuryButton.visible = false;
			mercuryOrbitButton.visible = false;
			mercurySpaceStationButton.visible = false;
		}

		//CATEGORY 4 BUTTON
		if (Category == 4) {
			venusButton.visible = true;
			venusOrbitButton.visible = true;
			venusSpaceStationButton.visible = true;

			//Planets
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 4 / 2, 70, 20, ms, venusButton, "Planet", "3.721 m/s", "a" + "true" , "a" + "-63");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 4 / 2, 37, 20, ms, venusOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 4 / 2, 75, 20, ms, venusSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
		} else {
			venusButton.visible = false;
			venusOrbitButton.visible = false;
			venusSpaceStationButton.visible = false;
		}

		//RENDER FONTS
		this.font.draw(ms, "CATALOG", 24, (this.height / 2) - 126 / 2, -1);
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		//BACKGROUND
		Minecraft.getInstance().getTextureManager().bindForSetup(texture);
		GuiComponent.blit(ms, 0, 0, 0, 0, this.width, this.height, this.width, this.height);

		//MILKY WAY
		Minecraft.getInstance().getTextureManager().bindForSetup(new ResourceLocation(BossToolsMod.ModId,"textures/milky_way.png"));
		GuiHelper.blit(ms, (this.width - 185) / 2, (this.height - 185) / 2, 0, 0, 185, 185, 185, 185);

		//SUN
		Minecraft.getInstance().getTextureManager().bindForSetup(new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/sun.png"));
		GuiHelper.blit(ms, (this.width - 15) / 2, (this.height - 15) / 2, 0, 0, 15, 15, 15, 15);

		//PLANETS
		this.addPlanet(ms, new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/mars.png"), -70, -70, 10, 10, rotationMars);
		this.addPlanet(ms, new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/earth.png"), -54, -54, 10, 10, rotationEarth);
		this.addPlanet(ms, new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/venus.png"), -37, -37, 10, 10, rotationVenus);
		this.addPlanet(ms, new ResourceLocation(BossToolsMod.ModId,"textures/sky/gui/mercury.png"), -20.5F, -20.5F, 10, 10, rotationMercury);

		//MENU
		if (Category == 0) {
			Minecraft.getInstance().getTextureManager().bindForSetup(new ResourceLocation(BossToolsMod.ModId, "textures/rocket_menu_list.png"));
			GuiComponent.blit(ms, 0, (this.height / 2) - 160 / 2, 0, 0, 105, 160, 105, 160);
		} else {
			Minecraft.getInstance().getTextureManager().bindForSetup(new ResourceLocation(BossToolsMod.ModId, "textures/rocket_menu_list_2.png"));
			GuiComponent.blit(ms, 0, (this.height / 2) - 160 / 2, 0, 0, 215, 160, 215, 160);
		}

		RenderSystem.disableBlend();
	}

	public void addPlanet(PoseStack ms, ResourceLocation planet, float x, float y, int width, int height, float rotation) {
		ms.pushPose();

		ms.translate(this.width / 2, this.height / 2, 0);
		ms.mulPose(new Quaternion(Vector3f.ZP, rotation, true));

		Minecraft.getInstance().getTextureManager().bindForSetup(planet);
		GuiHelper.blit(ms, x, y, 0, 0, width, height, width, height);

		ms.translate(-this.width / 2, -this.height / 2, 0);
		ms.popPose();
	}

	@Override
	protected void init() {
		super.init();

		/**CATEGORIES*/
		earthCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) - 48 / 2, 70, 20, defaultButtonTex, 1, menu.rocket, 1, "Earth");
		marsCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) - 4 / 2, 70, 20, defaultButtonTex, 2, menu.rocket, 2, "Mars");
		mercuryCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) + 40 / 2, 70, 20, defaultButtonTex, 3, menu.rocket, 3, "Mercury");
		venusCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) + 84 / 2, 70, 20, defaultButtonTex, 4, menu.rocket, 3, "Venus");

		/**BACK BUTTON*/
		backButton = this.addImageButtonSetCategory(10, (this.height / 2) - 48 / 2, 70, 20, dbbButtonTex, 0, menu.rocket, 1, "Back");
		backButton.visible = false;

		 /**TELEPORT BUTTONS*/
		earthButton = this.addImageButton(10, (this.height / 2) - 4 / 2, 70, 20, bbButtonTex, 0, "Earth");
		earthButton.visible = false;

		moonButton = this.addImageButton(10, (this.height / 2) + 40 / 2, 70, 20, bbButtonTex, 1, "Moon");
		moonButton.visible = false;

		marsButton = this.addImageButton(10, (this.height / 2) - 4 / 2, 70, 20, bbButtonTex, 2, "Mars");
		marsButton.visible = false;

		mercuryButton = this.addImageButton(10, (this.height / 2) - 4 / 2, 70, 20, bbButtonTex, 3, "Mercury");
		mercuryButton.visible = false;

		venusButton = this.addImageButton(10, (this.height / 2) - 4 / 2, 70, 20, bbButtonTex, 4, "Venus");
		venusButton.visible = false;

		 /**ORBIT BUTTONS*/
		earthOrbitButton = this.addImageButton(84, (this.height / 2) - 4 / 2, 37, 20, sbbButtonTex, 5, "Orbit");
		earthOrbitButton.visible = false;

		moonOrbitButton = this.addImageButton(84, (this.height / 2) + 40 / 2, 37, 20, sbbButtonTex, 6, "Orbit");
		moonOrbitButton.visible = false;

		marsOrbitButton = this.addImageButton(84, (this.height / 2) - 4 / 2, 37, 20, sbbButtonTex, 7, "Orbit");
		marsOrbitButton.visible = false;

		mercuryOrbitButton = this.addImageButton(84, (this.height / 2) - 4 / 2, 37, 20, sbbButtonTex, 8, "Orbit");
		mercuryOrbitButton.visible = false;

		venusOrbitButton = this.addImageButton(84, (this.height / 2) - 4 / 2, 37, 20, sbbButtonTex, 9, "Orbit");
		venusOrbitButton.visible = false;

		 /**SPACE STATION BUTTONS*/
		earthSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 4 / 2, 75, 20, brbButtonTex, 10, "Space Station", this.getSpaceStationItemList());
		earthSpaceStationButton.visible = false;

		moonSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) + 40 / 2, 75, 20, brbButtonTex, 11, "Space Station", this.getSpaceStationItemList());
		moonSpaceStationButton.visible = false;

		marsSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 4 / 2, 75, 20, brbButtonTex, 12, "Space Station", this.getSpaceStationItemList());
		marsSpaceStationButton.visible = false;

		mercurySpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 4 / 2, 75, 20, brbButtonTex, 13, "Space Station", this.getSpaceStationItemList());
		mercurySpaceStationButton.visible = false;

		venusSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 4 / 2, 75, 20, brbButtonTex, 14, "Space Station", this.getSpaceStationItemList());
		venusSpaceStationButton.visible = false;
	}

	@Override
	protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
	}

	public Rectangle2d getBounds(int left, int top, int width, int height) {
		return GuiHelper.getBounds(left, top, width, height);
	}

	public boolean checkTier(String rocketType, int stage) {
		int tier = 0;

		if (rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t1")) {
			tier = 1;
		}
		if (rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t2")) {
			tier = 2;
		}
		if (rocketType.equals("entity." + BossToolsMod.ModId + ".rocket_t3")) {
			tier = 3;
		}

		return tier >= stage;
	}

	public void buttonManager(String rocketType, ResourceLocation gb2, ResourceLocation rb2, ResourceLocation gb, ResourceLocation rb, int mouseX, int mouseY, int left, int top, int width, int height, PoseStack ms, ImageButtonPlacer button, String dim, String rocketTier, int stage) {
		String level = "c";

		if (checkTier(rocketType, stage)) {
			level = "a";
			button.setTexture(gb);
		} else {
			level = "c";
			button.setTexture(rb);
		}

		if (GuiHelper.isHover(this.getBounds(left, top, width, height), mouseX, mouseY)) {

			List<Component> list = new ArrayList<Component>();

			list.add(Component.nullToEmpty("\u00A79Category: " + "\u00A7" + level + dim));
			list.add(Component.nullToEmpty("\u00A79Provided: \u00A7b" + rocketTier));
			this.renderComponentTooltip(ms, list, mouseX, mouseY);


			if (checkTier(rocketType, stage)) {
				button.setTexture(gb2);
			} else {
				button.setTexture(rb2);
			}
		}
	}

	public void teleportButtonManager(ResourceLocation bb, ResourceLocation bb2, int mouseX, int mouseY, int left, int top, int width, int height, PoseStack ms, ImageButtonPlacer button, String planetType, String gravity, String oxygen, String temperature) {
		if (GuiHelper.isHover(this.getBounds(left, top, width, height), mouseX, mouseY)) {

			List<Component> list = new ArrayList<Component>();

			list.add(Component.nullToEmpty("\u00A79Type: " + "\u00A73" + planetType));
			list.add(Component.nullToEmpty("\u00A79Gravity: " + "\u00A73" + gravity));
			list.add(Component.nullToEmpty("\u00A79Oxygen: " + "\u00A7" + oxygen));
			list.add(Component.nullToEmpty("\u00A79Temperature: \u00A7" + temperature));
			this.renderComponentTooltip(ms, list, mouseX, mouseY);

			button.setTexture(bb2);

		} else {
			button.setTexture(bb);
		}
	}

	public void spaceStationCreatorButtonManager(ResourceLocation brb, ResourceLocation brb2, ResourceLocation bbb, ResourceLocation bbb2, int mouseX, int mouseY, int left, int top, int width, int height, PoseStack ms, ImageButtonPlacer button, String orbitType, String gravity, String oxygen, String temperature) {

		if (this.getSpaceStationItemList()) {
			button.setTexture(bbb);
		} else {
			button.setTexture(brb);
		}

		if (GuiHelper.isHover(this.getBounds(left, top, width, height), mouseX, mouseY)) {
			List<Component> list = new ArrayList<Component>();

			list.add(Component.nullToEmpty("\u00A79Item Requirement:"));

			list.add(Component.nullToEmpty(this.getSpaceStationItemCheck(new ItemStack(Items.DIAMOND), 6) ?  "\u00A7a" + "6 Diamonds" : "\u00A76" + "6 Diamonds"));
			list.add(Component.nullToEmpty(this.getSpaceStationItemCheck(new ItemStack(ModInnet.STEEL_INGOT.get()), 16) ?  "\u00A7a" + "16 Steel Ingots" : "\u00A76" + "16 Steel Ingots"));
			list.add(Component.nullToEmpty(this.getSpaceStationItemCheck(new ItemStack(ModInnet.IRON_PLATE.get()), 12) ?  "\u00A7a" + "12 Iron Plates" : "\u00A76" + "12 Iron Plates"));
			list.add(Component.nullToEmpty(this.getSpaceStationItemCheck(new ItemStack(ModInnet.DESH_PLATE.get()), 4) ?  "\u00A7a" + "4 Desh Plates" : "\u00A76" + "4 Desh Plates"));

			list.add(Component.nullToEmpty("\u00A7c----------------"));
			list.add(Component.nullToEmpty("\u00A79Type: " + "\u00A73" + orbitType));
			list.add(Component.nullToEmpty("\u00A79Gravity: " + "\u00A73" + gravity));
			list.add(Component.nullToEmpty("\u00A79Oxygen: " + "\u00A7" + oxygen));
			list.add(Component.nullToEmpty("\u00A79Temperature: \u00A7" + temperature));
			this.renderComponentTooltip(ms, list, mouseX, mouseY);

			if (this.getSpaceStationItemList()) {
				button.setTexture(bbb2);
			} else {
				button.setTexture(brb2);
			}
		}
	}

	public boolean getSpaceStationItemList() {
		Boolean boolean1 = this.getSpaceStationItemCheck(new ItemStack(Items.DIAMOND), 6);
		Boolean boolean2 = this.getSpaceStationItemCheck(new ItemStack(ModInnet.STEEL_INGOT.get()), 16);
		Boolean boolean3 = this.getSpaceStationItemCheck(new ItemStack(ModInnet.IRON_PLATE.get()), 12);
		Boolean boolean4 = this.getSpaceStationItemCheck(new ItemStack(ModInnet.DESH_PLATE.get()), 4);

		return boolean1 && boolean2 && boolean3 && boolean4;
	}

	public boolean getSpaceStationItemCheck(ItemStack itemStackIn, int count) {
		Inventory inv = menu.player.getInventory();
		int itemStackCount = 0;

		for (int i = 0; i < inv.getContainerSize(); ++i) {
			ItemStack itemStack = inv.getItem(i);

			if (itemStack.sameItem(itemStackIn)) {
				itemStackCount = itemStackCount + itemStack.getCount();
			}

			if (!itemStack.isEmpty() && itemStack.sameItem(itemStackIn) && itemStackCount >= count) {
				return true;
			}
		}

		return false;
	}

	public void backButtonManager(ResourceLocation bb, ResourceLocation bb2, int mouseX, int mouseY, int left, int top, int width, int height, ImageButtonPlacer button) {
		if (GuiHelper.isHover(this.getBounds(left, top, width, height), mouseX, mouseY)) {
			button.setTexture(bb2);
		} else {
			button.setTexture(bb);
		}
	}

	public ImageButtonPlacer addImageButton(int xIn, int yIn, int width, int height, ResourceLocation texture, int handler, String title) {
		ImageButtonPlacer button = this.addWidget(new ImageButtonPlacer(xIn, yIn, width, height, 0, 0, 0, texture, width, height, (p_2130901) -> {
			BossToolsMod.PACKET_HANDLER.sendToServer(new PlanetSelectionGui.NetworkMessage(handler));
		}, Component.nullToEmpty(title)));
		return button;
	}

	public ImageButtonPlacer addSpaceStationImageButton(int xIn, int yIn, int width, int height, ResourceLocation texture, int handler, String title, boolean condition) {
		ImageButtonPlacer button = this.addWidget(new ImageButtonPlacer(xIn, yIn, width, height, 0, 0, 0, texture, width, height, (p_2130901) -> {
			if (condition) {
				BossToolsMod.PACKET_HANDLER.sendToServer(new PlanetSelectionGui.NetworkMessage(handler));
			}
		}, Component.nullToEmpty(title)));
		return button;
	}

	public ImageButtonPlacer addImageButtonSetCategory(int xIn, int yIn, int width, int height, ResourceLocation texture, int newCategory, String rocket, int stage, String title) {
		ImageButtonPlacer button = this.addWidget(new ImageButtonPlacer(xIn, yIn, width, height, 0, 0, 0, texture, width, height, (p_2130901) -> {
			if (checkTier(rocket, stage)) {
				this.Category = newCategory;
			}
		}, Component.nullToEmpty(title)));
		return button;
	}
}
