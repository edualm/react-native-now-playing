import MediaPlayer

@objc(NowPlaying)
class NowPlaying: RCTEventEmitter {
    
    var initialized = false
    var listenerCount = 0
    
    override func startObserving() {
        listenerCount += 1
        
        initialize()
    }
    
    override func stopObserving() {
        guard listenerCount > 0 else {
            return
        }
        
        listenerCount -= 1
    }
}

extension NowPlaying {
    
    func initialize() {
        guard !initialized else { return }
        
        initialized = true
        
        let player = MPMusicPlayerController.systemMusicPlayer
        
        let notificationTypes: [Notification.Name] = [
            .MPMusicPlayerControllerNowPlayingItemDidChange,
            .MPMusicPlayerControllerPlaybackStateDidChange
        ]
        
        notificationTypes.forEach {
            NotificationCenter.default.addObserver(self,
                                                   selector: #selector(nowPlayingEventReceived(notification:)),
                                                   name: $0,
                                                   object: player)
        }
        
        player.beginGeneratingPlaybackNotifications()
    }
    
    @objc
    func nowPlayingEventReceived(notification: Notification) {
        guard listenerCount > 0 else {
            return
        }
        
        guard let player = notification.object as? MPMusicPlayerController else {
            return
        }
        
        let state: NowPlayingState
        
        if let media = player.nowPlayingItem {
            let info = NowPlayingInfo(player: player, item: media)
            state = .init(isPlaying: true, item: info)
        } else {
            state = .init(isPlaying: false, item: nil)
        }
        
        do {
            let encoder = JSONEncoder()
            let jsonData = try encoder.encode(state)
            let json = String(data: jsonData, encoding: .utf8)
            
            self.sendEvent(withName: "NOW_PLAYING", body: json)
        } catch {
            print(error)
        }
    }
}
