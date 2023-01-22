package mod.vemerion.wizardstaff.init;

import mod.vemerion.wizardstaff.Main;
import mod.vemerion.wizardstaff.block.MagicLightBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

	public static final RegistryObject<Block> MAGIC_BRICKS = BLOCKS.register("magic_bricks",
			() -> new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_RED).strength(2, 6)));
	public static final RegistryObject<Block> MAGIC_LIGHT = BLOCKS.register("magic_light", () -> new MagicLightBlock(
			BlockBehaviour.Properties.of(Material.AIR).strength(-1, 3600000).noLootTable().noOcclusion().lightLevel(s -> {
				return 15;
			})));
}
