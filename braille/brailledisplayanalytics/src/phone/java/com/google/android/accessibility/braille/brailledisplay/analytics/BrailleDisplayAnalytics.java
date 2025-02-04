package com.google.android.accessibility.braille.brailledisplay.analytics;

import android.content.Context;
import com.google.android.accessibility.braille.common.translate.BrailleLanguages.Code;

/** Stub braille display analytics. */
public class BrailleDisplayAnalytics {
  private static BrailleDisplayAnalytics instance;

  public static BrailleDisplayAnalytics getInstance(Context context) {
    if (instance == null) {
      instance = new BrailleDisplayAnalytics(context.getApplicationContext());
    }
    return instance;
  }

  private BrailleDisplayAnalytics(Context context) {}

  public void logStartedEvent(String device, Code inputCode, Code outputCode) {}

  public void logTypingBrailleCharacter(int count) {}

  public void logReadingBrailleCharacter(int count) {}

  public void logBrailleInputCodeSetting(Code code) {}

  public void logBrailleOutputCodeSetting(Code code) {}

  public void logWordWrappingSetting(boolean enabled) {}
}
