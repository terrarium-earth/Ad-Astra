package net.mrscauthd.beyond_earth.gui.screens.planetselection;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;
import net.mrscauthd.beyond_earth.gui.helper.ImageButtonPlacer;
import net.mrscauthd.beyond_earth.util.Rectangle2d;

@OnlyIn(Dist.CLIENT)
public class PlanetSelectionGuiWindow extends AbstractContainerScreen<PlanetSelectionGui.GuiContainer> {

	private static ResourceLocation texture = new ResourceLocation(BeyondEarthMod.MODID,"textures/screens/planet_selection_gui.png");

	private static ResourceLocation defaultButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/red_button.png");
	private static ResourceLocation gbButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/green_button.png");
	private static ResourceLocation gb2ButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/green_button_2.png");
	private static ResourceLocation rbButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/red_button.png");
	private static ResourceLocation rb2ButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/red_button_2.png");

	private static ResourceLocation dbbButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/dark_blue_button.png");
	private static ResourceLocation dbb2ButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/dark_blue_button_2.png");

	private static ResourceLocation bbButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/blue_button.png");
	private static ResourceLocation bb2ButtonTex = new ResourceLocation(BeyondEarthMod.MODID,"textures/buttons/blue_button_2.png");

	private static ResourceLocation sbbButtonTex = new ResourceLocation(BeyondEarthMod.MODID, "textures/buttons/small_blue_button.png");
	private static ResourceLocation sbb2ButtonTex = new ResourceLocation(BeyondEarthMod.MODID, "textures/buttons/small_blue_button_2.png");

	private static ResourceLocation bgbButtonTex = new ResourceLocation(BeyondEarthMod.MODID, "textures/buttons/big_green_button.png");
	private static ResourceLocation bgb2ButtonTex = new ResourceLocation(BeyondEarthMod.MODID, "textures/buttons/big_green_button_2.png");

	private static ResourceLocation brbButtonTex = new ResourceLocation(BeyondEarthMod.MODID, "textures/buttons/big_red_button.png");
	private static ResourceLocation brb2ButtonTex = new ResourceLocation(BeyondEarthMod.MODID, "textures/buttons/big_red_button_2.png");

	public int Category = -1;

	public float milkyWay = 0;
	public float rotationMars = 0;
	public float rotationEarth = 90;
	public float rotationVenus = 180;
	public float rotationMercury = 270;

	public float rotationGlacio = 180;

	//CATEGORY BUTTONS (-1)
	public ImageButtonPlacer solarSystemButton;
	public ImageButtonPlacer proximaCentauriButton;

	//CATEGORY BUTTONS (0)
	public ImageButtonPlacer earthCategoryButton;
	public ImageButtonPlacer marsCategoryButton;
	public ImageButtonPlacer mercuryCategoryButton;
	public ImageButtonPlacer venusCategoryButton;

	//CATEGORY BUTTONS (5)
	public ImageButtonPlacer glacioCategoryButton;

	//TELEPORT BUTTONS
	public ImageButtonPlacer earthButton;
	public ImageButtonPlacer moonButton;
	public ImageButtonPlacer marsButton;
	public ImageButtonPlacer mercuryButton;
	public ImageButtonPlacer venusButton;
	//Category 5 Teleport Buttons
	public ImageButtonPlacer glacioButton;

	//BACK BUTTON
	public ImageButtonPlacer backButton;

	//ORBIT BUTTONS
	public ImageButtonPlacer earthOrbitButton;
	public ImageButtonPlacer moonOrbitButton;
	public ImageButtonPlacer marsOrbitButton;
	public ImageButtonPlacer mercuryOrbitButton;
	public ImageButtonPlacer venusOrbitButton;
	//Category 5 Teleport Buttons
	public ImageButtonPlacer glacioOrbitButton;

	//SPACE STATION BUTTONS
	public ImageButtonPlacer earthSpaceStationButton;
	public ImageButtonPlacer moonSpaceStationButton;
	public ImageButtonPlacer marsSpaceStationButton;
	public ImageButtonPlacer mercurySpaceStationButton;
	public ImageButtonPlacer venusSpaceStationButton;
	//Category 5 Teleport Buttons
	public ImageButtonPlacer glacioSpaceStationButton;

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

