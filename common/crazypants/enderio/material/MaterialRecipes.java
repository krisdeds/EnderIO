package crazypants.enderio.material;

import static crazypants.enderio.ModObject.itemBasicCapacitor;
import static crazypants.enderio.ModObject.itemIndustrialBinder;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import crazypants.enderio.Config;
import crazypants.enderio.ModObject;
import crazypants.enderio.machine.IMachineRecipe;
import crazypants.enderio.machine.MachineRecipeRegistry;
import crazypants.enderio.machine.alloy.BasicAlloyRecipe;
import crazypants.enderio.machine.alloy.VanillaSmeltingRecipe;

public class MaterialRecipes {

  public static void registerOresInDictionary() {
    OreDictionary.registerOre("dustIron", new ItemStack(ModObject.itemPowderIngot.actualId, 1, PowderIngot.POWDER_IRON.ordinal()));
    OreDictionary.registerOre("dustGold", new ItemStack(ModObject.itemPowderIngot.actualId, 1, PowderIngot.POWDER_GOLD.ordinal()));
    OreDictionary.registerOre("dustCopper", new ItemStack(ModObject.itemPowderIngot.actualId, 1, PowderIngot.POWDER_COPPER.ordinal()));
    OreDictionary.registerOre("dustTin", new ItemStack(ModObject.itemPowderIngot.actualId, 1, PowderIngot.POWDER_TIN.ordinal()));
  }

