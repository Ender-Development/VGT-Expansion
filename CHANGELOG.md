# Changelog

## v0.3.3
### Changes
- added a few more config options for the large Turbines
    - Should the rotor should take damage?
    - Should the player be hurt when a rotor breaks?
    - How much damage should the player take?

### Internal
- redid the mixin registration
- added compatibility with the newest version of GTClassic

## v0.3.2
### Bugfixes
- fixed Chemical Reactor recipes with item and fluid outputs only outputting the fluid

## v0.3.1
Sorry that this update took so long. I'm involved in quite a few projects and didn't have the time to work on this mod. The next thing on my todo list is implementing GroovyScript support, which may be a bit difficult as the mod uses the IC2 way of registering recipes. So whenever IC2/VGT gets proper GroovyScript support, it _should_ automatically work for this mod as well.

### Changes
- updated to the newest version of IC2C for 1.12.2 to fix the memory leak
- updated dependencies
- added config options to disable the registration of the default recipes
- the Distillation Tower now uses Reinforced Cases instead of Advanced Casings

### Bugfixes
- fixed steam furnace (thanks to @Tesseract4D)
- fixed the fusion reactor not accepting item recipes

## v0.3.0
### Changes
- first release under Ender Development
- switched to RetroFuturaGradle
- updated dependencies
- switched from GregTech Classic to Vintage GregTech
- made sure compatibility is given with all IC2C related mods
- updated to the newest version of IC2C for 1.12.2
- polished the README.md
- updated `mcmod.info`