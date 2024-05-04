package mod.vemerion.wizardstaff.event;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.capability.Wizard;
import mod.vemerion.wizardstaff.init.ModContainers;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
import mod.vemerion.wizardstaff.init.ModMagics;
import mod.vemerion.wizardstaff.init.ModParticles;
import mod.vemerion.wizardstaff.model.DruidArmorModel;
import mod.vemerion.wizardstaff.model.GrapplingHookModel;
import mod.vemerion.wizardstaff.model.MagicSoulSandArmModel;
import mod.vemerion.wizardstaff.model.NetherPortalModel;
import mod.vemerion.wizardstaff.model.NetherWizardStaffModel;
import mod.vemerion.wizardstaff.model.WarlockArmorModel;
import mod.vemerion.wizardstaff.model.WizardArmorModel;
import mod.vemerion.wizardstaff.model.WizardHatModel;
import mod.vemerion.wizardstaff.model.WizardStaffModel;
import mod.vemerion.wizardstaff.renderer.GrapplingHookRenderer;
import mod.vemerion.wizardstaff.renderer.MagicSoulSandArmRenderer;
import mod.vemerion.wizardstaff.renderer.MagicWitherSkullRenderer;
import mod.vemerion.wizardstaff.renderer.NetherPortalRenderer;
import mod.vemerion.wizardstaff.renderer.WizardHatRenderer;
import mod.vemerion.wizardstaff.screen.MagicScreen;
import mod.vemerion.wizardstaff.staff.WizardStaffScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.DustParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	public static KeyMapping cycleMagicKey;

	@SubscribeEvent
	public static void onRegister(FMLClientSetupEvent event) {
		MenuScreens.register(ModContainers.WIZARD_STAFF.get(), WizardStaffScreen::new);
		MenuScreens.register(ModContainers.MAGIC.get(), MagicScreen::new);
	}

	@SubscribeEvent
	public static void onRegisterKeys(RegisterKeyMappingsEvent event) {
		cycleMagicKey = new KeyMapping("key." + Main.MODID + ".cycleMagicKey", KeyConflictContext.IN_GAME,
				InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_V), "key." + Main.MODID + ".category");
		event.register(cycleMagicKey);
	}

	@SubscribeEvent
	public static void onRegisterEntityRendererLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModLayerLocations.WARLOCK_ARMOR, WarlockArmorModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.DRUID_ARMOR, DruidArmorModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.WIZARD_ARMOR, WizardArmorModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.GRAPPLING_HOOK, GrapplingHookModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.MAGIC_SOUL_SAND_ARM, MagicSoulSandArmModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.NETHER_PORTAL, NetherPortalModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.WIZARD_HAT, WizardHatModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.NETHER_WIZARD_STAFF, NetherWizardStaffModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.WIZARD_STAFF, WizardStaffModel::createLayer);
	}

	@SubscribeEvent
	public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.PUMPKIN_MAGIC.get(), NoRenderer::new);
		event.registerEntityRenderer(ModEntities.NETHER_PORTAL.get(), NetherPortalRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_WITHER_SKULL.get(), MagicWitherSkullRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_SOUL_SAND_ARM.get(), MagicSoulSandArmRenderer::new);
		event.registerEntityRenderer(ModEntities.GRAPPLING_HOOK.get(), GrapplingHookRenderer::new);
		event.registerEntityRenderer(ModEntities.MUSHROOM_CLOUD.get(), NoRenderer::new);
		event.registerEntityRenderer(ModEntities.WIZARD_HAT.get(), WizardHatRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_VEX.get(), VexRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_MINING_VEX.get(), VexRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_MISSILE_ENTITY.get(), r -> new ThrownItemRenderer<>(r, 1, true));
	}

	private static class NoRenderer<T extends Entity> extends EntityRenderer<T> {

		protected NoRenderer(EntityRendererProvider.Context renderManager) {
			super(renderManager);
		}

		@Override
		public ResourceLocation getTextureLocation(T entity) {
			return null;
		}

	}

	@SubscribeEvent
	public static void onRegisterColor(RegisterColorHandlersEvent.Item event) {
		event.register((stack, tint) -> {
			return tint > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
		}, ModItems.WIZARD_HAT.get(), ModItems.WIZARD_BOOTS.get(), ModItems.WIZARD_CHESTPLATE.get(),
				ModItems.WIZARD_LEGGINGS.get(), ModItems.DRUID_HELMET.get(), ModItems.DRUID_BOOTS.get(),
				ModItems.DRUID_CHESTPLATE.get(), ModItems.DRUID_LEGGINGS.get(), ModItems.WARLOCK_BOOTS.get(),
				ModItems.WARLOCK_CHESTPLATE.get(), ModItems.WARLOCK_HELMET.get(), ModItems.WARLOCK_LEGGINGS.get());
	}

	@SubscribeEvent
	public static void onRegisterParticleFactories(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticles.MAGIC_SMOKE_PARTICLE.get(), sprite -> new SmokeParticle.Provider(sprite));
		event.registerSpriteSet(ModParticles.MAGIC_FLAME_PARTICLE.get(), sprite -> new FlameParticle.Provider(sprite));
		event.registerSpriteSet(ModParticles.MAGIC_DUST_PARTICLE.get(), sprite -> new DustParticle.Provider(sprite));
	}

	@SubscribeEvent
	public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
		event.registerAboveAll("zoom_magic", (gui, ps, partialTick, screenWidth, screenHeight) -> {
			var mc = Minecraft.getInstance();
			if (!(Wizard.isUsingMagic(ModMagics.ZOOM_MAGIC.get(), mc.player)
					&& mc.options.getCameraType().isFirstPerson()))
				return;

			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableDepthTest();

			float time = mc.level.getGameTime() + partialTick;

			RenderSystem.setShaderColor(Math.abs(Mth.sin(time / 20f)) * 0.5f + 0.5f, 0,
					Math.abs(Mth.cos(time / 20f + 0.2f)) * 0.5f + 0.5f, 1.0F);
			RenderSystem.setShaderTexture(0, new ResourceLocation("textures/misc/spyglass_scope.png"));

			ps.pushPose();
			ps.translate((screenWidth - 256) / 2, 0, 0);
			ps.translate(256 / 2, 256 / 2, 0);
			ps.mulPose(Axis.ZP.rotationDegrees(time));
			ps.translate(-256 / 2, -256 / 2, 0);

			GuiComponent.blit(ps, 0, 0, -90, 0, 0, 256, 256, 256, 256);

			RenderSystem.restoreProjectionMatrix();
			ps.popPose();
			RenderSystem.applyModelViewMatrix();
		});
	}
}
