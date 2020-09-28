package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RunStart extends GameEvent {
    public final static String key = "run_start";

    public boolean isDev;
    public boolean isBeta;
    public boolean isAlpha;
    public boolean isModded;
    public boolean isDailyRun;
    public boolean hasDoneDailyToday;
    public boolean isFinalActAvailable;
    public boolean isEndless;
    public boolean isTrial;
    public boolean isAscensionMode;

    public Long specialSeed;
    public Long seed;
    public long dailyDate;
    public int ascensionLevel;

    public String trialName;

    public RunStart() {
        super(key, "Started a new run.");
    }

    public static RunStart fromGameSettings() {
        RunStart event = new RunStart();
        event.seed = Settings.seed;
        event.isDev = Settings.isDev;
        event.isBeta = Settings.isBeta;
        event.isAlpha = Settings.isAlpha;
        event.isModded = Settings.isModded;
        event.isDailyRun = Settings.isDailyRun;
        event.hasDoneDailyToday = Settings.hasDoneDailyToday;
        event.dailyDate = Settings.dailyDate;
        event.isFinalActAvailable = Settings.isFinalActAvailable;
        event.isEndless = Settings.isEndless;
        event.isTrial = Settings.isTrial;
        event.specialSeed = Settings.specialSeed;
        event.trialName = Settings.trialName;
        event.isAscensionMode = AbstractDungeon.isAscensionMode;
        event.ascensionLevel = AbstractDungeon.ascensionLevel;
        return event;
    }
}
