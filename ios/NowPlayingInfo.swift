import Foundation
import MediaPlayer

struct NowPlayingInfo: Codable {
    
    let playbackTime: Double
    let playbackDuration: Double
    
    let title: String?
    let artist: String?
    let album: String?
    
    let artwork: Data?
}

extension NowPlayingInfo {
    
    init(player: MPMusicPlayerController, item: MPMediaItem) {
        self.playbackTime = player.currentPlaybackTime
        self.playbackDuration = item.playbackDuration
        
        self.title = item.title
        self.artist = item.artist
        self.album = item.albumTitle
        
        self.artwork = item.artwork?.image(at: CGSize(width: 256, height: 256))?.pngData()?.base64EncodedString()
    }
}
