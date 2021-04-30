package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
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

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> reg = event.getRegistry();
		reg.register(Init.setup(
				new Block(Block.Properties.create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(2, 6)),
				"magic_bricks"));
	}
}
