import UIKit
import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
     }
  }
  
 

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}


//@main
//struct iosApp: App {
//    var body: some Scene {
//        WindowGroup {
//          //  ContentView()
//        }
//    }
//}
//
//struct ContentView: View {
//
//    private let signIn: SignInComponent
//
//
//    @StateValue
//    private var login: SignInComponentLogin
//
//
//     init(_ signIn: SignInComponent) {
//        self.signIn = signIn
//         _login = StateValue<SignInComponentLogin>(signIn.login)
//     }
//
//    var body: some View {
//        VStack{
//            TextEditor(text: _login.wrappedValue)
//                         .frame(maxHeight: .infinity)
//                         .padding(8)
//
//                     HStack {
//                         Text("Completed")
////                         Image(systemName: model.isDone ? "checkmark.square" : "square")
////                             .onTapGesture { self.component.onDoneChanged(isDone: !model.isDone) }
//                     }.padding(8)
//                 }
////
//    }
//}

//struct DetailsView: View {
//    private let list: ListComponent
//
//    @StateValue
//    private var model: ListComponentModel
//
//    init(_ list: ListComponent) {
//        self.list = list
//        _model = StateValue(list.model)
//    }
//
//    var body: some View {
//        List(model.items, ...) { item in
//            // Display the item
//        }
//    }
//}

//struct ComposeView: UIViewControllerRepresentable {
//    func makeUIViewController(context: Context) -> UIViewController {
//        MainKt.MainViewController()
//    }
//
//    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
//}
