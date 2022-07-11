package mod.vemerion.wizardstaff.event;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModContainers;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModLayerLocations;
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
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.DustParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
	
	public static KeyMapping cycleMagicKey;
	
	@SubscribeEvent
	public static void onRegister(FMLClientSetupEvent event) {
		MenuScreens.register(ModContainers.WIZARD_STAFF, WizardStaffScreen::new);
		MenuScreens.register(ModContainers.MAGIC, MagicScreen::new);
		
		cycleMagicKey = new KeyMapping("key." + Main.MODID + ".cycleMagicKey", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_V), "key." + Main.MODID + ".category");
		ClientRegistry.registerKeyBinding(cycleMagicKey);
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
		event.registerEntityRenderer(ModEntities.PUMPKIN_MAGIC, NoRenderer::new);
		event.registerEntityRenderer(ModEntities.NETHER_PORTAL, NetherPortalRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_WITHER_SKULL, MagicWitherSkullRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_SOUL_SAND_ARM, MagicSoulSandArmRenderer::new);
		event.registerEntityRenderer(ModEntities.GRAPPLING_HOOK, GrapplingHookRenderer::new);
		event.registerEntityRenderer(ModEntities.MUSHROOM_CLOUD, NoRenderer::new);
		event.registerEntityRenderer(ModEntities.WIZARD_HAT, WizardHatRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_VEX, VexRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGIC_MINING_VEX, VexRenderer::new);
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
	public static void onRegisterColor(ColorHandlerEvent.Item event) {
		event.getItemColors().register((stack, tint) -> {
			return tint > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
		}, ModItems.WIZARD_HAT, ModItems.WIZARD_BOOTS, ModItems.WIZARD_CHESTPLATE, ModItems.WIZARD_LEGGINGS,
				ModItems.DRUID_HELMET, ModItems.DRUID_BOOTS, ModItems.DRUID_CHESTPLATE, ModItems.DRUID_LEGGINGS,
				ModItems.WARLOCK_BOOTS, ModItems.WARLOCK_CHESTPLATE, ModItems.WARLOCK_HELMET,
				ModItems.WARLOCK_LEGGINGS);
	}

	@SubscribeEvent
	public static void onRegisterParticleFactories(ParticleFactoryRegisterEvent event) {
		var mc = Minecraft.getInstance();
		mc.particleEngine.register(ModParticles.MAGIC_SMOKE_PARTICLE, sprite -> new SmokeParticle.Provider(sprite));
		mc.particleEngine.register(ModParticles.MAGIC_FLAME_PARTICLE, sprite -> new FlameParticle.Provider(sprite));
		mc.particleEngine.register(ModParticles.MAGIC_DUST_PARTICLE, sprite -> new DustParticle.Provider(sprite));
	}
}
