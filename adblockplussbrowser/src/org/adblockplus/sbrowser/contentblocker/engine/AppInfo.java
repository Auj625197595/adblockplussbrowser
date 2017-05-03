/*
 * This file is part of Adblock Plus <https://adblockplus.org/>,
 * Copyright (C) 2006-2016 Eyeo GmbH
 *
 * Adblock Plus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 *
 * Adblock Plus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Adblock Plus.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adblockplus.sbrowser.contentblocker.engine;

import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

@SuppressLint("DefaultLocale")
public class AppInfo
{
  public final String addonName;
  public final String addonVersion;
  public final String application;
  public final String applicationVersion;
  public final String platform;
  public final String platformVersion;
  public final String locale;

  private AppInfo(
      final String addonName,
      final String addonVersion,
      final String application,
      final String applicationVersion,
      final String platform,
      final String platformVersion,
      final String locale)
  {
    this.addonName = addonName;
    this.addonVersion = addonVersion;
    this.application = application;
    this.applicationVersion = applicationVersion;
    this.platform = platform;
    this.platformVersion = platformVersion;
    this.locale = locale;
  }

  @Override
  public String toString()
  {
    final StringBuilder sb = new StringBuilder();

    sb.append("{addonName=");
    sb.append(this.addonName);
    sb.append(", addonVersion=");
    sb.append(this.addonVersion);
    sb.append(", application=");
    sb.append(this.application);
    sb.append(", applicationVersion=");
    sb.append(this.applicationVersion);
    sb.append(", platform=");
    sb.append(this.platform);
    sb.append(", platformVersion=");
    sb.append(this.platformVersion);
    sb.append(", locale=");
    sb.append(this.locale);
    sb.append('}');

    return sb.toString();
  }

  public static AppInfo create(final Context context)
  {
    return new Builder().autoFill(context).build();
  }

  public static class Builder
  {
    private static final String SBROWSER_PACKAGE_NAME = "com.sec.android.app.sbrowser";

    private String addonName = "adblockplussbrowser";
    private String addonVersion = "0";
    private String application = "sbrowser";
    private String applicationVersion = "0";
    private String platform = "android";
    private String platformVersion = Integer.toString(Build.VERSION.SDK_INT);
    private String locale = "en-US";

    public Builder autoFill(Context context)
    {
      try
      {
        this.addonVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
            .toLowerCase();
      }
      catch (Throwable t)
      {
        // ignored
      }

      try
      {
        this.applicationVersion = context.getPackageManager().getPackageInfo(SBROWSER_PACKAGE_NAME,
            0).versionName.toLowerCase();
      }
      catch (Throwable t)
      {
        // ignored
      }

      this.locale = Locale.getDefault().toString().replace('_', '-');

      return this;
    }

    public AppInfo build()
    {
      return new AppInfo(this.addonName, this.addonVersion, this.application,
          this.applicationVersion, this.platform, this.platformVersion, this.locale);
    }
  }
}
