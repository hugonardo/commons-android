package net.hugonardo.android.commons.sync;

import android.content.SyncResult;
import android.content.SyncStats;
import android.support.annotation.NonNull;

public final class SyncResultUtil {

    /**
     * Soma os dados de {@code source} em {@code target}.
     * <p>
     * Os dados de {@code source} não serão alterados. {@code target} terá seus valores incrementados em seus
     * correspondentes de source.
     *
     * @param target para onde os dados serão somados
     * @param source de onde os dados serão obtidos
     * @return o target
     */
    public static SyncResult sum(@NonNull SyncResult target, @NonNull SyncResult source) {
        SyncStats targetStats = target.stats;
        SyncStats sourceStats = source.stats;
        sumParseExceptions(targetStats, sourceStats);
        sumAuthExceptions(targetStats, sourceStats);
        sumConflictDetectedExceptions(targetStats, sourceStats);
        sumIoExceptions(targetStats, sourceStats);
        sumSkippedEntries(targetStats, sourceStats);
        sumEntries(targetStats, sourceStats);
        sumInserts(targetStats, sourceStats);
        sumUpdates(targetStats, sourceStats);
        sumDeletes(targetStats, sourceStats);
        return target;
    }

    private static void sumAuthExceptions(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numAuthExceptions += sourceStats.numAuthExceptions;
    }

    private static void sumConflictDetectedExceptions(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numConflictDetectedExceptions += sourceStats.numConflictDetectedExceptions;
    }

    private static void sumDeletes(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numDeletes += sourceStats.numDeletes;
    }

    private static void sumEntries(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numEntries += sourceStats.numEntries;
    }

    private static void sumInserts(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numInserts += sourceStats.numInserts;
    }

    private static void sumIoExceptions(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numIoExceptions += sourceStats.numIoExceptions;
    }

    private static void sumParseExceptions(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numParseExceptions += sourceStats.numParseExceptions;
    }

    private static void sumSkippedEntries(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numSkippedEntries += sourceStats.numSkippedEntries;
    }

    private static void sumUpdates(SyncStats targetStats, SyncStats sourceStats) {
        targetStats.numUpdates += sourceStats.numUpdates;
    }
}
