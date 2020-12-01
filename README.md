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
[This class](src/main/java/mod/vemerion/wizardstaff/Magic/Magics.java)
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

### Modifying/removing/adding magics
To modify or remove spells, the process is similar to how data-packs can be used to
remove/modify vanilla recipes. If you are unsure how to create data-packs, I recommend
consulting the Minecraft wiki.

To get started, create a new folder named 'wizard-staff' in the data folder in
the resource pack. In the 'wizard-staff' folder, create a folder called 'wizard-staff-magics', which is
where you will add your json files. If you want to change the cost of the flame-thrower
spell, you would create a new json file called 'blaze\_powder\_magic.json', and set
the members as desired. If you instead want to completely remove the spell, set the magic
member to "no\_magic". [This folder](src/main/resources/data/wizard-staff/wizard-staff-magics)
contains the json files for all the default spells.

If you instead want to add a new magic to an item, you would create a new json file with
whatever name you want, and specify the members as desired.

Important Note: An item can only be associated with one magic, so if you have overlapping
ingredients for different json files, only one will be applied. Avoid this.

### Example
This section contains an example to illustrate how you could add the flying magic to the
dirt item.

'data/wizard-staff/wizard-staff-magics/flying\_dirt.json'

```
{
  "cost": 2.3, // The exp cost drained from the player every tick
  "duration": -1, // Negative nbr for infinite duration
  "magic": "elytra_magic", // The flying spell
  "ingredient": [ // The item(s) associated with the spell
    {
      "item": "minecraft:dirt"
    }
  ]
}                   
```

