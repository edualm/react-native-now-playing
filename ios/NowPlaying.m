#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(NowPlaying, RCTEventEmitter)

RCT_EXTERN_METHOD(startObserving)
RCT_EXTERN_METHOD(stopObserving)

@end
