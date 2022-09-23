plugins {
    id("software.onepiece.playdate-picture-game") // version "1.0"
}

dependencies {
    playdateSdk("playdate:PlaydateSDK:1.12.3")
    kenneyGameAssets("58-bit-pack:1bitpack_kenney_1.2:1")
    // kenneyGameAssets("4-bit-input-prompts-pixel-16:1-bit-input-prompts-pixel-16:1")
    // kenneyGameAssets("39-bit-platformer-pack:1bitplatformerpack:1")
}

// tasks.installPlaydateSdk {
//     doLast { Thread.sleep(5000) }
// }
// tasks.processGameAssetPacks {
//     doLast { Thread.sleep(5000) }
// }