  public static void addRecipes() {

    ItemStack industialBinder;
    if(Config.useAlternateBinderRecipe) {
      industialBinder = new ItemStack(itemIndustrialBinder.actualId, 8, 0);
      GameRegistry.addShapedRecipe(industialBinder, "   ", "gg ", "gg ", 'g', Block.gravel);
      GameRegistry.addShapedRecipe(industialBinder, "   ", " gg", " gg", 'g', Block.gravel);
    } else {
      industialBinder = new ItemStack(itemIndustrialBinder.actualId, 4, 0);
      GameRegistry.addSmelting(Block.gravel.blockID, industialBinder, 0);
    }
    ItemStack redstoneInductor = new ItemStack(ModObject.itemMachinePart.actualId, 1, MachinePart.REDSTONE_INDUCTOR.ordinal());
    ItemStack basicGear = new ItemStack(ModObject.itemMachinePart.actualId, 1, MachinePart.BASIC_GEAR.ordinal());
    ItemStack IndustrialBinder = new ItemStack(ModObject.itemIndustrialBinder.actualId, 1, 0);
    ItemStack basicCapacitor = new ItemStack(itemBasicCapacitor.actualId, 1, 0);
    ItemStack wrench = new ItemStack(ModObject.itemYetaWrench.actualId, 1, 0);
    ItemStack blueSteel = new ItemStack(ModObject.itemAlloy.actualId, 1, Alloy.BLUE_STEEL.ordinal());
    ItemStack activatedCapacitor = new ItemStack(itemBasicCapacitor.actualId, 1, 1);
    ItemStack enderCapacitor = new ItemStack(itemBasicCapacitor.actualId, 1, 2);
    ItemStack fusedQuartzFrame = new ItemStack(ModObject.itemFusedQuartzFrame.actualId, 1, 0);
    ItemStack machineChassi = new ItemStack(ModObject.itemMachinePart.actualId, 1, MachinePart.MACHINE_CHASSI.ordinal());
    ItemStack mJReader = new ItemStack(ModObject.itemMJReader.actualId, 1, 0);

    // Basic Capacitor
    GameRegistry.addShapedRecipe(basicCapacitor, "   ", "rpr", "   ", 'r', redstoneInductor, 'p', Item.paper);

    // Activated Capacitor
    GameRegistry.addShapedRecipe(activatedCapacitor, "   ", "gwi", "RbR", 'b', blueSteel, 'g', new ItemStack(ModObject.itemAlloy.actualId, 1,
        Alloy.ACTIVATED_GOLD.ordinal()), 'i',
        new ItemStack(ModObject.itemAlloy.actualId, 1, Alloy.ACTIVATED_IRON.ordinal()), 'R', new ItemStack(Item.redstoneRepeater), 'w', Block.cloth);

    // Ender Capacitor
    GameRegistry.addShapedRecipe(enderCapacitor, "   ", "gwi", "RbR", 'b', blueSteel, 'g',
        new ItemStack(ModObject.itemAlloy.actualId, 1, Alloy.ENDER_GOLD.ordinal()), 'i',
        new ItemStack(ModObject.itemAlloy.actualId, 1, Alloy.ENDER_IRON.ordinal()), 'R', new ItemStack(Item.redstoneRepeater), 'w', Block.cloth);

    int meta = 0;
    for (Alloy alloy : Alloy.values()) {
      ItemStack ingot = new ItemStack(ModObject.itemAlloy.actualId, 1, meta);
      IMachineRecipe recipe = new BasicAlloyRecipe(ingot, alloy.unlocalisedName, alloy.ingrediants);
      if(ItemAlloy.useNuggets) {
        ItemStack nugget = new ItemStack(ModObject.itemAlloy.actualId, 9, meta + Alloy.values().length);
        GameRegistry.addShapelessRecipe(nugget, ingot);
        nugget = nugget.copy();
        nugget.stackSize = 1;
        GameRegistry.addShapedRecipe(ingot, "nnn", "nnn", "nnn", 'n', nugget);
      }
      MachineRecipeRegistry.instance.registerRecipe(ModObject.blockAlloySmelter.unlocalisedName, recipe);
      meta++;
    }

    MachineRecipeRegistry.instance.registerRecipe(ModObject.blockAlloySmelter.unlocalisedName, new FusedQuartzRecipe());
    MachineRecipeRegistry.instance.registerRecipe(ModObject.blockAlloySmelter.unlocalisedName, new VanillaSmeltingRecipe());

    FurnaceRecipes.smelting().addSmelting(ModObject.itemPowderIngot.actualId, PowderIngot.POWDER_IRON.ordinal(), new ItemStack(Item.ingotIron), 0);
    FurnaceRecipes.smelting().addSmelting(ModObject.itemPowderIngot.actualId, PowderIngot.POWDER_GOLD.ordinal(), new ItemStack(Item.ingotGold), 0);

    // Industrial Binder - will be removed!
    GameRegistry.addShapedRecipe(fusedQuartzFrame, "bsb", "s s", "bsb", 'b', industialBinder, 's', new ItemStack(Item.stick));

    // Wrench
    GameRegistry.addShapedRecipe(wrench, "i i", " b ", " i ", 'b', industialBinder, 'i', new ItemStack(Item.ingotIron));

    // Machine Chassi
    GameRegistry.addShapedRecipe(machineChassi, "fif", "i i", "fif", 'f', Block.fenceIron, 'i', Item.ingotIron);

    // Redstone Inductor
    GameRegistry.addShapedRecipe(redstoneInductor, "grg", "gig", "grg", 'r', Item.redstone, 'g', Item.goldNugget, 'i', Item.ingotIron);

    // Basic Gear
    GameRegistry.addShapedRecipe(basicGear, "scs", "c c", "scs", 's', Item.stick, 'c', Block.cobblestone);

    // MJ Reader
    //GameRegistry.addShapedRecipe(mJReader, "r r", "rir", "gxg", 'r', Item.redstone, 'i', Item.ingotIron, 'g', basicGear, 'x', redstoneInductor);
    // TO-DO: Sort this class!    
  }

  public static void addOreDictionaryRecipes() {
    int oreId = OreDictionary.getOreID("ingotCopper");
    ArrayList<ItemStack> ingots = OreDictionary.getOres(oreId);
    if(!ingots.isEmpty()) {
      FurnaceRecipes.smelting().addSmelting(ModObject.itemPowderIngot.actualId, PowderIngot.POWDER_COPPER.ordinal(), ingots.get(0), 0);
    }
    oreId = OreDictionary.getOreID("ingotTin");
    ingots = OreDictionary.getOres(oreId);
    if(!ingots.isEmpty()) {
      FurnaceRecipes.smelting().addSmelting(ModObject.itemPowderIngot.actualId, PowderIngot.POWDER_TIN.ordinal(), ingots.get(0), 0);
    }
  }

}
