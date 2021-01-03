package com.nedaluof.qurany.util.timber

import timber.log.Timber

/**
 * Created by nedaluof on 1/1/2021.
 */
class ReleaseTree : Timber.Tree() {
  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    /** Sending crash report to Firebase CrashAnalytics
     * FirebaseCrash.report(message);
     * FirebaseCrash.report(new Exception(message));
     * */
  }
}
