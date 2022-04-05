#import "FlutterFaceRecognitionPlugin.h"
#import <face_recognition/face_recognition-Swift.h>

@implementation FlutterFaceRecognitionPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterFaceRecognitionPlugin registerWithRegistrar:registrar];
}
@end
