package ru.aviasales.template.utils;

public class PrivacyPolicyUrl {
  private static final String DEFAULT_PRIVACY_POLICY_URL = "https://ios.jetradar.com/privacy_policy_template/index.html";

  private static String url = "";

  public static void setUrl(String newUrl){
    url = newUrl;
  }

  public static String getUrl() {
    if (url.isEmpty()) {
      return DEFAULT_PRIVACY_POLICY_URL;
    }

    if (url.startsWith("http://") || url.startsWith("https://")) {
      return url;
    }

    return "http://" + url;
  }
}