		milkyWay = (milkyWay + partialTicks * 0.4f) % 360;

		rotationMars = (rotationMars + partialTicks * 0.4f) % 360;
		rotationEarth = (rotationEarth + partialTicks * 0.8f) % 360;
		rotationVenus = (rotationVenus + partialTicks * 0.7f) % 360;
		rotationMercury = (rotationMercury + partialTicks * 0.7f) % 360;

		rotationGlacio = (rotationGlacio + partialTicks * 0.7f) % 360;

		String rocketType = menu.rocket;

		//CATEGORY -1 BUTTON
		if (Category == -1) {
			solarSystemButton.visible = true;
			proximaCentauriButton.visible = true;

			this.categoryButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 68 / 2, 70, 20, ms, solarSystemButton, "Sun", "Solar System");

			this.categoryButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 24 / 2, 70, 20, ms, proximaCentauriButton, "Proxima Centauri", "Solar System");
		} else {
			solarSystemButton.visible = false;
			proximaCentauriButton.visible = false;
		}

		//CATEGORY 0 BUTTON
		if (Category == 0) {
			earthCategoryButton.visible = true;
			marsCategoryButton.visible = true;
			mercuryCategoryButton.visible = true;
			venusCategoryButton.visible = true;

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) - 24 / 2, 70, 20, ms, earthCategoryButton, "Earth", "Tier 1 Rocket", 1);

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) + 21 / 2, 70, 20, ms, marsCategoryButton, "Mars", "Tier 2 Rocket", 2);

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) + 65 / 2, 70, 20, ms, mercuryCategoryButton, "Mercury", "Tier 3 Rocket", 3);

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) + 109 / 2, 70, 20, ms, venusCategoryButton, "Venus", "Tier 3 Rocket", 3);
		} else {
			earthCategoryButton.visible = false;
			marsCategoryButton.visible = false;
			mercuryCategoryButton.visible = false;
			venusCategoryButton.visible = false;
		}

		//BACK BUTTON
		if (Category >= 0) {
			backButton.visible = true;

			//back
			this.backButtonManager(dbbButtonTex, dbb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 68 / 2, 70, 20, backButton);
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
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 24 / 2, 70, 20, ms, earthButton, "Planet", "9.807 m/s", "a" + "true" , "a" + "14");
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) + 21 / 2, 70, 20, ms, moonButton, "Moon", "1.62 m/s", "c" + "false" , "c" + "-160");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 24 / 2, 37, 20, ms, earthOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) + 21 / 2, 37, 20, ms, moonOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 24 / 2, 75, 20, ms, earthSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) + 21 / 2, 75, 20, ms, moonSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

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
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 24 / 2, 70, 20, ms, marsButton, "Planet", "3.721 m/s", "c" + "false" , "a" + "-63");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 24 / 2, 37, 20, ms, marsOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 24 / 2, 75, 20, ms, marsSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
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
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 24 / 2, 70, 20, ms, mercuryButton, "Planet", "3.7 m/s", "c" + "false" , "a" + "430");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 24 / 2, 37, 20, ms, mercuryOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 24 / 2, 75, 20, ms, mercurySpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
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
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 24 / 2, 70, 20, ms, venusButton, "Planet", "8.87 m/s", "c" + "false" , "a" + "482");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 24 / 2, 37, 20, ms, venusOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 24 / 2, 75, 20, ms, venusSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
		} else {
			venusButton.visible = false;
			venusOrbitButton.visible = false;
			venusSpaceStationButton.visible = false;
		}

		/**CATEGORY 5 BUTTON*/ //Proxima Centauri MAIN
		if (Category == 5) {
			glacioCategoryButton.visible = true;

			this.buttonManager(rocketType, gb2ButtonTex, rb2ButtonTex, gbButtonTex, rbButtonTex, mouseX, mouseY, 10, (this.height / 2) - 22 / 2, 70, 20, ms, glacioCategoryButton, "Glacio", "Tier 4 Rocket", 4);
		} else {
			glacioCategoryButton.visible = false;
		}

		if (Category == 6) {
			glacioButton.visible = true;
			glacioOrbitButton.visible = true;
			glacioSpaceStationButton.visible = true;

			//Planets
			this.teleportButtonManager(bbButtonTex, bb2ButtonTex, mouseX, mouseY, 10, (this.height / 2) - 24 / 2, 70, 20, ms, glacioButton, "Planet", "9.807 m/s", "a" + "true" , "a" + "-20");

			//Orbits
			this.teleportButtonManager(sbbButtonTex, sbb2ButtonTex, mouseX, mouseY, 84, (this.height / 2) - 24 / 2, 37, 20, ms, glacioOrbitButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");

			//Space Station
			this.spaceStationCreatorButtonManager(brbButtonTex, brb2ButtonTex , bgbButtonTex, bgb2ButtonTex, mouseX, mouseY, 125, (this.height / 2) - 24 / 2, 75, 20, ms, glacioSpaceStationButton, "Orbit", "No Gravity", "c" + "false" , "c" + "-270");
		} else {
			glacioButton.visible = false;
			glacioOrbitButton.visible = false;
			glacioSpaceStationButton.visible = false;
		}

		//RENDER FONTS
		this.font.draw(ms, "CATALOG", 24, (this.height / 2) - 143 / 2, -1);
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		//BACKGROUND
		RenderSystem.setShaderTexture(0, texture);
		GuiComponent.blit(ms, 0, 0, 0, 0, this.width, this.height, this.width, this.height);

		//Solar System
		if (Category >= 0 && Category <= 4) {
			RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/solar_system.png"));
			GuiHelper.blit(ms, (this.width - 185) / 2, (this.height - 185) / 2, 0, 0, 185, 185, 185, 185);
		}

		//Proxima Centauri
		if (Category >= 5 && Category <= 6) {
			RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/proxima_centauri.png"));
			GuiHelper.blit(ms, (this.width - 185) / 2, (this.height - 185) / 2, 0, 0, 185, 185, 185, 185);
		}

		//SUN
		if (Category >= 0 && Category <= 4) {
			RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/gui/sun.png"));
			GuiHelper.blit(ms, (this.width - 15) / 2, (this.height - 15) / 2, 0, 0, 15, 15, 15, 15);
		} else if (Category >= 5 && Category <= 6) {
			RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/gui/blue_sun.png"));
			GuiHelper.blit(ms, (this.width - 15) / 2, (this.height - 15) / 2, 0, 0, 15, 15, 15, 15);
		}

		//MILKY WAY
		if (Category == -1) {
			this.addGalaxy(ms, new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/gui/milky_way.png"), -125, -125, 250, 250, milkyWay);
		}

		//PLANETS
		if (Category >= 0 && Category <= 4) {
			this.addPlanet(ms, new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/gui/mars.png"), -70, -70, 10, 10, rotationMars);
			this.addPlanet(ms, new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/gui/earth.png"), -54, -54, 10, 10, rotationEarth);
			this.addPlanet(ms, new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/gui/venus.png"), -37, -37, 10, 10, rotationVenus);
			this.addPlanet(ms, new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/gui/mercury.png"), -20.5F, -20.5F, 10, 10, rotationMercury);
		} else if (Category >= 5 && Category <= 6) {
			this.addPlanet(ms, new ResourceLocation(BeyondEarthMod.MODID, "textures/sky/gui/glacio.png"), -20.5F, -20.5F, 10, 10, rotationGlacio);
		}

		//MENU
		if ((Category < 1) || (Category == 5)) {
			RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID,"textures/rocket_menu_list.png"));
			GuiComponent.blit(ms, 0, (this.height / 2) - 177 / 2, 0, 0, 105, 177, 105, 177);
		} else if ((Category >= 1 && Category <= 4) || (Category >= 5 && Category <= 6)) {
			RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID,"textures/rocket_menu_list_2.png"));
			GuiComponent.blit(ms, 0, (this.height / 2) - 177 / 2, 0, 0, 215, 177, 215, 177);
		}

		RenderSystem.disableBlend();
	}

	public void addPlanet(PoseStack ms, ResourceLocation planet, float x, float y, int width, int height, float rotation) {
		ms.pushPose();

		ms.translate(this.width / 2, this.height / 2, 0);
		ms.mulPose(new Quaternion(Vector3f.ZP, rotation, true));

		RenderSystem.setShaderTexture(0, planet);
		GuiHelper.blit(ms, x, y, 0, 0, width, height, width, height);

		ms.translate(-this.width / 2, -this.height / 2, 0);
		ms.popPose();
	}

	public void addGalaxy(PoseStack ms, ResourceLocation planet, float x, float y, int width, int height, float rotation) {
		ms.pushPose();

		ms.translate(this.width / 2, this.height / 2, 0);
		ms.mulPose(new Quaternion(Vector3f.ZP, rotation, true));

		RenderSystem.setShaderTexture(0, planet);
		GuiHelper.blit(ms, x, y, 0, 0, width, height, width, height);

		ms.translate(-this.width / 2, -this.height / 2, 0);
		ms.popPose();
	}

	@Override
	protected void init() {
		super.init();

		/**CATEGORY -1*/
		solarSystemButton = this.addImageButtonSetSolarSystem(10, (this.height / 2) - 68 / 2, 70, 20, bbButtonTex, 0, "Sun");
		proximaCentauriButton = this.addImageButtonSetSolarSystem(10, (this.height / 2) - 24 / 2, 70, 20, bbButtonTex, 5, "Proxima Cent.");

		/**CATEGORY 0*/
		earthCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) - 24 / 2, 70, 20, defaultButtonTex, 1, menu.rocket, 1, "Earth");
		earthCategoryButton.visible = false;

		marsCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) + 21 / 2, 70, 20, defaultButtonTex, 2, menu.rocket, 2, "Mars");
		marsCategoryButton.visible = false;

		mercuryCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) + 65 / 2, 70, 20, defaultButtonTex, 3, menu.rocket, 3, "Mercury");
		mercuryCategoryButton.visible = false;

		venusCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) + 109 / 2, 70, 20, defaultButtonTex, 4, menu.rocket, 3, "Venus");
		venusCategoryButton.visible = false;

		/**CATEGORY 5*/
		glacioCategoryButton = this.addImageButtonSetCategory(10, (this.height / 2) - 24 / 2, 70, 20, defaultButtonTex, 6, menu.rocket, 4, "Glacio");
		glacioCategoryButton.visible = false;

		/**BACK BUTTON*/
		backButton = this.addImageButtonBack(10, (this.height / 2) - 68 / 2, 70, 20, dbbButtonTex, "Back");
		backButton.visible = false;

		 /**TELEPORT BUTTONS*/
		 //CATEGORY 0
		earthButton = this.addImageButton(10, (this.height / 2) - 24 / 2, 70, 20, bbButtonTex, 0, "Earth");
		earthButton.visible = false;

		moonButton = this.addImageButton(10, (this.height / 2) + 21 / 2, 70, 20, bbButtonTex, 1, "Moon");
		moonButton.visible = false;

		marsButton = this.addImageButton(10, (this.height / 2) - 24 / 2, 70, 20, bbButtonTex, 2, "Mars");
		marsButton.visible = false;

		mercuryButton = this.addImageButton(10, (this.height / 2) - 24 / 2, 70, 20, bbButtonTex, 3, "Mercury");
		mercuryButton.visible = false;

		venusButton = this.addImageButton(10, (this.height / 2) - 24 / 2, 70, 20, bbButtonTex, 4, "Venus");
		venusButton.visible = false;

		//CATEGORY 5
		glacioButton = this.addImageButton(10, (this.height / 2) - 24 / 2, 70, 20, bbButtonTex, 5, "Glacio");
		glacioButton.visible = false;

		/**ORBIT BUTTONS*/
		//CATEGORY 0
		earthOrbitButton = this.addImageButton(84, (this.height / 2) - 24 / 2, 37, 20, sbbButtonTex, 6, "Orbit");
		earthOrbitButton.visible = false;

		moonOrbitButton = this.addImageButton(84, (this.height / 2) + 21 / 2, 37, 20, sbbButtonTex, 7, "Orbit");
		moonOrbitButton.visible = false;

		marsOrbitButton = this.addImageButton(84, (this.height / 2) - 24 / 2, 37, 20, sbbButtonTex, 8, "Orbit");
		marsOrbitButton.visible = false;

		mercuryOrbitButton = this.addImageButton(84, (this.height / 2) - 24 / 2, 37, 20, sbbButtonTex, 9, "Orbit");
		mercuryOrbitButton.visible = false;

		venusOrbitButton = this.addImageButton(84, (this.height / 2) - 24 / 2, 37, 20, sbbButtonTex, 10, "Orbit");
		venusOrbitButton.visible = false;
		//CATEGORY 5
		glacioOrbitButton = this.addImageButton(84, (this.height / 2) - 24 / 2, 37, 20, sbbButtonTex, 11, "Orbit");
		glacioOrbitButton.visible = false;

		 /**SPACE STATION BUTTONS*/
		//CATEGORY 0
		earthSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 24 / 2, 75, 20, brbButtonTex, 12, "Space Station", this.getSpaceStationItemList());
		earthSpaceStationButton.visible = false;

		moonSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) + 21 / 2, 75, 20, brbButtonTex, 13, "Space Station", this.getSpaceStationItemList());
		moonSpaceStationButton.visible = false;

		marsSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 24 / 2, 75, 20, brbButtonTex, 14, "Space Station", this.getSpaceStationItemList());
		marsSpaceStationButton.visible = false;

		mercurySpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 24 / 2, 75, 20, brbButtonTex, 15, "Space Station", this.getSpaceStationItemList());
		mercurySpaceStationButton.visible = false;

		venusSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 24 / 2, 75, 20, brbButtonTex, 16, "Space Station", this.getSpaceStationItemList());
		venusSpaceStationButton.visible = false;
		//CATEGORY 5
		glacioSpaceStationButton = this.addSpaceStationImageButton(125, (this.height / 2) - 24 / 2, 75, 20, brbButtonTex, 17, "Space Station", this.getSpaceStationItemList());
		glacioSpaceStationButton.visible = false;
	}

	@Override
	protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
	}

	public Rectangle2d getBounds(int left, int top, int width, int height) {
		return GuiHelper.getBounds(left, top, width, height);
	}

	public boolean checkTier(String rocketType, int stage) {
		int tier = 0;

		if (rocketType.equals("entity." + BeyondEarthMod.MODID + ".rocket_t1")) {
			tier = 1;
		}
		if (rocketType.equals("entity." + BeyondEarthMod.MODID + ".rocket_t2")) {
			tier = 2;
		}
		if (rocketType.equals("entity." + BeyondEarthMod.MODID + ".rocket_t3")) {
			tier = 3;
		}
		if (rocketType.equals("entity." + BeyondEarthMod.MODID + ".rocket_t4")) {
			tier = 4;
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

	public void categoryButtonManager(ResourceLocation bb, ResourceLocation bb2, int mouseX, int mouseY, int left, int top, int width, int height, PoseStack ms, ImageButtonPlacer button, String solarSystem, String type) {
		button.setTexture(bb);

		if (GuiHelper.isHover(this.getBounds(left, top, width, height), mouseX, mouseY)) {

			List<Component> list = new ArrayList<Component>();

			list.add(Component.nullToEmpty("\u00A79Category: " + "\u00A7b" + solarSystem));
			list.add(Component.nullToEmpty("\u00A79Type: " + "\u00A73" + type));
			this.renderComponentTooltip(ms, list, mouseX, mouseY);

			button.setTexture(bb2);
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
			list.add(Component.nullToEmpty(this.getSpaceStationItemCheck(new ItemStack(ModInit.STEEL_INGOT.get()), 16) ?  "\u00A7a" + "16 Steel Ingots" : "\u00A76" + "16 Steel Ingots"));
			list.add(Component.nullToEmpty(this.getSpaceStationItemCheck(new ItemStack(ModInit.IRON_PLATE.get()), 12) ?  "\u00A7a" + "12 Iron Plates" : "\u00A76" + "12 Iron Plates"));
			list.add(Component.nullToEmpty(this.getSpaceStationItemCheck(new ItemStack(ModInit.DESH_PLATE.get()), 4) ?  "\u00A7a" + "4 Desh Plates" : "\u00A76" + "4 Desh Plates"));

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
		Boolean boolean2 = this.getSpaceStationItemCheck(new ItemStack(ModInit.STEEL_INGOT.get()), 16);
		Boolean boolean3 = this.getSpaceStationItemCheck(new ItemStack(ModInit.IRON_PLATE.get()), 12);
		Boolean boolean4 = this.getSpaceStationItemCheck(new ItemStack(ModInit.DESH_PLATE.get()), 4);

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

		return menu.player.getAbilities().instabuild;
	}

	public void backButtonManager(ResourceLocation bb, ResourceLocation bb2, int mouseX, int mouseY, int left, int top, int width, int height, ImageButtonPlacer button) {
		if (GuiHelper.isHover(this.getBounds(left, top, width, height), mouseX, mouseY)) {
			button.setTexture(bb2);
		} else {
			button.setTexture(bb);
		}
	}

	public ImageButtonPlacer addImageButton(int xIn, int yIn, int width, int height, ResourceLocation texture, int handler, String title) {
		ImageButtonPlacer button = this.addRenderableWidget(new ImageButtonPlacer(xIn, yIn, width, height, 0, 0, 0, texture, width, height, (p_2130901) -> {
			BeyondEarthMod.PACKET_HANDLER.sendToServer(new PlanetSelectionGui.NetworkMessage(handler));
		}, Component.nullToEmpty(title)));
		return button;
	}

	public ImageButtonPlacer addSpaceStationImageButton(int xIn, int yIn, int width, int height, ResourceLocation texture, int handler, String title, boolean condition) {
		ImageButtonPlacer button = this.addRenderableWidget(new ImageButtonPlacer(xIn, yIn, width, height, 0, 0, 0, texture, width, height, (p_2130901) -> {
			if (condition) {
				BeyondEarthMod.PACKET_HANDLER.sendToServer(new PlanetSelectionGui.NetworkMessage(handler));
			}
		}, Component.nullToEmpty(title)));
		return button;
	}

	public ImageButtonPlacer addImageButtonSetCategory(int xIn, int yIn, int width, int height, ResourceLocation texture, int newCategory, String rocket, int stage, String title) {
		ImageButtonPlacer button = this.addRenderableWidget(new ImageButtonPlacer(xIn, yIn, width, height, 0, 0, 0, texture, width, height, (p_2130901) -> {
			if (checkTier(rocket, stage)) {
				this.Category = newCategory;
			}
		}, Component.nullToEmpty(title)));
		return button;
	}

	public ImageButtonPlacer addImageButtonBack(int xIn, int yIn, int width, int height, ResourceLocation texture, String title) {
		ImageButtonPlacer button = this.addRenderableWidget(new ImageButtonPlacer(xIn, yIn, width, height, 0, 0, 0, texture, width, height, (p_2130901) -> {
			if (this.Category >= 1 && this.Category <= 4) {
				this.Category = 0;
			} else if (this.Category == 5) {
				this.Category = -1;
			} else if (this.Category >= 6 && this.Category <= 6) {
				this.Category = 5 ;
			} else {
				this.Category = this.Category - 1;
			}
		}, Component.nullToEmpty(title)));
		return button;
	}

	public ImageButtonPlacer addImageButtonSetSolarSystem(int xIn, int yIn, int width, int height, ResourceLocation texture, int newCategory, String title) {
		ImageButtonPlacer button = this.addRenderableWidget(new ImageButtonPlacer(xIn, yIn, width, height, 0, 0, 0, texture, width, height, (p_2130901) -> {
			this.Category = newCategory;
		}, Component.nullToEmpty(title)));
		return button;
	}
}