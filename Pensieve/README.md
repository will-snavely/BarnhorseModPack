# Pensieve
Remember your cherished Spire memories.

## About
Pensieve is a mod for Slay the Spire that seeks
to provide a more complete run history—as complete
as possible. For example:
- Recording what happened on each individual turn.
- Recording when relics were triggered.
- Tracking information about individual cards.

## Installing

Pensieve is currently only available on Github.
You should head to the 
[Releases](https://github.com/will-snavely/BarnhorseModPack/releases)
page and download the latest version of `Pensive.jar`.
This should then be placed in the `mods` folder of
your Slay the Spire installation directory (if you 
are using Steam, you can find this by right-clicking
the game, and navigating to "Manage", then selecting
"Browse Local Files").

## Enabling

To use the mod, you must launch Slay the Spire with mods enabled, 
then ensure that "Pensieve" is selected in the mod configuation
dialog.

## Run Data
The mod will record data for each run it observes under
the `etc/barnhorse/pensieve/runs` folder within your
Slay the Spire `mods` folder. The currently active 
run should appear directly in the `runs` folder, 
and previous runs can be found in the `archives`
directory. Run files are named: `<seed>_<class>.run`,
for example `3Q3XW9132VY83_IRONCLAD.run`. Archived
runs have a slightly different naming scheme:
`<seed>_<class>_<timestamp>_archive.run`, where
`<timestamp>` reflects when the run was archived.

## Viewing the Run Data
There is currently no integration with the Slay the Spire client,
though this might be done in the future. Therefore, to view the 
run data, you currently must consider the following options:

1. The `Pensieve.jar` file can be executed (e.g., double-clicked
in Windows) to launch a simple event viewer. From here, you can
load a run file (either current or archived) and browse the events
that have been recorded. This jar file should be executed from
inside the `mods` directory, so that dependencies can be
correctly resolved.

2. You can write a Java program to interrogate a run database
directly. See the 
[Event Viewer](./src/main/java/org/barnhorse/sts/viewer/View.java)
for a relatively simple example.

3. Configure the mod to produce raw JSON output, and inspect the
run files directly. See the "Configuration" details below. You
can also export JSON from the Event Viewer tool. Note that JSON-formatted
run files cannot be loaded back into the viewer tool. Here is a
[sample](./sample/sample.lines) of what the raw JSON output
looks like.

## Mod Configuration
Some minor configuration can be supplied by creating a JSON
file at `mods/etc/barnhorse/pensieve/config.json`.

```
{
    "eventLogDirectory": "path/to/custom/log/directory",
    "archiveDirectory": "/path/to/custom/archive/directory",
    "storageEngine": "FILE" or "NITRITE"; default is NITRITE"
}
```

The only moderately interesting thing you can do here,
currently, is specify `"FILE"` for `storageEngine`. 
This will format the run database in the 
[JSON Lines](https://jsonlines.org/)
format, which makes direct review possible. Note that the
Event Viewer will not work with run files in this format,
however.

## Events

The current set of events recorded by the mod can be discovered
by exploring the [events](./src/main/java/org/barnhorse/sts/lib/events)
folder. When the mod is more mature, and the event set is more
settled, you'll find more documentation here for individual 
events.

## Building

The mod can be built locally using Maven. There are some
steps you must following first, though, for this to work.

### Install Slay The Spire  on Steam 
The only supported build configuration requires you to 
have Slay the Spire installed through Steam. This should
work on both Windows and Linux, though this has not been
heavily tested.

### Install ModTheSpire and BaseMod through Steam Workshop
You should install Mod the Spire and BaseMod through
Steam Workshop. This is done by "subscribing" to the
respective pages of these tools:

- https://steamcommunity.com/workshop/filedetails/?id=1605060445
- https://steamcommunity.com/workshop/filedetails/?id=1605833019

### Setting up a `settings.xml` file for Maven
This tells Maven where to find all of the local Slay the Spire
Jar files. My `settings.xml` file [looks like this](./sample/settings_sample.xml).
This file should be placed in the appropriate location for
your system. Typically, this is inside a folder named `.m2`
in your home directory (`/home/username/.m2` on Linux, 
`C:\Users\UserName\.m2` on Windows). Read this link for
[more information on settings.xml](https://maven.apache.org/ref/3.6.3/maven-settings/settings.html).

You will likely need to update the sample XML file to suit your
local system. The critical thing is that the following variables
are set:
- `sts-home`: Should be set to the location of
your Slay the Spire installation (typically
a folder named `SlayTheSpire` somewhere in
 the local `Steam` folder.)
- `sts-jar`: Should be set to the path of your Slay the Spire 
jar distribution, a.k.a. `desktop-1.0.jar`. This should
be in `sts-home`.
- `basemod-jar`: Should be set to the path of your local 
BaseMod jar file (`BaseMod.jar`). This should be somewhere
within `Steam\steamapps\workshop\content`.
- `mts-jar`: Should be set to the path of your local 
Mod the Spire jar file (`ModTheSpire.jar`). This 
also should be somewhere under 
`Steam\steamapps\workshop\content`.

### Build the Mod
After downloading the source, the mod can be built using:
- `mvn clean install`

This should be run from the root of the repository.
Assuming your `settings.xml` file is configured properly,
this should compile the source and copy the mod jar file
to the appropriate folder in your Spire installation.

If you just wish to build the source, without
installing, you can run:
- `mvn clean package`
