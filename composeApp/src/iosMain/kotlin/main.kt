import androidx.compose.ui.window.ComposeUIViewController
import com.a9992099300.gameTextConstructor.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
