package net.hugonardo.android.commons.sync;

import static net.hugonardo.android.commons.sync.SyncResultUtil.sum;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import android.content.SyncResult;
import android.content.SyncStats;
import net.hugonardo.android.commons.RobolectricTest;
import org.junit.*;

public class SyncResultUtilRobolectricTest extends RobolectricTest {

    private final SyncResult source = new SyncResult();

    private final SyncStats sourceStats = source.stats;

    private final SyncResult target = new SyncResult();

    private final SyncStats targetStats = target.stats;

    @Test
    public void sumAuthExceptions() throws Exception {
        sourceStats.numAuthExceptions = 5;
        sum(target, source);
        assertThat(targetStats.numAuthExceptions, is(5L));

        targetStats.numAuthExceptions = 4;
        sourceStats.numAuthExceptions = 2;
        sum(target, source);
        assertThat(targetStats.numAuthExceptions, is(6L));
    }

    @Test
    public void sumConflictDetectedExceptions() throws Exception {
        sourceStats.numConflictDetectedExceptions = 5;
        sum(target, source);
        assertThat(targetStats.numConflictDetectedExceptions, is(5L));

        targetStats.numConflictDetectedExceptions = 4;
        sourceStats.numConflictDetectedExceptions = 2;
        sum(target, source);
        assertThat(targetStats.numConflictDetectedExceptions, is(6L));
    }

    @Test
    public void sumDeletes() throws Exception {
        sourceStats.numDeletes = 5;
        sum(target, source);
        assertThat(targetStats.numDeletes, is(5L));

        targetStats.numDeletes = 4;
        sourceStats.numDeletes = 2;
        sum(target, source);
        assertThat(targetStats.numDeletes, is(6L));
    }

    @Test
    public void sumEntries() throws Exception {
        sourceStats.numEntries = 5;
        sum(target, source);
        assertThat(targetStats.numEntries, is(5L));

        targetStats.numEntries = 4;
        sourceStats.numEntries = 2;
        sum(target, source);
        assertThat(targetStats.numEntries, is(6L));
    }

    @Test
    public void sumInserts() throws Exception {
        sourceStats.numInserts = 5;
        sum(target, source);
        assertThat(targetStats.numInserts, is(5L));

        targetStats.numInserts = 4;
        sourceStats.numInserts = 2;
        sum(target, source);
        assertThat(targetStats.numInserts, is(6L));
    }

    @Test
    public void sumIoExceptions() throws Exception {
        sourceStats.numIoExceptions = 5;
        sum(target, source);
        assertThat(targetStats.numIoExceptions, is(5L));

        targetStats.numIoExceptions = 4;
        sourceStats.numIoExceptions = 2;
        sum(target, source);
        assertThat(targetStats.numIoExceptions, is(6L));
    }

    @Test
    public void sumParseExceptions() throws Exception {
        sourceStats.numParseExceptions = 5;
        sum(target, source);
        assertThat(targetStats.numParseExceptions, is(5L));

        targetStats.numParseExceptions = 4;
        sourceStats.numParseExceptions = 2;
        sum(target, source);
        assertThat(targetStats.numParseExceptions, is(6L));
    }

    @Test
    public void sumSkippedEntries() throws Exception {
        sourceStats.numSkippedEntries = 5;
        sum(target, source);
        assertThat(targetStats.numSkippedEntries, is(5L));

        targetStats.numSkippedEntries = 4;
        sourceStats.numSkippedEntries = 2;
        sum(target, source);
        assertThat(targetStats.numSkippedEntries, is(6L));
    }

    @Test
    public void sumUpdates() throws Exception {
        sourceStats.numUpdates = 5;
        sum(target, source);
        assertThat(targetStats.numUpdates, is(5L));

        targetStats.numUpdates = 4;
        sourceStats.numUpdates = 2;
        sum(target, source);
        assertThat(targetStats.numUpdates, is(6L));
    }
}