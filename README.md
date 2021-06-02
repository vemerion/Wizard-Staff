# Wizard Staff

This is the source code to my Minecraft mod **Wizard Staff**, a mod that lets the player be
a wizard, wielding a magic staff and fancy wizard hat!

## Data-driven Magic
As of version 1.3 (the suggestions update), the spells (also called magics) for the wizard staff
are data-driven, and can be modified/removed/created via json. This section describes how the
system can be used.

### Json format
The json format for the magics is fairly simple, and must contain the following members:

#### "cost"
The exp cost of the magic, which must have the value of a non-negative number. This can mean
different things for different magics. For 'continuous' spells, such as the blaze powder spell,
this is the exp cost drained every tick the spell is used (20 ticks = 1 second). For 'immediate'
spells, such as the carved pumpkin spell, it is the total cost of using the magic once.

#### "duration"
The duration (or cast time) of the spell, in ticks. A negative value can be used to indicate
that the duration of the magic is unlimited.

#### "magic"
This member determines what the actual spell is. This member expects a string, which must
be a valid magic name. For example, "blaze\_powder\_magic" is the flame-thrower spell.
[This class](src/main/java/mod/vemerion/wizardstaff/init/ModMagics.java)
contains all the different magic names. "no\_magic" is used to specify no spell at all.

#### "ingredient"
This member is used to specify which items to associate with the spell. It is the most
complex member, and expects either an array of objects, or a single object.
These objects should contain one member each, either a "tag" or an "item".

For instance, the below example would be used to specify the tag 'cobblestone' and the item
'blaze powder':

```
"ingredient": [
  {
    "tag": "forge:cobblestone"
  },
  {
    "item": "minecraft:blaze_powder"
  }
]
```

### Additional Json Members
As of version 1.5 (the restructuring update), the magic system was expanded to give some magics
additional json members to make them more configurable. For instance, for the lodestone teleportation
magic ([link](src/generated/resources/data/wizard-staff/wizard-staff-magics/lodestone_magic.json)) you can
now specify the block to bind the teleportation to, via the 'waypoint' json member.

You might see a member called 'block\_match' in some json files (for example in the
[chop tree magic](src/generated/resources/data/wizard-staff/wizard-staff-magics/chop_tree_magic.json)).
This member works similar to how ingredient works, but for blocks (however it is not as complex and
versatile as ingredient). It is an object that expects one member, which must be either 'block' or
'tag'.

### Modifying/removing/adding magics
To modify or remove spells, the process is similar to how data-packs can be used to
remove/modify vanilla recipes. If you are unsure how to create data-packs, I recommend
consulting the Minecraft wiki.

To get started, create a new folder named 'wizard-staff' in the data folder in
the resource pack. In the 'wizard-staff' folder, create a folder called 'wizard-staff-magics', which is
where you will add your json files. If you want to change the cost of the flame-thrower
spell, you would create a new json file called 'blaze\_powder\_magic.json', and set
the members as desired. If you instead want to completely remove the spell, set the magic
member to "no\_magic". [This folder](src/generated/resources/data/wizard-staff/wizard-staff-magics)
contains the json files for all the default spells.

If you instead want to add a new magic to an item, you would create a new json file with
whatever name you want, and specify the members as desired.

Important Note: An item can only be associated with one magic, so if you have overlapping
ingredients for different json files, only one will be applied. Avoid this.

### Examples
This section contains a few examples to illustrate how you could add your own magics via
a data-pack.


#### Add flying magic to pink stained glass pane:
'data/DATAPACK NAME/wizard-staff-magics/flying\_pink\_stained\_glass\_pane.json'

```
{
  "cost": 2.3, // The exp cost drained from the player every tick
  "duration": -1, // Negative nbr for infinite duration
  "magic": "elytra_magic", // The flying spell
  "ingredient": [ // The item(s) associated with the spell
    {
      "item": "minecraft:pink_stained_glass_pane"
    }
  ]
}                   
```

#### Create your own potion spell:
'data/DATAPACK NAME/wizard-staff-magics/my\_own\_potion\_spell.json'

```
{
  "cost": 25, // The exp cost drained from the player when the spell is completed
  "duration": 20, // Cast time is 20 ticks (1 second)
  "magic": "potion_magic", // The flying spell
  "ingredient": { // The item associated with the spell
      "item": "minecraft:gray_stained_glass_pane"
  },
  "level": 5, // The level of the potion effect
  "potion_time": 300, // The duration of the potion (in ticks)
  "radius": 0, // The affected radius around the player
  "potion": "minecraft:poison", // The potion effect
  "affect_caster": true, // Does the effect also apply to the caster?
  "sound": "minecraft:ambient.cave" // The sound played when using the spell
}                   
```


### Detailed Explanation
If you still do not understand how to use the data-driven magic system, then this section
will try to step by step go through everything:

* Locate your Minecraft world folder. If you are using the vanilla launcher, you can usually find you world folder on this path: 'C:\Users\YOUR WINDOWS PROFILE\AppData\Roaming\\.minecraft\saves\YOUR WORLD NAME'.


* In your world folder, there should be a folder called 'datapacks'. Step into that folder.


* Now, in the 'datapacks' folder, create a new folder that will contain the datapack, as explained [on the Minecraft wiki](https://minecraft.gamepedia.com/Data_Pack). You will also need to add a pack.mcmeta file.


* Then, create a new folder called 'data'. In that folder, create a folder named after your data pack (or, name the folder 'wizard-staff' if you want to override the default spells), and in that folder, create a folder called 'wizard-staff-magics'. You path should now be something like: 'datapacks\DATAPACK NAME\data\DATAPACK NAME\wizard-staff-magics\'.


* The 'wizard-staff-magics' folder is where you will put the json files. If you want to change the parameters of an already existing spell, you best bet is to copy one of the default spells (found [here](src/generated/resources/data/wizard-staff/wizard-staff-magics)), and then change the json members as you like.


* That's it! You should now be able to start Minecraft and test out the spells you added/modified.
