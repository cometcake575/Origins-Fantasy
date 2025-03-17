package com.starshootercity.originsfantasy;

import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.Ability;
import com.starshootercity.originsfantasy.abilities.*;
import com.starshootercity.util.Metrics;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OriginsFantasy extends OriginsAddon {
    @Override
    public @NotNull String getNamespace() {
        return "fantasyorigins";
    }

    private static OriginsFantasy instance;

    public static OriginsFantasy getInstance() {
        return instance;
    }

    @Override
    public @NotNull List<Ability> getAbilities() {
        List<Ability> abilities = new ArrayList<>(List.of(
                new AllayMaster(),
                new ArrowEffectBooster(),
                new BardicIntuition(),
                new BowBurst(),
                new BreathStorer(),
                new Chime(),
                new DoubleHealth(),
                new DragonFireball(),
                new Elegy(),
                new EndCrystalHealing(),
                new EndBoost(),
                new FortuneIncreaser(),
                new IncreasedArrowDamage(),
                new IncreasedArrowSpeed(),
                new HeavyBlow(),
                HeavyBlow.IncreasedCooldown.increasedCooldown,
                HeavyBlow.IncreasedDamage.increasedDamage,
                EndBoost.EndHealth.endHealth,
                EndBoost.EndStrength.endStrength,
                new IncreasedSpeed(),
                new InfiniteHaste(),
                new InfiniteNightVision(),
                new OceanWish(),
                OceanWish.LandWeakness.landWeakness,
                OceanWish.LandHealth.landHealth,
                OceanWish.LandSlowness.landSlowness,
                new MagicResistance(),
                new MoonStrength(),
                new NaturalArmor(),
                new NoteBlockPower(),
                new PerfectShot(),
                new PermanentHorse(),
                new PoorShot(),
                new StrongSkin(),
                new SuperJump(),
                new OceansGrace(),
                OceansGrace.WaterHealth.waterHealth,
                OceansGrace.WaterStrength.waterStrength,
                new VampiricTransformation(),
                new DaylightSensitive(),
                new WaterSensitive(),
                new Leeching(),
                new Stronger(),
                new UndeadAlly(),
                new SolidStance(),
                new StoneSkin(),
                new Stiff(),
                new FireResistant(),
                new PotionResistant()
        ));
        if (nmsInvoker.getGenericScaleAttribute() != null) {
            abilities.add(new LargeBody());
            abilities.add(new SmallBody());
        }
        return abilities;
    }

    private static FantasyNMSInvoker nmsInvoker;

    private static void initializeNMSInvoker() {
        nmsInvoker = switch (Bukkit.getMinecraftVersion()) {
            case "1.19" -> new FantasyNMSInvokerV1_19();
            case "1.19.1" -> new FantasyNMSInvokerV1_19_1();
            case "1.19.2" -> new FantasyNMSInvokerV1_19_2();
            case "1.19.3" -> new FantasyNMSInvokerV1_19_3();
            case "1.19.4" -> new FantasyNMSInvokerV1_19_4();
            case "1.20" -> new FantasyNMSInvokerV1_20();
            case "1.20.1" -> new FantasyNMSInvokerV1_20_1();
            case "1.20.2" -> new FantasyNMSInvokerV1_20_2();
            case "1.20.3" -> new FantasyNMSInvokerV1_20_3();
            case "1.20.4" -> new FantasyNMSInvokerV1_20_4();
            case "1.20.5", "1.20.6" -> new FantasyNMSInvokerV1_20_6();
            case "1.21" -> new FantasyNMSInvokerV1_21();
            case "1.21.1" -> new FantasyNMSInvokerV1_21_1();
            case "1.21.2", "1.21.3" -> new FantasyNMSInvokerV1_21_3();
            default -> new FantasyNMSInvokerV1_21_4();
        };
        Bukkit.getPluginManager().registerEvents(getNMSInvoker(), getInstance());
    }

    public static FantasyNMSInvoker getNMSInvoker() {
        return nmsInvoker;
    }

    @Override
    public void onRegister() {
        instance = this;
        initializeNMSInvoker();

        int pluginId = 25121;
        new Metrics(this, pluginId);
    }
}