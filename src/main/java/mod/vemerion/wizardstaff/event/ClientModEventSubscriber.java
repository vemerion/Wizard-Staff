package mod.vemerion.wizardstaff.event;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.init.ModContainers;
import mod.vemerion.wizardstaff.init.ModEntities;
import mod.vemerion.wizardstaff.init.ModItems;
import mod.vemerion.wizardstaff.init.ModParticles;
import mod.vemerion.wizardstaff.renderer.GrapplingHookRenderer;
import mod.vemerion.wizardstaff.renderer.MagicSoulSandArmRenderer;
import mod.vemerion.wizardstaff.renderer.MagicWitherSkullRenderer;
import mod.vemerion.wizardstaff.renderer.NetherPortalRenderer;
import mod.vemerion.wizardstaff.renderer.WizardHatRenderer;
import mod.vemerion.wizardstaff.staff.WizardStaffScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.RedstoneParticle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
	@SubscribeEvent
	public static void onRegister(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ModContainers.WIZARD_STAFF_CONTAINER, WizardStaffScreen::new);

		registerEntityRenderer();
	}

	private static void registerEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.PUMPKIN_MAGIC_ENTITY, NoRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.NETHER_PORTAL_ENTITY, NetherPortalRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MAGIC_WITHER_SKULL_ENTITY,
				MagicWitherSkullRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MAGIC_SOUL_SAND_ARM_ENTITY,
				MagicSoulSandArmRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.GRAPPLING_HOOK_ENTITY, GrapplingHookRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MUSHROOM_CLOUD_ENTITY, NoRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.WIZARD_HAT_ENTITY, WizardHatRenderer::new);
	}

	private static class NoRenderer<T extends Entity> extends EntityRenderer<T> {

		protected NoRenderer(EntityRendererManager renderManager) {
			super(renderManager);
		}

		@Override
		public ResourceLocation getEntityTexture(T entity) {
			return null;
		}

	}

	@SubscribeEvent
	public static void onRegisterColor(ColorHandlerEvent.Item event) {
		event.getItemColors().register((stack, tint) -> {
			return tint > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack);
		}, ModItems.WIZARD_HAT_ITEM, ModItems.WIZARD_BOOTS_ITEM, ModItems.WIZARD_CHESTPLATE_ITEM,
				ModItems.WIZARD_LEGGINGS_ITEM, ModItems.DRUID_HELMET_ITEM, ModItems.DRUID_BOOTS_ITEM,
				ModItems.DRUID_CHESTPLATE_ITEM, ModItems.DRUID_LEGGINGS_ITEM, ModItems.WARLOCK_BOOTS_ITEM,
				ModItems.WARLOCK_CHESTPLATE_ITEM, ModItems.WARLOCK_HELMET_ITEM, ModItems.WARLOCK_LEGGINGS_ITEM);
	}

	@SubscribeEvent
	public static void onRegisterParticleFactories(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(ModParticles.MAGIC_SMOKE_PARTICLE_TYPE,
				sprite -> new SmokeParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticles.MAGIC_FLAME_PARTICLE_TYPE,
				sprite -> new FlameParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticles.MAGIC_DUST_PARTICLE_TYPE,
				sprite -> new RedstoneParticle.Factory(sprite));

	}
}