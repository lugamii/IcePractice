package de.kontux.icepractice.util;

import de.kontux.icepractice.IcePracticePlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtil {

    public static void run(Runnable runnable) {
        IcePracticePlugin.getInstance().getServer().getScheduler().runTask(IcePracticePlugin.getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        try {
            IcePracticePlugin.getInstance().getServer().getScheduler().runTaskAsynchronously(IcePracticePlugin.getInstance(), runnable);
        } catch (IllegalStateException e) {
            IcePracticePlugin.getInstance().getServer().getScheduler().runTask(IcePracticePlugin.getInstance(), runnable);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimer(Runnable runnable, long delay, long timer) {
        IcePracticePlugin.getInstance().getServer().getScheduler().runTaskTimer(IcePracticePlugin.getInstance(), runnable, delay, timer);
    }

    public static int runTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(IcePracticePlugin.getInstance(), delay, timer);
        return runnable.getTaskId();
    }

    public static void runLater(Runnable runnable, long delay) {
        IcePracticePlugin.getInstance().getServer().getScheduler().runTaskLater(IcePracticePlugin.getInstance(), runnable, delay);
    }

    public static void runLaterAsync(Runnable runnable, long delay) {
        try {
            IcePracticePlugin.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(IcePracticePlugin.getInstance(), runnable, delay);
        } catch (IllegalStateException e) {
            IcePracticePlugin.getInstance().getServer().getScheduler().runTaskLater(IcePracticePlugin.getInstance(), runnable, delay);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimerAsync(Runnable runnable, long delay, long timer) {
        try {
            IcePracticePlugin.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(IcePracticePlugin.getInstance(), runnable, delay, timer);
        } catch (IllegalStateException e) {
            IcePracticePlugin.getInstance().getServer().getScheduler().runTaskTimer(IcePracticePlugin.getInstance(), runnable, delay, timer);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimerAsync(BukkitRunnable runnable, long delay, long timer) {
        IcePracticePlugin.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(IcePracticePlugin.getInstance(), runnable, delay, timer);
    }

}