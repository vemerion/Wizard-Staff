package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModBlocks {
	
	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry()
				.register(Init.setup(new Block(
						Block.Properties.create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(2.0F, 6.0F)),
						"magic_bricks_block"));
	}

	@ObjectHolder(Main.MODID + ":magic_bricks_block")
	public static final Block MAGIC_BRICKS_BLOCK = null;
}
