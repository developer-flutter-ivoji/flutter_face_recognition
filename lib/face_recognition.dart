import 'dart:async';

import 'package:flutter/services.dart';

class FlutterTestSelfiecapture {
  static const MethodChannel _channel = const MethodChannel('face_recognition');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> detectLiveliness(
      String msgselfieCapture, String msgBlinkEye) async {
    final String filePath = await _channel.invokeMethod('detectLiveliness',
        {"msgselfieCapture": msgselfieCapture, "msgBlinkEye": msgBlinkEye});
    return filePath;
  }

  static Future<dynamic> ocrFromDocumentImage({String? imagePath}) async {
    final ocrLines = await _channel.invokeMethod('ocrFromDocImage', {
      "imagePath": imagePath
    });
    return ocrLines;
  }
}
