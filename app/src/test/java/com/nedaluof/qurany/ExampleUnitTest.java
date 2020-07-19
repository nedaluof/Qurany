package com.nedaluof.qurany;

import com.nedaluof.qurany.util.Utility;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created By nedaluof  2020.
 */

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void device_language(){
        assertSame(Utility.getLanguage(),"_english");
    }
}