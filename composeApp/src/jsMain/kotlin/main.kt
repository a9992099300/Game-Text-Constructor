import com.a9992099300.gameTextConstructor.App
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("Game Text Constructor") {
            App()
        }
    }
}
