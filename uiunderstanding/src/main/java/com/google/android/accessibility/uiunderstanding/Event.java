/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.android.accessibility.uiunderstanding;

import static com.google.android.accessibility.utils.StringBuilderUtils.optionalInt;
import static com.google.android.accessibility.utils.StringBuilderUtils.optionalSubObj;

import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.accessibility.utils.StringBuilderUtils;
import java.util.Objects;

/** A cleaned-up / interpreted accessibility-event, including a node-tree-snapshot. */
public abstract class Event {
  //////////////////////////////////////////////////////////////////////////////////
  // Inner classes

  /** An event-ID used as a key for tracking metrics. */
  public static class Id {
    @NonNull public final String className;
    public final long uptimeMillisec;

    public Id(@NonNull String className, long uptimeMillisec) {
      this.className = className;
      this.uptimeMillisec = uptimeMillisec;
    }

    @Override
    public boolean equals(Object otherObj) {
      if (otherObj instanceof Id) {
        Id other = (Id) otherObj;
        return TextUtils.equals(this.className, other.className)
            && (this.uptimeMillisec == other.uptimeMillisec);
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return Objects.hash(className, uptimeMillisec);
    }

    @Override
    public String toString() {
      return className + ":" + uptimeMillisec;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  // Constants

  //////////////////////////////////////////////////////////////////////////////////
  // Interfaces

  //////////////////////////////////////////////////////////////////////////////////
  // Member data

  // Event time that should be consistent with AccessibilityEvent.getEventTime(), set by
  // AccessibilityManager.sendAccessibilityEvent() using uptime-milliseconds.
  private final long uptimeMillisec;

  @NonNull private final Snapshot snapshot; // TODO: Add sub-classes that use snapshot.
  @Nullable private final SnapshotView source;

  //////////////////////////////////////////////////////////////////////////////////
  // Construction

  public Event(long uptimeMillisec, @NonNull Snapshot snapshot, @Nullable SnapshotView source) {
    this.uptimeMillisec = uptimeMillisec;
    this.snapshot = snapshot;
    this.source = source;
  }

  //////////////////////////////////////////////////////////////////////////////////
  // Methods

  @NonNull
  public Id getId() {
    return new Id(this.getClass().getSimpleName(), SystemClock.uptimeMillis());
  }

  @NonNull
  protected String baseToString() {
    return StringBuilderUtils.joinFields(
        optionalInt("uptime", uptimeMillisec, 0), optionalSubObj("source", source));
  }

  //////////////////////////////////////////////////////////////////////////////////
  // Sub-classes

  /**
   * An event not directly caused by an AccessibilityEvent, but rather generated by
   * event-interpreters or triggered by an AccessibilityService.
   */
  static class Synthetic extends Event {
    Synthetic(@NonNull Snapshot snapshot) {
      super(SystemClock.uptimeMillis(), snapshot, /* source= */ null);
    }

    @Override
    public String toString() {
      return baseToString();
    }
  }

  /** An event indicating that a snapshot has been completed. */
  public static class SnapshotDone extends Synthetic {
    SnapshotDone(@NonNull Snapshot snapshot) {
      super(snapshot);
    }
  }
}