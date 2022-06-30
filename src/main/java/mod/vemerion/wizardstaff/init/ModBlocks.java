package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.block.MagicLightBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModBlocks {

	public static final Block MAGIC_BRICKS = null;
	public static final Block MAGIC_LIGHT = null;

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> reg = event.getRegistry();
		reg.register(Init.setup(new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_RED).strength(2, 6)),
				"magic_bricks"));
		reg.register(Init.setup(new MagicLightBlock(BlockBehaviour.Properties.of(Material.AIR).strength(-1, 3600000)
				.noDrops().noOcclusion().lightLevel(s -> {
					return 15;
				})), "magic_light"));
	}
}
