package net.logcat.automation.tests;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class SampleTest {

    private static final String PACKAGE_NAME = "com.android.dialer";
    private static final long LAUNCH_TIMEOUT = 2000;

    private UiDevice d;

    @Before
    public void startMainActivityFromHomeScreen() {
        d = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // XXX device.pressHome will delay next activity start for at least 5 seconds
        // https://code.google.com/p/android/issues/detail?id=4536
        // do not use pressHome ever

        launchApp();
        d.wait(Until.hasObject(By.pkg(PACKAGE_NAME)), LAUNCH_TIMEOUT);
    }

    private void launchApp() {
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);
    }

    @Test
    public void checkPreconditions() {
        assertThat(d, notNullValue());
        assertTrue("Launched dialer app", d.hasObject(By.pkg(PACKAGE_NAME)));
    }

}
