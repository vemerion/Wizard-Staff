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
import mod.vemerion.wizardstaff.screen.MagicScreen;
import mod.vemerion.wizardstaff.staff.WizardStaffScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.RedstoneParticle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.VexRenderer;
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
		ScreenManager.registerFactory(ModContainers.WIZARD_STAFF, WizardStaffScreen::new);
		ScreenManager.registerFactory(ModContainers.MAGIC, MagicScreen::new);

		registerEntityRenderer();
	}

	private static void registerEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.PUMPKIN_MAGIC, NoRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.NETHER_PORTAL, NetherPortalRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MAGIC_WITHER_SKULL,
				MagicWitherSkullRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MAGIC_SOUL_SAND_ARM,
				MagicSoulSandArmRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.GRAPPLING_HOOK, GrapplingHookRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MUSHROOM_CLOUD, NoRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.WIZARD_HAT, WizardHatRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MAGIC_VEX, VexRenderer::new);
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
		}, ModItems.WIZARD_HAT, ModItems.WIZARD_BOOTS, ModItems.WIZARD_CHESTPLATE,
				ModItems.WIZARD_LEGGINGS, ModItems.DRUID_HELMET, ModItems.DRUID_BOOTS,
				ModItems.DRUID_CHESTPLATE, ModItems.DRUID_LEGGINGS, ModItems.WARLOCK_BOOTS,
				ModItems.WARLOCK_CHESTPLATE, ModItems.WARLOCK_HELMET, ModItems.WARLOCK_LEGGINGS);
	}

	@SubscribeEvent
	public static void onRegisterParticleFactories(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(ModParticles.MAGIC_SMOKE_PARTICLE,
				sprite -> new SmokeParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticles.MAGIC_FLAME_PARTICLE,
				sprite -> new FlameParticle.Factory(sprite));
		Minecraft.getInstance().particles.registerFactory(ModParticles.MAGIC_DUST_PARTICLE,
				sprite -> new RedstoneParticle.Factory(sprite));

	}
}
